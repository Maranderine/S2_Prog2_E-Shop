package Domain.Search;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

/**
 * abstract class for searchable objects
 */
public abstract class Searchable {

  protected HashSet<String> searchTerms;

  public Searchable() {
    this.searchTerms = new HashSet<>();
  }

  public Searchable(HashSet<String> searchTerms) {
    this.searchTerms = searchTerms;

    // look through the given set and replace all non lower case entries with
    // lowercase
    String entrie;
    String lower;
    Iterator<String> iter = this.searchTerms.iterator();

    while (iter.hasNext()) {
      entrie = iter.next();
      lower = entrie.toLowerCase();
      if (!entrie.equals(lower)) {
        SearchTermChange(entrie, lower);
      }
    }
  }

  public Searchable(String[] stringArr) {
    SearchTermAdd(stringArr);
  }

  public Searchable(String string) {
    SearchTermAdd(string);
  }

  /**
   * Looks if Event contains the term.
   * term must be exact.
   * faster
   * Ignores character case
   * 
   * @param term to look for
   * @return
   */
  protected boolean SearchTermCheck(String term) {
    term = term.toLowerCase();
    for (String string : this.searchTerms) {
      if (string.contains(term))
        return true;
    }
    return false;
  }

  /**
   * look how many search terms of the obj are contained in the termstring.
   * Ignores character case.
   * For one term only use SearchTermCheck
   * 
   * @param termstring to check for occurences
   * @return int value of how many terms were found, or 0
   */
  protected int SearchTermContains(String termstring) {
    int val = 0;
    termstring = termstring.toLowerCase();
    for (String string : this.searchTerms) {
      if (termstring.contains(string))
        val++;
    }
    return val;
  }

  /**
   * adds string to search term set
   * 
   * @param string
   */
  protected void SearchTermAdd(String string) {
    this.searchTerms.add(string.toLowerCase());
  }

  protected void SearchTermAdd(String[] stringArr) {
    this.searchTerms = new HashSet<String>();
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
   * replaces the old term with the new one
   * 
   * @param target
   * @param newTerm
   */
  protected void SearchTermChange(String target, String newTerm) {
    SearchTermRemove(target);
    SearchTermAdd(newTerm);
  }

}
