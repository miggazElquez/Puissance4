package puissance4;

import java.util.ArrayList;

public class Plateau {

	public int[][] tableau;
	private static int VIDE = 0;
	private static int ROUGE = 1;
	private static int JAUNE = 2;
	
	public Plateau() {
		
		tableau = new int[6][7];
		
	}
	
	public int[][] getTableau() {
		return tableau;
	}
	
	//Cette fonction nous permet de voir o� sont les jetons (c'est un debug)
	public void printTab() {
		
		System.out.println("-----------------------------");
		for(int i=0; i<6; i++) {
			
			System.out.print("| ");
			
			for(int j=0; j<7; j++) {
				
				int piece = tableau[i][j];
				if(piece == VIDE) {
					System.out.print(" ");
				} else if(piece == ROUGE) {
					System.out.print("R");
				} else if(piece == JAUNE) {
					System.out.print("J");
				}
				
				System.out.print(" | ");
				
			}
			
			System.out.println("\n-----------------------------");
			
		}
	}
	
	

	
	public boolean placerPiece(Coup coup) {
		int ligne = coup.y;
		int colonne = coup.x;
		Couleur couleur = coup.couleur;
		if(ligne < 5) {
			
			if(tableau[ligne][colonne] == VIDE) {
				
				if(tableau[ligne+1][colonne] != VIDE) {
						
					tableau[ligne][colonne] = couleur.toInt();
					//this.printTab();
					
					return true;
					
				}else return false;
			}
			
		}else {
			
			if(tableau[ligne][colonne] == VIDE) {
				
				tableau[ligne][colonne] = couleur.toInt();
				//this.printTab();
				
				return true;
				
			}
			
			return false;
			
		}
		
		return false;
		
	}
	
	public void enleverPiece(Coup coup) {
		tableau[coup.y][coup.x] = VIDE;
	}
	
	public boolean verifWin(Couleur joueur) {return verifWin(joueur.toInt());}
	
	public boolean verifWin(int joueur) {
		
		boolean ligne,colonne;
		boolean diag1,diag2;
		for (int y=0;y<6;y++) {
			for (int x=0;x<7;x++){
				if (tableau[y][x] == joueur) {
					ligne = true;
					colonne = true;
					diag1 = true;
					diag2 = true;
					for (int i=1;i<4;i++) {
						if (x+i<7) {
							if (tableau[y][x+i] != joueur) {
								ligne = false;
							}
						}else ligne = false;
						
						if (y+i<6) {
							if (tableau[y+i][x] != joueur) {
								colonne = false;
							}
						}else colonne = false;
						
						if (y+i<6 && x+i<7) {
							if (tableau[y+i][x+i] != joueur) {
								diag1 = false;
							}
						}else diag1 = false;
						
						if (y+i <6 && x-i >= 0) {
							if (tableau[y+i][x-i] != joueur) {
								diag2 = false;
							}
						}else diag2 = false;
						
					}
					if (ligne || colonne || diag1 || diag2) {
						return true;
					}
				}
			}
		}
		return false;
	}	

	public boolean full() {
		return listeCoup(Couleur.ROUGE).size() == 0;
	}
	
	public ArrayList<Coup> listeCoup(int couleur){return listeCoup(Couleur.fromInt(couleur));}
	
	public ArrayList<Coup> listeCoup(Couleur couleur) {
		
		ArrayList<Coup> result = new ArrayList<Coup>();
		for (int x=0;x<7;x++) {
			for (int y=5;y>-1;y--) {
				if (tableau[y][x] == VIDE) {
					result.add(new Coup(y,x,couleur));
					break;
				}
			}
		}
		return result;
	}
	
