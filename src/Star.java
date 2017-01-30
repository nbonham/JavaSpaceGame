import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Random;


public class Star {
	private int xPos;
	private int yPos;
	Color starColor;
	static final int DIM = 600; // this is the default screen size
	private Random r = new Random();
	
	// Screen uses this for the initial set of stars
	public Star(){		
		xPos = r.nextInt(DIM);
		yPos = r.nextInt(DIM);
		starColor = Color.yellow;
	}
	
	// Screen uses this constructor for a resizing event
	public Star(int starX, int starY){			
		xPos = r.nextInt(starX);
		yPos = r.nextInt(starY);
		starColor = Color.yellow;
	}
	
	public void drawStar(Graphics g){
		int[] xArr1 = {xPos, xPos+5, xPos+10};
		int[] yArr1 = {yPos + 10, yPos, yPos+10};
		int[] xArr2 = {xPos, xPos+10, xPos+5};
		int[] yArr2 = {yPos +5, yPos+5, yPos+10};
		
		g.setColor(starColor);
		g.fillPolygon(xArr1, yArr1, 3);
		g.fillPolygon(xArr2, yArr2, 3);	
	}
}