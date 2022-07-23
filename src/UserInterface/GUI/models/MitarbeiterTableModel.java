package UserInterface.GUI.models;

import java.util.Vector;
import javax.swing.table.AbstractTableModel;
import Domain.BenutzerObjekte.Benutzer;
import Domain.BenutzerObjekte.Mitarbeiter;

public class MitarbeiterTableModel extends AbstractTableModel{
    private Vector<Benutzer> mitarbeiter;
    private String[] spaltenNamen = {"Nr", "Name"};

    public MitarbeiterTableModel(Vector<Benutzer> alleNutzer) {
    	super(); 
    	//Kopie der Liste
    	// damit beim Aktualisieren keine Fehler passieren 
    	mitarbeiter = new Vector<Benutzer>();
        for(Benutzer m : alleNutzer){
            if(m instanceof Mitarbeiter){mitarbeiter.add(m);}
        }
        }

    public void setMitarbeiter(Vector<Benutzer> alleNutzer){
        mitarbeiter.clear();
        for(Benutzer m : alleNutzer){
            if(m instanceof Mitarbeiter){mitarbeiter.add(m);}
        }
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return mitarbeiter.size();
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
        Benutzer selectedBenutzer = mitarbeiter.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return selectedBenutzer.getKundenNr();
            case 1:
                return selectedBenutzer.getName();
            default:
                return null;
        }
    }
    
}
