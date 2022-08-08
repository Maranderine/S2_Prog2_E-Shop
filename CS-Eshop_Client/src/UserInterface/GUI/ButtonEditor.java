package UserInterface.GUI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


class ButtonEditor extends DefaultCellEditor{

    protected JButton btn;
    private String lb;
    private Boolean clicked;

    public ButtonEditor(JCheckBox txt){
       super(txt);
    
       btn = new JButton();
       btn.setOpaque(true);
       btn.addActionListener(new ActionListener(){
          public void actionPerformed(ActionEvent ae){
             fireEditingStopped();
          }
       });
    }
    //Override Methods
    @Override
    public Component getTableCellEditorComponent(JTable table, Object obj,
        boolean selected, int row, int col){

       lb=(obj==null)?"":obj.toString();
       btn.setText(lb);
       clicked=true;
       return btn;
    }
 
    public Object getCellEditorValue(){

       if(clicked){
          JOptionPane.showMessageDialog(btn, lb+"clicked");
       }
 
        clicked = false;
        return new String(lb);
    }
 
    @Override
    public boolean stopCellEditing(){
       clicked=false;
       return super.stopCellEditing();
    }
 
    @Override
    protected void fireEditingStopped(){
       super.fireEditingStopped();
    }
  }