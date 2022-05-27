
package Domain.EreignisLog;

import Domain.BenutzerObjekte.Benutzer;
import Domain.BenutzerObjekte.Benutzerverwaltung;

/**
 * EreignisLogVerwaltung
 */
public class EreignisLogVerwaltung {

  EreignisLog log = new EreignisLog();

  public EreignisLogVerwaltung() {

  }

  public boolean neuesEreignis(byte[] userHash, String type) {

    Benutzer user = eshop.BV_getAktiverBenutzer(userHash);
      
    log.add(, type);
    return true;

    return false;
  }

}
