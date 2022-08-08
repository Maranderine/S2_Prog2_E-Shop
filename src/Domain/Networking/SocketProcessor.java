package Domain.Networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.Socket;

import Domain.Eshop;
import Domain.Warenkorb.Warenkorb;
import Exceptions.Artikel.ExceptionArtikelNichtGefunden;
import Exceptions.Benutzer.ExceptionBenutzerNameUngültig;
import common.EshopInterface.REQUESTS;

public class SocketProcessor {

  private Socket clientSocket;
  private final Eshop eshop;
  private BufferedReader in;
  private PrintStream out; // nicht Writer, damit auch telnet-Client mit Server kommunizieren kann
  private ObjectInputStream ois;
  private ObjectOutputStream oos;

  public SocketProcessor(Eshop eshop, Socket socket) {

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
        System.out.println("PROCESSOR - ERROR - Fehler beim Lesen vom Client (Aktion): ");
        System.out.println(e.getMessage());
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

    System.out.println("PROCESSOR - execute " + request.name());

    switch (request) {
      case REPLY:
        //sends first argument back to client
        out.println(arguments[0]);
        break;
      case UI:
        // transmits the caalss name of used interface
        out.println(eshop.createUserInterface().getClass().getSimpleName());
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
        break;
      case WVGETSUMME:
        out.println(""+ eshop.WV_getSumme());
        break;
      case BVKUNDEHINZUFÜGEN:
        try {
          eshop.BV_kundeHinzufügen(arguments[0], arguments[1], arguments[2], arguments[3], arguments[4]);
        } catch (ExceptionBenutzerNameUngültig e2) {
          try {
            oos.writeObject(e2);
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
       
        break;
      case BVMITARBEITERHINZUFÜGEN:
        try {
          eshop.BV_mitarbeiterHinzufügen(arguments[0], arguments[1], arguments[2]);
        } catch (ExceptionBenutzerNameUngültig e) {
          try {
            oos.writeObject(e);
          } catch (IOException e1) {
            e1.printStackTrace();
          }
        }
        break;
      case BVGETALLENUTZER:
        try {
          oos.writeObject(eshop.BV_getAllNutzer());
        } catch (IOException e) {
        }
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
