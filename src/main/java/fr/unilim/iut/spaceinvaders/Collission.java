package fr.unilim.iut.spaceinvaders;

public class Collission {
	
	public boolean detecterCollision(int xAssaillant, int yAssaillant, int xCible, int yCible) {
		System.out.println(xAssaillant);
		System.out.println(xCible);
		System.out.println(yAssaillant);
		System.out.println(yCible);
		return (xAssaillant==xCible && yAssaillant==yCible);
	}
}
