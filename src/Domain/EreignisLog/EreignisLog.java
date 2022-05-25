package Domain.EreignisLog;

import java.util.Vector;

import BenutzerObjekte.Benutzer;

class EreignisLog {
  Vector<Ereignis> log = new Vector<Ereignis>();

  void add(Benutzer user, String type) {
    this.log.add(new Ereignis(user, type));
  }

  @Override
  public String toString() {
    String str = "EREIGNIS LOG:";
    for (Ereignis ereignis : this.log) {
      str += ereignis.toString() + "\t";
    }

    return str;
  }
}
