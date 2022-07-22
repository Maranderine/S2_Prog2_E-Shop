package UserInterface.GUI;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

/**
 * eigener CellRenderer um Zelle in einer Tabelle wie Button zu rendern
 */
class ButtonRenderer extends JButton implements TableCellRenderer {
   String tooltip;

   //Constructor
   public ButtonRenderer(String tooltip) {
      setOpaque(true);
      setToolTipText(tooltip);
   }

   public Component getTableCellRendererComponent(JTable table, Object obj, 
         boolean isSelected, boolean hasFocus, int row, int column) {
         setText((obj ==null) ? "":obj.toString());
      return this;
   }
 }

