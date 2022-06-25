package Exceptions.Artikel;

import java.util.Vector;

import Exceptions.ExceptionCollection;

/**
 * ExceptionArtikelCollection
 */
public class ExceptionArtikelCollection extends ExceptionCollection {

  public ExceptionArtikelCollection(String message) {
    super(message);
  }

  public ExceptionArtikelCollection(String message, Vector<ExceptionArtikel> list) {
    super(message, list);
  }
}