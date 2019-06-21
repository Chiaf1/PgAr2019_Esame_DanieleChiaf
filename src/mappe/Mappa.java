package mappe;

import java.util.ArrayList;

import XMLManager.*;

public class Mappa implements Comparable<Mappa> {
	/*
	 * ----COSTANTI----
	 */
	private static final String TAG_MAP_CELL_DESCRIPTION = "description";

	private static final String TAG_MAP_CELL_CONNECTION_EFFECT = "lifepoints";

	private static final String TAG_CELL_TYPE_END = "end";

	private static final String TAG_CELL_TYPE_EFFECT = "effect";

	private static final String TAG_CELL_TYPE_OPTION = "breanch";

	private static final String TAG_CELL_TYPE_EMPTY = "empty";

	private static final String TAG_MAP_CELL_OPTION_DESTINATION = "destination";

	private static final String TAG_MAP_CELL_OPTION = "option";

	private static final String TAG_MAP_CELL_TYPE = "type";

	private static final String TAG_MAP_CELL_ID = "id";

	private static final String TAG_MAP_CELL = "cell";

	private static final String TAG_MAP_MAP = "map";

	private static final String TAG_MAP_TITLE = "title";

	/*
	 * ------------ATTRIBUTI------------
	 */

	/**
	 * collezione delle celle della mappa
	 */
	private ArrayList<Cella> celle = new ArrayList<>();
	/**
	 * indicatore del tipo della cella vuota
	 */
	public static final int TAG_C_VUOTA = 1;
	/**
	 * indicatore del tipo della cella scelta
	 */
	public static final int TAG_C_SCELTA = 2;
	/**
	 * indicatore del tipo della cella effetto
	 */
	public static final int TAG_C_EFFETTO = 3;
	/**
	 * indicatore del tipo della cella finale
	 */
	public static final int TAG_C_FINALE = 4;
	/**
	 * stringa contenente l'indirizzo del file sorgente della mappa
	 */
	private String filePath;
	/**
	 * nome della mappa
	 */
	private String nomeMappa;
	/**
	 * istanza della classe DecodificatoreXML che serve a leggere i file XML
	 */
	private DecodificatoreXML decoder = new DecodificatoreXML();

	/*
	 * ------------METODI------------
	 */

	/**
	 * costruttore della classe mappa che inizializza il percorso del file sorgente
	 * 
	 * @param _filePath (indirizzo sorgente mappa)
	 */
	public Mappa(String _filePath) {
		filePath = _filePath;

		generaMappa();

		int stop = 0;
	}

	/**
	 * ritorna il nome della mappa
	 * 
	 * @return (nome della mappa)
	 */
	public String getNomeMappa() {
		return nomeMappa;
	}

	/**
	 * ritorna il percorso del file sorgente della mappa
	 * 
	 * @return (percorso sorgente della mappa)
	 */
	public String getFilePath() {
		return filePath;
	}

	/**
	 * ritorna le celle della mappa
	 * 
	 * @return
	 */
	public ArrayList<Cella> getCelle() {
		return celle;
	}

	/**
	 * metodo per la generazione della mappa
	 */
	private void generaMappa() {
		// lettura e acquisizione del file sorgente
		decoder.leggiFile(filePath);
		StrutturaDati file = decoder.getFile();

		// creazione della mappa seguendo il sorgente
		for (StrutturaDati map : file.getAttributi()) {
			if (map.getNome().equals(TAG_MAP_MAP)) {
				nomeMappa = map.getTag(TAG_MAP_TITLE);
			}
		}

		// creo tutte le celle
		for (StrutturaDati map : file.getAttributi()) {
			if (map.getNome().equals(TAG_MAP_MAP)) {
				for (StrutturaDati cell : map.getAttributi()) {
					if (cell.getNome().equals(TAG_MAP_CELL)) {
						int id = Integer.valueOf(cell.getTag(TAG_MAP_CELL_ID));
						int tipo = getTipo(cell.getTag(TAG_MAP_CELL_TYPE));
						String desc = getDescrizione(cell);
						Cella newCella = new Cella(id, tipo, desc);
						celle.add(newCella);
					}
				}
			}
		}

		for (StrutturaDati map : file.getAttributi()) {
			// attributi di rpg
			if (map.getNome().equals(TAG_MAP_MAP)) {
				for (StrutturaDati cell : map.getAttributi()) {
					// attributi di map
					if (cell.getNome().equals(TAG_MAP_CELL)) {
						// acquisizione cella attuale
						int idCellaAtt = Integer.valueOf(cell.getTag(TAG_MAP_CELL_ID));
						Cella cellaAtt = cercaCella(idCellaAtt);
						for (StrutturaDati bivio : cell.getAttributi()) {
							// attributi della cella
							if (bivio.getNome().equals(TAG_MAP_CELL_OPTION)) {// se l'attributo e' un opzione
								int idBivio = Integer.valueOf(bivio.getTag(TAG_MAP_CELL_OPTION_DESTINATION));
								String intro = getIntroduzione(bivio);
								// set effetto se necessario ovvero la cella è una cella effetto
								if (cellaAtt.getTipo() == TAG_C_EFFETTO) {
									int effetto = getEffetto(bivio);
									Bivio newBivio = new Bivio(intro, idBivio, true, effetto);
									cellaAtt.addBivio(newBivio);
								} else {
									Bivio newBivio = new Bivio(intro, idBivio, false, 0);
									cellaAtt.addBivio(newBivio);
								}

							}
						}
					}
				}
			}
		}
	}

