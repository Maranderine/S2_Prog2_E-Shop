package UserInterface.GUI;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.*;

public class KundeGUI extends JPanel{
    GUI gui;

    public KundeGUI(GUI gui){
        initializeLayout();
        initializeAction();
        setVisible(true);
    }

    public void initializeLayout(){
        setPreferredSize(new Dimension(600,600));
        setLayout(null);
        setBackground(new Color(71,39,79));

    }

    public void initializeAction(){

    }
    
}
