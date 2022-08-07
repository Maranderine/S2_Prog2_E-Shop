import Exceptions.Artikel.ExceptionArtikelCollection;
import Exceptions.Artikel.ExceptionArtikelKonnteNichtGelöschtWerden;
import Exceptions.Artikel.ExceptionArtikelNichtGefunden;
import Exceptions.Artikel.ExceptionArtikelUngültigerBestand;
import Exceptions.Benutzer.ExceptionBenutzerNameUngültig;
import Exceptions.Ereignis.ExceptionEreignisNichtGefunden;
import UserInterface.UserInterface;

import java.util.HashMap;
import java.util.Vector;
import Domain.Artikel.Artikel;

import Domain.BenutzerObjekte.Benutzer;
import Domain.BenutzerObjekte.Benutzerverwaltung;
import Domain.EreignisLog.Ereignisse.Ereignis;
import Domain.Search.SuchOrdnung;
import Domain.Warenkorb.Rechnung;

public interface EshopInterface{

    public void BV_kundeHinzufügen(String name, String username, String password, String email, String address)
        throws ExceptionBenutzerNameUngültig;

    public void BV_mitarbeiterHinzufügen(String name, String username, String password)
        throws ExceptionBenutzerNameUngültig;

    public Vector<Benutzer> BV_getAllNutzer();

    public Benutzerverwaltung.BeutzerType login(UserInterface callingUI, String username, String password);

    public void logout(UserInterface callingUI);

    public HashMap<Artikel, Integer> WK_getInhalt();

    public Object WV_getWarenkorb();

    public void WV_setArtikel(Artikel artikel, int integer);

    public void WV_removeArtikel(Artikel artikel);

    public void WV_clearAll();

    public Rechnung WV_kaufen(byte[] userHash) throws ExceptionArtikelCollection;

    public double WV_getSumme();

    public Artikel AV_addArtikel(byte[] userHash, String name, int bestand, double einzelpreis, int packungsInhalt);

    public void AV_deleteArtikel(byte[] userHash, String name) throws ExceptionArtikelKonnteNichtGelöschtWerden;


    public void AV_setArtikel(byte[] userHash, Artikel artikel, String neuerName);

    public void AV_setArtikel(byte[] userHash, Artikel artikel, int bestand) throws ExceptionArtikelUngültigerBestand;

    public void AV_setArtikel(byte[] userHash, String name, int bestand)
      throws ExceptionArtikelNichtGefunden, ExceptionArtikelUngültigerBestand;

      public void AV_setArtikel(byte[] userHash, Artikel artikel, double preis);

      public void AV_setArtikel(byte[] userHash, String name, double preis);

    public void AV_setArtikel(byte[] userHash, Artikel artikel, String neuerName, int bestand, double preis);

    public void AV_setArtikel(byte[] userHash, String name, String neuerName, int bestand, double preis)
    throws ExceptionArtikelNichtGefunden, ExceptionArtikelUngültigerBestand;

    public Artikel AV_findArtikelByName(String name) throws ExceptionArtikelNichtGefunden;

    public Vector<Artikel> AV_getAlleArtikelList();

    public String AV_ArtikelAusgeben(Vector<Artikel> list, boolean detailed, String leereNachicht);
    
    public SuchOrdnung AV_sucheArtikel(String searchTerm);

    public void AV_sortListName(SuchOrdnung ordnung, boolean reverse);

    public void AV_sortListName(Vector<Artikel> artikelList, boolean reverse);

    public void AV_sortListPreis(SuchOrdnung ordnung, boolean reverse);

    public void AV_sortListPreis(Vector<Artikel> artikelList, boolean reverse);

    public void AV_sortListRelevanz(SuchOrdnung ordnung);

    public String EV_logDisplay();

    public Ereignis EV_getEreignis(int ereignisNummer) throws ExceptionEreignisNichtGefunden;

    public Integer[] EV_getBestandsHistore(Artikel artikel);

    public Vector<Ereignis> EV_getLog();

    public SuchOrdnung EV_sucheEreignisse(String searchterm);

    public void saveData();

}