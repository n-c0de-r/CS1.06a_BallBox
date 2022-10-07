import java.awt.Color;
import java.awt.Rectangle;
import java.util.Random;

/**
 * Write a description of class BoxBall here.
 * Creates a Ball that bounces in a predefined border box.
 *
 * @author  Tobias Bayer (update by n-c0de-r)
 * @version 22-06-15 (update on 22-10-06)
 */
public class BoxBall {
    Random random;
    private int diameter;
    private Color color;
    private Canvas canvas;
    // Border limits
    private int minX;
    private int minY;
    private int maxX;
    private int maxY;
    // Ball variables
    private int xPos;
    private int yPos;
    private int xSpeed;
    private int ySpeed;
    
    /**
     * Constructor of a new BoxBall.
     * @param diameter  Size of the ball
     * @param color     Color of the ball
     * @param border    Border where a ball bounces off
     * @param canvas    Canvas to draw to
     */
    public BoxBall(int diameter, Color color, int border, Canvas canvas) {
        this.random = new Random();
        this.diameter = diameter;
        this.color = color;
        this.canvas = canvas;
        
        // Limiting positions for each ball, so it doesn't exceed
        minX = border; // Positions are relative to the Ball's
        minY = border; // individual diameter.
        maxX = canvas.getSize().width - border - diameter;
        maxY = canvas.getSize().height - border - diameter;
        
        // Set random positions and speed for each ball
        generatePosition();
        generateSpeed(20);
    }
    
    /**
     * Generates the random position of a ball within the borders.
     */
    private void generatePosition() {
        xPos = random.nextInt(minX, maxX);
        yPos = random.nextInt(minY, maxY);
    }
    
    /**
     * Generates a random speed for each direction within a limit.
     * @param limit The highest speed possible.
     */
    private void generateSpeed(int limit) {
        // Generate random sign modifier for each direction
        int directionX = signSwitch(); // -1 or +1
        int directionY = signSwitch(); // -1 or +1
        // Generate (currently set) -20 to +20 but no 0
        xSpeed = random.nextInt(1, limit+1) * directionX;
        ySpeed = random.nextInt(1, limit+1) * directionY;
        // Also possible: random.nextInt(limit+1) * directionX +1;
    }
    
    /**
     * Generates only the numbers -1 or +1, skips the 0.
     */
    private int signSwitch() {
        /* The math behind this works like this:
         * First generate a 0 or 1 with random.nextInt(2).
         * Multiply by 2. Why? Simple:
         * If it is 0, it stays 0. But a 1 becomes 2.
         * Finally subtract -1 from either result. Why?
         * 0-1 = -1 and 2-1= +1
         * Effectively making a sign shift in the end. */
        return random.nextInt(2) * 2 - 1;
    }

    /**
     * Draw this ball at its current position onto the canvas.
     **/
    public void draw() {
        canvas.setForegroundColor(color);
        canvas.fillCircle(xPos, yPos, diameter);
    }

    /**
     * Erase this ball at its current position.
     **/
    public void erase() {
        canvas.eraseCircle(xPos, yPos, diameter);
    }

    /**
     * Move this ball according to its position and speed and redraw.
     **/
    public void move()
    {
        // remove from canvas at the current position
        erase();
        
        // compute new position
        xPos += xSpeed;
        yPos += ySpeed;
        
        // check if new position would have hit the frame
        if (xPos <= minX || xPos >= maxX) {
            xSpeed *= -1; // Change direction to opposite
            xPos += xSpeed; // Update the position again
        }
        if (yPos <= minY || yPos >= maxY) {
            ySpeed *= -1;
            yPos += ySpeed;
        }
        // draw again at new position
        draw();
    }

}