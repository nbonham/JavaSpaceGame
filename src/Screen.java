/** Name: Nathaniel A Bonham
 *  Assignments: Space Ship
 * 
 * Worked with: Tim Yoo
 * 
 * Other Sources:
 * http://stackoverflow.com/questions/1088595/how-to-do-something-on-swing-component-resizing
 * https://docs.oracle.com/javase/tutorial/uiswing/events/componentlistener.html
 * http://www.tutorialspoint.com/java/util/arraylist_remove_object.htm
 * http://www.tutorialspoint.com/java/java_string_valueof.htm
 * http://math.hws.edu/javanotes/c7/s3.html
 * http://docs.oracle.com/javase/7/docs/api/java/awt/Polygon.html
 * http://stackoverflow.com/questions/8524874/java-swing-key-bindings-missing-action-for-released-key
 *  
 * Expected score: 150 / 150 
 */    

// Import necessary packages
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Random;

// Screen extends JPanel, implements action, mouse, mousemotion and component listeners
public class Screen extends JPanel implements ActionListener, MouseListener, MouseMotionListener, ComponentListener, KeyListener
{
	// asteroid information
	private final int WIDTH = 1200, HEIGHT = 800;	// the initial size of the panel
	private final int NUM_ASTEROIDS = 30; 			// can change the # of asteroids here
	private final int NUM_STARS = 100; 				// can change the # of stars here

	// create SpaceShip variable, stars array and asteroids array list
	private SpaceShip ship1 ;
	private Star stars[] = new Star[NUM_STARS];
	private ArrayList<Asteroid> asteroids = new ArrayList<Asteroid>();

	// create timer for asteroids
	private Timer timer;
	private final int DELAY = 25; // initializes the speed of asteroids

	// create timer for game clock
	private Timer timeLeft;
	public int seconds = 15; // initializes the number of seconds

	// initialize score
	private int score = 0;

	// variables for size of screen used in resizing events
	private int panelW, panelH;

	// instance of random that gets used extensively in class
	private Random r = new Random();

	// flags
	Boolean gameOver = false ;  // game over flag
	Boolean asteroidHit = false ; // used to toggle "hit" message 
	int hitX = 0 ; // variables that will later be updated for "hit" message location
	int hitY = 0 ; 
	

	// set up the game  
	public Screen(){

		// fire the laser when the spacebar is pressed
		// I tried using key listener, following the documentation with no luck
		// and then found this method of using input maps and overriding action performed
		// for specific kinds of key presses
		// http://docs.oracle.com/javase/tutorial/uiswing/misc/keybinding.html
		// http://stackoverflow.com/questions/8524874/java-swing-key-bindings-missing-action-for-released-key

		getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "pressed");
	    getInputMap().put(KeyStroke.getKeyStroke("released SPACE"), "released");
	    getActionMap().put("pressed", new AbstractAction() {
	      @Override public void actionPerformed(ActionEvent e) {
	        ship1.setShooting(true);
	        	      }
	    });
	    
	    getActionMap().put("released", new AbstractAction() {
	      @Override public void actionPerformed(ActionEvent e) {
	    	  ship1.setShooting(false);	       
	      }
	    });
		

		// new instances of stars
		for (int i = 0; i < NUM_STARS;i++){
			stars[i] = new Star ();
		}

		// new instances of asteroids, set to appear in the initial width and height of panel
		for (int i = 0; i < NUM_ASTEROIDS;i++){
			asteroids.add(new Asteroid(r.nextInt(WIDTH), r.nextInt(HEIGHT)));
		}

		// asteroids timer
		timer = new Timer(DELAY, this);

		// create player ship
		ship1 = new SpaceShip();

		// listening for actions
		addMouseListener(this);
		addMouseMotionListener(this);
		addComponentListener(this);

		// set background to black and initialize to WIDTH and HEIGHT
		setPreferredSize (new Dimension(WIDTH, HEIGHT));
		setBackground (Color.black);

		// seconds timer & stop the game when seconds are 0
		// 1000 ms = 1 second
		timeLeft = new Timer (1000, new ActionListener(){
			public void actionPerformed (ActionEvent e) {
				seconds -= 1; 
				if(seconds == 0){
					gameOver = true ;
					repaint();
					timeLeft.stop();
					timer.stop();
				}
			}
		}
				); // end parenthesis for timeLeft


