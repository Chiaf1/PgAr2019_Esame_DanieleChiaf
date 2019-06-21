package core;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import mappe.*;

public class Menu {
	/*
	 * ---------ATTRIBUTI---------
	 */

	private static final String BANNER_VITTORIA = "";

	/**
	 * collezione di mappe
	 */
	public CollezioneMappe mappe;
	/**
	 * player del gioco
	 */
	public Personaggio player;
	/**
	 * scanner per la lettura da console
	 */
	public static Scanner lettore = new Scanner(System.in);

	/*
	 * ---------METODI---------
	 */

	/*
	 * ---------SCRITTURA/GESTIONE---------
	 */

	/**
	 * costrutture della classe che carica tutti i valori iniziali di default
	 */
	public Menu() {
		mappe = new CollezioneMappe();
	}

	/**
	 * metodo per la gestione dell'introduzione alla partita
	 */
	private void intro() {
		// descrizione gioco balle varie
		String msg = "Benvenuti nel primo GDR di casa chiaf," + "\nspero che l'esperienza vi piaccia..."
				+ "\n buon divertimento";
		System.out.println(msg);
	}

	/**
	 * metodo per il setUp della partita, selezione mappa, setUp personaggio
	 */
	private void setUp() {
		scrivi("\n-------------------------------SetUp Partita-------------------------------");
		String nomeP = letturaString("\nPer prima cosa ti chiedo di inserire il nome del tuo personaggio");
		player = new Personaggio(nomeP);

		scrivi("Ottimo " + nomeP + ", ");
		mapMenu();

		scrivi("\nOttimo il setUp della partita e' stato completato,\nora è il momento di inziare la partita");

		scrivi("\n-------------------------------Inizio Partita-------------------------------");
	}

	/**
	 * metodo per la gestione della partita nel suo complesso
	 */
	public void avventura() {
		boolean isLoser = false;
		do {
			intro();

			setUp();
			aspetta();

			// core
			while (true) {
				scrivi("\n\n\n");
				Cella cellaAtt = mappe.getCellaById(player.getIdCasellaAttuale());
				if (cellaAtt.getTipo() == mappe.getMappaInUso().TAG_C_FINALE) {
					break;
				}
				int men = letturaInt("vuoi visualizzare l'inventario? se si inserisci 1");
				if (men == 1) {
					if (player.getOggetti().size()>0) {
						scrivi("Ecco i tuoi oggetti");
						for (String obj: player.getOggetti()) {
							scrivi("_" + obj);
						}
					}else {
						scrivi("mi spiace non hai ancora oggetti");
					}
				}
				// msg vita att
				scrivi("La vita attuale di " + player.getNome() + " e' " + player.getVita() + ";");
				aspetta();
				// descizione cella
				scrivi(cellaAtt.getDescrizione());
				aspetta();
				// ozioni:
				scrivi("\nLe opzioni sono: ");
				for (int i = 0; i < cellaAtt.getBivio().size(); i++) {
					scrivi("_" + i + " " + cellaAtt.getBivio().get(i).getIntroduzione() + "\n");
				}
				int scelta = letturaInt("Cosa scegli: ");
				while (!(scelta < cellaAtt.getBivio().size() && scelta >= 0)) {
					scrivi("\nMi spiace ma il numero che hai inserito non si riferisce a nessun opzione possibile");
					scelta = letturaInt("\nScegli ancora: ");
				}
				aspetta();
				// assegnazione strada scelta
				Bivio bivSelected = cellaAtt.getBivio().get(scelta);
				if (bivSelected.hasAnEffect()) {// controllo se la cella ha un effetto ed eventuale branch
					int effetto = bivSelected.getEffetto();
					scrivi("\nLa cella in cui ti trovavi era una cella effetto quindi: ");
					if (effetto < 0) {
						scrivi("perdi " + effetto + " punti vita");
					} else {
						scrivi("guadagni  " + effetto + " punti vita");
					}
					player.modVita(bivSelected.getEffetto());
				} else {
					scrivi("La cella non aveva effetti avanzi senza problemi");
				}
				// set delle prossima cella
				player.setIdCasellaAttuale(bivSelected.getIdColl());
				aspetta();
				cellaAtt = mappe.getCellaById(player.getIdCasellaAttuale());
				// nel caso in cui la cella sia gate o loot
				if (cellaAtt.getTipo() == mappe.getMappaInUso().TAG_C_GATE) {// se la cella è gate
					if (player.getOggetti().size() > 0) {
						for (String obj : player.getOggetti()) {
							if (obj.equals(cellaAtt.getOggetto())) {
								scrivi("hey, hai trovato la cella gate che combacia con il tuo oggetto " + obj
										+ ",\navanzi su un percorso alternativo");
								for (Bivio biv : cellaAtt.getBivio()) {
									if (biv.hasObj()) {
										player.setIdCasellaAttuale(biv.getIdColl());
									}
								}
							} else {
								for (Bivio biv : cellaAtt.getBivio()) {
									if (!biv.hasObj()) {
										player.setIdCasellaAttuale(biv.getIdColl());
									}
								}
							}
						}
					}
				}
				if (cellaAtt.getTipo() == mappe.getMappaInUso().TAG_C_LOOT) {// se la cella è loot
					scrivi("Che fortuna hai trovato una casella loot, ");
					if (cellaAtt.hasObj()) {// se possiede ancora l'oggetto lo prende
						scrivi("hai ottenuto un/a " + cellaAtt.getOggetto());
						player.addOggetto(cellaAtt.getAndRremove());
					} else {//altrimenti no
						scrivi("purtroppo o per fortuna l'hai già loottata quindi non prendi nulla");
						player.setIdCasellaAttuale(cellaAtt.getBivio().get(0).getIdColl());
					}
				}
			}
		} while (finale(isLoser));

	}

