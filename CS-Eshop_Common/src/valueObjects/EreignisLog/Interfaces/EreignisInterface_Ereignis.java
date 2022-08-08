package Domain.EreignisLog.Interfaces;

import java.util.Date;

/**
 * Interface für Ereignisse
 */
public interface EreignisInterface_Ereignis {

  /**
   * gets die Ereignis Identifikations Nummer
   * 
   * @return int Identifikations Nummer des Ereignisses
   */
  public int getEreignisNummer();

  /**
   * gets den Ereignis Beschreibungs Text
   * 
   * @return String kurze ereignis Beschreibung
   */
  public String getEreignisDesc();

  /**
   * gets das Ereignis erstellungs Datum
   * 
   * @return Date datum der erstellung
   */
  public Date getEreignisDatum();
}
