package Exceptions.Ereignis;

import Domain.EreignisLog.Ereignisse.Ereignis;

public class ExceptionEreignis extends Exception {
  public Ereignis ereignis = null;

  public ExceptionEreignis(Ereignis ereignis, String message) {
    super(message);
    this.ereignis = ereignis;
  }
}