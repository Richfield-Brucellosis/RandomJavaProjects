import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {
    public static void main (String[] args) {
        Bing d = new Bing();
        Scanner scan = new Scanner(System.in);
        try {
            while (true) {
                scan.nextLine();
                d.update();
            }
        } catch (IllegalStateException | NoSuchElementException e) {
            // System.in has been closed
            System.out.println("System.in was closed; exiting");
        }
    }
}
class Bing extends Thing {
    public void list (int i) {
        switch (i) {
            case 1:
                ADMove(10, 0, 0.5);
                break;
            case 2:
                System.out.println("complete");
        }
    }
}