package UserInterface.GUI;
import javax.swing.JOptionPane;

public class InfoBox {
    
    public void infoBox(String infoMessage, String titleBar)
    {
        JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
    }
    
}