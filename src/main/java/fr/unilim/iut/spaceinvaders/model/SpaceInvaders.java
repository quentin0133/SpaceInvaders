package fr.unilim.iut.spaceinvaders.model;

import fr.unilim.iut.spaceinvaders.Constante;
import fr.unilim.iut.spaceinvaders.DebordementEspaceJeuException;
import fr.unilim.iut.spaceinvaders.Dimension;
import fr.unilim.iut.spaceinvaders.Direction;
import fr.unilim.iut.spaceinvaders.HorsEspaceJeuException;
import fr.unilim.iut.spaceinvaders.Position;
import fr.unilim.iut.spaceinvaders.moteurJeu.Commande;
import fr.unilim.iut.spaceinvaders.moteurJeu.Jeu;
import fr.unilim.iut.vaisseau.MissileException;

public class SpaceInvaders implements Jeu {
	
	private int longueur;
	private int hauteur;
	private Vaisseau vaisseau;
	private Missile missile;
	private Envahisseur envahisseur;
	private boolean direction;

	public SpaceInvaders(int longueur, int hauteur) {
		this.longueur = longueur;
		this.hauteur = hauteur;
	}

	@Override
	public boolean etreFini() {
		return false; 
	}

	@Override
	public String toString() {
		return recupererEspaceJeuDansChaineASCII();
	}

	public String recupererEspaceJeuDansChaineASCII() {
		StringBuilder espaceDeJeu = new StringBuilder();
		for (int y = 0; y < hauteur; y++) {
			for (int x = 0; x < longueur; x++) {
				espaceDeJeu.append(recupererMarqueDeLaPosition(x, y));
			}
			espaceDeJeu.append(Constante.MARQUE_FIN_LIGNE);
		}
		return espaceDeJeu.toString();
	}

	public char recupererMarqueDeLaPosition(int x, int y) {
		char marque;
		if (this.aUnVaisseauQuiOccupeLaPosition(x, y))
			marque = Constante.MARQUE_VAISSEAU;
		else if (this.aUnMissileQuiOccupeLaPosition(x, y))
				marque = Constante.MARQUE_MISSILE;
		else if(this.aUnEnvahisseurQuiOccupeLaPosition(x, y))
				marque = Constante.MARQUE_ENVAHISSEUR;
		else marque = Constante.MARQUE_VIDE;
		return marque;
	}

	public boolean aUnVaisseauQuiOccupeLaPosition(int x, int y) {
		return (aUnVaisseau() && vaisseau.occupeLaPosition(x, y));
	}
	
	public boolean aUnMissileQuiOccupeLaPosition(int x, int y) {
		return (aUnMissile() && missile.occupeLaPosition(x, y));
	}
	
	public boolean aUnEnvahisseurQuiOccupeLaPosition(int x, int y) {
		return (aUnEnvahisseur() && envahisseur.occupeLaPosition(x, y));
	}

	public boolean aUnVaisseau() {
		return vaisseau != null;
	}
	
	public boolean aUnMissile() {
		return missile != null;
	}
	
	public boolean aUnEnvahisseur() {
		return envahisseur != null;
	}

	public boolean estDansEspaceJeu(int x, int y) {
		return (((x >= 0) && (x < longueur)) && ((y >= 0) && (y < hauteur)));
	}

	public void deplacerVaisseauVersLaDroite() {
		if (vaisseau.abscisseLaPlusADroite() < (longueur - 1))
			vaisseau.deplacerHorizontalementVers(Direction.DROITE);
		if (!estDansEspaceJeu(vaisseau.abscisseLaPlusADroite(), vaisseau.ordonneeLaPlusHaute())) {
			vaisseau.positionner(longueur - vaisseau.getDimension().longueur(), vaisseau.ordonneeLaPlusHaute());
		}
	}
	
	public void deplacerVaisseauVersLaGauche() {
		if (0 < vaisseau.abscisseLaPlusAGauche())
			vaisseau.deplacerHorizontalementVers(Direction.GAUCHE);
		if (!estDansEspaceJeu(vaisseau.abscisseLaPlusAGauche(), vaisseau.ordonneeLaPlusHaute())) {
			vaisseau.positionner(0, vaisseau.ordonneeLaPlusHaute());
		}
	}

	public void positionnerUnNouveauVaisseau(Dimension dimension, Position position, int vitesse) {

		int x = position.abscisse();
		int y = position.ordonnee();

		if (!estDansEspaceJeu(x, y))
			throw new HorsEspaceJeuException("La position du vaisseau est en dehors de l'espace jeu");

		int longueurVaisseau = dimension.longueur();
		int hauteurVaisseau = dimension.hauteur();

		if (!estDansEspaceJeu(x + longueurVaisseau - 1, y))
			throw new DebordementEspaceJeuException("Le vaisseau déborde de l'espace jeu vers la droite à cause de sa longueur");
		if (!estDansEspaceJeu(x, y - hauteurVaisseau + 1))
			throw new DebordementEspaceJeuException("Le vaisseau déborde de l'espace jeu vers le bas à cause de sa hauteur");

		vaisseau = new Vaisseau(dimension,position,vitesse);
	}
	
