
package assignment;


public class Cupboard{ 
    
    public void takeCup(String position) throws InterruptedException{
        System.out.println(position + " taking cup from Cupboard");
        Thread.sleep(500);
    }
    
    public void takeGlass(String position) throws InterruptedException{
        System.out.println(position + " taking glass from Cupboard");
        Thread.sleep(500);
    }
    
    public void takeMilk(String position) throws InterruptedException{
        System.out.println(position + " taking milk from Cupboard");
        Thread.sleep(500);
    }
    
    public void takeCoffee(String position) throws InterruptedException{
        System.out.println(position + " taking coffee from Cupboard");
        Thread.sleep(500);
    }


}
