package puissance4;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JSlider;


public class Config extends JFrame{
	
	private static final long serialVersionUID = 1L;
	
	ArrayList<Component> toDisable;
	JButton start;
	JRadioButton joueurCommence, IACommence;
	Couleur couleurIA = Couleur.VIDE;
	boolean modeChoisi = false;

	public Config() {
		
		this.setTitle("Configuration");
		this.setSize(500,300);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setAlwaysOnTop(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		this.add(panel);
		
		//panel.setLayout(new BoxLayout(panel,BoxLayout.PAGE_AXIS));
		panel.setLayout(new GridLayout(0,1));
		
	
		//**************IA********************
		
		Font IA_yes_IA_no_font = new Font("Arial", Font.BOLD, 20);
		ButtonGroup IA = new ButtonGroup();
		JPanel IA_panel = new JPanel();
		JRadioButton IA_yes = new JRadioButton("Un joueur");
		JRadioButton IA_no = new JRadioButton("Deux joueurs");
		IA.add(IA_yes);
		IA.add(IA_no);
		IA_panel.add(IA_yes);
		IA_panel.add(IA_no);
		IA_yes.setToolTipText("Jouer contre une IA");
		IA_no.setToolTipText("Jouer contre un autre joueur en local");
		IA_yes.setFont(IA_yes_IA_no_font);
		IA_no.setFont(IA_yes_IA_no_font);
		
		
		
		panel.add(IA_panel);
		
		toDisable = new ArrayList<Component>();
		
		IA_yes.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent event) {
				for (Component comp : toDisable) {
					comp.setEnabled(true);
				}
				modeChoisi = true;
				updateStart();
			}
		});
		
		IA_no.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent event) {
				for (Component comp : toDisable) {
					comp.setEnabled(false);
				}
				couleurIA = Couleur.VIDE;
				modeChoisi = true;
				updateStart();
			}
		});
		
		
		JSeparator sep = new JSeparator();
		panel.add(sep);
		
		//Choix de la difficulté
		
		Font diff_font = new Font("ARIAL", Font.BOLD, 15);
		JSlider diff = new JSlider(0,10); //Difficulté entre 0 et x
		diff.setFont(diff_font);
		Font text_font = new Font("ARIAL", Font.BOLD, 20);
		String text = "Niveau de difficulté de l'IA ?";
		JLabel choix_label = new JLabel(text);
		choix_label.setFont(text_font);
		choix_label.setHorizontalAlignment(JLabel.CENTER);
		
		diff.setToolTipText("Choisissez la difficulté de l'IA."
				+ " Pour une difficulté de 0, elle jouera aléatoirement."
				+ " Attention, plus la difficulté augmente, plus le temps de calcul augmente");
		
		diff.setMinorTickSpacing(1);
		diff.setMajorTickSpacing(1);
		diff.setPaintTicks(true);
		diff.setPaintLabels(true);

		
		toDisable.add(diff);
		
		panel.add(choix_label);
		panel.add(diff);
		
		
		
		
		
		
		//Choix de la couleur
		
		Font joueur_IA_font = new Font("Arial", Font.BOLD, 20);
		JPanel panelCouleur = new JPanel();
		ButtonGroup couleur = new ButtonGroup();
		joueurCommence = new JRadioButton("Vous commencez");
		IACommence = new JRadioButton("L'IA commence");
		couleur.add(joueurCommence);
		couleur.add(IACommence);
		panelCouleur.add(joueurCommence);
		panelCouleur.add(IACommence);
		joueurCommence.setToolTipText("Vous joueurez le premier coup. (votre couleur : jaune)");
		IACommence.setToolTipText("L'IA joueura le premier coup. (votre couleur : rouge)");
		joueurCommence.setFont(joueur_IA_font);
		IACommence.setFont(joueur_IA_font);
		
		joueurCommence.addActionListener(
			(ActionEvent event) -> {couleurIA = Couleur.ROUGE;updateStart();}
		);
		
		IACommence.addActionListener(
			(ActionEvent event) -> {couleurIA = Couleur.JAUNE;updateStart();}
		);
		

		toDisable.add(choix_label);
		toDisable.add(joueurCommence);
		toDisable.add(IACommence);
		panel.add(panelCouleur);
		
		
		
		Font start_font = new Font("Arial", Font.BOLD, 20);
		start = new JButton("Play !");
		start.setFont(start_font);
		panel.add(start);
		
		Config ceci = this;
		start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					Grille newGrille = new Grille(diff.getValue(), couleurIA);
					newGrille.setVisible(true);
					newGrille.tableau_bouton[5][3].requestFocusInWindow(); //Met le focus sur le bon bouton
					ceci.dispose();
				}
		});
		
		updateStart();
		
		
		
		
	}
	
	public void updateStart() {
		if (!modeChoisi) {
			start.setEnabled(false);
		}else if (joueurCommence.isEnabled() && !(joueurCommence.isSelected() || IACommence.isSelected())) {
			start.setEnabled(false);
		}else {
			start.setEnabled(true);
		}
	}
	
	
}
