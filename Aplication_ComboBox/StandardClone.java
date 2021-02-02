package Aplication_ComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class StandardClone extends JDialog{
	public static final long serialVersionUID = 1L;
	
	Klasa a = null;
	Klasa b = null;
	
	JLabel tytulGora = new JLabel("Klasa a = new Klasa()");
	JLabel setImieA = new JLabel("ustaw imie dla obiektu a: ");
	JLabel setHowOldA = new JLabel("ustaw wiek dla obiektu a: ");
	JLabel tytulDol = new JLabel("Klasa b = a || a.clone()");
	JLabel setImieB = new JLabel("ustaw imie dla obiektu b: ");
	JLabel setHowOldB = new JLabel("ustaw wiek dla obiektu b: ");
	JLabel dataObiektA = new JLabel("Dane dla klasy obiektu a: ");
	JLabel dataObiektB = new JLabel("Dane dla klasy obiektu b: ");
	
	JTextField imieA = new JTextField(5);
	JTextField oldA = new JTextField(5);
	JTextField imieB = new JTextField(5);
	JTextField oldB = new JTextField(5);
	JTextField dataA = new JTextField(10);
	JTextField dataB = new JTextField(10);
	
	JButton setA = new JButton("Przypisanie Danych do obiektu a");
	JButton setB = new JButton("Przypisanie Danych do obiektu b");
	JCheckBox clone = new JCheckBox("Klonowanie Obiektu");
	
	public StandardClone(final PanelWithMenu pwm){
		this.setLayout(new GridBagLayout());
		pwm.setExtendedState(JFrame.ICONIFIED);
		
		this.add(tytulGora,new GBCd(0,0,4,1).setInsets(1));
		this.add(setImieA,new GBCd(0,1,1,1).setInsets(1));
		this.add(imieA,new GBCd(1,1,1,1).setInsets(1).setFill(GBCd.HORIZONTAL).setWeight(1, 0));
		this.add(setHowOldA,new GBCd(2,1,1,1).setInsets(1));
		this.add(oldA,new GBCd(3,1,1,1).setInsets(1).setFill(GBCd.HORIZONTAL).setWeight(1, 0));
		
		this.add(tytulDol,new GBCd(0,2,4,1).setInsets(1));
		this.add(setImieB,new GBCd(0,3,1,1).setInsets(1).setWeight(0, 1));
		this.add(imieB,new GBCd(1,3,1,1).setInsets(1).setFill(GBCd.HORIZONTAL).setWeight(1, 0));
		this.add(setHowOldB,new GBCd(2,3,1,1).setInsets(1).setAnchor(GBCd.EAST));
		this.add(oldB,new GBCd(3,3,1,1).setInsets(1).setFill(GBCd.HORIZONTAL).setWeight(1, 0));
		
		this.add(dataObiektA,new GBCd(0,4,1,1).setInsets(1));
		this.add(dataA,new GBCd(1,4,3,1).setAnchor(GBCd.WEST).setWeight(1, 0).setFill(GBCd.HORIZONTAL));
		this.add(dataObiektB,new GBCd(0,5,1,1).setInsets(1));
		this.add(dataB,new GBCd(1,5,3,1).setAnchor(GBCd.WEST).setWeight(1, 0).setFill(GBCd.HORIZONTAL));
		
		this.add(setA,new GBCd(4,0,1,1).setInsets(1).setFill(GBCd.VERTICAL).setWeight(0, 1));
		this.add(setB,new GBCd(4,1,1,1).setInsets(1).setFill(GBCd.VERTICAL).setWeight(0, 1));
		this.add(clone,new GBCd(4,2,1,4).setInsets(1).setFill(GBCd.VERTICAL).setWeight(0, 1));
		
		this.setVisible(true);
		this.pack();
		
		setA.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				a = new Klasa();
				try{
					if(clone.isSelected()){
					      b = a.clone();
					}else{b = a;}
					
					a.setImie(imieA.getText());
					a.setWiekKlasa(Integer.parseInt(oldA.getText()));
					
					dataA.setText("imie = "+a.getImie()+" wiek = "+a.getWiek());
					dataB.setText("imie = "+b.getImie()+" wiek = "+b.getWiek());
				}catch(CloneNotSupportedException v){ JOptionPane.showMessageDialog(null, v); }
				
			}
		});
		
		setB.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				a = new Klasa();
				try{
					if(clone.isSelected()){
					      b = a.clone();
					}else{b = a;}
					
					a.setImie(imieA.getText());
					a.setWiekKlasa(Integer.parseInt(oldA.getText()));
					
					b.setImie(imieB.getText());
					b.setWiekKlasa(Integer.parseInt(oldB.getText()));
					
					dataA.setText("imie = "+a.getImie()+" wiek = "+a.getWiek());
					dataB.setText("imie = "+b.getImie()+" wiek = "+b.getWiek());
				}catch(CloneNotSupportedException v){ JOptionPane.showMessageDialog(null, v); }
			}
		});
		
		//Przywracania okna g³ównego po zamkniêciu okna dialogowego
		this.addComponentListener(new ComponentAdapter(){
			@Override
			public void componentHidden(ComponentEvent e){
				pwm.setExtendedState(JFrame.NORMAL);
			}
		});
	}
}
final class Klasa implements Cloneable{
	private String imie;
	protected Wiek wiek = new Wiek();
	protected void setImie(String imie){ this.imie = imie;}
	protected String getImie(){ return imie; }
	protected void setWiekKlasa(int w){ wiek.setWiek(w); }
	protected int getWiek(){
		return wiek.getWiek();
	}
	@Override
	protected Klasa clone()throws CloneNotSupportedException{
		Klasa k = (Klasa)super.clone();
		k.wiek = k.wiek.clone();
		return k;
	}
}
final class Wiek implements Cloneable{
	private int wiek;
	protected void setWiek(int wiek){ this.wiek = wiek; }
	protected int getWiek(){ return wiek; }
	@Override
	protected Wiek clone()throws CloneNotSupportedException{
		return (Wiek)super.clone();
	}
	
}
class GBCd extends GridBagConstraints{
	private static final long serialVersionUID = 1L;
	public GBCd(int gridx,int gridy){
		this.gridx = gridx; this.gridy = gridy;
	}
	public GBCd(int gridx,int gridy,int gridwidth,int gridheight){
		this.gridx = gridx; this.gridy = gridy; this.gridwidth = gridwidth; this.gridheight = gridheight;
	}
	public GBCd setFill(int fill){
		this.fill = fill;
		return this;
	}
	public GBCd setAnchor(int anchor){
		this.anchor = anchor;
		return this;
	}
	public GBCd setWeight(int weightx,int weighty){
		this.weightx = weightx; this.weighty = weighty;
		return this;
	}
	public GBCd setInsets(int x){
		this.insets = new Insets(x,x,x,x);
		return this;
	}
	public GBCd setInsets(int top,int left,int bottom,int right){
		this.insets = new Insets(top,left,bottom,right);
		return this;
	}
}


