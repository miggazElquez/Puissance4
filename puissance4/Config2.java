package puissance4;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Config2 extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	
	JButton noIA;
	JButton start;
	JButton IAstart;
	JComboBox<String> box;

	Config2(){
		
		Font texte = new Font("Arial", Font.BOLD,35);
		Font start_IAstart = new Font("Arial", Font.BOLD,20);
		this.setTitle("Configuration");
		this.setSize(500,300);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		JPanel panel1 = new JPanel();
		panel1.setLayout(new GridLayout(4,1));
		String text = "  Niveau de difficult� de l'IA ?";
		String[] choix = {"                       1","                       2","                       3","                       4","                       5","                       6","                       7"};
		box = new JComboBox<String>(choix);
		JLabel test = new JLabel(text);
		test.setFont(texte);
		panel1.add(test);
		box.setFont(texte);
		panel1.add(box);
		JPanel panel2 = new JPanel();
		panel2.setLayout(new GridLayout(1,2));
		start = new JButton("Vous commencez");
		start.setToolTipText("Vous commencez à jouer et vous jouerez les jaunes.");
		IAstart = new JButton("L'IA commence");
		IAstart.setToolTipText("L'IA commence à jouer et vous jouerez les rouges.");
		start.setFont(start_IAstart);
		IAstart.setFont(start_IAstart);
		start.addActionListener(this);
		IAstart.addActionListener(this);
		panel2.add(start);
		panel2.add(IAstart);
		panel1.add(panel2);
		noIA = new JButton("Jouer sans IA");
		noIA.setToolTipText("Cliquer pour jouer en local contre une autre personne.");
		noIA.setFont(start_IAstart);
		noIA.addActionListener(this);
		panel1.add(noIA);
		this.add(panel1);
		this.setVisible(true);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		
		JButton cible = (JButton) event.getSource();
		
		if(cible == IAstart) {
			
			Grille newGrille = new Grille(box.getSelectedIndex() + 1, Couleur.JAUNE);
			newGrille.setVisible(true);
			this.dispose();
			
		}else {
			
			if(cible == start) {
				
				Grille newGrille = new Grille(box.getSelectedIndex() + 1, Couleur.ROUGE);
				newGrille.setVisible(true);
				this.dispose();
				
			}else {
				
				if(cible == noIA) {
					
					Grille newGrille = new Grille(1, Couleur.VIDE);
					newGrille.setVisible(true);
					this.dispose();
					
				}else {
					
					System.exit(0);
					
				}
			}
		
		}
		
	}

}
