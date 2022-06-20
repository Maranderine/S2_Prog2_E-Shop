package Domain.Search;

import java.util.Collection;
import java.util.List;
import java.util.Vector;

/**
 * abstract class for searchable objects.
 * Alle search terms müssen lowecase sein!!!
 */
public abstract class Searchable {

  /**
   * such begriffe.
   * Alle search terms müssen lowecase sein!!!
   */
  protected Vector<String> searchTerms;

  /**
   * Searchable.
   * Alle search terms müssen lowecase sein!!!
   */
  public Searchable() {
    this.searchTerms = new Vector<String>();
  }

  /**
   * Searchable.
   * Alle search terms müssen lowecase sein!!!
   * 
   * @param searchTerms
   */
  public Searchable(List<String> searchTerms) {
    this.searchTerms = (Vector<String>) searchTerms;

    // look through the given set and replace all non lower case entries with
    // lowercase
    String formatted;

    for (String string : searchTerms) {

      formatted = searchableStringFormat(string);
      if (!string.equals(formatted)) {
        SearchTermReplace(string, formatted);
      }
    }
  }

  /**
   * Searchable.
   * Alle search terms müssen lowecase sein!!!
   * 
   * @param stringArr
   */
  public Searchable(String[] stringArr) {
    SearchTermAdd(stringArr);
  }

  /**
   * Searchable.
   * Alle search terms müssen lowecase sein!!!
   * 
   * @param string
   */
  public Searchable(String string) {
    SearchTermAdd(string);
  }

  /**
   * string formatting to be done before using the string
   * searchable equivalent to searchableStringFormat
   * 
   * @return String formaatted
   */
  private String searchableStringFormat(String string) {
    return string.toLowerCase();
  }

  /**
   * adds string to search term set.
   * Alle search terms müssen lowecase sein!!!
   * 
   * @param string
   */
  protected void SearchTermAdd(String string) {
    this.searchTerms.add(searchableStringFormat(string));
  }

  /**
   * add all search terms.
   * Alle search terms müssen lowecase sein!!!
   * 
   * @param stringArr
   */
  protected void SearchTermAdd(String[] stringArr) {
    this.searchTerms = new Vector<String>();
    for (String string : stringArr) {
      SearchTermAdd(string);
    }
  }

  /**
   * removes string from search term list
   * 
   * @param string
   */
  protected void SearchTermRemove(String string) {
    this.searchTerms.remove(string);
  }

  /**
   * removes terms from search term list
   * 
   * @param stringArr
   */
  protected void SearchTermRemove(String[] stringArr) {
    for (String string : stringArr) {
      SearchTermRemove(string);
    }
  }

  /**
   * removes string from search term list
   * 
   * @param string
   */
  protected void SearchTermRemove(Collection<String> collection) {
    this.searchTerms.removeAll(collection);
  }

  /**
   * replaces the old term with the new one.
   * Alle search terms müssen lowecase sein!!!
   * 
   * @param oldTerm to be replaced
   * @param newTerm to to replace the old one
   */
  protected void SearchTermReplace(String oldTerm, String newTerm) {
    SearchTermRemove(oldTerm);
    SearchTermAdd(newTerm);
  }

  // #region searching
  /**
   * look how many search terms of the obj are contained in the termstring.
   * Ignores character case.
   * For one term only use SearchTermCheck
   * 
   * @param termstring to check for occurences
   * @return int value of how many terms were found, or 0
   */
  protected int SearchTermContains(String termstring) {
    termstring = searchableStringFormat(termstring);
    int val = 0;
    for (String string : this.searchTerms) {
      if (string.contains(termstring))
        val++;
    }
    return val;
  }

  // #endregion

  @Override
  public abstract String toString();

  /**
   * detailed toString, giving all data, except objects
   * 
   * @return
   */
  public abstract String toStringDetailed();
}
