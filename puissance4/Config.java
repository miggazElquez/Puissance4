package puissance4;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;


public class Config extends JFrame{
	
	ArrayList<Component> toDisable;
	JSlider diff;
	JButton start;
	Couleur couleurIA = Couleur.VIDE;
	boolean modeChoisi = false;

	public Config() {
		
		this.setTitle("Configuration");
		this.setSize(500,300);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		
		JPanel panel = new JPanel();
		this.add(panel);
		
		//panel.setLayout(new BoxLayout(panel,BoxLayout.PAGE_AXIS));
		panel.setLayout(new GridLayout(0,1));
		
	
		//**************IA********************
		
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
		
		diff = new JSlider(0,7);
		String text = "Niveau de difficulté de l'IA ?";
		JLabel choix_label = new JLabel(text);
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
		
		JPanel panelCouleur = new JPanel();
		ButtonGroup couleur = new ButtonGroup();
		JRadioButton joueurCommence = new JRadioButton("Vous commencez");
		JRadioButton IACommence = new JRadioButton("L'IA commence");
		couleur.add(joueurCommence);
		couleur.add(IACommence);
		panelCouleur.add(joueurCommence);
		panelCouleur.add(IACommence);
		joueurCommence.setToolTipText("Vous joueurez le premier coup. (votre couleur : jaune");
		IACommence.setToolTipText("L'IA joueura le premier coup. (votre couleur : rouge)");
		
		joueurCommence.addActionListener(
			(ActionEvent event) -> {couleurIA = Couleur.ROUGE;updateStart();}
		);
		
		IACommence.addActionListener(
			(ActionEvent event) -> {couleurIA = Couleur.JAUNE;updateStart();}
		);
		
		
		toDisable.add(joueurCommence);
		toDisable.add(IACommence);
		panel.add(panelCouleur);
		
		
		
		start = new JButton("Play !");
		panel.add(start);
		
		Config ceci = this;
		start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					Grille newGrille = new Grille(diff.getValue(), couleurIA);
					newGrille.setVisible(true);
					ceci.dispose();
				}
		});
		
		updateStart();
		
		
		
		
	}
	
	public void updateStart() {
		if (!modeChoisi) {
			start.setEnabled(false);
		}else if (diff.isEnabled() && couleurIA == Couleur.VIDE) {
				start.setEnabled(false);
		}else {
			start.setEnabled(true);
		}
	}
	
	
}
