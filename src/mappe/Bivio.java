package mappe;

public class Bivio {
	/**
	 * descrizion del bivio
	 */
	private String introduzione;
	/**
	 * id della cella al quale si collega
	 */
	private int idColl;
	/**
	 * flag che indica se il bivio causa un effetto
	 */
	private boolean hasAnEffect;
	/**
	 * effetto del bivio
	 */
	private int effetto;
	/**
	 * se il bivio ha bisogno di un oggetto
	 */
	private boolean hasObject;

	/**
	 * costruttore per la classe bivio dove inizializzo tutte le variabili
	 * 
	 * @param _intro
	 * @param _idColl
	 * @param _hasAnEffect
	 * @param _effetto
	 */
	public Bivio(String _intro, int _idColl, boolean _hasAnEffect, int _effetto, boolean _hasObj) {
		introduzione = _intro;
		idColl = _idColl;
		hasAnEffect = _hasAnEffect;
		effetto = _effetto;
		hasObject = _hasObj;
	}

	/**
	 * ritorna l'effetto
	 * 
	 * @return
	 */
	public int getEffetto() {
		return effetto;
	}

	/**
	 * ritorna l'id del collegamento
	 * 
	 * @return
	 */
	public int getIdColl() {
		return idColl;
	}

	/**
	 * ritorna l'introduzione
	 * 
	 * @return
	 */
	public String getIntroduzione() {
		return introduzione;
	}

	/**
	 * ritorna il flag hasAnEffect
	 * 
	 * @return
	 */
	public boolean hasAnEffect() {
		return hasAnEffect;
	}

	/**
	 * ritona il flag hasObj
	 * 
	 * @return
	 */
	public boolean hasObj() {
		return hasObject;
	}
}
