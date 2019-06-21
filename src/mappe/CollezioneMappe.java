package mappe;

import java.util.ArrayList;

public class CollezioneMappe {
	/**
	 * lista con i percorsi dei file sorgenti delle mappe
	 */
	private ArrayList<String> percorsi = new ArrayList<>();
	/**
	 * lista con tutte le mappe utilizzabili
	 */
	private ArrayList<Mappa> mappe = new ArrayList<>();
	/**
	 * mappa in uso al momento
	 */
	private Mappa mappaInUso;

	public CollezioneMappe() {
		// acquisisci tutte le mappe
	}

	/**
	 * metodo toString per facilitare la selezione delle mappe
	 */
	@Override
	public String toString() {
		return null;
	}

	/**
	 * metodo per la selezione della mappa
	 * 
	 * @param _index
	 * @return
	 */
	public boolean selezioneMappa(int _index) {

	}

	/**
	 * ritorna la mappa che si sta usando al momento
	 * 
	 * @return
	 */
	public Mappa getMappaInUso() {
		return mappaInUso;
	}

	public void aggiungiMappa() {

	}

	public void rimuoviMappa() {

	}

}
