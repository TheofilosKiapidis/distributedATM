package project;
import java.io.Serializable;

class Card implements Serializable{
  private String pin;
  private int id;

  public Card(String pin, int id){
    this.pin = pin;
    this.id = id;
  }

  public String getPin(){
    return this.pin;
  }

  public int getId(){
    return this.id;
  }

  public void setPin(String pin){
    this.pin = pin;
  }

  public void setId(int id){
    this.id = id;
  }
}
