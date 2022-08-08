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
import Domain.BenutzerObjekte.Benutzerverwaltung.BeutzerType;
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
import UserInterface.CUI;
import UserInterface.UserInterface;
import UserInterface.GUI.GUI;
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

  // #region implement

  @Override
  public void BV_kundeHinzufügen(String name, String username, String password, String email, String address)
      throws ExceptionBenutzerNameUngültig {
      String sp = REQUESTS.splitter;
      out.println(REQUESTS.BVKUNDEHINZUFÜGEN + sp + name + sp + username + password + sp + email + sp + address);
      try {
        ExceptionBenutzerNameUngültig e = (ExceptionBenutzerNameUngültig)ois.readObject();
        throw e;
      } catch (ClassNotFoundException | IOException e) {
      }
  }

  @Override
  public void BV_mitarbeiterHinzufügen(String name, String username, String password)
      throws ExceptionBenutzerNameUngültig {
      String sp = REQUESTS.splitter;
      out.println(REQUESTS.BVMITARBEITERHINZUFÜGEN + sp + name + sp + username + sp + password);
      try {
        ExceptionBenutzerNameUngültig e = (ExceptionBenutzerNameUngültig)ois.readObject();
        throw e;
      } catch (ClassNotFoundException | IOException e) {
      }
    // TODO Auto-generated method stub

  }

  @Override
  public Vector<Benutzer> BV_getAllNutzer() {
    Vector<Benutzer> nutzer = new Vector<Benutzer>();
    out.println(REQUESTS.BVGETALLENUTZER);
    try {
      nutzer = (Vector<Benutzer>) ois.readObject();
    } catch (ClassNotFoundException|IOException e) {
      e.printStackTrace();
    }
    return nutzer;
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
    HashMap<Artikel, Integer> inhalt = new HashMap<Artikel, Integer>();
    out.println(REQUESTS.WKGETINHALT);
    try {
      inhalt = (HashMap<Artikel, Integer>)ois.readObject();
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
      korb = (Warenkorb)ois.readObject();
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
    // TODO Auto-generated method stub
    return null;
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
