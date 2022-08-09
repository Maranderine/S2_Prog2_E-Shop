package Domain.Networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Vector;

import Domain.Eshop;
import Domain.Artikel.Artikel;
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
import UserInterface.UserSession;
import common.EshopInterface.BenutzerType;
import common.EshopInterface.REQUESTS;

public class SocketProcessor extends UserSession {

  private Socket clientSocket;
  private final Eshop eshop;
  private BufferedReader in;
  private PrintStream out; // nicht Writer, damit auch telnet-Client mit Server kommunizieren kann
  private ObjectInputStream ois;
  private ObjectOutputStream oos;

  public SocketProcessor(Eshop eshop, Socket socket) {
    super(eshop);
    this.eshop = eshop;
    clientSocket = socket;

    try {
      in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
      out = new PrintStream(clientSocket.getOutputStream());
      oos = new ObjectOutputStream(clientSocket.getOutputStream());
      ois = new ObjectInputStream(clientSocket.getInputStream());
    } catch (IOException e) {
      try {
        clientSocket.close();
      } catch (IOException e2) {
      }

      System.err.println("PROCESSOR - ERROR - error on stream create: " + e);
      return;

    }

    System.out.println("PROCESSOR - Connected to client: "
        + clientSocket.getInetAddress() + ":" + clientSocket.getPort());
  }

  /**
   * receives and processes client commands
   */
  public void Process() {

    String input;
    String[] inputList;
    REQUESTS request;
    boolean continueLoop = true;

    out.println("PROCESSOR - Processor Ready");

    do {

      try {
        // split into index + vars
        input = in.readLine();
        System.out.println("PROCESSOR - input: " + input);

        if (input == null) {
          System.out.println("PROCESSOR - interpreting quit!");
          request = REQUESTS.QUIT;
          inputList = new String[0];
        } else {
          // split into parts
          inputList = input.split(REQUESTS.splitter);

          // get request from first index
          request = REQUESTS.get(inputList[0]);

          // remove first index
          if (inputList.length > 1) {
            String[] copy = new String[inputList.length - 1];
            for (int j = 0, i = 1; i < inputList.length; j++, i++) {
              copy[j] = inputList[i];
            }
            inputList = copy;
          } else {
            inputList = new String[0];
          }
        }

      } catch (Exception e) {
        // System.out.println("PROCESSOR - ERROR - Fehler beim Lesen vom Client
        // (Aktion): ");
        // System.out.println(e.getMessage());
        continue;
      }

      continueLoop = Execute(request, inputList);

      // reset
      request = null;
    } while (continueLoop);

    System.out.println("PROCESSOR - connection " + clientSocket.getInetAddress()
        + ":" + clientSocket.getPort() + " terminated by user");

    // Verbindung beenden
    try {
      clientSocket.close();
    } catch (IOException e2) {
    }
  }

