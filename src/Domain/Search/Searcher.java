package Domain.Search;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

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
   * Erstellt eine neue ordnung die alle Objecte enthält,
   * die eine oder mehrere übeinstimmungen mit dem such string haben
   * Einträge in der ordnung sind markiert mit ihrer relevanz zum such String
   * Ungeordnet.
   * 
   * @param list        liste mir Objekten die durchsucht weren sollen
   * @param Suchbegriff singulärer suchbegriff
   * @return
   */
  protected SuchOrdnung SearchCompileOrdnung(List<Searchable> list, String suchString) {
    SuchOrdnung ordnung = new SuchOrdnung();
    int relevanz;
    // geht gegeben liste durch
    for (Searchable searchable : list) {
      // wenn suchbegriff gefunden wurdde
      relevanz = searchable.SearchTermContains(suchString);
      if (relevanz != 0) {
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
  protected SuchOrdnung SearchCompileOrdnungSorted(List<Searchable> list, String suchString) {
    SuchOrdnung ordnung = SearchCompileOrdnung(list, suchString);
    sortOrdnung(ordnung);
    return ordnung;
  }

  /**
   * Sortiert gegebene ordnung mit der standart ordnung nach relevanz-
   * 
   * @param ordnung SuchOrdnung objekt
   */
  protected void sortOrdnung(SuchOrdnung ordnung) {
    ordnung.sort();
  }

  /**
   * Sortiert gegebene ordnung mit der standart ordnung nach relevanz-
   * 
   * @param ordnung    SuchOrdnung objekt
   * @param comparator
   */
  protected void sortOrdnung(SuchOrdnung ordnung, Comparator<HashMap<OrdnungIndex, Object>> comparator) {
    ordnung.sort(comparator);
  }
}