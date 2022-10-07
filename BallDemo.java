import java.awt.Color;
import java.awt.Rectangle; // Assignment 2
import java.awt.Dimension; // Assignment 3
import java.util.Random;

/**
 * Class BallDemo - a short demonstration showing animation with the 
 * Canvas class. 
 *
 * @author Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public class BallDemo   
{
    private Canvas myCanvas;

    /**
     * Create a BallDemo object. Creates a fresh canvas and makes it visible.
     */
    public BallDemo()
    {
        myCanvas = new Canvas("Ball Demo", 1200, 500);
        // Assignment 2 & 3 function call
        drawFrame(20);
    }

    /**
     * Simulate two bouncing balls
     */
    public void bounce()
    {
        int ground = 400;   // position of the ground line

        myCanvas.setVisible(true);

        // draw the ground
        myCanvas.setForegroundColor(Color.BLACK);
        myCanvas.drawLine(50, ground, 550, ground);
        
        // Assignment 1, draw various things here:
        
        // create and show the balls
        BouncingBall ball1 = new BouncingBall(50, 50, 16, Color.BLUE, ground, myCanvas);
        ball1.draw();
        BouncingBall ball2 = new BouncingBall(70, 80, 20, Color.RED, ground, myCanvas);
        ball2.draw();
        
        // make them bounce
        boolean finished =  false;
        while (!finished) {
            myCanvas.wait(50);           // small delay
            ball1.move();
            ball2.move();
            // stop once ball has travelled a certain distance on x axis
            if(ball1.getXPosition() >= 550 || ball2.getXPosition() >= 550) {
                finished = true;
            }
        }
    }
    
    /**
     * Assignment 4
     * Simulate two bouncing balls
     * @param number    Number of balls to bounce
     */
    public void bounce(int number)
    {
        int ground = 400;   // position of the ground line

        myCanvas.setVisible(true);

        // draw the ground
        myCanvas.setForegroundColor(Color.BLACK);
        myCanvas.drawLine(50, ground, 550, ground);
        
        // create and show the balls
        /* You have many options here, and all can be valid. But why?
         * Arrays: store items of the same type and can easily be iterated
         * with an index. As the types of Balls are all the same, this 
         * would be easiest and most fitting. Also it is best for a fixed
         * number of items, which we already have.
         * 
         * ArrayList: is an extension of an Array. Making it easy to
         * add and remove new elements. Our size doesn't vary, so an
         * ArrayList might be overkill. But is possible and allows to 
         * use a for-each loop,which is an advantage again.
         * 
         * HashMap: is most usefull if similar types of information need
         * to be accessed via different methods (keys) and but can be
         * accessed without order. While we don't care which ball bounces
         * when, and we need to move them all. HashMap might overcomplicate
         * the iteration, as we need to keep track of each balls access
         * key individually.
         * 
         * HashSet: A Set automatically prevents duplicate objects and
         * stores only individual ones. Since the balls can be similar
         * objects, deleting (or even risking it) would be bad. Best
         * used if eg. one creates many random numbers, but wants only
         * to store individual ones, each time they emerge. */
         
        // Create an new array of size of the given number
        BouncingBall[] balls = new BouncingBall[number];
         
        /* Add Ball objects to the array
         * We need to use a regular loop here, as it has no elements yet and
         * a for-each loop cannot add new elements or replace existing ones.
         * It only can access existing ones, and invoke their methods etc.
         */
        // Assignment 5
        Random rng = new Random();
        for (int i=0; i<number; i++) {
            int x = (int) myCanvas.getSize().getWidth();
            int y = (int) myCanvas.getSize().getHeight()/2;
            int posX = rng.nextInt(30, x-60);
            int posY = rng.nextInt(30, y-30);
            balls[i] = new BouncingBall(posX, posY, 16, Color.BLUE, ground, myCanvas);
        }
        
        // Find the leftmost ball, as their positions are random
        int lowestIndex = 0; // Index of leftmost ball
        int leftBall = (int) myCanvas.getSize().getWidth();
        for (int i=0; i<number; i++) {
            // Get the ball's x coordinate
            int x = (int) balls[i].getXPosition();
            if (x < leftBall) {
                leftBall = x; // Update the left ball to compare
                lowestIndex = i; // Update the index
            }
        }
        
        // make them bounce
        boolean finished =  false;
        while (!finished) {
            myCanvas.wait(50);           // small delay
            // stop once ball has travelled a certain distance on x axis
            
            /* Assignment 4
             * iterate over the whole collection and make them move and draw.
             * A regular loop is possible too, but can be complicated and
             * has potential for typos and other errors. Also: whenever we
             * loop and just access all elements of a collection this is
             * prefered and good style, but not every language has it.*/
            for (BouncingBall ball : balls) {
                ball.move();
                /* Once the leftmost ball has reached a certain
                 * spot on the right side, terminate the whole thing, as 
                 * all others further right balls have already reached it.
                 */
                if(balls[lowestIndex].getXPosition() >= 550) {
                    finished = true;
                }
            }
        }
    }
    
    /**
     * Assignment 6, for the bored
     * Make balls bounce inside a certain box.
     */
    public void boxBounce(int number) {
        int ground = 400;   // position of the ground line

        myCanvas.setVisible(true);

        // draw the ground
        myCanvas.setForegroundColor(Color.BLACK);
        myCanvas.drawLine(50, ground, 550, ground);
        
        BoxBall[] balls = new BoxBall[number];
        
        // Draw the box. Call a frame method from Assignment 3
        int frame = 25;
        drawFullFrame(frame);
        
        // Make an array of vibrant colors to chose from
        Color[] ballColors = {Color.red, Color.green, Color.blue, 
        Color.black, Color.pink, Color.yellow, Color.cyan, Color.orange};

        myCanvas.setVisible(true);
        
        // create and show the balls
        Random rng = new Random();
        for (int i = 0; i < number; i++) {
            // Between 10 & 40; same as rng.nextInt(10, 40)
            int size = 10 + rng.nextInt(40);
            
            // Pick a random color, turns out pastel-like often
            int r = rng.nextInt(128, 256);
            int g = rng.nextInt(128, 256);
            int b = rng.nextInt(128, 256);
            Color color1 = new Color(r,g,b);
            
            // Or pick one from a list, more vibrant but static
            Color color2 = ballColors[rng.nextInt(ballColors.length)];
            balls[i] = new BoxBall(size, color1, frame, myCanvas);
            balls[i].draw();
        }

        // make them bounce
        boolean finished =  false;
        while (!finished) {
            myCanvas.wait(50);           // small delay
            for (int i = 0; i < balls.length; i++) {
                balls[i].move();
            }
        }
    }
    
    /**
     * Assignment 2, hardcoded approach;
     * Drawing a rectangle frame of offset by 20 pixels
     * from borders. This size is fixed.
     */
    public void drawFrame()
    {
        /* "Hardcoding" (typing stuff out, not letting the
         * computer calculate the relevant numbers) isn't
         * flexible and can be problematic later on.
         * Try to avoid it with more experience. */
         
        /* To draw a rectangle, we can use a Rectangle class.
         * We need to import it's library to make use of it.
         * Via: import java.awt.Rectangle;
         * Rectangle need the following parameters at creation:
         * Rectangle(start position x, start pos. y, width, height);*/
        Rectangle rect = new Rectangle(20, 20, 560, 460);
        myCanvas.draw(rect);
    }
    
    /**
     * Assignment 2, better solution;
     * Drawing a rectangle frame of a given offset from borders
     * @param offset    The offset from the canvas borders
     */
    public void drawFrame(int offset)
    {
        /* Instead of hardcoding the rectangle size, use a
         * variable as a parameter. It offsets and resizes
         * the rectangle. */
        int x = offset;
        int y = offset;
        /* It needs to be reduced by 2 times, as the first offset
         * shifts the rectangle down and right. Virtually "moving"
         * the whole rectangle to the edge. Just reducing the size
         * by the one offset it was moved, would make it overlap
         * exactly. But as it needs to be smaller, it needs to be
         * resized yet again by another offset. */
        int sizeX = 600 - offset*2;
        int sizeY = 500 - offset*2;
        Rectangle rect = new Rectangle(x, y, sizeX, sizeY);
        myCanvas.draw(rect);
    }
    
    /**
     * Assignment 3; using Dimensions
     * Drawing a rectangle frame of a given offset from borders
     * and using the canvas dimensions to calculate everything.
     * @param offset    The offset from the canvas borders.
     * @param sizes     The Dimensions of the canvas.
     */
    public void drawDimensionsFrame(int offset) {
        /* To use Dimensions you need to import the library
         * via: import java.awt.Dimension
         * at the start of this whole document. */
        Dimension sizes = myCanvas.getSize();
        int x = offset;
        int y = offset;
        int sizeX = sizes.width - offset*2;
        int sizeY = sizes.height - offset*2;
        
        Rectangle rect = new Rectangle(x, y, sizeX, sizeY);
        myCanvas.draw(rect);
        /* This is much more flexible in code and in funtionality.
         * If the core structure of the outside code changes, this
         * function is unaffected.
         * Not hardcoding the numbers makes the rectangle fit in
         * into any size of canvas. */
    }
    
    /**
     * Assignment 3; Filled border,
     * Drawing a filled rectangle frame of a given offset fro
     * borders & using the canvas dimensions to calculate everything.
     * @param offset    The offset from the canvas borders.
     */
    public void drawFullFrame(int offset) {
        Dimension sizes = myCanvas.getSize();
        int sizeX = sizes.width; // canvas width
        int sizeY = sizes.height; // canvas hight
        int offX = offset;
        int offY = offset;
        int rectWidth = sizeX - offset*2;
        int rectHight = sizeY - offset*2;
        
        /* Fill a solid rectangle and cut out its counterpart
         * with pre-defined methods given in this code. */
        myCanvas.fillRectangle(0, 0, sizeX, sizeY);
        myCanvas.eraseRectangle(offX, offY, rectWidth, rectHight);
        
        /* If you didn't have these, you could do it yourself:
         * // Save the original foreground color for later reset
         * Color foreColor = myCanvas.getForegroundColor();
         * 
         * // Create a rectangle in the size of the whole canvas.
         * Rectangle back = new Rectangle(0, 0, sizeX, sizeY);
         * 
         * // Fill the whole canvas with the black big rectangle.
         * myCanvas.fill(back);
         * 
         * // Get the original background color and set it for drawing
         * Color backColor = myCanvas.getBackgroundColor();
         * myCanvas.setForegroundColor(backColor);
        
         * // Create a new rectangle and fill this, so that it
         * // overlays the first, and "cuts" the difference out.
         * // Effectively "erasing" it and leaving a filled border!
         * Rectangle rect = new Rectangle(offX, offY, rectWidth, rectHight);
         * myCanvas.fill(rect);
         *
         * // reset the foreground color
         * myCanvas.setForegroundColor(foreColor); */        
    }
}
