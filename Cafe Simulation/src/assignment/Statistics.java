
package assignment;

import java.util.concurrent.atomic.AtomicInteger;


public class Statistics {
    
    AtomicInteger juiceOrder = new AtomicInteger();
    AtomicInteger coffeeOrder = new AtomicInteger();
    AtomicInteger customerServed = new AtomicInteger();
    AtomicInteger totalCustomer = new AtomicInteger();
    int finalCustLeft;
    int finalCustServed;
    

    public synchronized void addJuiceOrder(){
        juiceOrder.getAndAdd(1);
    }
    
    public synchronized void addCappucinoOrder(){
        coffeeOrder.getAndAdd(1);
    }
    
    public synchronized void addCustomerServed(){
        customerServed.getAndAdd(1);
    }
    
    public synchronized void addTotalCust(){
        totalCustomer.getAndAdd(1);
    }
    
    public void displayTotalStatistics() {
        finalCustLeft = totalCustomer.intValue();
        finalCustServed = customerServed.intValue();
        finalCustLeft = finalCustLeft - finalCustServed;
        System.out.println("@@@@@@@@@@@@@@ THE STATISTICS @@@@@@@@@@@@");
        System.out.println("The number of fruit juice ordered : " + juiceOrder);
        System.out.println("The number of coffee ordered      : " + coffeeOrder);
        System.out.println("The number of customer served     : " + customerServed);
        System.out.println("The number of customer left       : " + finalCustLeft);

    }

}
