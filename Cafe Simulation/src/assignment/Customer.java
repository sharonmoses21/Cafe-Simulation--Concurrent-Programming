
package assignment;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Customer implements Runnable{
    public static final List<Customer> allCustomer = new ArrayList<Customer>();
    int custID;
    String custName;
    public boolean closingTime =false;
    boolean leave = false;
    long duration = 0;
    CafeStatus cafeStatus;
    Cafe cf;
    CustomerGenerator cg = new CustomerGenerator(cafeStatus,cf);
   
    
    public Customer (Cafe c,int customerID,CafeStatus cafeStatus){
        this.cf = c;
        this.custID = customerID;
        this.cafeStatus = cafeStatus;
        this.custName = ("Customer "+ customerID + " :");
    }
    

    public void run() {
        cf.goIn(this);
        System.out.println(custName + " arriving at the cafe.");
        try {
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            Logger.getLogger(Customer.class.getName()).log(Level.SEVERE, null, ex);
        }
        synchronized (cafeStatus.seat) {
            while (cafeStatus.tableSeat < 1) {

                try {
                    System.out.println(custName + " is waiting at the line.");
                    cafeStatus.seat.wait();
                    
                    if(closingTime){
                        leave = true;
                        System.out.println(custName + " are required to leave the cafe");
                        break; //exit the loop
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(Customer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        if (!leave) {

            try {
                cafeStatus.tableSeat--;
            } catch (Exception e) {
                e.getMessage();
            }

            cf.enter(this);

            cf.leave(this);

            synchronized (cafeStatus.seat) {

                cafeStatus.tableSeat++;
                if(!closingTime){
                    cafeStatus.seat.notify();
                }
            }

            
        }

    }
    
    public void setClosingTime() {
        synchronized(cafeStatus.seat){
            this.closingTime = true;
            cafeStatus.seat.notifyAll();
        }
    }

}
