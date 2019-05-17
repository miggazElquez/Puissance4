package puissance4;

import javax.swing.JButton;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Bouton extends JButton{

	private static final long serialVersionUID = 1L;
	
	Couleur color;
	int x;
	int y;
	Grille grille;
	
	Font font = new Font("Times New Roman", Font.BOLD,160);
	
	public Bouton(int y,int x, Couleur color, Grille grille) {
		
		this.setColor(color);
		this.x = x;
		this.y = y;
		this.grille = grille;
		this.setBackground(new Color(255,255,255));
		
		Bouton ceci = this;
		this.addFocusListener(new FocusListener() {
			
			public void focusGained(FocusEvent event) {
				ceci.setBackground(new Color(240,240,240));
			}

			public void focusLost(FocusEvent event) {
				ceci.setBackground(new Color(255,255,255));
			}			
		});
		
		this.addKeyListener(new KeyListener() {

			public void keyPressed(KeyEvent event) {
				int keyCode = event.getKeyCode();
				int x_=0;
				int y_=0;
				
				if (keyCode == KeyEvent.VK_LEFT) x_=-1;
				if (keyCode == KeyEvent.VK_RIGHT) x_=1;
				if (keyCode == KeyEvent.VK_DOWN) y_=1;
				if (keyCode == KeyEvent.VK_UP) y_=-1;
				
				int nouv_x = x + x_;
				int nouv_y = y + y_;
				grille.tableau_bouton[nouv_y][nouv_x].requestFocusInWindow();
				
				
			}

			public void keyReleased(KeyEvent event) {
				//Do nothing
			}

			public void keyTyped(KeyEvent event) {
				//Do nothing
			}
		});
		
		
		
		this.addMouseListener(new MouseListener() {

			public void mouseClicked(MouseEvent arg0) {
				//System.out.println("Clicked " +x+" " +y);
			}

			public void mouseEntered(MouseEvent arg0) {
				//System.out.println("Entered " +x+" " +y);
			}

			public void mouseExited(MouseEvent arg0) {
				//System.out.println("Exited " +x+" " +y);
			}

			public void mousePressed(MouseEvent arg0) {
				//System.out.println("Pressed " +x+" " +y);
			}

			public void mouseReleased(MouseEvent arg0) {
				//System.out.println("Released " +x+" " +y);
			}
			
		});
		
	}
	
	public void setColor(Couleur color) {
		
		this.setFont(font);
		this.color = color;
		this.setText("\u25CF");
		if(color.equals(Couleur.VIDE)) {
			this.setText("");
		}else {
			if(color.equals(Couleur.ROUGE)) this.setForeground(Color.RED);
			if(color.equals(Couleur.JAUNE)) this.setForeground(Color.YELLOW);
		}
	}
}
