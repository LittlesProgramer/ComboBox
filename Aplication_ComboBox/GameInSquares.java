package Aplication_ComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.geom.Rectangle2D;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JComponent;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.awt.GridLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class GameInSquares extends JDialog {
	private static final long serialVersionUID = 1L;
	Rysuj panel = new Rysuj();
	
	public GameInSquares(final PanelWithMenu pwm){
		super(pwm,"Gra w Kwadraty",false);
		this.add(panel);
		Toolkit tool = Toolkit.getDefaultToolkit();
		Dimension d = tool.getScreenSize(); d.width = 600; d.height = 500;
		this.setSize(d);
		panel.setOpaque(false);
		panel.setBackground(Color.magenta);
		panel.setForeground(Color.red);
		panel.getRootPane().setOpaque(false);
		panel.getRootPane().getLayeredPane().setBackground(Color.yellow);
		panel.getRootPane().getLayeredPane().setOpaque(false);
		panel.getRootPane().setBackground(Color.blue);
		panel.getRootPane().getContentPane().setBackground(new Color(130,170,150));
		panel.getRootPane().getContentPane().setForeground(Color.black);
		
		pwm.setExtendedState(JFrame.ICONIFIED);
		this.setVisible(true);
		
		this.addComponentListener(new ComponentAdapter(){
			@Override
			public void componentHidden(ComponentEvent e){
				pwm.setExtendedState(JFrame.NORMAL);
			}
		});
	}
}

@SuppressWarnings("serial")
class Rysuj extends JPanel implements Cloneable{
	private static final int squareXsize = 10;
	ArrayList<Rectangle2D> listaRect = new ArrayList<Rectangle2D>();
	private Rectangle2D current;
	
	public Rysuj(){
		this.addMouseListener(new MouseListener(){
			@Override
			public void mouseExited(MouseEvent e){}
			@Override
			public void mousePressed(MouseEvent e){
				current = znajdz(e.getPoint());
				if(current == null) dodaj(e.getPoint());
			}
			@Override
			public void mouseEntered(MouseEvent e){}
			@Override
			public void mouseClicked(MouseEvent e){
				current = znajdz(e.getPoint());
				if(current != null && e.getClickCount() >= 2) usun(current);
			}
			@Override
			public void mouseReleased(MouseEvent e){}
		});
		
		this.addMouseMotionListener(new MouseMotionListener(){
			@Override
			public void mouseDragged(MouseEvent e){
				current = znajdz(e.getPoint());
				if(current != null){
					current.setFrame(e.getX() - squareXsize/2, e.getY() - squareXsize/2, squareXsize, squareXsize);
					repaint();
				}
			}
			@Override
			public void mouseMoved(MouseEvent e){
				current = znajdz(e.getPoint());
				if(current == null){
					setCursor(Cursor.getDefaultCursor());
				}else{ setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR)); }
			}
		});
	}
	@Override
	protected Rysuj clone() throws CloneNotSupportedException{
		return (Rysuj)super.clone();
	}
	public void usun(Rectangle2D rect){
		if(rect == current) current = null;
		
		listaRect.remove(rect);
		repaint();
	}
	public Rectangle2D znajdz(Point2D p){
		for(Rectangle2D r : listaRect){
			if(r.contains(p)) return r;
		}
		return null;
	}
	public void dodaj(Point2D p){//Point p
		double x = p.getX();
		double y = p.getY();
		
		current = new Rectangle2D.Double(x - squareXsize/2,y - squareXsize/2,squareXsize,squareXsize);
		listaRect.add(current);
		repaint();
	}
	@Override
	public void paintComponent(Graphics g){
		Graphics2D g2 = (Graphics2D)g;
		for(Rectangle2D r : listaRect){
			g2.draw(r);
		}
	}
}
