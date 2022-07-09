package UserInterface.GUI;
import javax.swing.JOptionPane;

public class InfoBox {
    String errorString ="";
    
    public void infoBox(String infoMessage, String titleBar)
    {
        JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
    }

    public void infoBox(String titleBar)
    {
        JOptionPane.showMessageDialog(null, errorString, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
        resetString();
    }

    public void addString(String err){
        errorString += err + "\n";
    }

    public void resetString(){
        errorString = "";
    }

    public boolean errorAccurred(){
        if(!(errorString.equals(""))){return true;}
        return false;
    }
    
}