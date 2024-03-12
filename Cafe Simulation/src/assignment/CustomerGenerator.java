
package assignment;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;


public class CustomerGenerator implements Runnable{
    CafeStatus cafeStatus;
    Cafe cafe;
    int custID = 1;
    int numCust = 10;
    boolean closingTime = false;
        
    public CustomerGenerator (CafeStatus cafeStatus,Cafe cafe){
        this.cafeStatus = cafeStatus;
        this.cafe = cafe;
    }
    
    public void run(){
        while(!closingTime){
            if (custID <= numCust){
                try {
                    Customer customer = new Customer(cafe, custID,cafeStatus);
                    Thread thcust = new Thread(customer);
                    thcust.start();
                    custID ++;
                    TimeUnit.SECONDS.sleep((long)(Math.random()*3));
                }catch(Exception e){
                    System.out.println("Error");
                }
            }
            else{
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(CustomerGenerator.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        if(closingTime){
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
                Logger.getLogger(CustomerGenerator.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
