package mappe;

import java.util.ArrayList;

public class Cella {
	/*
	 * ------------ATTRIBUTI------------
	 */

	/**
	 * id univoco della cella
	 */
	private int id;
	/**
	 * valore integer che indica la tipologia della casella, la codifica �
	 * specificata nella classe mappa
	 */
	public int tipo;
	/**
	 * ArrayList Contenente l'id delle celle collegate
	 */
	private ArrayList<Bivio> bivio = new ArrayList<>();
	/**
	 * descrizione/testo appartenente alla casella
	 */
	private String descrizione;

	/*
	 * ------------METODI------------
	 */

	/**
	 * costrutture della classe cella che inizializza l'id, il tipo e la descrizione
	 * 
	 * @param _id     (id da assegnare)
	 * @param _tipo   (tipologia della cella)
	 * @param _desc   (descrizione della cella)
	 * @param _azione (testo informativo sulla stanza)
	 */
	public Cella(int _id, int _tipo, String _desc) {
		id = _id;
		tipo = _tipo;
		descrizione = _desc;
	}

	/**
	 * ritorna l'id della casella
	 * 
	 * @return (id della casella)
	 */
	public int getId() {
		return id;
	}

	/**
	 * ritorna il tipo della casella
	 * 
	 * @return (tipo della casella)
	 */
	public int getTipo() {
		return tipo;
	}

	/**
	 * ritona i collegamenti della casella
	 * 
	 * @return (caselle adiacenti)
	 */
	public ArrayList<Bivio> getBivio() {
		return bivio;
	}

	/**
	 * ritorna la descrizione della casella
	 * 
	 * @return (descrizione della casella)
	 */
	public String getDescrizione() {
		return descrizione;
	}

	/**
	 * metodo per l'aggiunta di una cella al bivio della casella attuale
	 * 
	 * @param _newCell (id della cella adiacente da aggiungere)
	 */
	public void addBivio(Bivio _newCell) {
		bivio.add(_newCell);
	}
}
