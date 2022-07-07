package UserInterface.GUI;
import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;

import java.util.Vector;

import Domain.Artikel.Artikel;

public class ArtikelTableModel extends AbstractTableModel{
    private Vector<Artikel> artikel;
    private String[] spaltenNamen = { "Nummer", "Name", "Preis", "Bestand" };

    
    public ArtikelTableModel(Vector<Artikel> aktuelleArtikel) {
    	super(); 
    	// Ich erstelle eine Kopie der Bücherliste,
    	// damit beim Aktualisieren (siehe Methode setBooks())
    	// keine unerwarteten Seiteneffekte entstehen.
    	artikel = new Vector<Artikel>();
        if(!(aktuelleArtikel == null)){
    	    artikel.addAll(aktuelleArtikel);
        }
    }

    public void setBooks(Vector<Artikel> aktuelleArtikel){
        artikel.clear();
        artikel.addAll(aktuelleArtikel);
        fireTableDataChanged();
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
        
        return artikel.size();
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
        Artikel selectedArtikel = artikel.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return selectedArtikel.getArtikelNr();
            case 1:
                return selectedArtikel.getName();
            case 2:
                return selectedArtikel.getBestand();
            case 3:
                return selectedArtikel.getBestand();
            default:
                return null;
        }   
    
    }
}

