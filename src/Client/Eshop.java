package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Vector;

import Domain.Artikel.Artikel;
import Domain.BenutzerObjekte.Benutzer;
import Domain.EreignisLog.Ereignisse.Ereignis;
import Domain.Search.SuchOrdnung;
import Domain.Warenkorb.Rechnung;
import Domain.Warenkorb.Warenkorb;
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
import UserInterface.UserSession;
import common.EshopInterface;

public class Eshop implements EshopInterface {

  private Socket socket = null;
  private BufferedReader in;
  private PrintStream out;
  private ObjectInputStream ois;
  private ObjectOutputStream oos;
  String sp = REQUESTS.splitter;

  public Eshop(String host, int port) {
    System.out.println("/////////////CLIENT/////////////");

    try {
      socket = new Socket(host, port);
      in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      out = new PrintStream(socket.getOutputStream());
      oos = new ObjectOutputStream(socket.getOutputStream());
      ois = new ObjectInputStream(socket.getInputStream());

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
    reply("Hello Im am a computer.");

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

  public void reply(String messageToSend) {

    // getting the split chaaracter for ease of use
    String sp = REQUESTS.splitter;

    // sending the request
    // assembling the string to send: Request + argument, seperated by the slitter
    // character
    // request + splitter + argument
    String send = REQUESTS.REPLY + sp + messageToSend;

    // sendding the request
    out.println(send);

    try {

      // waiting for a server reply
      String message = in.readLine();
      // printing message
      System.out.println("Received Message: " + message);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  // #endregion networking
  // #region usefull

  private String[] inLineAndSplit() {

    try {
      return REQUESTS.split(in.readLine());

    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  private Exception processException(String str) {
    Exception exception = null;
    switch (CLIENT_FEEDBACK.get(str)) {
      case FEHLER:
        try {
          exception = (Exception) ois.readObject();
        } catch (ClassNotFoundException | IOException e) {
          e.printStackTrace();
        }
        break;
      case FEHLERFREI:
        break;
      default:
        System.err.println("CLIENT - ERROR - feedback process: unknown feedback");
        break;
    }

    return exception;
  }

  private Exception waitForException() {
    Exception exception = null;
    try {
      System.out.println("Waiting for exception...");
      exception = processException(in.readLine());
    } catch (IOException e) {
      e.printStackTrace();
    }
    return exception;
  }

  // #endregion
  // #region implement

  @Override
  public void BV_kundeHinzufügen(String name, String username, String password, String email, String address)
      throws ExceptionBenutzerNameUngültig {

    String sp = REQUESTS.splitter;
    out.println(REQUESTS.BVKUNDEHINZUFÜGEN + sp + name + sp + username + password + sp + email + sp + address);

    Exception exception = waitForException();
    if (exception != null) {
      throw (ExceptionBenutzerNameUngültig) exception;
    }
  }

  @Override
  public void BV_mitarbeiterHinzufügen(String name, String username, String password)
      throws ExceptionBenutzerNameUngültig {
    String sp = REQUESTS.splitter;
    out.println(REQUESTS.BVMITARBEITERHINZUFÜGEN + sp + name + sp + username + sp + password);
    String back = "";
    try {
      back = in.readLine();
    } catch (IOException e1) {
      e1.printStackTrace();
    }
    if (back.equals("fehler")) {
      try {
        ExceptionBenutzerNameUngültig e = (ExceptionBenutzerNameUngültig) ois.readObject();
        throw e;
      } catch (ClassNotFoundException | IOException e) {
      }
    }
  }

  @Override
  public Vector<Benutzer> BV_getAllNutzer() {
    Vector<Benutzer> nutzer = new Vector<Benutzer>();
    out.println(REQUESTS.BVGETALLENUTZER);
    try {
      nutzer = (Vector<Benutzer>) ois.readObject();
    } catch (ClassNotFoundException | IOException e) {
      e.printStackTrace();
    }
    return nutzer;
  }

  @Override
  public BenutzerType login(UserSession callingUI, String username, String password) {
    String sp = REQUESTS.splitter;
    out.println(REQUESTS.LOGIN + sp + username + sp + password);

    BenutzerType userType = BenutzerType.NONE;

    try {
      userType = BenutzerType.get(in.readLine());

      if (userType != BenutzerType.NONE) {
        callingUI.userHash = in.readLine().getBytes();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    return userType;
  }

  @Override
  public void logout(UserSession callingUI) {

    out.println(REQUESTS.LOGOUT);

    byte arr[] = {};
    callingUI.userHash = arr;
  }

  @Override
  public HashMap<Artikel, Integer> WK_getInhalt() {
    HashMap<Artikel, Integer> inhalt = new HashMap<Artikel, Integer>();
    out.println(REQUESTS.WKGETINHALT);
    try {
      inhalt = (HashMap<Artikel, Integer>) ois.readObject();
    } catch (ClassNotFoundException | IOException e) {
      e.printStackTrace();
    }
    return inhalt;
  }

  @Override
  public Warenkorb WV_getWarenkorb() {
    Warenkorb korb = null;
    out.println(REQUESTS.WVGETWARENKORB);
    try {
      korb = (Warenkorb) ois.readObject();
    } catch (ClassNotFoundException | IOException e) {
      e.printStackTrace();
    }
    return korb;
  }

  @Override
  public void WV_setArtikel(Artikel artikel, int integer) {
    out.println(REQUESTS.WVGETWARENKORB + sp + artikel.getName() + sp + integer);
  }

  @Override
  public void WV_removeArtikel(Artikel artikel) {
    out.println(REQUESTS.WVREMOVEARTIKEL + sp + artikel.getName());
  }

  @Override
  public void WV_clearAll() {
    out.println(REQUESTS.WVCLEARALL);
  }

  @Override
  public Rechnung WV_kaufen(byte[] userHash) throws ExceptionArtikelCollection {

    // sende signal //userHash spezifisch! braucht nicht gesendet zu werden. der
    // socket hält eine kopie
    out.println(REQUESTS.WVKAUFEN);

    Exception exception = waitForException();

    if (exception != null) {
      // erhaalte fehler
      throw (ExceptionArtikelCollection) exception;
    } else {
      // erhalte rechnung
      try {
        return (Rechnung) ois.readObject();
      } catch (ClassNotFoundException | IOException e) {
        // absolut alles ist schief gelaaufen
        e.printStackTrace();
        return null;
      }
    }
  }

  @Override
  public double WV_getSumme() {
    out.println(REQUESTS.WVGETSUMME);
    double d = 0.0;
    try {
      d = Double.parseDouble(in.readLine());
    } catch (NumberFormatException | IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return d;
  }

  @Override
  public Artikel AV_addArtikel(byte[] userHash, String name, int bestand, double einzelpreis, int packungsInhalt)
      throws ExceptionArtikelExistiertBereits, ExceptionArtikelKonnteNichtErstelltWerden {
    Artikel artikel = null;
    out.println(REQUESTS.AVADDARTIKEL + sp + userHash + name + bestand + einzelpreis + sp + packungsInhalt);
    try {
      artikel = (Artikel) ois.readObject();
    } catch (ClassNotFoundException | IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public void AV_deleteArtikel(byte[] userHash, String name) throws ExceptionArtikelKonnteNichtGelöschtWerden {
    out.println(REQUESTS.AVDELETEARTIKEL + sp + userHash + sp + name);
    try {
      if (in.readLine().equals("fehler")) {
        throw (ExceptionArtikelKonnteNichtGelöschtWerden) ois.readObject();
      }
    } catch (ClassNotFoundException | IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void AV_setArtikel(byte[] userHash, Artikel artikel, String neuerName)
      throws ExceptionArtikelNameExistiertBereits, ExceptionArtikelNameUngültig {
    out.println(REQUESTS.AVSETARTIKELNAME + sp + userHash + sp + artikel.getName() + sp + neuerName);
  }

  @Override
  public void AV_setArtikel(byte[] userHash, Artikel artikel, int bestand) throws ExceptionArtikelUngültigerBestand {
    out.println(REQUESTS.AVSETARTIKELDATABESTAND + sp + userHash + sp + artikel.getName() + bestand);
  }

  @Override
  public void AV_setArtikel(byte[] userHash, String name, int bestand)
      throws ExceptionArtikelNichtGefunden, ExceptionArtikelUngültigerBestand {
    out.println(REQUESTS.AVSETARTIKELDATABESTAND + sp + userHash + sp + name + bestand);

  }

  @Override
  public void AV_setArtikel(byte[] userHash, Artikel artikel, double preis) {
    out.println(REQUESTS.AVSETARTIKELPREIS + sp + userHash + sp + artikel.getName() + sp + preis);

  }

  @Override
  public void AV_setArtikel(byte[] userHash, String name, double preis) throws ExceptionArtikelNichtGefunden {
    out.println(REQUESTS.AVSETARTIKELDATAPREIS + sp + userHash + sp + name + sp + preis);

  }

  @Override
  public void AV_setArtikel(byte[] userHash, Artikel artikel, String neuerName, int bestand, double preis)
      throws ExceptionArtikelNichtGefunden, ExceptionArtikelUngültigerBestand {
    out.println(
        REQUESTS.AVSETARTIKELALL + sp + userHash + sp + artikel.getName() + sp + neuerName + sp + bestand + sp + preis);
  }

  @Override
  public void AV_setArtikel(byte[] userHash, String name, String neuerName, int bestand, double preis)
      throws ExceptionArtikelNichtGefunden, ExceptionArtikelUngültigerBestand {
    out.println(REQUESTS.AVSETARTIKELDATAALL + sp + userHash + sp + name + sp + neuerName + sp + bestand + sp + preis);

  }

  @Override
  public Artikel AV_findArtikelByName(String name) throws ExceptionArtikelNichtGefunden {

    Artikel artikel = null;
    out.println(REQUESTS.AVFINDARTIKELBYNAME + sp + name);

    Exception exception = waitForException();

    if (exception != null) {
      throw (ExceptionArtikelNichtGefunden) exception;
    } else {
      try {
        artikel = (Artikel) ois.readObject();
      } catch (ClassNotFoundException | IOException e) {
        e.printStackTrace();
      }
    }
    return artikel;
  }

  @Override
  public Vector<Artikel> AV_getAlleArtikelList() {
    Vector<Artikel> alleArtikel = new Vector<Artikel>();
    out.println(REQUESTS.AVGETALLEARTIKELLIST);
    try {
      alleArtikel = (Vector<Artikel>) ois.readObject();
    } catch (ClassNotFoundException | IOException e) {
      e.printStackTrace();
    }
    return alleArtikel;
  }

  // #endregion impl
  // #region unimplemented

  @Override
  public String AV_ArtikelAusgeben(Vector<Artikel> list, boolean detailed, String leereNachicht) {
    try {
      out.println(REQUESTS.AVARTIKELAUSGEBEN + sp + detailed + sp + leereNachicht);
      oos.writeObject(list);
      oos.flush();
      return in.readLine().replace("/n", "\n");
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      return "";
    }
  }

  @Override
  public SuchOrdnung AV_sucheArtikel(String searchTerm) {
    out.println(REQUESTS.AVSUCHEARTIKEL + sp + searchTerm);

    try {
      return (SuchOrdnung) ois.readObject();
    } catch (ClassNotFoundException | IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      return null;
    }
  }

  @Override
  public SuchOrdnung AV_sortListName(SuchOrdnung ordnung, boolean reverse) {

    String str = "ord";

    try {
      out.println(REQUESTS.AVSORTLISTNAME + sp + str + sp + reverse);
      oos.writeObject(ordnung);

      ordnung = (SuchOrdnung) ois.readObject();
    } catch (IOException | ClassNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    return ordnung;
  }

  @Override
  public Vector<Artikel> AV_sortListName(Vector<Artikel> artikelList, boolean reverse) {

    String str = "vec";

    try {
      out.println(REQUESTS.AVSORTLISTNAME + sp + str + sp + reverse);
      oos.writeObject(artikelList);

      artikelList = (Vector<Artikel>) ois.readObject();
    } catch (IOException | ClassNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    return artikelList;
  }

  @Override
  public SuchOrdnung AV_sortListPreis(SuchOrdnung ordnung, boolean reverse) {

    String str = "ord";
    try {
      out.println(REQUESTS.AVSORTLISTPREIS + sp + str + sp + reverse);
      oos.writeObject(ordnung);

      ordnung = (SuchOrdnung) ois.readObject();
    } catch (IOException | ClassNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return ordnung;
  }

  @Override
  public Vector<Artikel> AV_sortListPreis(Vector<Artikel> artikelList, boolean reverse) {

    String str = "vec";
    try {
      out.println(REQUESTS.AVSORTLISTPREIS + sp + str + sp + reverse);
      oos.writeObject(artikelList);

      artikelList = (Vector<Artikel>) ois.readObject();
    } catch (IOException | ClassNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return artikelList;
  }

  @Override
  public SuchOrdnung AV_sortListRelevanz(SuchOrdnung ordnung) {

    try {
      out.println(REQUESTS.AVSORTLISTRELEVANZ);
      oos.writeObject(ordnung);

      ordnung = (SuchOrdnung) ois.readObject();
    } catch (IOException | ClassNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return ordnung;
  }

  ////////////////////////////////////////////////////////////////////////

  @Override
  public String EV_logDisplay() {
    out.println(REQUESTS.EVLOGDISPLAY);
    try {
      System.out.println(in.readLine());
    } catch (IOException e) {
      e.printStackTrace();
    }

    return null;
  }

  @Override
  public Ereignis EV_getEreignis(int ereignisNummer) throws ExceptionEreignisNichtGefunden {
    Ereignis ereignis = null;
    out.println(REQUESTS.EVGETEREIGNIS + sp + ereignisNummer);
    Exception e = waitForException();
    if(!(e==null)){throw (ExceptionEreignisNichtGefunden)e;}
    try {
      ereignis = (Ereignis)ois.readObject();
    } catch (ClassNotFoundException|IOException e2) {
      e.printStackTrace();
    }
    return ereignis;
  }

  @Override
  public Integer[] EV_getBestandsHistore(Artikel artikel) {
    Integer[] history = null;
    out.println(REQUESTS.EVGETBESTANDSHISTORIE + artikel.getName());
    try {
      history = (Integer[]) ois.readObject();
    } catch (ClassNotFoundException | IOException e) {
      e.printStackTrace();
    }
    return history;
  }

  @Override
  public Vector<Ereignis> EV_getLog() {
    Vector<Ereignis> log = null;
    out.println(REQUESTS.EVGETLOG);
    try {
      log = (Vector<Ereignis>) ois.readObject();
    } catch (ClassNotFoundException | IOException e) {
      e.printStackTrace();
    }
    return log;
  }

  @Override
  public SuchOrdnung EV_sucheEreignisse(String searchterm) {
    SuchOrdnung ordnung = null;
    out.println(REQUESTS.EVSUCHEEREIGNISSE + sp + searchterm);
    try {
      ordnung = (SuchOrdnung) ois.readObject();
    } catch (ClassNotFoundException | IOException e) {
      e.printStackTrace();
    }
    return ordnung;
  }

  // #endregion implement

  @Override
  public String createUserInterface() {
    out.println(REQUESTS.UI);

    try {
      return in.readLine();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      return null;
    }
  }

}
