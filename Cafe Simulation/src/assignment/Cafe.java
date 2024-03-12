
package assignment;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cafe {
    int order;
    int numCust = 20;
    Owner owner = new Owner(this);
    Waiter waiter = new Waiter(this);
    CafeStatus shopStatus;
    ServingArea serveArea;
    Statistics stats;
    String orderName;
    Object serving = new Object();
    boolean closingTime = false;
    long duration = 0;
    
    LinkedList<Customer> listCustomer = new LinkedList<Customer>();
    public final List<Customer> allCustomer = new ArrayList<>();
    
    public Cafe(ServingArea serveArea,CafeStatus shopStatus,Statistics stats){
        this.serveArea = serveArea;
        this.shopStatus = shopStatus;
        this.stats = stats;
        owner.addStats(stats);
        waiter.addStats(stats);
        CustomerGenerator custGen = new CustomerGenerator(shopStatus,this);
        Thread thCustGen = new Thread(custGen);
        Thread thown = new Thread(owner);
        Thread thwait = new Thread(waiter);
        Clock clock = new Clock(owner,waiter,custGen,this);
        Thread thclock = new Thread(clock);
        
        
        thCustGen.start();
        thown.start();
        thclock.start();
    }
    
    
    public void goIn(Customer customer){
        allCustomer.add(customer);
    }
    
    public void enter(Customer customer){
       
        System.out.println(customer.custName  + " has entered the Cafe.");
        listCustomer.offer(customer);
        System.out.println("Available Seats :" + shopStatus.tableSeat);

        synchronized(serving){ 
            serving.notify();
        }
        synchronized(customer){
            try {
                customer.wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(Cafe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void leave(Customer customer){

        duration = (long) (Math.random() * 10); 
        try {
            TimeUnit.SECONDS.sleep(duration);
        } catch (InterruptedException ex) {
            Logger.getLogger(Customer.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(customer.custName + " has finished drinking in " + duration + " seconds");
        System.out.println(customer.custName + " is now leaving.");
    }
    
    void Serve(String position) {
        

        synchronized (serving){
            try{
                while (listCustomer.size() <= 0 && stats.customerServed.intValue() != numCust) {

                    serving.wait();

                }
            }catch(Exception e){
                System.out.println(e.toString());
            }
        }
        

        if(stats.customerServed.intValue() == numCust){
            return;
        }
        
        Customer customer = listCustomer.poll();

        
        if (owner.available == true) {
            synchronized (owner) {
                try {
                    owner.available = false;
                    takeOrder(position, customer);
                    owner.addStats(stats);
                    waiter.addStats(stats);
                    owner.available = true;
                } catch (Exception e) {
                    System.out.println(e.toString());
                }
            }
        } else {
            synchronized (waiter) {
                try {
                    
                  
                    waiter.available = false;
                    takeOrder(position, customer);
                    owner.addStats(stats);
                    waiter.addStats(stats);
                    waiter.available = true;
                   
                } catch (Exception e) {
                    System.out.println(e.toString());
                }
            }
        }

        synchronized (customer) {
            customer.notify();
        }
    }
    
    public void takeOrder(String position, Customer customer){
        System.out.println(position + " is now taking order from " + customer.custName);
        makeOrder(position,customer);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Cafe.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void makeOrder(String position, Customer customer) {
        order = new Random().nextInt(2);

        if (order == 0) {
            System.out.println(customer.custName + " had ordered Fruit Juice");
            stats.addJuiceOrder();
            try {
                makeJuice(position, order,customer);
            } catch (InterruptedException ex) {
                Logger.getLogger(Cafe.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.orderName = "FRUIT JUICE";
            return;
        } else {
            System.out.println(customer.custName + " had ordered Cappucino");
            stats.addCappucinoOrder();
            try {
                makeCappucino(position, order,customer);
            } catch (InterruptedException ex) {
                Logger.getLogger(Cafe.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.orderName = "CAPPUCINO";
            return;
        }
    }
    
    public void makeJuice(String position, int order,Customer customer) throws InterruptedException {
        serveArea.useCupboard(position, order);
        System.out.println(position + " is now making Fruit Juice");
        serveArea.useJuiceTap(position);
        System.out.println(position + " had finished making Fruit Juice and served to "+ customer.custName);
        stats.addCustomerServed();
    }

    public void makeCappucino(String position, int order,Customer customer) throws InterruptedException {
        if (serveArea.coffeeAvailable == true && serveArea.milkAvailable == true){
            serveArea.useCupboard(position, order);
        }else{
            System.out.println(position + " is waiting for cupboard to be available");
            synchronized(serving){
                serving.wait();
                serveArea.useCupboard(position, order);
            }
        }
        System.out.println(position + " is now making Cappucino");
        Thread.sleep(1000);
        System.out.println(position + " had finished making Cappucino and served to " +customer.custName);
        System.out.println(position + " returning ingredients back to cupboard");
        stats.addCustomerServed();
        serveArea.coffeeAvailable = true; serveArea.milkAvailable = true;
        synchronized(serving){
            serving.notify();
        }
    }
    

    public void forceLeave(){
        synchronized(serving){
            serving.notifyAll();
        }
    }
    
}
