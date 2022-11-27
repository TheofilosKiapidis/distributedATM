package project;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import project.Card;

public class createCards {
  public static void main(String[] args) {

    try {
      //Create the demo card objects
      Card c1 = new Card("1010", 1);
      Card c2 = new Card("1100", 2);
      Card c3 = new Card("1001", 3);

      //Save first
      FileOutputStream fosc1 = new FileOutputStream("card1.ser");
      ObjectOutputStream oosc1 = new ObjectOutputStream(fosc1);
      oosc1.writeObject(c1);
      oosc1.close();

      //Save second
      FileOutputStream fosc2 = new FileOutputStream("card2.ser");
      ObjectOutputStream oosc2 = new ObjectOutputStream(fosc2);
      oosc2.writeObject(c2);
      oosc2.close();

      //Save third
      FileOutputStream fosc3 = new FileOutputStream("card3.ser");
      ObjectOutputStream oosc3 = new ObjectOutputStream(fosc3);
      oosc3.writeObject(c3);
      oosc3.close();
    }
    catch (IOException e){
     e.printStackTrace();
    }
  }
  
}
