package fr.unilim.iut.spaceinvaders.utils;

public class Position {
	public int x;
	public int y;

	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int abscisse() {
		return this.x;
	}

	public int ordonnee() {
		return this.y;
	}
	
	public void changerAbscisse(int nouvelleAbscisse) {
        this.x = nouvelleAbscisse;
   }

   public void changerOrdonnee(int nouvelleOrdonnee) {
       this.y = nouvelleOrdonnee;
   }


}