import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class Room 
{
    private final int number; 
    private RoomStatus status; 
    private final int capacity; 
    private int bedsFilled; 
    private String image; 
    private int xCoord; 
    private int yCoord; 
    
    public Room(int number, int capacity, String image, int x, int y, boolean reset) throws IOException
    {
        this.number = number; 
        this.capacity = capacity; 
        this.bedsFilled = capacity; 
        xCoord = x; 
        yCoord = y; 
        this.image = image; 
        setStatus(RoomStatus.NOINFORMATION, reset);  
    }
    
    public Room(int number, int capacity, int beds, String image, int x, int y, String status, boolean reset) throws IOException
    {
        this.number = number; 
        this.capacity = capacity; 
        this.bedsFilled = beds; 
        xCoord = x; 
        yCoord = y; 
        this.image = image; 
        switch (status)
        {
            case "occupied":
                setStatus(RoomStatus.OCCUPIED, reset); 
                break; 
            case "partial":
                setStatus(RoomStatus.PARTIAL, reset); 
                break;                    
            case "open":
                setStatus(RoomStatus.OPEN, reset); 
                break;
            default: 
                this.bedsFilled = capacity; 
                setStatus(RoomStatus.NOINFORMATION, reset);  
        }
    }
    
    public String image()
    {
        return image; 
    }
    
    public int x()
    {
        return xCoord; 
    }
    
    public int y()
    {
        return yCoord; 
    }
    
    public int number()
    {
        return number; 
    }
    
    public RoomStatus status()
    {
        return status; 
    }
    
    public int capacity()
    {
        return capacity; 
    }
    
    public int beds()
    {
        return bedsFilled; 
    }
    
    public void assignRandomColor() throws IOException
    {
        File img = new File(image); 
        BufferedImage in = ImageIO.read(img);
        Color c = new Color((int) (Math.random()*256), (int)(Math.random()*256), (int)(Math.random()*256)); 
        FloodFill.floodFillImage(in, xCoord, yCoord, c);
        ImageIO.write(in, "png", img);

    }
    
    public void setStatus(RoomStatus status, boolean reset) throws IOException
    {
        this.status = status;
        Color c1 = new Color(190, 190, 190); 
        Color c2 = new Color(255, 0, 0); 
        Color c3 = new Color(0, 255, 255); 
        Color c4 = new Color(255, 255, 0); 
        Color c5 = new Color(0, 255, 0); 

        if (reset) 
        {
            File img = new File(image); 
            BufferedImage in = ImageIO.read(img);
        
            switch(this.status)
            {
                case NOINFORMATION: 
                    FloodFill.floodFillImage(in, xCoord, yCoord, c1);
                    this.bedsFilled = this.capacity; 
                    break; 
                case OCCUPIED: 
                    FloodFill.floodFillImage(in, xCoord, yCoord, c2);
                    this.bedsFilled = this.capacity; 
                    break; 
                case OPEN: 
                    FloodFill.floodFillImage(in, xCoord, yCoord, c3);
                    this.bedsFilled = 0; 
                    break; 
                case PARTIAL: 
                    FloodFill.floodFillImage(in, xCoord, yCoord, c4);
                    Scanner sc = new Scanner(System.in); 
                    System.out.print("Partial requested: input new # beds: "); 
                    int i = sc.nextInt(); 
                    this.bedsFilled = i; 
                    break; 
                case ACTIVE: 
                    FloodFill.floodFillImage(in, xCoord, yCoord, c5);
                    break; 
                default:
                    throw new UnsupportedOperationException("Screw you :(");
            }
        
        
            ImageIO.write(in, "png", img);
        }

    }
    
    public void setBeds(int beds)
    {
        this.bedsFilled = beds; 
    }
    
    @Override
    public String toString()
    {
        return "Room " + number; 
    }
}
