
package assignment;

import java.util.logging.Level;
import java.util.logging.Logger;



public class Clock implements Runnable{
    private CustomerGenerator customerGenerator;
    private Owner owner;
    private Waiter waiter;
    private Cafe cafe;
    
    public Clock(Owner owner, Waiter waiter,CustomerGenerator customerGenerator,Cafe cafe){
       this.owner = owner;
       this.waiter = waiter;
       this.customerGenerator = customerGenerator;
       this.cafe = cafe;
    }
    
    public void run() {
        try {

            Thread.sleep(30000);
            NotifyLastCall();
            Thread.sleep(7000);
            NotifyClosingTime();
        } catch (InterruptedException ex) {
            Logger.getLogger(Clock.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //call last call function to notify owner and waiter
    public synchronized void NotifyLastCall(){
        System.out.println("############### CLOSING TIME SOON ###########");
        waiter.nearClosing = true;
        owner.nearClosing = true;
    }
    
    //call closing time function to notify owner, customer generator and cafe
    public synchronized void NotifyClosingTime(){
        System.out.println("############## CAFE IS NOW CLOSED ###############");
        owner.closingTime = true;
        customerGenerator.closingTime = true;
        cafe.closingTime = true;
    }
}
