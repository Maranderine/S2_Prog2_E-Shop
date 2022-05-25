package Domain.EreignisLog;

import java.util.Date;

import BenutzerObjekte.Benutzer;

class Ereignis {
  static int EreignisZaehler = 1;
  int EreignisNummer;
  String type;
  Benutzer user;
  Date date;

  public Ereignis(Benutzer user, String type) {
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
