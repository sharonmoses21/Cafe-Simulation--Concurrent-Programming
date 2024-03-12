
package assignment;


public class MainClass {
    public static void main(String ar[])
    {
        int numSeats = 10;
        
        CafeStatus shopStatus = new CafeStatus(numSeats);
        
        Cupboard c = new Cupboard();
        ServingArea s = new ServingArea(c);
        Statistics stats = new Statistics();
        Cafe cafe = new Cafe(s,shopStatus,stats);
        
    }
}

