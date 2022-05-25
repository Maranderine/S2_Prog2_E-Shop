package Domain.EreignisLog;

import java.util.Date;

import Domain.BenutzerObjekte.Benutzer;

class Ereignis {
  static int EreignisZaehler = 1;
  int EreignisNummer;
  String type;
  Benutzer user;
  Date date;

  protected Ereignis(Benutzer user, String type) {
    this.EreignisNummer = EreignisZaehler;
    this.type = type;
    this.user = user;
    this.date = new Date();
    
    EreignisZaehler++;
  }

  @Override
  public String toString() {
    
    return EreignisNummer + "\t" + user + "\t" + type + "\t" + date;
  }
}
