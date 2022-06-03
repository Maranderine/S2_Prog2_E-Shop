package Domain.EreignisLog;

import Domain.BenutzerObjekte.Benutzer;

/**
 * EreignisUser
 */
class EreignisUser extends Ereignis {

  protected EreignisUser(int nummer, Benutzer user, String type, String[] searchTerms) {
    super(nummer, user, type, searchTerms);
  }
}