	private int nbSerie3(Couleur joueur) {
		
		int valJoueur = joueur.toInt();
		int nb=0;
		int ligne,colonne;
		int diag1,diag2;
		for (int y=0;y<6;y++) {
			for (int x=0;x<7;x++){
				if (tableau[y][x] == valJoueur) {
					ligne = 1;
					colonne = 1;
					diag1 = 1;
					diag2 = 1;
					for (int i=1;i<3;i++) {
						if (x+i<7) {
							if (tableau[y][x+i] != valJoueur) {
								ligne = 0;
							}
						}else ligne = 0;
						
						if (y+i<6) {
							if (tableau[y+i][x] != valJoueur) {
								colonne = 0;
							}
						}else colonne = 0;
						
						if (y+i<6 && x+i<7) {
							if (tableau[y+i][x+i] != valJoueur) {
								diag1 = 0;
							}
						}else diag1 = 0;
						
						if (y+i <6 && x-i >= 0) {
							if (tableau[y+i][x-i] != valJoueur) {
								diag2 = 0;
							}
						}else diag2 = 0;
						
					}
					nb += ligne + colonne + diag1 + diag2;
				}
			}
		}		
		
		return nb;
		
	}
	
	
	public int valeur(int joueur) {return valeur(Couleur.fromInt(joueur));}
	
	public int valeur(Couleur joueur) {
		
		return nbSerie3(joueur) - nbSerie3(joueur.opponent());
		
	}
	
	public Coup IA_Decision(Couleur couleur,int profondeur, Grille frame) {

		int valJoueur = couleur.toInt();
		ArrayList<Coup> listeCoupPossible = listeCoup(couleur);
		//DEBUG
		/*for (Coup coup : listeCoupPossible) {
			System.out.println("coup " + coup.y + " " + coup.x);
		}*/
		int index;
		
		frame.bar.setMaximum(listeCoupPossible.size());
		frame.bar.setValue(0);
		
		if (profondeur==0) {
			index = (int) (Math.random()*(listeCoupPossible.size()));
			return listeCoupPossible.get(index);
		}
		
		ArrayList<Coup> bonCoup = new ArrayList<Coup>();
		int score;
		int valMax = -1000;
		int i=0;
		//int  taille = listeCoupPossible.size();
		for (Coup coup : listeCoupPossible) {
			placerPiece(coup);
			score = min(valJoueur, profondeur,valMax,true);
			if (score > valMax) {
				bonCoup = new ArrayList<Coup>();
				bonCoup.add(coup);
				valMax = score;
			}else if (score == valMax) {
				bonCoup.add(coup);
			}
			enleverPiece(coup);
			i++;

			frame.bar.setValue(i);
			
		}
		
		index = (int) (Math.random()*(bonCoup.size() - 1));
		//System.out.println("On a choisi entre " + bonCoup.size() + " coups possibles");
		return bonCoup.get(index);
		
	}
	
	
	private int min(int couleur, int profondeur,int valMinPossible, boolean firstStep) {
		if (verifWin(couleur)) {
			return 1000;
		}else if (verifWin((couleur==1)? 2:1)) {
			return -1000;
		}
		
		if (profondeur==0) {
			return valeur(couleur);
		}
		
		int score;
		int valMin = 1000;
		for (Coup coup : listeCoup((couleur==1)? 2:1)){
			placerPiece(coup);
			score = max(couleur,profondeur-1,valMin);
			if (score < valMin) {
				valMin = score;
			}
			enleverPiece(coup);
			if (firstStep) {
				if (valMin < valMinPossible) {
					return valMin;
				}
			}else {
				if (valMin <= valMinPossible) {
					return valMin;
				}
			}
		}
		return valMin;
	}

	private int max(int couleur, int profondeur,int valMaxPossible) {
		if (verifWin(couleur)) {
			return 1000;
		}else if (verifWin((couleur==1)? 2:1)) {
			return -1000;
		}
		
		if (profondeur==0) {
			return valeur(couleur);
		}
		
		int score;
		int valMax = -1000;
		for (Coup coup : listeCoup(couleur)) {
			placerPiece(coup);
			score = min(couleur, profondeur-1,valMax,false);
			if (score > valMax) {
				valMax = score;
			}
			enleverPiece(coup);
			if (valMax >= valMaxPossible) {
				return valMax;
			}
		}
		return valMax;
	}
	
	
}