	public void initialiserJeu() {
		Position positionVaisseau = new Position(this.longueur/2,this.hauteur-1);
		Dimension dimensionVaisseau = new Dimension(Constante.VAISSEAU_LONGUEUR, Constante.VAISSEAU_HAUTEUR);
		positionnerUnNouveauVaisseau(dimensionVaisseau, positionVaisseau, Constante.VAISSEAU_VITESSE);
		Position positionEnvahisseur = new Position(this.longueur/2, Constante.ENVAHISSEUR_HAUTEUR-1);
		Dimension dimensionEnvahisseur = new Dimension(Constante.ENVAHISSEUR_LONGUEUR, Constante.ENVAHISSEUR_HAUTEUR);
		positionnerUnNouveauEnvahisseur(dimensionEnvahisseur, positionEnvahisseur, Constante.ENVAHISSEUR_VITESSE);
	}

	public void tirerUnMissile(Dimension dimensionMissile, int vitesseMissile) {
		if ((vaisseau.getDimension().hauteur()+ dimensionMissile.hauteur()) > this.hauteur ) {
			throw new MissileException("Pas assez de hauteur libre entre le vaisseau et le haut de l'espace jeu pour tirer le missile");
		}

		this.missile = this.vaisseau.tirerUnMissile(dimensionMissile,vitesseMissile);

	}
	
	public void deplacerEnvahisseur() {
        if(this.envahisseur.abscisseLaPlusAGauche() <= 0)
            this.direction = true;
        else if(this.envahisseur.abscisseLaPlusADroite() >= Constante.ESPACEJEU_LONGUEUR - 1)
            this.direction = false;
        if(this.direction) {
            this.envahisseur.deplacerHorizontalementVers(Direction.DROITE);
        }
        else {
            this.envahisseur.deplacerHorizontalementVers(Direction.GAUCHE);
        }
    }

	@Override
	public void evoluer(Commande commandeUser) {
		if (commandeUser.gauche) 
			deplacerVaisseauVersLaGauche();
		

		if (commandeUser.droite) 
			deplacerVaisseauVersLaDroite();
		
		if (commandeUser.tir && !this.aUnMissile()) 
			tirerUnMissile(new Dimension(Constante.MISSILE_LONGUEUR, Constante.MISSILE_HAUTEUR), Constante.MISSILE_VITESSE);
		
		if (this.aUnMissile()) 
			deplacerMissile();

		if(this.aUnEnvahisseur()) {
			this.deplacerEnvahisseur();
		}

	}

	public Vaisseau recupererVaisseau() {
		return this.vaisseau;
	}

	public Missile recupererMissile() {
		return this.missile;
	}

	public void deplacerMissile() {
		if(this.missile.ordonneeLaPlusBasse() <= 0)
			this.missile = null;
		else
			this.missile.deplacerVerticalementVers(Direction.HAUT_ECRAN);
		
	}
	
	public void deplacerEnvahisseurVersLaDroite() {
		if (envahisseur.abscisseLaPlusADroite() < (longueur - 1))
			envahisseur.deplacerHorizontalementVers(Direction.DROITE);
		if (!estDansEspaceJeu(envahisseur.abscisseLaPlusADroite(), envahisseur.ordonneeLaPlusHaute())) {
			envahisseur.positionner(longueur - envahisseur.getDimension().longueur(), envahisseur.ordonneeLaPlusHaute());
		}
	}
	
	public void deplacerEnvahisseurVersLaGauche() {
		if (0 < envahisseur.abscisseLaPlusAGauche())
			envahisseur.deplacerHorizontalementVers(Direction.GAUCHE);
		if (!estDansEspaceJeu(envahisseur.abscisseLaPlusAGauche(), envahisseur.ordonneeLaPlusHaute())) {
			envahisseur.positionner(0, envahisseur.ordonneeLaPlusHaute());
		}
	}
	
	public void positionnerUnNouveauEnvahisseur(Dimension dimensionEnvahisseur, Position positionOrigineEnvahisseur, int vitesseEnvahisseur) {
		int x = positionOrigineEnvahisseur.abscisse();
		int y = positionOrigineEnvahisseur.ordonnee();

		if (!estDansEspaceJeu(x, y))
			throw new HorsEspaceJeuException("La position de l'envahisseur est en dehors de l'espace jeu");

		int longueurEnvahisseur = dimensionEnvahisseur.longueur();
		int hauteurEnvahisseur = dimensionEnvahisseur.hauteur();

		if (!estDansEspaceJeu(x + longueurEnvahisseur - 1, y))
			throw new DebordementEspaceJeuException("L'envahisseur déborde de l'espace jeu vers la droite à cause de sa longueur");
		if (!estDansEspaceJeu(x, y - hauteurEnvahisseur + 1))
			throw new DebordementEspaceJeuException("L'envahisseur déborde de l'espace jeu vers le bas à cause de sa hauteur");

		envahisseur = new Envahisseur(dimensionEnvahisseur,positionOrigineEnvahisseur,vitesseEnvahisseur);
	}

	public Envahisseur recupererEnvahisseur() {
		return this.envahisseur;
	}
}
