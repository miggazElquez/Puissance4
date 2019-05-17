package puissance4;

public enum Couleur {
	VIDE,JAUNE,ROUGE;
	
	public static Couleur fromInt(int i) {
		switch (i) {
		case 0:
			return VIDE;
		case 1:
			return ROUGE;
		default:
			return JAUNE;
		}
	}
	
	public int toInt() {
		if (this.equals(VIDE)) return 0;
		if (this.equals(ROUGE)) return 1;
		return 2; //JAUNE
	}
	
	public Couleur opponent() {
		if (this.equals(ROUGE)) return JAUNE;
		if (this.equals(JAUNE)) return ROUGE;
		return VIDE; //VIDE est son adversaire ???
	}
}
