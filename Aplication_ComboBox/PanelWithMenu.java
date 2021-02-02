package Aplication_ComboBox;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JLabel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

final public class PanelWithMenu extends JFrame { //Modu³ panelu g³ównego menu
	private static final long serialVersionUID = 1L;
	//Podstawowa rozmiar elementu menu
	private Dimension dim = new Dimension(50,21);
	
	PanelWithMenu pwm = this;
	JLabel label = new JLabel();
	JMenuBar pasek = new JMenuBar();
	JMenuBar pasek1 = new JMenuBar();
	
	//Uprawnienia Administracyjne
	JMojeMenu admin = new JMojeMenu("Administrator");
	JMenuItem create_count = new JMenuItem("Zak³adanie/Usuwanie konta");
	JMenuItem show_all_count = new JMenuItem("Poka¿ wszystkie konta");
	JMenuItem adm_authorized = new JMenuItem("Nadawanie/Odbieranie uprawnieñ");
	JMenuItem change_pass = new JMenuItem("Zmiana has³a");
	
	//Obs³uga Plików
	JMojeMenu file = new JMojeMenu("Plików obs³uga");
	JMenuItem open_file_1 = new JMenuItem("Otwieranie okna dialogowego plików pierwszym sposobem");
	JMenuItem open_file_2 = new JMenuItem("Otwieranie okna dialogowego plików drugim sposobem");
	JMenuItem open_file_3a = new JMenuItem("Przegl¹danie zawartoœci folderu(z ksi¹¿ki zaawansowana Java)");
	JMenuItem open_file_3b = new JMenuItem("Przegl¹danie zawartoœci folderu/podfolderów(z ksi¹¿ki zaawansowana Java)");
	
	//Obs³uga zapisu plików za pomoc¹ kilku Klas IO
	JMojeMenu sort_of_save_and_open_file = new JMojeMenu("Rodzaje zapisu/odczytu plików");
	JMenuItem write_and_read_text = new JMenuItem("Zapis/Odczyt tekstowy plików");
	JMenuItem write_and_read_binary = new JMenuItem("Zapis/Odczyt binarny plików");
	JMenuItem free_access_save_and_open = new JMenuItem("Tryb swobodnego zapisu metod¹ - Random AccessFile");
	JMenuItem serializable = new JMenuItem("Zapis/Odczyt obiektów za pomoc¹ Serializacji");
	JMenuItem paths_files = new JMenuItem("Zapis/Odczyt za pomoc¹ nowych metod klas Files i Paths");
	
	//Klonowanie
	JMojeMenu cloning = new JMojeMenu("Klonowanie obiektów");
	JMenuItem standard_cloning = new JMenuItem("Standardowe klonowanie");
	JMenuItem serializable_cloning = new JMenuItem("Klonowanie za pomoc¹ serializacji w (Java Zaawansowana)");
	
	//Operacje na tablicach
	JMojeMenu operation_on_the_table = new JMojeMenu("Operacje na tablicach");
	JMenuItem sort_data_table = new JMenuItem("rodzaje sortowania danych prostych oraz obiektowych za pomoca(Comparatora interfejsu Comprable)");
	
	//Operacje za pomoc¹ myszki
	JMojeMenu operation_on_the_mouse = new JMojeMenu("Obs³uga myszki");
	JMenuItem mouse_game_1 = new JMenuItem("Gra w kwadraty");
	JMenuItem mouse_game_2 = new JMenuItem("Gra w kó³ko i krzy¿yk");
	JMenuItem mouse_game_3 = new JMenuItem("Gra w Statki przez sieæ ( Jak siê douczê sieci) ");
	
	//Obs³uga Kolekcji
	JMojeMenu collections = new JMojeMenu("Obs³uga Kolekcji");
	JMenuItem lista = new JMenuItem("Kolekcja listy");
	JMenuItem zbiór = new JMenuItem("Kolekcja zbiorów");
	JMenuItem mapa = new JMenuItem("Kolekcja Mapy");
	
	//Wizualizacja Aplikacji
	JMojeMenu vizualisation = new JMojeMenu("Wizualizacja aplikacji");
	JMenuItem vizualisation_aplication = new JMenuItem("Wizualizacja aplikacji");
	
