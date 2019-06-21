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
	 * valore integer che indica la tipologia della casella, la codifica è
	 * specificata nella classe mappa
	 */
	public int tipo;
	/**
	 * ArrayList Contenente le celle collegate
	 */
	private ArrayList<Cella> bivio = new ArrayList<>();
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
	 * @param _id   (id da assegnare)
	 * @param _tipo (tipologia della cella)
	 * @param _desc (descrizione della cella)
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
	 * ritona le casella collegate alla casella attuale
	 * 
	 * @return (caselle adiacenti)
	 */
	public ArrayList<Cella> getBivio() {
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
	 * metodo per l'aggiunta di una cella al bivio della casella attuale (ricorda il
	 * .clone)
	 * 
	 * @param _newCell (clone della cella da aggiungere)
	 */
	public void addBivio(Cella _newCell) {
		bivio.add(_newCell);
	}
}
