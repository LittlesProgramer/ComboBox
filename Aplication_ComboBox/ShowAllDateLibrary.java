package Aplication_ComboBox;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.awt.EventQueue;
import javax.swing.JApplet;
import javax.swing.JEditorPane;
import javax.swing.JOptionPane;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.BufferedInputStream;
import java.io.ObjectInputStream;
import java.io.File;

public class ShowAllDateLibrary extends JApplet{
	private static final long serialVersionUID = 1L;
	private JEditorPane editor = new JEditorPane();
	List<Books> lista = new ArrayList<Books>();
	
	@Override
	public void init(){
		EventQueue.invokeLater(new Runnable(){
			@Override
			public void run(){
				add(editor);
				editor.setText("Brak certyfikatu, st¹d problem z wyswietleniem danych przez applet !!!");
				editor.setEditable(false);
				editor.setEnabled(false);
				editor.setSelectedTextColor(Color.red);
				editor.setSelectionColor(Color.green);
				editor.setDragEnabled(true);
				editor.setVisible(true);
				
				lista = (List<Books>)LibraryBook.getAllDateinLibrary();
				
				for(Books el : lista){
					editor.setText("imie: "+el.getName()+" nazwisko: "+el.getsurName()+" dataWypo¿yczenia: "+el.getDateLand()+" tytu³: "+el.getTitleBook()+"\n");
				}
			}
		});
	}
}
