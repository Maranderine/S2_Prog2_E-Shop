package Domain.EreignisLog;

import Domain.BenutzerObjekte.Benutzer;

/**
 * EreignisUser
 */
class EreignisUser extends Ereignis {

  protected EreignisUser(int nummer, Benutzer user, String type) {
    super(nummer, user, type);
  }
}