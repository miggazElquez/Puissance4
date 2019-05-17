package puissance4;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Victoire implements ActionListener{
	
	boolean toClose = false;
	JFrame caller;
	JButton non;
	JButton oui;
	JButton replay;
	JFrame victoire;
	int difficulty;
	Couleur couleur_config;
	
	public Victoire(Couleur couleur, Grille caller, int difficulty_call, Couleur couleur_config_call) {

		difficulty = difficulty_call;
		couleur_config = couleur_config_call;
		
		caller.setState(State.END);
		Font texte = new Font("Arial", Font.BOLD,15);
		Font replay_texte = new Font("Arial", Font.BOLD,25);
		Font oui_non = new Font("Arial", Font.BOLD,70);
		victoire = new JFrame("Victoire des " + couleur + "S !"); 
		victoire.setSize(500,300);
		victoire.setResizable(false);
		victoire.setLocationRelativeTo(null);
		JPanel panel1 = new JPanel();
		panel1.setLayout(new GridLayout(3,1));
		JPanel panel2 = new JPanel();
		panel2.setLayout(new GridLayout(1,2));
		oui = new JButton("Oui");
		oui.setToolTipText("Cliquer pour relancer une partie et sa configuration.");
		non = new JButton("Non");
		non.setToolTipText("Cliquer pour quitter le jeu.");
		String text = "   Bravo aux " + couleur + "S qui remportent la partie ! Voulez vous rejouer ?";
		JLabel test = new JLabel(text);
		test.setFont(texte);
		panel1.add(test);
		oui.setFont(oui_non);
		non.setFont(oui_non);
		oui.addActionListener(this);
		non.addActionListener(this);
		panel2.add(oui);
		panel2.add(non);
		panel1.add(panel2);
		replay = new JButton("Rejouer avec la même configuration");
		replay.setToolTipText("Rejouer avec la même difficult� et la m�me couleur (m�me personne : IA ou vous qui commence).");
		replay.setFont(replay_texte);
		replay.addActionListener(this);
		panel1.add(replay);
		victoire.add(panel1);
		victoire.setVisible(true);
		this.caller = caller;
		
	}



	@Override
	public void actionPerformed(ActionEvent event) {
		
		JButton cible = (JButton) event.getSource();
		
		if(cible == oui) {
			
			victoire.dispose();
			caller.dispose();
			Config newGrille = new Config();
			newGrille.setVisible(true);
			
		}else {
			
			if(cible == replay) {
				
				victoire.dispose();
				caller.dispose();
				Grille newGrille = new Grille(difficulty, couleur_config);
				newGrille.setVisible(true);
				
			}else {
		
				System.exit(0);
				
			}
		
		}
		
	}
		
}