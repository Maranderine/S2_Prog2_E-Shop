package UserInterface.GUI;
import javax.swing.JButton;
import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;
import java.lang.Class;

import java.util.Vector;

import Domain.Artikel.Artikel;

public class ArtikelTableModel extends AbstractTableModel{
    private Vector artikel;
    private String[] spaltenNamen = {"Nr", "Artikel", "Preis", "auf Lager", ""};

    
    public ArtikelTableModel(Vector<Object> aktuelleArtikel) {
    	super(); 
    	// Ich erstelle eine Kopie der Bücherliste,
    	// damit beim Aktualisieren (siehe Methode setBooks())
    	// keine unerwarteten Seiteneffekte entstehen.
    	artikel = new Vector<Object>();
        if(!(aktuelleArtikel == null)){
    	    artikel.addAll(aktuelleArtikel);
        }
    }

    public void setArtikel(Vector aktuelleArtikel){
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
        Artikel selectedArtikel = (Artikel) artikel.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return selectedArtikel.getArtikelNr();
            case 1:
                return selectedArtikel.getName();
            case 2:
                return selectedArtikel.getPreis();
            case 3:
                if(selectedArtikel.getBestand() > 10){return "";}
                if(selectedArtikel.getBestand() <= 10){return "noch " + selectedArtikel.getBestand() + " auf Lager";}
            case 4:
                return "+";
            default:
                return null;
        }   
    }
}

