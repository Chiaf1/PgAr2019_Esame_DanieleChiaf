package mappe;

import java.util.ArrayList;
import java.util.Collections;

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

	/**
	 * 
	 */
	public CollezioneMappe() {
		// acquisisci tutte le mappe
		percorsi.add("mappe/1) base.xml");
		percorsi.add("mappe/2) D1.xml");
		percorsi.add("mappe/3) D3.xml");
		percorsi.add("mappe/4) D1 + D3.xml");

		for (String path : percorsi) {
			Mappa newMappa = new Mappa(path);
			mappe.add(newMappa);
		}
		Collections.sort(mappe);
	}

	/**
	 * metodo toString per facilitare la selezione delle mappe
	 */
	@Override
	public String toString() {
		String msg = new String("Le mappe presenti al momento sono le seguenti: ");
		for (int i = 0; i < mappe.size(); i++) {
			msg = msg + ("\n_" + i + " " + mappe.get(i).toString());
		}
		return msg;
	}

	/**
	 * metodo per la selezione della mappa
	 * 
	 * @param _index
	 * @return
	 */
	public boolean selezioneMappa(int _index) {
		if (!(_index >= 0 && _index < mappe.size())) {
			return false;
		}
		mappaInUso = mappe.get(_index);
		return true;
	}

	/**
	 * ritorna la mappa che si sta usando al momento
	 * 
	 * @return
	 */
	public Mappa getMappaInUso() {
		return mappaInUso;
	}

	/**
	 * metodo per l'aggiunta di una mappa
	 * 
	 * @param _path
	 */
	public void aggiungiMappa(Mappa _map) {
		mappe.add(_map);
		percorsi.add(_map.getFilePath());	
		Collections.sort(mappe);
	}

	/**
	 * metodo che rimuove la mappa all'indice _index
	 * 
	 * @param _index
	 */
	public void rimuoviMappa(int _index) {
		percorsi.remove(_index);
		mappe.remove(_index);
		Collections.sort(mappe);
	}

	/**
	 * ritonra la cella con l'id inserito
	 * 
	 * @param _id
	 * @return
	 */
	public Cella getCellaById(int _id) {
		for (int i = 0; i<mappaInUso.getCelle().size();i++) {
			if (mappaInUso.getCelle().get(i).getId() == _id) {
				return mappaInUso.getCelle().get(i);
			}
		}
		return null;
	}
}
