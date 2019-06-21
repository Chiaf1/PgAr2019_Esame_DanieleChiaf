package XMLManager;

import java.util.*;

public class StrutturaDati {
	private String nome;
	private HashMap<String, String> tag = new HashMap<String, String>();
	private ArrayList<String> indiciKey = new ArrayList<String>();
	private ArrayList<StrutturaDati> attributi= new ArrayList<StrutturaDati>();
	private boolean isText = false;

	public StrutturaDati(String nome) {
		this.nome = nome;
	}

	public StrutturaDati() {
		nome="vuoto";
	}
	
	public String getNome() {
		return nome;
	}

	public boolean isText() {
		return isText;
	}

	public HashMap<String, String> getTag(){
		return tag;
	}
	public String getTag(String key) {
		return tag.get(key);
	}

	public String getTag(int i) {
		return tag.get(this.indiciKey.get(i));
	}

	public ArrayList<StrutturaDati> getAttributi() {
		return attributi;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void addTag(String tag, String valore) {
		indiciKey.add(tag);
		this.tag.put(tag, valore);
	}

	public boolean setTag(String tag, String valore) {
		try {
			this.tag.replace(tag, valore);
		} catch (Exception i) {
			return false;
		}
		return true;
	}

	public void removeTag(String tag) {
		indiciKey.remove(tag);
		this.tag.remove(tag);
	}

	public void removeTag(int i) {
		this.tag.remove(this.indiciKey.get(i));
		indiciKey.remove(i);
	}

	public void addAttributi(ArrayList<StrutturaDati> attributi) {
		this.attributi = attributi;
	}

	public void addAttributo(StrutturaDati attributo) {
		this.attributi.add(attributo);
	}

	public void setIsText(boolean isText) {
		this.isText = isText;
	}

}
