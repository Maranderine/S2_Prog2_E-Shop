package UserInterface.GUI;
import javax.swing.JOptionPane;


/**
 * Klasse die anzeigen von MessageDialogs erleichtert.
 * anstelle das jedes mal neu showMessageDialog angezeigt wird, ein Objekt das mitgegebenen String als messageDialog rausgibt
 */
public class InfoBox {
    String errorString ="";
    
    /**
     * Methode zum rausgeben eines mitgegeben Strings als Message Dialog
     * @param infoMessage
     * @param titleBar
     */
    public void infoBox(String infoMessage, String titleBar)
    {
        JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
    }


    /**
     * Methode zum rausgeben des im Objekt gespeicherten Strings als Message Dialogs 
     * @param titleBar
     */
    public void infoBox(String titleBar){
        JOptionPane.showMessageDialog(null, errorString, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
        resetString();
    }

    public Integer confirmBox(String message){
        return JOptionPane.showInternalConfirmDialog(null, message, "information", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * speichert mitgegebenen String in InfoBox
     * @param err
     */
    public void addString(String err){
        errorString += err + "\n";
    }

    /**
     * resets internen String in InfoBox
     */
    public void resetString(){
        errorString = "";
    }

    public boolean errorAccurred(){
        if(!(errorString.equals(""))){return true;}
        return false;
    }
    
}