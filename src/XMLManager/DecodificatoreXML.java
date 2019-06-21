package XMLManager;

import java.util.Map.Entry;

public class DecodificatoreXML {
	private LetturaScrittura inputFile = new LetturaScrittura();

	public boolean leggiFile(String pathInputFile) {
		if (!inputFile.setPathInputFile(pathInputFile))
			return false;
		if (!inputFile.leggiFile())
			return false;
		return true;
	}

	public StrutturaDati getFile() {
		try {
			return inputFile.getFile();
		} catch (Exception e) {
			StrutturaDati vuoto = new StrutturaDati();
			return vuoto;
		}
	}

	public boolean scriviFile(StrutturaDati input, String nomeFile, String formato, String versione) {
		if (inputFile.scriviFile(input, nomeFile, formato, versione))
			return true;
		return false;
	}
	
	public boolean confronto(StrutturaDati in1, StrutturaDati in2) {
		if(!in1.getNome().equals(in2.getNome())) {
			return false;
		}
		if(in1.getTag().size() != in2.getTag().size()) {
			return false;
		}
		for (Entry<String, String> tag : in1.getTag().entrySet()) {
			try {
				if(!in1.getTag(tag.getKey()).equals(in2.getTag(tag.getKey()))){
					return false;
				}
			}catch(Exception e){
				return false;
			}
		}
		if(in1.getAttributi().size() != in2.getAttributi().size()) {
			return false;
		}	
		for (int i = 0; i<in1.getAttributi().size(); i++) {
			if(!confronto(in1.getAttributi().get(i),in2.getAttributi().get(i))) {
				return false;
			}
		}
		return true;
	}
}
