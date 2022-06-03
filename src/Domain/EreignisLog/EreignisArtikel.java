package Domain.EreignisLog;

import Domain.BenutzerObjekte.Benutzer;

/**
 * EreignisArtikel
 */
class EreignisArtikel extends Ereignis {

  protected EreignisArtikel(int nummer, Benutzer user, String type, String[] searchTerms) {
    super(nummer, user, type, searchTerms);
  }
}