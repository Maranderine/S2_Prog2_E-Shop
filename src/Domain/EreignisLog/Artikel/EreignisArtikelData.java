package Domain.EreignisLog.Artikel;

import Domain.Artikel.Artikel;
import Domain.BenutzerObjekte.Benutzer;
import Domain.BenutzerObjekte.Benutzerverwaltung.BeutzerType;

public class EreignisArtikelData extends EreignisArtikel {

  public EreignisArtikelData(int eventNumber, String eventDesc, Benutzer CUser, int CUserNumber,
      BeutzerType CUserType, String CUserName, Artikel artikel, int artikelNumber, String artikelName,
      int artikelBestand, double artikelPreis) {
    super(eventNumber, eventDesc, CUser, CUserNumber, CUserType, CUserName, artikel, artikelNumber, artikelName,
        artikelBestand, artikelPreis);
    // TODO Auto-generated constructor stub
  }

}
