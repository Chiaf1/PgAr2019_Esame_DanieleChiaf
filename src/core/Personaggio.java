package core;

public class Personaggio {
	/*
	 * ------------ATTRIBUTI------------
	 */
	
	/**
	 * quantit� di vita attuale del personaggio
	 */
	private int vita;
	/**
	 * vita di partenza standard per tutti i personaggi
	 */
	private static final int VITA_PARTENZA = 100;
	/**
	 * id della casella dove attualmente si trova il personaggio
	 */
	private int idCasellaAttuale;
	/**
	 * id della casella di partenza
	 */
	private static final int ID_CASELLA_PARTENZA = 0;
	/**
	 * nome del personaggio
	 */
	private String nome;

	/*
	 * ----------METODI----------
	 */

	/**
	 * costrutture della classe che inizializza tutte le statistiche ed assegna il
	 * nome del personaggio
	 * 
	 * @param _nome (nome che si vuole dare al personaggio)
	 */
	public Personaggio(String _nome) {
		nome = _nome;
		vita = VITA_PARTENZA;
		idCasellaAttuale = ID_CASELLA_PARTENZA;
	}

	/**
	 * ritorna il valore della vita attuale del personaggio
	 * 
	 * @return (valore attuale della vita del personaggio)
	 */
	public int getVita() {
		return vita;
	}

	/**
	 * ritorna l'indice della casella dove si trova il personaggio al momento
	 * 
	 * @return (l'indice della casella dove � posizionato il personaggio)
	 */
	public int getIdCasellaAttuale() {
		return idCasellaAttuale;
	}

	/**
	 * metodo per la modifica del'indice della casella dove attualmente �
	 * posizionato il personaggio
	 * 
	 * @param _idCasellaAttuale (indice nuova casella)
	 */
	public void setIdCasellaAttuale(int _idCasellaAttuale) {
		idCasellaAttuale = _idCasellaAttuale;
	}

	/**
	 * ritorna il nome del personaggio
	 * 
	 * @return (il nome del personaggio)
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * metodo per modificare la vita del personaggio va indicato la quantita da
	 * modificare e se � un incremento di vita o no
	 * 
	 * @param _quantity (quantita' di vita da modificare)
	 * @param _isInc    (true se la vita va incrementata, false se va decrementata)
	 * @return (true se � stato possibile modificare la vita, false se la vita ha
	 *         raggiunto lo zero)
	 */
	public boolean modVita(int _quantity, boolean _isInc) {
		if (_isInc) {
			vita = vita + _quantity;
			return true;
		}
		vita = vita - _quantity;
		if (vita <= 0) {
			return false;
		}
		return true;
	}
}
