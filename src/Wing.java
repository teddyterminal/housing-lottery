import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.imageio.ImageIO;

public class Wing 
{
    private final String name; 
    private Set<Room> rooms; 
    private final int reserved; 
    private boolean locked; 
    private boolean east; 
    private final String image; 
    
    public Wing(String n, String image, int res, Set<Room> rooms)
    {
        name = n; 
        if (name.indexOf("East") != -1) east = true; 
        else east = false; 
        this.image = image; 
        reserved = res; 
        this.rooms = rooms; 
        locked = false; 
    }
    
    public String getName()
    {
        return name; 
    }
    
    public int reserved()
    {
        return reserved; 
    }
    
    public Set<Room> rooms()
    {
        Set<Room> r = new HashSet<Room>(); 
        
        for (Room room : rooms)
        {
            r.add(room); 
        }
        
        return r; 
    }
    
    public boolean locked()
    {
        return locked;
    }
    
    public void lock() throws IOException
    {
        locked = true; 
        
        drawLock(); 
    }
    
    public void unlock() throws IOException
    {
        locked = false;
        
        eraseLock(); 
    }
    
    public int wingCapacity()
    {
        int capacity = 0; 
        for (Room room : rooms)
        {
            capacity += room.capacity(); 
        }
        
        return capacity; 
    }
    
    public void add(Room room)
    {
        this.rooms.add(room); 
    }
    
    public int bedsUsed()
    {
        int beds = 0; 
        for (Room room : rooms)
        {
            beds += room.beds(); 
        }
        
        return beds; 
    }
    
    private void drawLock() throws IOException
    {
        File in = new File(image); 
        BufferedImage floorImage = ImageIO.read(in); 
        BufferedImage lock = ImageIO.read(new File("img/lock.jpg")); 
        int x = 0; 
        int y = 0; 
        
        if (east) {x = 1420; y = 540;}
        else {x = 410; y = 50;}
        
        
        for (int i = 0; i < lock.getWidth(); i++)
        {
            for(int j = 0; j < lock.getHeight(); j++)
            {
                floorImage.setRGB(x + i, y + j, lock.getRGB(i, j));
            }
        }
        
        ImageIO.write(floorImage, "png", in); 
    }
    
    private void eraseLock() throws IOException
    {
        BufferedImage floorImage = ImageIO.read(new File(image)); 
        BufferedImage lock = ImageIO.read(new File("img/lock.jpg")); 
        int x = 0; 
        int y = 0; 
        
        if (east) {x = 1420; y = 540;}
        else {x = 410; y = 50;}
        
        
        for (int i = 0; i < lock.getWidth(); i++)
        {
            for(int j = 0; j < lock.getHeight(); j++)
            {
                floorImage.setRGB(x + i, y + j, floorImage.getRGB(10, 10));
            }
        }
        
        ImageIO.write(floorImage, "png", new File(image)); 
    }
}
