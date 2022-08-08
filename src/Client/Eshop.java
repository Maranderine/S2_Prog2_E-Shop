package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Vector;

import Domain.Artikel.Artikel;
import Domain.BenutzerObjekte.Benutzer;
import Domain.BenutzerObjekte.Benutzerverwaltung.BeutzerType;
import Domain.EreignisLog.Ereignisse.Ereignis;
import Domain.Search.SuchOrdnung;
import Domain.Warenkorb.Rechnung;
import Exceptions.Artikel.ExceptionArtikelCollection;
import Exceptions.Artikel.ExceptionArtikelExistiertBereits;
import Exceptions.Artikel.ExceptionArtikelKonnteNichtErstelltWerden;
import Exceptions.Artikel.ExceptionArtikelKonnteNichtGelöschtWerden;
import Exceptions.Artikel.ExceptionArtikelNameExistiertBereits;
import Exceptions.Artikel.ExceptionArtikelNameUngültig;
import Exceptions.Artikel.ExceptionArtikelNichtGefunden;
import Exceptions.Artikel.ExceptionArtikelUngültigerBestand;
import Exceptions.Benutzer.ExceptionBenutzerNameUngültig;
import Exceptions.Ereignis.ExceptionEreignisNichtGefunden;
import UserInterface.CUI;
import UserInterface.UserInterface;
import UserInterface.GUI.GUI;
import common.EshopInterface;

public class Eshop implements EshopInterface {

  private Socket socket = null;
  private BufferedReader in;
  private PrintStream out;

  public Eshop(String host, int port) {
    System.out.println("/////////////CLIENT/////////////");

    try {
      socket = new Socket(host, port);
      in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      out = new PrintStream(socket.getOutputStream());

    } catch (IOException e) {
      System.err.println("CLIENT - ERROR - on socket stream create: " + e);

      if (socket != null) {
        try {
          socket.close();
          System.err.println("- Closing socket");
        } catch (Exception ioe) {
        }
      }
      System.exit(1);
    }

    System.out.println("CLIENT - connection established: " + socket.getInetAddress() + ":" + socket.getPort());

    // server welcome message
    try {
      String message = in.readLine();
      System.out.println("Server Message: " + message);
    } catch (Exception e) {
      // TODO: handle exception
    }

    // TEST TEST TEST TEST TEST TEST TEST TEST TEST TEST TEST TEST TEST TEST TEST
    String sp = REQUESTS.splitter;
    out.println(REQUESTS.REPLY + sp + "Hello Im am a computer.");
    try {
      System.out.println("Received Message: " + in.readLine());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  // #region networking

  public void quit() {
    System.out.println("CLIENT - quitting");
    out.println("quit");
    try {
      socket.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  // #endregion networking

  // #region implement

  @Override
  public void BV_kundeHinzufügen(String name, String username, String password, String email, String address)
      throws ExceptionBenutzerNameUngültig {
    // TODO Auto-generated method stub

  }

  @Override
  public void BV_mitarbeiterHinzufügen(String name, String username, String password)
      throws ExceptionBenutzerNameUngültig {
    // TODO Auto-generated method stub

  }

  @Override
  public Vector<Benutzer> BV_getAllNutzer() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public BeutzerType login(UserInterface callingUI, String username, String password) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void logout(UserInterface callingUI) {
    // TODO Auto-generated method stub

  }

  @Override
  public HashMap<Artikel, Integer> WK_getInhalt() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Object WV_getWarenkorb() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void WV_setArtikel(Artikel artikel, int integer) {
    // TODO Auto-generated method stub

  }

  @Override
  public void WV_removeArtikel(Artikel artikel) {
    // TODO Auto-generated method stub

  }

  @Override
  public void WV_clearAll() {
    // TODO Auto-generated method stub

  }

  @Override
  public Rechnung WV_kaufen(byte[] userHash) throws ExceptionArtikelCollection {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public double WV_getSumme() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public Artikel AV_addArtikel(byte[] userHash, String name, int bestand, double einzelpreis, int packungsInhalt)
      throws ExceptionArtikelExistiertBereits, ExceptionArtikelKonnteNichtErstelltWerden {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void AV_deleteArtikel(byte[] userHash, String name) throws ExceptionArtikelKonnteNichtGelöschtWerden {
    // TODO Auto-generated method stub

  }

  @Override
  public void AV_setArtikel(byte[] userHash, Artikel artikel, String neuerName)
      throws ExceptionArtikelNameExistiertBereits, ExceptionArtikelNameUngültig {
    // TODO Auto-generated method stub

  }

  @Override
  public void AV_setArtikel(byte[] userHash, Artikel artikel, int bestand) throws ExceptionArtikelUngültigerBestand {
    // TODO Auto-generated method stub

  }

  @Override
  public void AV_setArtikel(byte[] userHash, String name, int bestand)
      throws ExceptionArtikelNichtGefunden, ExceptionArtikelUngültigerBestand {
    // TODO Auto-generated method stub

  }

  @Override
  public void AV_setArtikel(byte[] userHash, Artikel artikel, double preis) {
    // TODO Auto-generated method stub

  }

  @Override
  public void AV_setArtikel(byte[] userHash, String name, double preis) throws ExceptionArtikelNichtGefunden {
    // TODO Auto-generated method stub

  }

  @Override
  public void AV_setArtikel(byte[] userHash, Artikel artikel, String neuerName, int bestand, double preis)
      throws ExceptionArtikelNichtGefunden, ExceptionArtikelUngültigerBestand {
    // TODO Auto-generated method stub

  }

  @Override
  public void AV_setArtikel(byte[] userHash, String name, String neuerName, int bestand, double preis)
      throws ExceptionArtikelNichtGefunden, ExceptionArtikelUngültigerBestand {
    // TODO Auto-generated method stub

  }

  @Override
  public Artikel AV_findArtikelByName(String name) throws ExceptionArtikelNichtGefunden {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Vector<Artikel> AV_getAlleArtikelList() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String AV_ArtikelAusgeben(Vector<Artikel> list, boolean detailed, String leereNachicht) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public SuchOrdnung AV_sucheArtikel(String searchTerm) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void AV_sortListName(SuchOrdnung ordnung, boolean reverse) {
    // TODO Auto-generated method stub

  }

  @Override
  public void AV_sortListName(Vector<Artikel> artikelList, boolean reverse) {
    // TODO Auto-generated method stub

  }

  @Override
  public void AV_sortListPreis(SuchOrdnung ordnung, boolean reverse) {
    // TODO Auto-generated method stub

  }

  @Override
  public void AV_sortListPreis(Vector<Artikel> artikelList, boolean reverse) {
    // TODO Auto-generated method stub

  }

  @Override
  public void AV_sortListRelevanz(SuchOrdnung ordnung) {
    // TODO Auto-generated method stub

  }

  @Override
  public String EV_logDisplay() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Ereignis EV_getEreignis(int ereignisNummer) throws ExceptionEreignisNichtGefunden {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Integer[] EV_getBestandsHistore(Artikel artikel) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Vector<Ereignis> EV_getLog() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public SuchOrdnung EV_sucheEreignisse(String searchterm) {
    // TODO Auto-generated method stub
    return null;
  }

  // #endregion implement

  @Override
  public UserInterface createUserInterface() {
    // TODO Auto-generated method stub

    out.println(REQUESTS.UI);

    try {
      switch (in.readLine()) {
        case "CUI":
          return new CUI(this);
        case "GUI":
          return new GUI(this);
      }
      return new CUI(this);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return null;
  }

}
