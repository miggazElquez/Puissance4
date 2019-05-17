package puissance4;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Config1 extends JFrame implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	
	JButton oui;
	JButton non;
	
	public Config1() {
		
		Font texte = new Font("Arial", Font.BOLD,30);
		Font oui_non = new Font("Arial", Font.BOLD,70);
		this.setTitle("Configuration");
		this.setSize(500,300);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		JPanel panel1 = new JPanel();
		panel1.setLayout(new GridLayout(2,1));
		String text = "  Voulez vous jouer avec une IA ?";
		JLabel test = new JLabel(text);
		test.setFont(texte);
		panel1.add(test);
		JPanel panel2 = new JPanel();
		panel2.setLayout(new GridLayout(1,2));
		oui = new JButton("Oui");
		oui.setToolTipText("Cliquer pour jouer contre une Intelligence Artificielle configurable.");
		non = new JButton("Non");
		non.setToolTipText("Cliquer pour jouer en local contre une autre personne.");
		oui.setFont(oui_non);
		non.setFont(oui_non);
		oui.addActionListener(this);
		non.addActionListener(this);
		panel2.add(oui);
		panel2.add(non);
		panel1.add(panel2);
		this.add(panel1);
		this.setVisible(true);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {

		JButton cible = (JButton) event.getSource();
		
		if(cible == oui) {
			
			Config2 newConfig2 = new Config2();
			newConfig2.setVisible(true);
			this.dispose();
			
		}else {
			
			if(cible == non) {
				
				Grille.IA = Couleur.VIDE;
				Grille newGrille = new Grille(1, Couleur.VIDE);
				newGrille.setVisible(true);
				this.dispose();
				
			}else {
		
				System.exit(0);
			
			}

		}
		
	}
	
}