  /**
   * executes the processed clieent request
   * 
   * @param request   the request enum entry acting as aa keyword to execute aa
   *                  associated funktion
   * @param arguments arguments provided
   * @return boolean if the processing loop should quit
   */
  private boolean Execute(REQUESTS request, String[] arguments) {
    String back = "";
    System.out.println("PROCESSOR - execute " + request.name());

    String str;

    switch (request) {
      case REPLY:
        // sends first argument back to client
        out.println(arguments[0]);
        break;
      case LOGIN:
        BenutzerType user = eshop.login(this, arguments[0], arguments[1]);
        // send user type
        out.println(user.get());

        // send user haash seperatly
        if (user != BenutzerType.NONE) {
          out.println(this.userHash.toString());
        }

        break;
      case LOGOUT:

        eshop.logout(this);

        // System.out.println("login proc: " + ui.userHash.toString());

        break;
      case UI:
        // transmits the caalss name of used interface
        out.println(eshop.createUserInterface());

        break;
      case WVSETARTIKEL:
        try {
          eshop.WV_setArtikel(eshop.AV_findArtikelByName(arguments[0]), Integer.parseInt(arguments[1]));
        } catch (NumberFormatException | ExceptionArtikelNichtGefunden e4) {
          e4.printStackTrace();
        }
        break;
      case WKGETINHALT:
        try {
          oos.writeObject(eshop.WK_getInhalt());
        } catch (IOException e3) {
          e3.printStackTrace();
        }
        break;
      case WVGETWARENKORB:
        try {
          oos.writeObject(eshop.WV_getWarenkorb());
        } catch (IOException e3) {
          e3.printStackTrace();
        }
        break;
      case WVREMOVEARTIKEL:
        try {
          eshop.WV_removeArtikel(eshop.AV_findArtikelByName(arguments[0]));
        } catch (ExceptionArtikelNichtGefunden e3) {
          e3.printStackTrace();
        }
        break;
      case WVCLEARALL:
        eshop.WV_clearAll();
        break;
      case WVKAUFEN:
        try {
          eshop.WV_kaufen(arguments[0].getBytes());
          out.println("fehlerfrei");
        } catch (ExceptionArtikelCollection e3) {
          out.println("fehler");
          try {
            oos.writeObject(e3);
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
        break;
      case WVGETSUMME:
        out.println("" + eshop.WV_getSumme());
        break;
      case BVKUNDEHINZUFÜGEN:
        back = "fehlerfrei";
        try {
          eshop.BV_kundeHinzufügen(arguments[0], arguments[1], arguments[2], arguments[3], arguments[4]);
        } catch (ExceptionBenutzerNameUngültig e2) {
          try {
            oos.writeObject(e2);
            back = "fehler";
          } catch (IOException e) {
            e.printStackTrace();
          }
        }

        break;
      case BVMITARBEITERHINZUFÜGEN:
        back = "fehlerfrei";
        try {
          eshop.BV_mitarbeiterHinzufügen(arguments[0], arguments[1], arguments[2]);
        } catch (ExceptionBenutzerNameUngültig e) {
          try {
            oos.writeObject(e);
            back = "fehler";
          } catch (IOException e1) {
            e1.printStackTrace();
          }
        }
        out.println(back);
        break;
      case BVGETALLENUTZER:
        try {
          oos.writeObject(eshop.BV_getAllNutzer());
        } catch (IOException e) {
        }
        break;
      case AVDELETEARTIKEL:
        try {
          eshop.AV_deleteArtikel(arguments[0].getBytes(), arguments[1]);
          out.println("fehlerfrei");
        } catch (ExceptionArtikelKonnteNichtGelöschtWerden e2) {
          out.println("fehler");
          try {
            oos.writeObject(e2);
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
        break;
      case AVFINDARTIKELBYNAME:
        back = "fehlerfrei";
        Artikel artikel = null;
        try {
         artikel = eshop.AV_findArtikelByName(in.readLine());
        } catch (ExceptionArtikelNichtGefunden e) {
          try {
            back = "fehler";
            out.println(back);
            oos.writeObject(e);
            break;
          } catch (IOException e1) {
            e1.printStackTrace();
          }
        } catch (IOException e) {
          e.printStackTrace();
        }
        out.println(back);
        try {
          oos.writeObject(artikel);
        } catch (IOException e) {
        }
        break;
      case AVGETALLEARTIKELLIST:
        try {
          oos.writeObject(eshop.AV_getAlleArtikelList());
        } catch (IOException e) {
          e.printStackTrace();
        }
        break;
      case AVSETARTIKELNAME:
        try {
          eshop.AV_setArtikel(arguments[0].getBytes(), eshop.AV_findArtikelByName(arguments[1]), arguments[2]);
        } catch (ExceptionArtikelNameExistiertBereits | ExceptionArtikelNameUngültig
            | ExceptionArtikelNichtGefunden e) {
          /*out.println("fehler");
          try {
            oos.writeObject(e);
          } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
          }*/
        }
        break;
      case AVSETARTIKELBESTAND:
        try {
          eshop.AV_setArtikel(arguments[0].getBytes(), eshop.AV_findArtikelByName(arguments[1]), Integer.parseInt(arguments[2]));
        } catch (NumberFormatException | ExceptionArtikelUngültigerBestand | ExceptionArtikelNichtGefunden e) {
          e.printStackTrace();
        }
        break;
      case AVSETARTIKELDATABESTAND:
        try {
          eshop.AV_setArtikel(arguments[0].getBytes(), arguments[1], Integer.parseInt(arguments[2]));
        } catch (NumberFormatException | ExceptionArtikelNichtGefunden | ExceptionArtikelUngültigerBestand e) {
          e.printStackTrace();
        }
        break;
      case AVSETARTIKELPREIS:
        try {
          eshop.AV_setArtikel(arguments[0].getBytes(), eshop.AV_findArtikelByName(arguments[1]), Double.parseDouble(arguments[2]));
        } catch (NumberFormatException | ExceptionArtikelNichtGefunden e) {
          e.printStackTrace();
        }
        break;
      case AVSETARTIKELDATAPREIS:
        try {
          eshop.AV_setArtikel(arguments[0].getBytes(), arguments[1], Double.parseDouble(arguments[2]));
        } catch (NumberFormatException | ExceptionArtikelNichtGefunden e) {
          e.printStackTrace();
        }
        break;
      case AVSETARTIKELALL:
        try {
          eshop.AV_setArtikel(arguments[0].getBytes(), eshop.AV_findArtikelByName(arguments[1]), arguments[2], Integer.parseInt(arguments[3]), Double.parseDouble(arguments[4]));
        } catch (NumberFormatException | ExceptionArtikelNichtGefunden | ExceptionArtikelUngültigerBestand e) {
          e.printStackTrace();
        }
        break;
      case AVSETARTIKELDATAALL:
        try {
          eshop.AV_setArtikel(arguments[0].getBytes(), arguments[1], arguments[2], Integer.parseInt(arguments[3]), Double.parseDouble(arguments[4]));
        } catch (NumberFormatException | ExceptionArtikelNichtGefunden | ExceptionArtikelUngültigerBestand e) {
          e.printStackTrace();
        }
        break;
      case AVADDARTIKEL: 
        Artikel artikel2 = null;
        try {
          artikel2 = eshop.AV_addArtikel(arguments[0].getBytes(), arguments[1], Integer.parseInt(arguments[2]), Double.parseDouble(arguments[3]), Integer.parseInt(arguments[4]));
          oos.writeObject(artikel2);
        } catch (NumberFormatException | ExceptionArtikelExistiertBereits
            | ExceptionArtikelKonnteNichtErstelltWerden e) {
          e.printStackTrace();
        } catch (IOException e) {
          e.printStackTrace();
        }
        break;
      case AVARTIKELAUSGEBEN:
        break;
      case AVSORTLISTPREIS:
        break;
      case AVSORTLISTNAME:
        break;
      case EVLOGDISPLAY:
        eshop.EV_logDisplay();
        break;
      case EVGETEREIGNIS: 
        break;
      case EVGETBESTANDSHISTORIE:
        break;
      case EVGETLOG: 
        break;
      case QUIT:
        eshop.quit();
        return false;
      default:
        System.out.println("PROCESSOR - ERROR - unknown request!");
        break;
    }

    return true;
  }

}
