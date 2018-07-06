import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Lottery 
{
    
    final static Wing w2 = new Wing("Two West", "img/two.png", 6, new HashSet<Room>());
    final static Wing e2 = new Wing("Two East", "img/two.png", 7, new HashSet<Room>());
    final static Wing w3 = new Wing("Three West", "img/three.png", 8, new HashSet<Room>());
    final static Wing e3 = new Wing("Three East", "img/three.png", 8, new HashSet<Room>());
    final static Wing w4 = new Wing("Four West", "img/four.png", 7, new HashSet<Room>());
    final static Wing e4 = new Wing("Four East", "img/four.png", 10, new HashSet<Room>());
    final static Wing w5 = new Wing("Five West", "img/five.png", 8, new HashSet<Room>());
    final static Wing e5 = new Wing("Five East", "img/five.png", 10, new HashSet<Room>()); 
    
    final static Set<Wing> wings = new HashSet<Wing>(Arrays.asList(w2, w3, w4, w5, e2, e3, e4, e5)); 

    
    
    public static void checkLocks() throws IOException
    {
        for (Wing w : wings) 
        {
            if (w.wingCapacity() - w.bedsUsed() <= w.reserved() && !w.locked()) w.lock();
            else if (w.wingCapacity() - w.bedsUsed() > w.reserved() && w.locked()) w.unlock();
        }
    }
    
    public static void main(String[] args) throws IOException
    {  
        BufferedReader reader = new BufferedReader(new FileReader(new File("src/spread.csv"))); 
        String line = reader.readLine(); 
        
        while (line != null)
        {
            line = line.replaceAll("\\s+","");
            String[] tokens = line.split(","); 
            int num = Integer.parseInt(tokens[0]); 
            int capacity = Integer.parseInt(tokens[1]); 
            int beds = Integer.parseInt(tokens[2]); 
            String imageFile = tokens[3]; 
            int x = Integer.parseInt(tokens[4]); 
            int y = Integer.parseInt(tokens[5]); 
            String status = tokens[6]; 
            System.out.println(num + " " + capacity + " " + beds + " " + imageFile + " " + x + " " + y + " " + status); 
            
            
            Room room = new Room(num, capacity, beds, imageFile, x, y, status, false); 
            if (num < 231) w2.add(room); 
            else if (num < 300) e2.add(room); 
            else if (num < 331) w3.add(room); 
            else if (num < 400) e3.add(room); 
            else if (num < 431) w4.add(room); 
            else if (num < 500) e4.add(room); 
            else if (num < 532) w5.add(room); 
            else e5.add(room); 
            line = reader.readLine(); 
        }
        

        

        for (Wing w : wings)
        {
           System.out.println(w.getName() + " " + w.bedsUsed() + " " + w.wingCapacity() + " " + (w.wingCapacity()-w.bedsUsed()) + " " + w.reserved());
            
            //System.out.println(w.getName() + " " + w.bedsUsed()); 
        }
       
        reader.close();
        Scanner sc = new Scanner(System.in); 

        while (true)
        {
            System.out.print("> ");
            String[] tokens = sc.nextLine().split(" ");
            
            if (tokens[0].equals("quit"))
            {
                PrintWriter writer = new PrintWriter(new File("src/spread.csv"));
                for (Wing w : wings)
                {
                    for (Room r : w.rooms())
                    {
                        writer.println(r.number() + ", " + r.capacity() + ", " + r.beds() + ", " + r.image() + ", " + r.x() + ", " + r.y() + ", " + r.status().toString());
                    }
                }
                writer.close();
                sc.close();
                break; 
            }
            else if (tokens[0].equals("open"))
            {
                Room room = getRoom(Integer.parseInt(tokens[1]));
                room.setStatus(RoomStatus.OPEN, true);    
            }
            else if (tokens[0].equals("occupy"))
            {
                Room room = getRoom(Integer.parseInt(tokens[1]));
                Wing wing = getWing(room); 
                room.setStatus(RoomStatus.OCCUPIED, true);               
                if (wing.locked()) System.out.println("Warning: " + wing.getName() + " is locked. Potential bylaw violation.");
            } 
            else if (tokens[0].equals("none"))
            {
                Room room = getRoom(Integer.parseInt(tokens[1]));
                room.setStatus(RoomStatus.NOINFORMATION, true);               
            } 
            else if (tokens[0].equals("activate"))
            {
                Room room = getRoom(Integer.parseInt(tokens[1]));
                room.setStatus(RoomStatus.ACTIVE, true);               
            }            
            else if (tokens[0].equals("partial"))
            {
                Room room = getRoom(Integer.parseInt(tokens[1]));
                Wing wing = getWing(room); 
                room.setStatus(RoomStatus.PARTIAL, true);     
                if (wing.locked()) System.out.println("Warning: " + wing.getName() + " is locked. Potential bylaw violation.");
            } 
            else
            {
                System.out.println("Illegal Argument");
            }
            checkLocks(); 
        }
    }
    
    
    
    public static Room getRoom(int number)
    {
        for (Wing w : wings)
        {
            for (Room r : w.rooms())
            {
                if(r.number() == number) return r; 
            }
        }
        
        return null; 
    }
    
    public static Wing getWing(Room room)
    {
        for (Wing w : wings)
        {
            if (w.rooms().contains(room)) return w; 
        }
        
        return null; 
    }
}
