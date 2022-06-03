package Domain.Search;

public class Searchable {

  protected String[] searchTerms;

  public Searchable(String[] searchTerms) {
    this.searchTerms = searchTerms;
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

    for (String string : searchTerms) {
      if (term.equalsIgnoreCase(string))
        return true;
    }
    return false;
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
    termstring = termstring.toLowerCase();
    for (String string : searchTerms) {
      if (termstring.contains(string))
        val++;
    }
    return val;
  }

}
