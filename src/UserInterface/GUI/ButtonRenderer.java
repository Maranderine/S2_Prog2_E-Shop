package UserInterface.GUI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;


class ButtonRenderer extends JButton implements TableCellRenderer {

   //Constructor
   public ButtonRenderer() {
      setOpaque(true);
      setToolTipText("in den Warenkorb");
   }

   public Component getTableCellRendererComponent(JTable table, Object obj, 
         boolean isSelected, boolean hasFocus, int row, int column) {
         setText((obj ==null) ? "":obj.toString());
      return this;
   }
 }

