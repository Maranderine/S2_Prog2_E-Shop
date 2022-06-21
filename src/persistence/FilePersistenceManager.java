package persistence;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.Vector;

import Domain.Artikel.Artikel;

public class FilePersistenceManager implements PersistenceManager {

  private BufferedReader reader = null;
  private PrintWriter writer = null;
  private ObjectOutputStream objectWriter = null;
  private ObjectInputStream objectReader = null;

  // Vorbereitung/Nachbereitung

  private boolean close() {
    try {
      if (writer != null) {
        writer.close();
      }

      if (reader != null) {
        reader.close();
      }
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }

    return true;
  }

  // Artikelverwaltung Datensicherung
  @Override
  public Vector<String[]> loadData(String artikelDoc) throws IOException {

    try {
      // öffnet Schnittstelle zum laden von Artikeln aus Datei
      reader = new BufferedReader(new FileReader(artikelDoc));
      Vector<String[]> dataList = new Vector<String[]>();
      String zeile;

      // Zeile einlesen, wenn nicht leer dann besteht zeile aus einem Artikel
      while (!((zeile = reader.readLine()) == null)) {
        // String zeile aufteilen in einzelne Strings, mit jeweils einem Wert des in
        dataList.add(zeile.split(";"));
      }
      // schnittstelle schließen
      close();
      return dataList;
    } catch (IOException e) {
      close();
      throw e;
    }
  }

  @Override
  public boolean saveData(String artikelDoc, Vector<Artikel> objektListe) throws IOException {
    // öffnet Schnittstelle zum schreiben von Artikeln in Datei
    writer = new PrintWriter(new BufferedWriter(new FileWriter(artikelDoc)));
    // schreibt jeden Artikel aus der Liste in "Datenform"
    // ("artikelnr;name;bestand;preis") in die Datei
    for (Artikel artikel : objektListe) {
      writer.println(artikel.toData());
    }
    // schnittstelle schließen
    close();
    return true;
  }
  // Benutzer Datensicherung

  @Override
  public Vector<? extends Object> loadObjekt(String kundenDoc) {
    Vector<? extends Object> nutzer;
    try {
      // öffnet Schnittstelle zum speichern von Objekten(Kunde) in kundenDoc
      objectReader = new ObjectInputStream(new FileInputStream(kundenDoc));
      // Solange noch Objekte in der Datei sind werden diese eingelesen und nach type
      // cast (Kunde) der Nutzer Liste hinzugefügt
      @SuppressWarnings("unchecked")
      Vector<? extends Object> user = (Vector<Object>) objectReader.readObject();
      nutzer = user;
    } catch (IOException | ClassNotFoundException e) {
      e.printStackTrace();
      // fallback error correction
      nutzer = new Vector<Object>();
    }
    close();
    return nutzer;
  }

  @Override
  public boolean saveObjekt(String kundenDoc, Vector<? extends Object> nutzerListe) {
    try {
      // öffnet Schnittstelle zum speichern von Objekten(Kunde) in kundenDoc
      objectWriter = new ObjectOutputStream(new FileOutputStream(kundenDoc));
      // Solange noch Objekte in der Datei sind werden diese eingelesen und nach type
      // cast (Kunde) der Nutzer Liste hinzugefügt
      objectWriter.writeObject(nutzerListe);
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }
    close();
    return true;
  }
}
