package Domain.Search;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Maanagement Klasse für eine geordnete liste mit Searchaable objekten
 */
public class SuchOrdnung {

  ArrayList<ArrayList<Object>> grid = new ArrayList<ArrayList<Object>>();

  public SuchOrdnung() {
  }

  /**
   * ordnungs index
   */
  protected enum OrdnungIndex {
    objekt(0),
    relevanz(1);

    private int index;

    OrdnungIndex(int index) {
      this.index = index;
    }

    public int Index() {
      return index;
    }

    static public int size() {
      return OrdnungIndex.values().length;
    }

  }

  public void add(Searchable searchable, int relevanz) {
    // create new entry
    ArrayList<Object> entry = createNewEntry();
    // set values
    setEntry(entry, searchable);
    setEntry(entry, relevanz);
    // add entry to grid
    grid.add(entry);
  }

  private ArrayList<Object> createNewEntry() {
    ArrayList<Object> entry = new ArrayList<Object>(2);

    for (int i = 0; i < OrdnungIndex.size(); i++) {
      entry.add(null);
    }
    return entry;
  }

  // set
  private void setEntry(List<Object> list, Searchable searchable) {
    list.set(OrdnungIndex.objekt.Index(), searchable);
  }

  private void setEntry(List<Object> list, int relevanz) {
    list.set(OrdnungIndex.relevanz.Index(), relevanz);
  }

  // get
  private Searchable getEntry_searchable(List<Object> list) {
    return (Searchable) list.get(OrdnungIndex.objekt.Index());
  }

  private int getEntry_relevanz(List<Object> list) {
    return (int) list.get(OrdnungIndex.relevanz.Index());
  }

  public Searchable getObjekt(int index) {
    return getEntry_searchable(grid.get(index));
  }

  public int getRelevanz(int index) {
    return getEntry_relevanz(grid.get(index));
  }

  // other
  protected void sort(Comparator<ArrayList<Object>> comparator) {
    // TODO complete, manager should do the comarator construction, maybe replace
    // Search term eval with SearchCompileOrdnung with comparitor
    grid.sort(comparator);
  }

  // #region generelles
  public int size() {
    return grid.size();
  }

  public void iterator() {
    grid.iterator();
  }
  // #endregion
}
