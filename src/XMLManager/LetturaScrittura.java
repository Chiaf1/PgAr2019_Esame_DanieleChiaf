package XMLManager;

import java.util.Map.Entry;

import javax.xml.stream.*;

import java.io.*;

class LetturaScrittura {
	private XMLInputFactory xmlif = null;
	private XMLStreamReader xmlr = null;
	private XMLOutputFactory xmlof = null;
	private XMLStreamWriter xmlw = null;
	StrutturaDati file;
	StrutturaDati vuoto = new StrutturaDati();

	protected boolean leggiFile() {
		try {
			while (xmlr.hasNext()) {
				switch (xmlr.getEventType()) {
				case XMLStreamConstants.START_DOCUMENT:
					break;
				case XMLStreamConstants.START_ELEMENT:
					file = letturaElemento();
				}
				xmlr.next();
			}
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

	private StrutturaDati letturaElemento() {
		StrutturaDati newStruttura = null;
		boolean isPrimo = true;
		try {
			while (xmlr.hasNext()) {
				switch (xmlr.getEventType()) {
				case XMLStreamConstants.START_DOCUMENT:
					return vuoto;
				case XMLStreamConstants.START_ELEMENT:
					if (isPrimo) {
						newStruttura = new StrutturaDati(xmlr.getLocalName());
						for (int i = 0; i < xmlr.getAttributeCount(); i++) {
							newStruttura.addTag(xmlr.getAttributeLocalName(i), xmlr.getAttributeValue(i));
						}
						isPrimo = false;
					} else {
						newStruttura.addAttributo(letturaElemento());
					}					
					break;
				case XMLStreamConstants.END_ELEMENT:
					return newStruttura;
				case XMLStreamConstants.CHARACTERS:
					if (xmlr.getText().trim().length() > 0) {
						StrutturaDati newText = new StrutturaDati(xmlr.getText());
						newText.setIsText(true);
						newStruttura.addAttributo(newText);
					}
					break;
				case XMLStreamConstants.ATTRIBUTE:
					
					break;
				}
				xmlr.next();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return vuoto;
		}
		return vuoto;
	}

	protected StrutturaDati getFile() {
		return file;
	}

	public StrutturaDati getVuoto() {
		return vuoto;
	}

	protected boolean scriviFile(StrutturaDati input, String nomeFile, String formato, String versione) {
		try {
			xmlof = XMLOutputFactory.newInstance();
			xmlw = xmlof.createXMLStreamWriter(new FileOutputStream(nomeFile + ".xml"), formato);
			xmlw.writeStartDocument(formato, versione);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
		if (scriviElemento(input)) {
			try {
				xmlw.writeEndDocument();
				return true;
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		return false;
	}

	private boolean scriviElemento(StrutturaDati input) {
		// controlla se all'interno dell'elemento ci sono altri elementi oppure del
		// testo
		if (input.isText()) {
			// in caso ci sia del testo lo scrivo come tale
			try {
				xmlw.writeCharacters(input.getNome());
			} catch (Exception e) {
				return false;
			}
		} else {
			// altrimenti scrivo tutti gli elementi in modo ricorsivo
			try {
				xmlw.writeStartElement(input.getNome());
				for (Entry<String, String> tag : input.getTag().entrySet()) {
					xmlw.writeAttribute(tag.getKey(), input.getTag(tag.getKey()));
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return false;
			}
			for (StrutturaDati att : input.getAttributi()) {
				if (!scriviElemento(att))
					return false;
			}
			try {
				xmlw.writeEndElement();
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return false;
			}
		}
		return true;
	}

	protected boolean setPathInputFile(String pathInputFile) {
		try {
			xmlif = XMLInputFactory.newInstance();
			xmlr = xmlif.createXMLStreamReader(pathInputFile, new FileInputStream(pathInputFile));
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}