		// start game timer and seconds timer
		timer.start();
		timeLeft.start();		
	}	// end bracket for Screen

	// paintComponent, draw all the graphics
	public void paintComponent (Graphics g)
	{
		super.paintComponent(g);

		// draw the stars
		for (int i = 0; i < NUM_STARS;i++){
			stars[i].drawStar(g);
		}		

		// draw the asteroids
		for (Asteroid a: asteroids){
			a.drawAsteroid(g);						
		}

		// draw the ship
		ship1.draw(g, getWidth());

		// store the height and width of the panel
		panelH = getHeight();
		panelW = getWidth();

		// display time left, score and lives remaining
		// http://www.tutorialspoint.com/java/java_string_valueof.htm
		g.setFont(new Font("Ariel", Font.PLAIN, 20));
		g.setColor(Color.white);
		g.drawString("Time left: " + String.valueOf(seconds), 10, 20);
		g.drawString("Score: " + String.valueOf(score), 10, 40);
		g.drawString("Lives left: " + String.valueOf(ship1.returnLives()), 10, 60);

		// display game over message & score 
		// show "perfect" if no lives were lost and all asteroids were destroyed
		if (gameOver == true){
			g.setFont(new Font("Ariel", Font.PLAIN, 50));
			g.drawString("GAME OVER", WIDTH/2 - 150, HEIGHT/2);
			if (score == NUM_ASTEROIDS){
				g.drawString("PERFECT!!!", WIDTH/2 - 150, HEIGHT/2 + 50);
			}
			else {
				g.drawString("FINAL SCORE: " + String.valueOf(score), WIDTH/2 - 150, HEIGHT/2 + 50);
			}
		}

		// hit message
		if (asteroidHit == true){
			g.drawString("HIT!!", hitX, hitY - 5);
		}

	}

	// main that sets up the panel
	public static void main(String[] args) {

		JFrame frame = new JFrame ("SpaceShips");
		frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);

		Screen panel = new Screen();

		frame.getContentPane().add(new Screen());
		frame.pack();
		frame.setVisible(true);		
	}

	// pressing down on the mouse button fires the ship's laser
	public void mousePressed(MouseEvent e) {
		if (gameOver == false){	
			ship1.setShooting(true);
			repaint();   
		}
	}

	// releasing the mouse button stops the laser from firing
	public void mouseReleased(MouseEvent e) {
		if (gameOver == false){	
			ship1.setShooting(false);
			repaint();
		}
	}

	// move the ship with your mouse
	public void mouseMoved(MouseEvent e) {
		if (gameOver == false){		
			ship1.move(e.getX(),e.getY());
			repaint();
		}
	}

	// allows moving while firing
	public void mouseDragged(MouseEvent e) {
		if (gameOver == false){	
			ship1.move(e.getX(),e.getY());
			repaint();
		}
	}

	// things that happen when the panel is resized
	public void componentResized(ComponentEvent e) {

		// recreates the stars so that they fill the panel		
		for (int i = 0; i < NUM_STARS;i++){
			stars[i] = new Star (getWidth(), getHeight());
		}

		// destroy the existing asteroids and create new ones within the resized panel
		// I wrote it this way as I wanted the asteroids to appear if also making the screen
		// smaller instead of just larger
		for (int i = 0; i < NUM_ASTEROIDS;i++){
			asteroids.remove(i);
			asteroids.add(new Asteroid(r.nextInt(getWidth()), r.nextInt(getHeight())));
		}	 
	}

	// Asteroid moving event
	public void actionPerformed(ActionEvent event) {

		/*	if (event.getSource().equals(timer)){*/

		// a little for loop so that the hit message is display long enough to be detectable/read
		for (int j = 0; j < DELAY * 8; j += DELAY){
			if (asteroidHit == true && j == DELAY * 7){
				asteroidHit = false ; 
			}
		}


		// stop the game if all asteroids are destroyed
		if (asteroids.isEmpty() == true){
			gameOver = true ; 
			repaint();
			timeLeft.stop();
			timer.stop();
		}

		// loop through the asteroids to move them and do collision detections
		// http://math.hws.edu/javanotes/c7/s3.html
		for (int i = 0; i < asteroids.size(); i++){

			Asteroid a = asteroids.get(i);

			int newX = a.getX() + a.getDeltaX();
			int newY = a.getY() + a.getDeltaY();

			if (newX < 0 || newX > panelW-a.getWidth())	// switch directions if at edge of screen
				a.reverseDeltaX();						// weird things happened with <=, so used <
			if (newY < 0 || newY > panelH-a.getHeight())
				a.reverseDeltaY();

			// Move an asteroid only 30% of the time on a given tick
			int maybeMove = r.nextInt(10);
			if (maybeMove > 7){						// this says a 3 in 10 chance on a tick
													// same as 30%

				a.moveAsteroid(newX, newY);				
				repaint();	
			}

			// use the bounding boxes to do collision detection
			// http://docs.oracle.com/javase/7/docs/api/java/awt/Polygon.html
			if (a.intersects(ship1.getBounds2D())){
				// System.out.println("HIT!!");
				ship1.reduceLives();
				asteroids.remove(a);				
			}

			if (ship1.returnLives() == 0){
				gameOver = true ;
				repaint();
				timer.stop();
				timeLeft.stop();
			}

			// Shooting a laser will add 1 to the score and remove the asteroid from the array list
			// shooting == true, ship x < asteroid x, ship y ~ asteroid y
			if (ship1.isShooting() == true && a.getX() > ship1.getX() && a.getY() <= ship1.getY() + a.getHeight()/1.5 && a.getY() >= (ship1.getY() - a.getHeight()/1.5)){
				score += 1;
				asteroids.remove(a);
				asteroidHit = true ; 	// updates flag for hit message
				hitX = a.getX();		// updates location where hit message prints
				hitY = a.getY();
				repaint();
			}

			// change the color of the ship when getting hit
			switch (ship1.returnLives()) {
			case 0: ship1.setColor(Color.MAGENTA);
			break;
			case 1:  ship1.setColor(Color.RED);
			break;
			case 2:  ship1.setColor(Color.YELLOW);
			break;
			case 3:  ship1.setColor(Color.BLUE);
			}			
		}
	}

	// Eclipse made me add all these and gets angry if I remove them. 
	// Not used for anything
	@Override
	public void mouseClicked(MouseEvent arg0) {
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
	}
	@Override
	public void componentHidden(ComponentEvent arg0) {
	}
	@Override
	public void componentMoved(ComponentEvent arg0) {
	}
	@Override
	public void componentShown(ComponentEvent arg0) {
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}
}






