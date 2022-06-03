package Domain.EreignisLog;

import java.util.Date;

import Domain.BenutzerObjekte.Benutzer;
import Domain.Search.Searchable;

class Ereignis extends Searchable {
  protected int EreignisNummer;
  protected String type;
  protected Date date;

  // user
  protected String usertype;
  protected String username;

  protected Ereignis(int nummer, Benutzer user, String type, String[] searchTerms) {
    super(searchTerms);
    this.EreignisNummer = nummer;
    this.type = type;
    this.date = new Date();

  }

  @Override
  public String toString() {
    return this.EreignisNummer + "\t" + this.username + "\t" + this.type + "\t##\t" + this.date;
  }

  protected String toStringDetailed() {
    return toString();
  }
}
