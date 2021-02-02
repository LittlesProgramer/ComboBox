package Aplication_ComboBox;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.Dimension;
import javax.swing.JFrame;

final class BuggyButtonTest{
    public static void main(String[] args){
      EventQueue.invokeLater(new Runnable(){
    	 @SuppressWarnings("all")
    	 Panel_Logowania pl = new Panel_Logowania();
    	 @Override
    	 public void run(){
    		 pl.getContentPane().setBackground(Color.green);
    		 pl.getContentPane().setForeground(Color.yellow);
    		 pl.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    		 pl.setLocation(100, 100);
    		 Toolkit tool = Toolkit.getDefaultToolkit();
    		 Dimension d = tool.getScreenSize(); d.width = 500; d.height = 400;
    		 pl.setSize(d);
    		 pl.setAlwaysOnTop(true);
    		 pl.setVisible(true);
    		 pl.pack();
    	 }
      });
   }
}




