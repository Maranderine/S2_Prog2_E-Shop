package persistence;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Vector;

import Domain.Artikel.Artikel;
import Domain.Artikel.Lager;
import Domain.BenutzerObjekte.Benutzer;
import Domain.BenutzerObjekte.Kunde;
import Domain.BenutzerObjekte.Mitarbeiter;

public class FilePersistenceManager implements PersistenceManager {

	private BufferedReader reader = null;
	private PrintWriter writer = null;
	private ObjectOutputStream objectWriter = null;
	private ObjectInputStream objectReader = null;
	
//Vorbereitung/Nachbereitung

	public boolean close() {
		try{
		if (writer != null) {writer.close();}

		if (reader != null) {reader.close();}
		} catch(IOException e){
			e.printStackTrace();
			return false;
		}

		return true;
	}

//Artikelverwaltung Datensicherung

	public Vector<Artikel> loadArticle(String artikelDoc) throws IOException {
		//öffnet Schnittstelle zum laden von Artikeln aus Datei 
        reader = new BufferedReader(new FileReader(artikelDoc));
        Vector<Artikel> artikelListe = new Vector<Artikel>();
		String zeile;

        // Zeile einlesen, wenn nicht leer dann besteht zeile aus einem Artikel "artikelnr;name;bestand;preis"
        while(!((zeile = reader.readLine()) == null)){
            //String zeile aufteilen in einzelne Strings, mit jeweils einem Wert des in dieser Zeile eingelesenen Artikels 
            String [] data = zeile.split(";");
            //neuen Artikeln mit eingelesenen Werten, hinzufügen zur artikeL´lListe  
            artikelListe.add(new  Artikel(Integer.parseInt(data[0]), data[1], Integer.parseInt(data[2]), Double.parseDouble(data[3])));
        }
		//schnittstelle schließen
		close();
		return artikelListe;
    }

	public boolean saveArticle(String artikelDoc, Vector<Artikel> artikelListe) throws IOException {
		//öffnet Schnittstelle zum schreiben von Artikeln in Datei
		writer = new PrintWriter(new BufferedWriter(new FileWriter(artikelDoc)));
		//schreibt jeden Artikel aus der Liste in "Datenform" ("artikelnr;name;bestand;preis") in die Datei 
		for(Artikel artikel : artikelListe){
			writer.println(artikel.toData());
		}
		//schnittstelle schließen
		close();
		return true;
	}
//Benutzer Datensicherung 

	/*public  List<Benutzer> loadNutzer(String kundenDoc, String MitarbeiterDoc){
		List<Benutzer> nutzer = new Vector<Benutzer>();
		Object obj = null;
		try {
			//öffnet Schnittstelle zum speichern von Objekten(Kunde) in kundenDoc
			objectReader = new ObjectInputStream(new FileInputStream(kundenDoc));
			//Solange noch  Objekte in der Datei sind werden diese eingelesen und nach type cast (Kunde) der Nutzer Liste hinzugefügt
			while ((obj = objectReader.readObject()) != null) {
				nutzer.add((Kunde) obj);
			}
			close();
			//öffnet Schnittstelle zum speichern von Objekten(Mitarbeiter) in MitarbieterDoc
			objectWriter = new ObjectOutputStream(new FileOutputStream(MitarbeiterDoc));
			//Solange noch  Objekte in der Datei sind werden diese eingelesen und nach type cast (Mitarbeiter) der Nutzer Liste hinzugefügt
			while ((obj = objectReader.readObject()) != null) {
				nutzer.add((Mitarbeiter) obj);
			}
		}catch(IOException | ClassNotFoundException e){
			e.printStackTrace();
		}
		close();
		return nutzer;
	}*/

	public  Vector<Benutzer> loadNutzer(String kundenDoc){
		Vector<Benutzer> nutzer = new Vector<Benutzer>();
		try {
			//öffnet Schnittstelle zum speichern von Objekten(Kunde) in kundenDoc
			objectReader = new ObjectInputStream(new FileInputStream(kundenDoc));
			//Solange noch  Objekte in der Datei sind werden diese eingelesen und nach type cast (Kunde) der Nutzer Liste hinzugefügt
			nutzer = (Vector<Benutzer>)objectReader.readObject();
			}catch(IOException | ClassNotFoundException e){
				e.printStackTrace();
			}
			close();
			return nutzer;
	}

	public  boolean saveNutzer(String kundenDoc, Vector<Benutzer> nutzer ){
		try {
			//öffnet Schnittstelle zum speichern von Objekten(Kunde) in kundenDoc
			objectWriter = new ObjectOutputStream(new FileOutputStream(kundenDoc));
			//Solange noch  Objekte in der Datei sind werden diese eingelesen und nach type cast (Kunde) der Nutzer Liste hinzugefügt
			objectWriter.writeObject(nutzer);
			}catch(IOException e){
				e.printStackTrace();
				return false;
			}
			close();
			return true;
	}
}
