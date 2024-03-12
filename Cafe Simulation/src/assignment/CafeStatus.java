
package assignment;


public class CafeStatus {

    Object seat = new Object();
    int tableSeat;

    Object serving = new Object();

    CafeStatus(int seatSize) {
        tableSeat = seatSize;
    }
}
