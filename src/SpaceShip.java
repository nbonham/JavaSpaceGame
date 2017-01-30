/** Name: Nathaniel A Bonham
 *  Class name: SpaceShip 
 *  Worked with: Tim Yoo
 *  Expected grade: 95/100
 *  Received help from: https://docs.oracle.com/javase/1.5.0/docs/api/java/awt/Graphics.html
 *  Other comments: 
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.Random; 


public class SpaceShip extends Polygon {

	private int xPos, yPos; // private int xPos, yPos
	private Boolean shooting; // private boolean shooting
	private Color color; // private Color clr
	//stores the body color of the ship, default value is red
	private final int WIDTH = 40;
	private final int HEIGHT = 30;
	private int mouseX=0, mouseY=0;
	private int shipLives = 3 ; // initializes the number of lives each ship has

	Random rand = new Random(); //initializing the rand variable


	//public SpaceShip()
	//default constructor selects random numbers for xPos and yPos. shooting = false
	//random values between 0 and 250
	public SpaceShip(){
		xPos = rand.nextInt(251); 
		yPos = rand.nextInt(251); 
		shooting = false;
		color = Color.red;	
		
		int upperLeftX = xPos ; 
		int upperLeftY = yPos ; 
		int lowerLeftX = xPos ; 
		int lowerLeftY = yPos + HEIGHT ; 
		
		int upperRightX = xPos + WIDTH;
		int upperRightY = yPos ; 
		int lowerRightX = xPos + WIDTH ;
		int lowerRightY = yPos + HEIGHT;
		
		addPoint(upperLeftX, upperLeftY);
		addPoint(lowerLeftX, lowerLeftY);
		addPoint(lowerRightX, lowerRightY);
		addPoint(upperRightX, upperRightY);
		addPoint(upperLeftX, upperLeftY);
	}	

	//public SpaceShip(int,int)
	//constructor takes these values as parameters. shooting = false
	public SpaceShip(int a, int b){
		xPos = a; 
		yPos = b; 
		shooting = false;
		color = Color.red;
	}

	//public setShooting(boolean):void - done
	//setShooting updates the value of the shooting variable
	public void setShooting(Boolean c){
		shooting = c;
		// System.out.println("ship is shooting? " + shooting); // was using this to test method
	}
	
	public Boolean isShooting(){
		return shooting;
	}

	public void draw(Graphics g, int x) {
		// my ships intentionally look like a sad cyclops
		// draw the body
		g.setColor(color);	
		g.fillOval(xPos, yPos, WIDTH, HEIGHT);

		// draw the window(s)
		g.setColor (Color.yellow);
		g.fillRect(xPos+16, yPos+8, WIDTH/4, HEIGHT/4);

		// draw the arc(s)
		// drawArc(int x, int y, int width, int height, int startAngle, int arcAngle) 
		g.setColor(Color.yellow);
		g.drawArc(xPos+9, yPos+18, WIDTH-20, HEIGHT/2, 0, 180);

		// draw the line(s)
		// drawLine(int x1, int y1, int x2, int y2) 
		g.setColor(Color.yellow);
		g.drawLine(xPos+20, yPos, xPos, yPos-10);
		g.drawLine(xPos+20, yPos, xPos+40, yPos-10);

		// draw the laser if ship is shooting
		if(shooting == true){
			g.setColor(Color.red);
			g.drawLine(xPos+40, yPos+15, x, yPos+15);	
			g.setColor(Color.yellow);
			g.fillOval(xPos+7, yPos+15, WIDTH-14, HEIGHT/2);
		}
	}

	// setColor(Color):void
	// updates the value of the clr attribute
	public void setColor(Color colorChange){
		color = colorChange; 
	}

	public void move(int x, int y){
		this.translate(x - xPos, y - yPos);
		
		//System.out.println("Move to "  + x + " " + y);
		xPos = x;
		yPos = y;		
	}
	
	// reduces the number of lives for a ship object
	public void reduceLives(){
		shipLives -= 1; 
	}
	
	// returns the number of lives for the ship object
	public int returnLives(){
		return shipLives ; 
	}
	
	public int getX(){
		return xPos;
	}
	
	public int getY(){
		return yPos;
	}
}



