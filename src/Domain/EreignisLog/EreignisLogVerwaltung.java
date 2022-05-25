
package Domain.EreignisLog;

import BenutzerObjekte.Benutzer;

/**
 * EreignisLogVerwaltung
 */
public class EreignisLogVerwaltung {

  EreignisLog log = new EreignisLog();

  public EreignisLogVerwaltung() {

  }

  
  private void ereignisAdd(Benutzer user, String type) {
    log.add(user, type);
  }

}
