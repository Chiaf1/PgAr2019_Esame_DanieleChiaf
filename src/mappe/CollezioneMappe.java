package mappe;

import java.util.ArrayList;
import java.util.Arrays;

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
		percorsi.add("/mappe/1) base.xml");
		

		for (String path : percorsi) {
			Mappa newMappa = new Mappa(path);
			mappe.add(newMappa);
		}
		//sort();
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
		if (!(_index>=0 && _index < mappe.size())) {
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

	public void aggiungiMappa() {

	}

	public void rimuoviMappa() {

	}

	/**
	 * ritonra la cella con l'id inserito
	 * 
	 * @param _id
	 * @return
	 */
	public Cella getCellaById(int _id) {
		for (Cella cell: mappaInUso.getCelle()) {
			if (cell.getId()==_id) {
				return cell;
			}
		}
		return null;
	}

	/**
	 * metodo che riordina la lista di mappe dall più piccola alla più grande
	 */
	private void sort() {

	}

}
