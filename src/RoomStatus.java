
public enum RoomStatus 
{
    OCCUPIED("occupied"), 
    NOINFORMATION("none"), 
    PARTIAL("partial"), 
    OPEN("open"), 
    ACTIVE("active"); 
    
    private final String str; 
    
    private RoomStatus(String s)
    {
        this.str = s; 
    }
    
    @Override
    public String toString()
    {
        return str; 
    }
}
