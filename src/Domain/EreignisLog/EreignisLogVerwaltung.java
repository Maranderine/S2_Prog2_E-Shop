
package Domain.EreignisLog;

import Domain.BenutzerObjekte.Benutzer;

/**
 * EreignisLogVerwaltung
 */
public class EreignisLogVerwaltung {

  EreignisLog log = new EreignisLog();

  public EreignisLogVerwaltung() {

  }

  
  public void ereignisAdd(Benutzer user, String type) {
    log.add(user, type);
  }

}
