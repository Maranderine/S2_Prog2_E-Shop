package DatenObjekte;

import java.util.HashMap;

/**
 * Die Warenkorb Klasse.
 * HashMap<Artikel, Integer> inhalt
 */
public class Warenkorb {
  private HashMap<Artikel, Integer> inhalt;

  public Warenkorb() {
    this.inhalt = new HashMap<Artikel, Integer>();
  }

  // Gibt Inhalt zurück
  public HashMap<Artikel, Integer> getInhalt() {
    return inhalt;
  }

  public void setInhalt(HashMap<Artikel, Integer> inhalt) {
    this.inhalt = inhalt;
  }
  
}