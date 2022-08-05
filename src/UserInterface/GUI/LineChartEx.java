package UserInterface.GUI;

import java.awt.*;
import javax.swing.*;
import java.awt.geom.*;

public class LineChartEx extends JPanel{
    Integer[] coordinates={100,20,30};
    int mar=50;

    public LineChartEx(){
    }

    public LineChartEx(Integer[] coord){
        coordinates = coord;
    }
    
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g1=(Graphics2D)g;
        g1.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        int width=getWidth();
        int height=getHeight();
        g1.draw(new Line2D.Double(mar,mar,mar,height-mar));
        g1.draw(new Line2D.Double(mar,height-mar,width-mar,height-mar));
        g1.drawString(""+getMax(), mar-30, mar+10);
        g1.drawString("30", width-mar+10, height-mar);
        double x=(double)(width-2*mar)/(30);//coordinates.length-1);
        double scale=(double)(height-2*mar)/getMax();

        for(int i=1; i<=30;i++){
            double x1=mar+i*x;
            g1.draw(new Line2D.Double(x1,height-mar+5,x1,height-mar-5));
        }

        for(int i=1; i<=10;i++){
            int y = (height-2*mar)/10;
            double y1=mar+i*y;
            g1.draw(new Line2D.Double(mar-5,y1,mar+5,y1));
        }

        g1.setPaint(Color.BLUE);
        for(int i=0;i<coordinates.length-1;i++){
            double x1=mar+i*x;
            double y1=height-mar-scale*coordinates[i];
            double x2=mar+(i+1)*x;
            double y2=height-mar-scale*coordinates[i+1];
            g1.draw(new Line2D.Double(x1,y1,x2,y2));
            //g1.fill(new Ellipse2D.Double(x1,y1,4,4));
        }
        
        
        
    }
    private int getMax(){
        int max=-Integer.MAX_VALUE;
        for(int i=0;i<coordinates.length;i++){
            if(coordinates[i]>max)
                max=coordinates[i];
           
        }return max;
    }       
        
    public static void main(String args[]){
        JFrame frame =new JFrame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.add(new LineChartEx());
    frame.setSize(400,400);
    frame.setLocation(200,200);
    frame.setVisible(true);
    }
}