	//Info o Aplikacji
	JMojeMenu about_aplication = new JMojeMenu("O aplikacji");
	JMenuItem ab_ap = new JMenuItem("Info o aplikacji");
	
	//Obs³uga Apletów
	JMojeMenu aplets = new JMojeMenu("Obs³uga apletów");
	JMenuItem library_Book = new JMenuItem("Wypo¿yczalnia Ksi¹¿ek");
	
	//Obs³uga Dzwiêku
	JMojeMenu sounds = new JMojeMenu("Obs³uga dzwieków");
	JMenuItem music_Symfony = new JMenuItem("Symfonie Bethowena");
	
	//Obs³uga Dokumentów PDF
	JMojeMenu pdf_format = new JMojeMenu("Obs³uga dokumentów PDF");
	JMenuItem show_pdf = new JMenuItem("Poka¿ PDF");
	
	//Wyjscie
	JMojeMenu exit = new JMojeMenu("Menu Exit");
	JMenuItem exit_aps = new JMenuItem("Wyjœcie z aplikacji");
	
	@SuppressWarnings("all")
	public PanelWithMenu(Panel_Logowania panLog){
		
		this.add(label);
		this.setJMenuBar(pasek);
		//this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setSize(800, 500);
		this.getContentPane().setBackground(Color.yellow);
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setDefaultCloseOperation(JFrame.NORMAL);
		//this.setAlwaysOnTop(true);
		panLog.setExtendedState(JFrame.ICONIFIED);
		this.setVisible(true);
		
		admin.setPreferredSize(dim);
		pasek.add(admin);
		admin.add(create_count);
		create_count.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				new Create_Delete_Count(pwm);
			}
		});
		admin.add(show_all_count);
		show_all_count.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				new Show_All_Count(pwm);
			}
		});
		admin.add(adm_authorized);
		admin.add(change_pass);
		change_pass.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				new Change_Password_Class(pwm);
			}
		});
		
		file.setPreferredSize(dim);
		pasek.add(file);
		file.add(open_file_1);
		open_file_1.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				new Open_File_Menner1(pwm,label);
			}
		});
		file.add(open_file_2);
		open_file_2.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				new Open_File_Menner2(pwm,label);
			}
		});
		file.add(open_file_3a);
		open_file_3a.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				new Open_File_Menner3(pwm);
			}
		});
		file.add(open_file_3b);
		open_file_3b.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				new Open_File_Menner4(pwm);
			}
		});
		
		pasek.add(sort_of_save_and_open_file);
		sort_of_save_and_open_file.add(write_and_read_text);
		write_and_read_text.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				new Write_Read_Text(pwm);
			}
		});
		
		sort_of_save_and_open_file.setPreferredSize(dim);
		sort_of_save_and_open_file.add(write_and_read_binary);
		write_and_read_binary.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				new Write_Read_Binary(pwm);
			}
		});
		sort_of_save_and_open_file.add(free_access_save_and_open);
		free_access_save_and_open.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				new Write_Read_RAF(pwm);
			}
		});
		sort_of_save_and_open_file.add(serializable);
		serializable.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				new Serializacja(pwm);
			}
		});
		sort_of_save_and_open_file.add(paths_files);
		paths_files.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				new FilesAndPaths(pwm);
			}
		});
		
		cloning.setPreferredSize(dim);
		pasek.add(cloning);
		cloning.add(standard_cloning);
		standard_cloning.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				new StandardClone(pwm);
			}
		});
		cloning.add(serializable_cloning);
		
		operation_on_the_table.setPreferredSize(dim);
		pasek.add(operation_on_the_table);
		operation_on_the_table.add(sort_data_table);
		sort_data_table.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				new SortData(pwm);
			}
		});
		
		operation_on_the_mouse.setPreferredSize(dim);
		pasek.add(operation_on_the_mouse);
		operation_on_the_mouse.add(mouse_game_1);
		mouse_game_1.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				new GameInSquares(pwm);
			}
		});
		
		operation_on_the_mouse.add(mouse_game_2);
		mouse_game_2.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				new TicTacToe(pwm);
			}
		});
		operation_on_the_mouse.add(mouse_game_3);
		
		collections.setPreferredSize(dim);
		pasek.add(collections);
		collections.add(lista);
		lista.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				new ColectionList(pwm);
			}
		});
		collections.add(zbiór);
		zbiór.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				new ColectionZbior(pwm);
			}
		});
		collections.add(mapa);
		mapa.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				new ColectionMapa(pwm);
			}
		});
		
		vizualisation.setPreferredSize(dim);
		pasek.add(vizualisation);
		vizualisation.add(vizualisation_aplication);
		vizualisation_aplication.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				new Vizualisation(pwm);
			}
		});
		
		about_aplication.setPreferredSize(dim);
		pasek.add(about_aplication);
		about_aplication.add(ab_ap);
		ab_ap.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				new AplicationInfo(pwm);
			}
		});
		
		aplets.setPreferredSize(dim);
		pasek.add(aplets);
		aplets.add(library_Book);
		library_Book.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				new LibraryBook(pwm);
			}
		});
		
		sounds.setPreferredSize(dim);
		pasek.add(sounds);
		sounds.add(music_Symfony);
		music_Symfony.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				new MusicSymfony(pwm);
			}
		});
		
		pdf_format.setPreferredSize(dim);
		pasek.add(pdf_format);
		pdf_format.add(show_pdf);
		show_pdf.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				new ShowMePdf(pwm);
			}
		});
		
		exit.setPreferredSize(dim);
		pasek.add(exit);
		exit.add(exit_aps);
		exit_aps.addActionListener(new ActionListener(){
			JLabel labs = new JLabel("Zamkniêcie za 3 s");
			@Override
			public void actionPerformed(ActionEvent e){
				pwm.getContentPane().add(labs); // dlaczego siê nie wyœwietla
				Exit_Aps eps = new Exit_Aps(pwm);
			}
		});
		
		// Pêtla ustawiaj¹ca nas³uch na ka¿dym z elementów menu
		for(int x = 0 ; x < pasek.getComponentCount(); x++){
			((JMenu)pasek.getComponentAtIndex(x)).addChangeListener(new Nasluch());
		}
	}
	
	// Klasa Wewnetrzna  ustawiaj¹ca odpowiedni¹ wielkoœæ elementów menu w zale¿noœci od
	// wielkoœci napisu znajduj¹cego siê na przycisku menu
	class Nasluch implements ChangeListener{

		private Rectangle2D rectStringWidth = null;
		
		@Override
		public void stateChanged(ChangeEvent e){
			
			JMojeMenu m = (JMojeMenu)e.getSource();
			rectStringWidth = m.getRectStringSize();
			
			if(m.isSelected()){
				m.setPreferredSize(new Dimension((int)rectStringWidth.getWidth()+12,(int)rectStringWidth.getHeight()));

				if(rectStringWidth.getWidth() > pasek.getWidth() - ((pasek.getComponentCount()-1) * dim.width)){
					
					for(int x = 0 ; x < pasek.getComponentCount() ; x++){
						if(x != pasek.getComponentIndex(m)){
							pasek.getComponentAtIndex(x).setPreferredSize(new Dimension(0,21));
						}
					}
					pasek.revalidate();
				}
			}else{ 
				if(rectStringWidth.getWidth() > pasek.getWidth()- ((pasek.getComponentCount()-1) * dim.width)){
					for(int x = 0 ; x < pasek.getComponentCount() ; x++){
					   if(x != pasek.getComponentIndex(m)){
							pasek.getComponentAtIndex(x).setPreferredSize(dim);
					   }
					}
				}
				m.setPreferredSize(dim);	
			}
			    m.revalidate();
		}
	}
	
   // Klasa odpowiedzialna za menu( metoda getRectStringSize() podaje
   // rozmiar ka¿dego z napisów znajduj¹cych siê na menu
   class JMojeMenu extends JMenu{
		
		private String str = null;
		private Rectangle2D RectStringSize = null;
		
		public JMojeMenu(String s){ this.str = s; this.setText(s); }
		@Override
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D)g;
			FontRenderContext context = g2.getFontRenderContext();
			Font f = g2.getFont();
			RectStringSize = f.getStringBounds(str, context); 
		}
		public Rectangle2D getRectStringSize(){ return RectStringSize; }
	}
}
