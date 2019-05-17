package puissance4;

public class Coup {
	int x;
	int y;
	Couleur couleur;
	
	public Coup(int y, int x, Couleur couleur){
		
		if (!isValid()) {
			System.out.println("ATTENTION !!! un coup a été créé avec y = "+y+" et x = "+x);
		}
		this.x = x;
		this.y = y;
		this.couleur = couleur;
	}
	
	public boolean isValid() {
		if (y>=0 && y<6 && x>=0 && x<7) {
			return true;
		}else {
			return false;
		}
	}
	
	public String toString() {
		return "Coup(y="+y+", x="+x+", j="+couleur+")";
	}
}