	/**
	 * metodo per gestire tutta la parte riguardante le mappe
	 */
	public void mapMenu() {
		boolean isTrovata = false;
		while (!isTrovata) {
			isTrovata = true;
			aspetta();
			scrivi("ora devo chiederti quale mappa vorresti giocare?\nscegli tra le alternative seguenti:\n");
			scrivi(mappe.toString());
			int scelta = letturaInt(
					"\nSe la mappa che vuoi giocare non è presente nell'elnco e la vuoi aggiungere inserisci \"-1\""
							+ "\nse invece vuoi eliminarne una inserisci \"-2\""
							+ "\nse invece vuoi giocare una mappa già presente premi \"0\"" + "\ninserisci qui: ");
			if (scelta < 0) {
				if (scelta > -2) {
					String path = letturaString("Ok, apprezzo l'iniziativa, prego inserisci il percorso del file: ");
					while (!(testMappa(path))) {
						scrivi("C'è stato un problema con l'aggiunta della tua mappa,");
						path = letturaString("prego reinserisci il percorso");
					}
					Mappa newMap = new Mappa(path);
					mappe.aggiungiMappa(newMap);
					isTrovata = false;
				} else {
					scrivi(mappe.toString());
					int daElim = letturaInt("Per selezionare quale mappa eliminare inserisci il suo numero");
					mappe.rimuoviMappa(daElim);
					isTrovata = false;
				}
			}
			int indexMappa = letturaInt("Inserisci qui il numero della mappa: ");
			while (!mappe.selezioneMappa(indexMappa)) {
				scrivi("Ops,\nsembrerebbe esserci un porblema con la selezione sei sicuro di aver inserito un numero presente nell'elenco?\nPerfavore reinserisci\n");
				indexMappa = letturaInt("Prego reinserisci: ");
			}
		}
	}

	/**
	 * metodo per testare il percorso del file
	 * 
	 * @param _path
	 * @return
	 */
	private boolean testMappa(String _path) {
		try {
			Mappa newMap = new Mappa(_path);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * metodo per la gestione del fine partita, riceve in ingresso un boolean che
	 * indica se il player ha perso oppure no
	 * 
	 * @param _isLoser (flag partita persa)
	 * @return (true ripeti la partita, false concludi)
	 */
	private boolean finale(boolean _isLoser) {
		scrivi("\n-------------------------------Fine Partita-------------------------------");
		aspetta();

		scrivi("\nLa partita e' ora finita, ");
		aspetta();
		if (_isLoser) {
			scrivi(player.getNome()
					+ " purtroppo sei morto prima di arrivare alla fine della mappa, e non poi vedere il bellissimo banner della vittora,\ncomunque non abbatterti perche' puoi ritentarela e magari stavolta riuscirai ad arrivare alla fine.");

		} else {
			scrivi("Complimenti " + player.getNome() + " hai vinto!!!! " + BANNER_VITTORIA
					+ "\nse ri sei divertito puoi ricominciare la partita e magari scegliere una mappa più difficile, oppure puoi anche divertiriti a creare la tua mappa personalizzata e caricarla.");
		}

		aspetta();
		int restart = letturaInt("\nAllora? cosa hai deciso?\n_0 chiudi il programma;\n_1 tenti una nuova partita");

		while (!(restart <= 1 && restart >= 0)) {
			scrivi("\nHey, sei arrivato fino a qui e sbagli ad inserire i dati nell'ultimo menu??");
			restart = letturaInt("\nDai reinserisci la tua risposta: ");
		}

		if (restart == 1) {
			return true;
		}

		return false;
	}

	/**
	 * metodo per semplificare la scrittura in console
	 * 
	 * @param _msg (messaggio da scrivere)
	 */
	private void scrivi(String _msg) {
		System.out.println(_msg);
	}

	/*
	 * ---------LETTURA---------
	 */

	/**
	 * metodo per la lettura di una stringa dalla console
	 * 
	 * @param msg (è il messaggio che viene scritto prima della lettura dei dati)
	 * @return la stringa letta dalla console
	 */
	public static String letturaString(String msg) {
		System.out.print(msg);
		return lettore.next();
	}

	/**
	 * metodo per la lettura di un intero da console, se il dato immesso non è nel
	 * formato corretto comunica l'errore e ripete l'operazione
	 * 
	 * @param msg (è il messaggio che viene scritto prima della lettura dei dati)
	 * @return l'intero che viene letto
	 */
	public static int letturaInt(String msg) {
		boolean finito = false;
		int valoreLetto = 0;
		do {
			System.out.print(msg);
			try {
				valoreLetto = lettore.nextInt();
				finito = true;
			} catch (InputMismatchException e) {
				System.out.println("il valore inserito non è nel formato corretto");
				lettore.next();
			}
		} while (!finito);
		return valoreLetto;
	}

	/**
	 * metodo privato che mette in pausa l'esecuzione del programma per 1 secondo
	 */
	private void aspetta() {
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (Exception e) {

		}
	}

}
