package Domain.Search;

import java.util.HashSet;

public class Searchable {

  protected HashSet<String> searchTerms;

  public Searchable() {
    this.searchTerms = new HashSet<String>();
  }

  public Searchable(HashSet<String> searchTerms) {
    this.searchTerms = searchTerms;
  }

  public Searchable(String[] stringArr) {
    this.searchTerms = new HashSet<String>();
    for (String string : stringArr) {
      this.searchTerms.add(string);
    }
  }

  /**
   * Looks if Event contains the term.
   * term must be exact.
   * fast
   * Ignores character case
   * 
   * @param term to look for
   * @return
   */
  protected boolean isSearchTerm(String term) {
    return this.searchTerms.contains(term);
  }

  /**
   * look how many search terms of the obj are contained in the termstring.
   * Ignores character case.
   * For one term only use isSearchTerm
   * 
   * @param string to check for accurences
   * @return int value of how many terms were found
   */
  protected int containsSearchTerm(String termstring) {
    int val = 0;

    this.searchTerms.forEach();

    return val;
  }

}
