package fr.unilim.iut.spacerinvaders;

public class Vaisseau {
	private int x;
	private int y;
	
	public Vaisseau(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public boolean occupeLaPosition(int x, int y) {
		return (this.x==x) && (this.y==y);
	}

	public void seDeplacerVersLaDroite() {
		this.x = this.x + 1 ;
	}
	
	public void seDeplacerVersLaGauche() {
		this.y = this.y + 1 ;
	}
	
	public int abscisse() {
       return this.x;
	}

	public int ordonne() {
		return this.y;
	}
}
