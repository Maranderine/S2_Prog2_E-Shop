package UserInterface.GUI.models;
import javax.swing.table.AbstractTableModel;
import java.util.HashMap;
import java.util.Vector;
import java.util.Map.Entry;

import Domain.Artikel.Artikel;

/**
 * Klasse von TabelModel abgeleitet, speziell für Warenkorb Tabelle 
 */
public class WKTableModel extends AbstractTableModel{
    private HashMap<Artikel, Integer> warenkorb;
    private Vector<Artikel> korbArtikel;
    private String[] spaltenNamen = {"Nr", "Artikel", "Stück", "Preis", ""};

    
    public WKTableModel(HashMap korbInhalt) {
    	super(); 
    	//Kopie der Liste,
    	// damit beim Aktualisieren keine unerwarteten Seiteneffekte entstehen.
    	warenkorb = korbInhalt;
        if(!(warenkorb == null)){korbArtikel = getKeys();}
    }

    //aktualsisiert Tabelle
    public void setArtikel(HashMap aktuelleArtikel){
        warenkorb = aktuelleArtikel;
        korbArtikel = (warenkorb ==null)? null: getKeys();
        fireTableDataChanged();
    }

    public Vector<Artikel> getKeys(){
        Vector<Artikel> artikel = new Vector<>();
        for (Entry<Artikel, Integer> entry : warenkorb.entrySet()) {
           artikel.add(entry.getKey());
        }
        return artikel;
    }
    /*
     * Ab hier überschriebene Methoden mit Informationen, 
     * die eine JTable von jedem TableModel erwartet:
     * - Anzahl der Zeilen
     * - Anzahl der Spalten
     * - Benennung der Spalten
     * - Wert einer Zelle in Zeile / Spalte
     */
    @Override
    public int getRowCount() {
        return (korbArtikel == null)? 0 :  korbArtikel.size();
    }

    @Override
    public int getColumnCount() {
        return spaltenNamen.length;
    }

    public String getColumnName(int col){
        return spaltenNamen[col];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if(korbArtikel ==null){return "";}
        else{
            Artikel artikel = korbArtikel.get(rowIndex);
            int bestand = warenkorb.get(artikel);
            switch (columnIndex) {
                case 0:
                    return artikel.getArtikelNr();
                case 1:
                    return artikel.getName();
                case 2:
                    return bestand;
                case 3:
                    return artikel.getPreis();
                case 4:
                    return "-";
                default:
                    return null;
            }
        }   
    }
}
