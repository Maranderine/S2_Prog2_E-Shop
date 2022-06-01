package Domain.EreignisLog;

import Domain.BenutzerObjekte.Benutzer;

/**
 * EreignisArtikel
 */
class EreignisArtikel extends Ereignis {

  protected EreignisArtikel(int nummer, Benutzer user, String type) {
    super(nummer, user, type);
  }
}