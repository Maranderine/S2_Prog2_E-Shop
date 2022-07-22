package UserInterface.GUI.models;
import javax.swing.table.AbstractTableModel;
import java.util.Vector;
import Domain.Artikel.Artikel;
import Domain.Artikel.Massengutartikel;

/**
 * Klasse von TabelModel abgeleitet, speziell für Artikel Shop Tabelle 
 */
public class ArtikelVwTableModel extends AbstractTableModel{
    private Vector artikel;
    private String[] spaltenNamen = {"Nr", "Artikel", "Preis", "Bestand", ""};

    
    public ArtikelVwTableModel(Vector<Object> aktuelleArtikel) {
    	super(); 
    	//Kopie der Liste
    	// damit beim Aktualisieren keine Fehler passieren 
    	artikel = new Vector<Object>();
        if(!(aktuelleArtikel == null)){
    	    artikel.addAll(aktuelleArtikel);
        }
    }

    //aktualisiert Tabelle 
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

                return (selectedArtikel instanceof Massengutartikel)? 
                selectedArtikel.getName() + "("+ ((Massengutartikel) selectedArtikel).getstückZahl() + " Stk)"
                : selectedArtikel.getName() ;
            case 2:
                return selectedArtikel.getPreis();
            case 3:
                return selectedArtikel.getBestand();
            case 4:
                return "...";
            default:
                return null;
        }   
    }
}
