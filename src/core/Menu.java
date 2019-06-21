package core;

import java.util.InputMismatchException;
import java.util.Scanner;

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
		String msg = "Benvenuti ";

		System.out.println(msg);
	}

	/**
	 * metodo per il setUp della partita, selezione mappa, setUp personaggio
	 */
	private void setUp() {
		scrivi("\n-------------------------------SetUp Partita-------------------------------");
		String nomeP = letturaString("\nPer prima cosa ti chiedo di inserire il nome del tuo personaggio");
		player = new Personaggio(nomeP);

		scrivi("\nOttimo " + nomeP
				+ " ora devo chiederti cquale mappa vorresti giocare?,\nscegli tra le alternative seguenti:\n");
		mappe.toString();

		int indexMappa = letturaInt("Inserisci qui il numero della mappa: ");
		while (!mappe.selezioneMappa(indexMappa)) {
			scrivi("\nOps,\nsembrerebbe esserci un porblema con la selezione sei sicuro di aver inserito un numero presente nell'elenco?\nPerfavore reinserisci\n");
		}

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
			
			//core
			while (true) {
				scrivi("\n\n\n");
				Cella cellaAtt = mappe.getCellaById(player.getIdCasellaAttuale());
				if (cellaAtt.getTipo() == mappe.getMappaInUso().TAG_C_FINALE) {
					break;
				}
				scrivi("La vita attuale di " + player.getNome() + " e' " + player.getVita() + ";");
				scrivi(cellaAtt.getDescrizione());
				scrivi("\nLe opzioni sono: ");
				for (int i = 0; i < cellaAtt.getBivio().size(); i++) {
					scrivi(i + " " + cellaAtt.getBivio().get(i).getIntroduzione() + "\n");
				}
				int scelta = letturaInt("Cosa scegli: ");
				while (scelta < cellaAtt.getBivio().size() && scelta >= 0) {
					scrivi("\nMi spiace ma il numero che hai inserito non si riferisce a nessun opzione possibile");
					scelta = letturaInt("\nScegli ancora: ");
				}
				Bivio bivSelected = cellaAtt.getBivio().get(scelta);
				if (bivSelected.hasAnEffect()) {
					int effetto = bivSelected.getEffetto();
					scrivi("\nLa cella in cui ti trovavi era una cella effetto quindi: ");
					if (effetto < 0) {
						scrivi("perdi " + effetto + "punti vita");
					}else {
						scrivi("guadagni  " + effetto + "punti vita");
					}
					player.modVita(bivSelected.getEffetto());
				}
			}
		} while (finale(isLoser));

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

		scrivi("\nLa partita e' ora finita, ");
		if (_isLoser) {
			scrivi(player.getNome()
					+ " purtroppo sei morto prima di arrivare alla fine della mappa, e non poi vedere il bellissimo banner della vittora,\ncomunque non abbatterti perche' puoi ritentarela e magari stavolta riuscirai ad arrivare alla fine.");

		} else {
			scrivi("Complimenti " + player.getNome() + " hai vinto!!!! " + BANNER_VITTORIA
					+ "\nse ri sei divertito puoi ricominciare la partita e magari scegliere una mappa più difficile, oppure puoi anche divertiriti a creare la tua mappa personalizzata e caricarla.");
		}

		int restart = letturaInt("\nAllora? cosa hai deciso?\n_0 chiudi il programma;\n_1 tenti una nuova partita");

		while (restart <= 1 && restart >= 0) {
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

}
