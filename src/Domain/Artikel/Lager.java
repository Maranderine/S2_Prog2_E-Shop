package Domain.Artikel;
import java.util.Vector;

public class Lager {
    
    public Vector<Artikel> artikelListe;
    static int artikelNrCount = 0;

    public Lager(){
        this.artikelListe = new Vector<Artikel>();
    }

    public String toString() {
        String str = "Artikelnr\tName\tBestand\tPreis\n";
        if (!artikelListe.isEmpty())
          for (Artikel artikel : artikelListe) {
            str += "\t" + artikel.toString() + "\n";
          }
        else
          str += "Keine Artikel";
    
        return str;
      }
}
