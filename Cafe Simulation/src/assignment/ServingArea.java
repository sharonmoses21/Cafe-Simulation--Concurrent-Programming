
package assignment;


public class ServingArea {
    Cupboard c;
    boolean milkAvailable = true;
    boolean coffeeAvailable = true;


    public ServingArea(Cupboard c){
        this.c = c;
    }
    

    public synchronized void useJuiceTap(String position) throws InterruptedException{
        System.out.println(position + " is now using Juice Tap.");
        Thread.sleep(1000);
    }
    
    public synchronized void useCupboard(String position, int order) throws InterruptedException{

        if (order == 0){
            c.takeGlass(position);
            
        }

        else{
            c.takeCup(position);
            c.takeMilk(position);
            c.takeCoffee(position);
            
            milkAvailable = false;
            coffeeAvailable = false;
        }
    }
}

