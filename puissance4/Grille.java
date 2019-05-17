package puissance4;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class Grille extends JFrame implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	
	private State state;
	Couleur joueur;
	Plateau plateau = new Plateau();
	int[][] tableau_fin = plateau.getTableau();
	Bouton[][] tableau_bouton;
	int color;
	Couleur IA; //Est configuré par Config1 et Config2
	int IA_difficulte; //Est configuré par Config2, pas plus de 7 pour pas trop d'attente
	JProgressBar bar;
	
	public Grille(int difficulty, Couleur couleur) {
		
		IA_difficulte = difficulty;
		IA = couleur;
		
		//plateau.printTab();
		
		setState(State.INGAME);
		this.setTitle("Puissance 4");
		this.setSize(1000,700);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Font font = new Font("Serial",Font.BOLD,50);
		
		tableau_bouton = new Bouton[6][7];
		
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		JPanel grille = new JPanel();
		grille.setLayout(new GridLayout(6,7));
		panel.add(grille);
		
		if(IA != Couleur.VIDE) {
			bar = new JProgressBar(0);
			bar.setStringPainted(true);
			panel.add("South",bar);
		}

		
		for (int i=0;i<6;i++){
			for (int j=0;j<7;j++) {
				Bouton bouton = new Bouton(i,j,Couleur.fromInt(plateau.getTableau()[i][j]),this);
				tableau_bouton[i][j] = bouton;
				bouton.setFont(font);
				bouton.addActionListener(this);
				grille.add(bouton);
				
			}
		}
		
		this.joueur = Couleur.JAUNE;
		this.setContentPane(panel);
		if (joueur.equals(IA)) {	//Premier tour de l'IA si besoin
			if(isState(State.INGAME)) {
				Coup coupIA = plateau.IA_Decision(joueur, IA_difficulte, this);
				//System.out.println("L'IA joue y="+coupIA.y + " x=" + coupIA.x); // voir l.111, indique le premier coup jou� par l'IA
				placerPiece(joueur,coupIA.y,coupIA.x);
				if(plateau.verifWin(joueur)) {
					Victoire victoire = new Victoire(joueur, this, IA_difficulte, IA);
					victoire.victoire.setAlwaysOnTop(true);
				}
				joueur = joueur.opponent();
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		
		Bouton cible = (Bouton) event.getSource();
		int x = cible.x;
		int y = cible.y;
		
		if(isState(State.INGAME)) {

			if(placerPiece(joueur, y, x)) {
				if(plateau.verifWin(joueur)) {
					Victoire victoire = new Victoire(joueur, this, IA_difficulte, IA);
					victoire.victoire.setAlwaysOnTop(true);
				}
				joueur = joueur.opponent();
			}
			
			if (joueur==IA) {
				if(isState(State.INGAME)) {
					
					Grille ceci = this;
					
					Thread thread = new Thread(new Runnable() {
						public void run() {
							ceci.setState(State.WAITING);
							Coup coupIA = plateau.IA_Decision(joueur, IA_difficulte, ceci);
							//System.out.println("L'IA joue "+coupIA); //voir l.74, indique le coup joué par l'IA
							placerPiece(joueur,coupIA.y,coupIA.x);
							if(plateau.verifWin(joueur)) {
								Victoire victoire = new Victoire(joueur,ceci, IA_difficulte, IA);
								victoire.victoire.setAlwaysOnTop(true);
							}
							joueur = joueur.opponent();
							ceci.setState(State.INGAME);
						}
					});
					thread.start();
				}
			}
		}
	}
	
	public boolean placerPiece(Couleur couleur, int ligne, int colonne) {
		
		if(plateau.placerPiece(new Coup(ligne, colonne, couleur))) {
			
			tableau_bouton[ligne][colonne].setColor(couleur);
			return true;
			
		}else return false;
		

		
	}
	
	public void setState(State state) {
		
		this.state = state;
		
	}
	
	public boolean isState(State state) {
		
		return this.state == state;
		
	}

	
	
}