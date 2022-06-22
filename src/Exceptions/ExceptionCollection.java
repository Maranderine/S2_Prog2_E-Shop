package Exceptions;

import java.util.Iterator;
import java.util.Vector;

/**
 * iteraable
 */
public class ExceptionCollection extends Exception implements Iterable<Exception> {

  public Vector<Exception> exceptionList = new Vector<Exception>();

  /**
   * iteraable
   * 
   * @param message
   */
  public ExceptionCollection(String message) {
    super(message);
  }

  /**
   * iteraable
   * 
   * @param message
   * @param list
   */
  public ExceptionCollection(String message, Vector<? extends Exception> list) {
    this(message);
    exceptionList.addAll(list);
  }

  public void add(Exception exception) {
    exceptionList.add(exception);
  }

  public boolean isEmpty() {
    return exceptionList.isEmpty();
  }

  public int empty() {
    return exceptionList.size();
  }

  // displays niormal to string + allthat is in list
  @Override
  public String getMessage() {
    String str = super.getMessage() + "\n\n";

    for (Exception exception : exceptionList) {
      str += exception.getMessage() + "\n";
    }

    return str;
  }

  @Override
  public Iterator<Exception> iterator() {
    return (Iterator<Exception>) exceptionList.iterator();
  }
}