	/**
	 * metodo per la decodifica del tipo della cella
	 * 
	 * @param _type (stringa identificante la tipologia della classe)
	 * @return (il tipo della cella secondo la codifica determinata dalle costanti)
	 */
	private int getTipo(String _type) {
		if (_type.equals(TAG_CELL_TYPE_EMPTY)) {
			return TAG_C_VUOTA;
		}
		if (_type.equals(TAG_CELL_TYPE_OPTION)) {
			return TAG_C_SCELTA;
		}
		if (_type.equals(TAG_CELL_TYPE_EFFECT)) {
			return TAG_C_EFFETTO;
		}
		if (_type.equals(TAG_CELL_TYPE_END)) {
			return TAG_C_FINALE;
		}
		return TAG_C_VUOTA;
	}

	/**
	 * ritorna l'effetto del bivio sotto esame
	 * 
	 * @param _bivio
	 * @return
	 */
	private int getEffetto(StrutturaDati _cell) {
		return Integer.valueOf(_cell.getTag(TAG_MAP_CELL_CONNECTION_EFFECT));
	}

	/**
	 * metodo per ricavare la descrizione di una cella dal formato StrutturaDati
	 * 
	 * @param _cell (cella dalla quale ricavarla)
	 * @return (la descrizione)
	 */
	private String getDescrizione(StrutturaDati _cell) {
		for (StrutturaDati cell : _cell.getAttributi()) {
			if (cell.getNome().equals(TAG_MAP_CELL_DESCRIPTION)) {
				for (StrutturaDati text : cell.getAttributi()) {
					if (text.isText()) {
						return text.getNome().trim();
					}
				}
			}
		}
		return null;
	}

	/**
	 * metodo per ricavare la descrizione introduttiva di una cella dal formato
	 * StrutturaDati
	 * 
	 * @param _cell
	 * @return
	 */
	private String getIntroduzione(StrutturaDati _cell) {
		for (StrutturaDati testo : _cell.getAttributi()) {
			if (testo.isText()) {
				return testo.getNome().trim();
			}
		}
		return null;
	}

	/**
	 * motodo per la ricerca della cella tramite id
	 * 
	 * @param _idToFind (id della cella da cercare)
	 * @return (cella trovata)
	 */
	public Cella cercaCella(int _idToFind) {
		for (Cella cell : celle) {
			if (cell.getId() == _idToFind) {
				return cell;
			}
		}
		return null;
	}

	/**
	 * metodo per la presentazione della mappa
	 */
	@Override
	public String toString() {
		String msg = (nomeMappa + " - " + filePath + " ;\n");
		return msg;
	}

	/**
	 * ritorna un valore minore di zero se il numero di caselle della classe è più
	 * basso di quella di o, un valore positivo se la classe un numero più alto di
	 * celle
	 * 
	 * @param o
	 * @return
	 */
	@Override
	public int compareTo(Mappa o) {
		if (celle.size() < o.getCelle().size()) {
			return -1;
		}
		if (celle.size() > o.getCelle().size()) {
			return 1;
		}
		return 0;
	}
}
