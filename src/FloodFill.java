/* FloodFill.java
 * Benedict Brown
 *
 * Takes an image file as a command-line argument,
 * and flood fills all black regions red.
 *
 * This program is intended primarily to demonstrate an
 * an application of depth-first search.
 *
 * NOTE: You will likely get a StackOverFlowException if you
 *       run this on large images, because Java's default stack
 *       size is quite limited.  The usual workaround for this
 *       is to maintain a stack of pixels that need to be filled.
 *       Instead of recursively calling flood(), you just push
 *       new pixels onto the stack.  flood() runs until the stack is
 *       empty.  Recursion is much more elegant though.
 */

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import javax.imageio.ImageIO;

public class FloodFill {
    public static void floodFillImage(BufferedImage image,int x, int y, Color color) 
    {
        int srcColor = image.getRGB(x, y);
        boolean[][] hits = new boolean[image.getHeight()][image.getWidth()];

        Queue<Point> queue = new LinkedList<Point>();
        queue.add(new Point(x, y));

        while (!queue.isEmpty()) 
        {
            Point p = queue.remove();

            if(floodFillImageDo(image,hits,p.x,p.y, srcColor, color.getRGB()))
            {     
                queue.add(new Point(p.x,p.y - 1)); 
                queue.add(new Point(p.x,p.y + 1)); 
                queue.add(new Point(p.x - 1,p.y)); 
                queue.add(new Point(p.x + 1,p.y)); 
            }
        }
    }

    private static boolean floodFillImageDo(BufferedImage image, boolean[][] hits,int x, int y, int srcColor, int tgtColor) 
    {
        if (y < 0) return false;
        if (x < 0) return false;
        if (y > image.getHeight()-1) return false;
        if (x > image.getWidth()-1) return false;

        if (hits[y][x]) return false;

        if (image.getRGB(x, y)!=srcColor)
            return false;
                            
        image.setRGB(x, y, tgtColor);
        hits[y][x] = true;
        return true;
    }
    
    public static void main(String[] args) throws IOException {

        System.out.println(System.currentTimeMillis());
        File img = new File("img/two.png"); 
        System.out.println(System.currentTimeMillis());

        BufferedImage in = ImageIO.read(img);
        System.out.println(System.currentTimeMillis());

        floodFillImage(in, 90, 40, Color.WHITE); 
        System.out.println(System.currentTimeMillis());

        ImageIO.write(in, "png", img);
        System.out.println(System.currentTimeMillis());
        
        return; 
    }
}