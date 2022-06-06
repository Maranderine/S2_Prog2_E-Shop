package Domain.Search;

import java.util.List;
import java.util.Vector;

/**
 * Eine klasse die luch und listen kompilier funktionen enthält
 * 
 * Soll nur an Searchable Objecten benutzt werden.
 */
public abstract class Searcher {

  public Searcher() {

  }

  /**
   * Erstellt eine neue liste die alle Objecte enthält,
   * die den Such Begriff enthalten
   * 
   * @param list        liste mir Objekten die durchsucht weren sollen
   * @param Suchbegriff singulärer suchbegriff
   * @return
   */
  protected List<Searchable> SearchCompileList(List<Searchable> list, String suchbegriff) {
    Vector<Searchable> compiledList = new Vector<Searchable>();
    // geht gegeben liste durch
    for (Searchable searchable : list) {
      // wenn suchbegriff gefunden wurdde
      if (searchable.SearchTermCheck(suchbegriff))
        // fürgt obj der liste hinzu
        compiledList.add(searchable);
    }
    return compiledList;
  }

  // #region ordung

  /**
   * Erstellt eine neue ordnung die alle Objecte enthält,
   * die eine oder mehrere übeinstimmungen mit dem such string haben
   * Einträge in der ordnung sind markiert mit ihrer relevanz zum such String
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

  // #endregion
}
