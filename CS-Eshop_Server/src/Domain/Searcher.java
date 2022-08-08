package Domain.Search;

import java.util.AbstractList;
import java.util.Comparator;
import java.util.HashMap;

import Domain.Search.SuchOrdnung.OrdnungIndex;

/**
 * Eine klasse die luch und listen kompilier funktionen enthält
 * 
 * Soll nur an Searchable Objecten benutzt werden.
 */
public abstract class Searcher {

  public Searcher() {

  }

  /**
   * string formatting to be done before using the string
   * searchable equivalent to searchableStringFormat
   * 
   * @param string
   * @return String formaatted
   */
  private String searcherStringFormat(String string) {
    return string.toLowerCase();
  }

  /**
   * 
   * @param searchable obj
   * @param termArr    string array
   * @return relevanz Hohe relevanz hoch (niedriger index) in der liste.
   */
  private int evalRelevanz(Searchable searchable, String[] termArr) {
    int relevanz = 0;
    for (String string : termArr) {
      relevanz += searchable.SearchTermContains(string);
    }

    return relevanz;
  }

  /**
   * Erstellt eine neue ordnung die alle Objecte enthält,
   * die eine oder mehrere übeinstimmungen mit dem such string haben
   * Einträge in der ordnung sind markiert mit ihrer relevanz zum such String
   * Ungeordnet.
   * 
   * @param list        liste mir Objekten die durchsucht weren sollen
   * @param Suchbegriff singulärer suchbegriff
   * @return SuchOrdnung
   */
  protected SuchOrdnung SearchCompileOrdnung(AbstractList<? extends Searchable> list, String suchString) {

    SuchOrdnung ordnung = new SuchOrdnung();
    String[] suchStringList = searcherStringFormat(suchString.trim()).split(" ");
    int relevanz;
    // geht liste mit Objekten durch
    for (Searchable searchable : list) {

      // geht liste an such strings durch
      relevanz = evalRelevanz(searchable, suchStringList);
      // Hohe relevanz hoch (niedriger index) in der liste.
      if (relevanz > 0) {
        ordnung.add(searchable, relevanz);
      }
    }
    return ordnung;
  }

  /**
   * Erstellt eine neue ordnung die alle Objecte enthält,
   * die eine oder mehrere übeinstimmungen mit dem such string haben
   * Einträge in der ordnung sind markiert mit ihrer relevanz zum such String
   * Geordnet nach relevanz.
   * 
   * @param list        liste mir Objekten die durchsucht weren sollen
   * @param Suchbegriff singulärer suchbegriff
   * @return
   */
  protected SuchOrdnung SearchCompileOrdnungSorted(AbstractList<? extends Searchable> list, String suchString) {
    SuchOrdnung ordnung = SearchCompileOrdnung(list, suchString);
    sortOrdnung(ordnung);
    return ordnung;
  }

  
  
  // #region sorting

  /**
   * Sortiert gegebene ordnung mit der standart ordnung nach relevanz-
   * 
   * @param ordnung SuchOrdnung objekt
   */
  protected void sortOrdnung(SuchOrdnung ordnung) {
    ordnung.sort();
  }

  /**
   * Sortiert gegebene ordnung
   * 
   * @param ordnung    SuchOrdnung objekt
   * @param comparator
   */
  protected void sortOrdnung(SuchOrdnung ordnung, Comparator<HashMap<OrdnungIndex, ? extends Object>> comparator) {
    ordnung.sort(comparator);
  }

  // #endregion sorting
}
