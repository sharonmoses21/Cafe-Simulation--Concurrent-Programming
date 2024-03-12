
package assignment;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Owner implements Runnable{
    String name = "Owner :";
    Cafe cafe;
    Customer customer;
    Statistics stats;
    public boolean closingTime = false;
    boolean available = true;
    boolean nearClosing = false;
    private List<Customer> allCustomers ;
    int numCust = 10;
    
    public Owner(Cafe cafe)
    {
        this.cafe = cafe;
        
    }
    
 
    public void run() {
        

        while (!nearClosing) {
            if( numCust != stats.customerServed.intValue()){
                cafe.Serve(this.name);
                allCustomers = cafe.allCustomer;
            }else{
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Owner.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        while(!closingTime){
            cafe.forceLeave();
            System.out.println(name + " LAST ORDER, ORDER NOW");
            if (numCust != stats.customerServed.intValue()) {
                for (int i = 0; i < allCustomers.size(); i++) {
                    allCustomers.get(i).setClosingTime();
                    stats.addTotalCust();
                }

                while (!cafe.listCustomer.isEmpty()) {
                    cafe.Serve(this.name);
                }
            }
            else{
                try {
                    for (int i = 0; i < allCustomers.size(); i++) {
                        allCustomers.get(i).setClosingTime();
                        stats.addTotalCust();
                    }
                    Thread.sleep(10000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Owner.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        if(closingTime){
            while (!cafe.listCustomer.isEmpty()) {
                cafe.Serve(this.name);
            }
            System.out.println(name + " all customer has been served");
            try {
                Thread.sleep(5000); //wait for 5 seconds
            } catch (InterruptedException ex) {
                Logger.getLogger(Owner.class.getName()).log(Level.SEVERE, null, ex);
            }

            stats.displayTotalStatistics();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Owner.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println(name + " will now leave the Cafe");
            try {
                Thread.sleep(2000);

            } catch (InterruptedException ex) {
                Logger.getLogger(Owner.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void addStats(Statistics stats){
        this.stats = stats;
    }
}
