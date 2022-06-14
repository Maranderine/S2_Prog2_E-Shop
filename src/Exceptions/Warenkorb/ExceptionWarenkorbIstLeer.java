package Exceptions.Warenkorb;


public class ExceptionWarenkorbIstLeer extends Exception{
  public ExceptionWarenkorbIstLeer() {
    super("Ihr Warenkorb ist leer!");
  }
}
