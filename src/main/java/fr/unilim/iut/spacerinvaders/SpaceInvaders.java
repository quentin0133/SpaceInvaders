package fr.unilim.iut.spacerinvaders;

import org.junit.Before;

import fr.unilim.iut.spaceinvaders.utils.HorsEspaceJeuException;

public class SpaceInvaders {
	private static final String MARQUE_FIN_LIGNE = "\n";
	private static final char MARQUE_VIDE = '.';
	private static final char MARQUE_VAISSEAU = 'V';
	private int longueur;
	private int hauteur;
	private Vaisseau vaisseau;
	
	public class SpaceInvadersTest {
			
	private SpaceInvaders spaceinvaders;
	
		@Before
		public void initialisation() {
		    spaceinvaders = new SpaceInvaders(15, 10);
		}
	
		//...
	}
	
	public SpaceInvaders(int longueur, int hauteur) {
		this.longueur = longueur;
		this.hauteur = hauteur;
	}
	
	@Override
	public String toString() {
	     return recupererEspaceJeuDansChaineASCII();
	}

	private String recupererEspaceJeuDansChaineASCII() {
		StringBuilder espaceDeJeu = new StringBuilder();
	     for (int y = 0; y < hauteur; y++) {
	         for (int x = 0; x < longueur; x++) {
	        	espaceDeJeu.append(recupererMarqueDeLaPosition(y, x));
	         }
	         espaceDeJeu.append(MARQUE_FIN_LIGNE);
	     }
	     return espaceDeJeu.toString();
	}

	private char recupererMarqueDeLaPosition(int y, int x) {
		char marque;
		if (aUnVaisseau() && vaisseau.occupeLaPosition(x, y))
			marque=MARQUE_VAISSEAU;
		else
			marque=MARQUE_VIDE;
		return marque;
	}

	private boolean aUnVaisseau() {
		return vaisseau!=null;
	}

	public void positionnerUnNouveauVaisseau(int x, int y) {
			
		if (!estDansEspaceJeu(x, y))
			throw new HorsEspaceJeuException("La position du vaisseau est en dehors de l'espace jeu");
		
		vaisseau = new Vaisseau(x, y);

	}

	private boolean estDansEspaceJeu(int x, int y) {
		return (x >= 0) && (x < longueur) && (y >= 0) && (y < hauteur);
	}
}
