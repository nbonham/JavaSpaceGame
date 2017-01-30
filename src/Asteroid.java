import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.util.Random;


public class Asteroid extends Polygon {
	private Random r = new Random();
	private int xPosAst, yPosAst;
	private Color asteroidClr ;
	
	private int deltaX = r.nextInt(31)-15;		// makes each asteroid move random from 
	private int deltaY = r.nextInt(31)-15; 		// one another. (10)-5 means some will move in opposite
												// directions when the game starts rather than all moving
												// in the same direction
	
	private int randWidth = r.nextInt(30)+15;	// randomly sizes each instance of an asteroid
	private int randHeight = r.nextInt(30)+15;


	// Asteroid constructor class	
	public Asteroid(int x, int y){
		this.xPosAst = x;
		this.yPosAst = y;
		asteroidClr = Color.magenta;			// change the color of asteroids here
	
		int upperLeftX = xPosAst ; 
		int upperLeftY = yPosAst ; 
		int lowerLeftX = xPosAst ; 
		int lowerLeftY = yPosAst + randHeight ; 
		
		int upperRightX = xPosAst + randWidth;
		int upperRightY = yPosAst ; 
		int lowerRightX = xPosAst + randWidth ;
		int lowerRightY = yPosAst + randHeight;
		
		addPoint(upperLeftX, upperLeftY);
		addPoint(lowerLeftX, lowerLeftY);
		addPoint(lowerRightX, lowerRightY);
		addPoint(upperRightX, upperRightY);
		addPoint(upperLeftX, upperLeftY);
		
		// will need to do this for my Ship class as well
		
	}
	
	// draw the asteroid object
	public void drawAsteroid(Graphics g)
	{
		g.setColor(asteroidClr);
		g.fillOval(xPosAst, yPosAst, randHeight, randWidth);		
	}
	
	
	
	
/*	public Rectangle asteroidBounds(){
		
		Rectangle asteroidBox = new Rectangle(xPosAst, yPosAst, randHeight, randWidth);
				
		return asteroidBox;*/
		
	
	
	// Asteroid move the asteroids
	// calculations for parameters int x and int y happen in the Screen class
	public void moveAsteroid(int x, int y){

		this.translate(x - xPosAst, y - yPosAst); // added this 11-15
		// http://docs.oracle.com/javase/7/docs/api/java/awt/Polygon.html
		
		this.xPosAst = x;
		this.yPosAst = y;
		
	}
	
	// getters so that screen can access object information
	public int getWidth(){
		return randWidth;
	}
	
	public int getX(){
		return xPosAst;
	}
	
	public int getY(){
		return yPosAst;
	}
	
	public int getHeight(){
		return randHeight;
	}

	public int getDeltaX(){
		return deltaX;
	}
	public int getDeltaY(){
		return deltaY;
	}
	
	// these reverse the direction of the asteroids
	// logic that dictates when an object should reverse is located
	// in the Screen class
	public void reverseDeltaX(){
		deltaX = deltaX * -1;
	}
	public void reverseDeltaY(){
		deltaY = deltaY * -1;
	}
	
	
}
