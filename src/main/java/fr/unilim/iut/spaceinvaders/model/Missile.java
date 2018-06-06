package fr.unilim.iut.spaceinvaders.model;

import fr.unilim.iut.spaceinvaders.Dimension;
import fr.unilim.iut.spaceinvaders.Position;
import fr.unilim.iut.spaceinvaders.Sprite;

public class Missile extends Sprite {

	public Missile(Dimension dimension, Position origine, int vitesse) {
		super(dimension, origine, vitesse);
	}
}
