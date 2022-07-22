package UserInterface.GUI.models;

import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import Domain.EreignisLog.Ereignisse.Ereignis;

public class EventTableModel extends AbstractTableModel{
    private Vector<Ereignis> log;
    private String[] spaltenNamen = {"Nr", "Beschreibung", "Datum"};

    
    public EventTableModel(Vector<Ereignis> eventLog) {
    	super(); 
    	//Kopie der Liste
    	// damit beim Aktualisieren keine Fehler passieren 
    	log = new Vector<Ereignis>();
        if(!(eventLog == null)){
    	    log.addAll(eventLog);
        }
    }

    //aktualisiert Tabelle 
    public void setArtikel(Vector eventLog){
        log.clear();
        log.addAll(eventLog);
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
        
        return log.size();
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
        Ereignis selectedEreignis = log.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return selectedEreignis.getEreignisNummer();
            case 1:
                return selectedEreignis.getEreignisDesc();
            case 2:
                return selectedEreignis.getEreignisDatum();
            default:
                return null;
        }   
    }
}

