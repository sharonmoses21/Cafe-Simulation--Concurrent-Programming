
package assignment;

import java.util.logging.Level;
import java.util.logging.Logger;


public class Waiter implements Runnable {
    String name = "Waiter :";
    Cafe cafe;
    Statistics stats;
    public boolean nearClosing = false;
    boolean available = true;
    int numCust = 20;
    
    public Waiter(Cafe cafe)
    {
        this.cafe = cafe;
    }

    public void run()
    {

        while(!nearClosing){
            if(numCust != stats.customerServed.intValue()){
                cafe.Serve(this.name);
            }else{
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Waiter.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        if(nearClosing){
            cafe.forceLeave();
            System.out.println(name + " will now leave the cafe");

            try {
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Waiter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void addStats(Statistics stats){
        this.stats = stats;
    }
}
