package Domain.EreignisLog.Interfaces;

import Domain.Verwaltung;

/**
 * EreignisInterface_System
 */
public interface EreignisInterface_System {

  /**
   * gets das aufrufende System(Verwaaltung) Objekt
   * 
   * @return Verwaltung Objekt
   */
  public Verwaltung getCallingSystem();
}