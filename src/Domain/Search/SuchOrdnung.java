package Domain.Search;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

/**
 * Maanagement Klasse für eine geordnete liste mit Searchaable objekten
 */
public class SuchOrdnung {

  private ArrayList<HashMap<OrdnungIndex, Object>> grid = new ArrayList<HashMap<OrdnungIndex, Object>>();

  /**
   * 
   */
  public SuchOrdnung() {
  }

  /**
   * ordnungs index
   */
  protected enum OrdnungIndex {
    OBJEKT,
    RELEVANZ;

    protected static int size() {
      return OrdnungIndex.values().length;
    }
  }

  public void add(Searchable searchable, int relevanz) {
    // create new entry
    HashMap<OrdnungIndex, Object> entry = createNewEntry();
    // set values
    setEntry(entry, searchable);
    setEntry(entry, relevanz);
    // add entry to grid
    grid.add(entry);
  }

  private HashMap<OrdnungIndex, Object> createNewEntry() {
    HashMap<OrdnungIndex, Object> entry = new HashMap<OrdnungIndex, Object>(2);

    return entry;
  }

  // set
  private void setEntry(HashMap<OrdnungIndex, Object> entry, Searchable searchable) {
    entry.put(OrdnungIndex.OBJEKT, searchable);
  }

  private void setEntry(HashMap<OrdnungIndex, Object> entry, int relevanz) {
    entry.put(OrdnungIndex.RELEVANZ, relevanz);
  }

  // get
  private Searchable getEntry_searchable(HashMap<OrdnungIndex, Object> entry) {
    return (Searchable) entry.get(OrdnungIndex.OBJEKT);
  }

  private int getEntry_relevanz(HashMap<OrdnungIndex, Object> entry) {
    return (int) entry.get(OrdnungIndex.RELEVANZ);
  }

  public Searchable getObjekt(int index) {
    return getEntry_searchable(grid.get(index));
  }

  public int getRelevanz(int index) {
    return getEntry_relevanz(grid.get(index));
  }

  // #region sorting
  /**
   * Comparator Object mit compare methode die nach relevanz ordnet. Hohe relevanz
   * hoch (niedriger index) in der liste.
   */
  private Comparator<HashMap<OrdnungIndex, Object>> ComparatorRelevanz = new Comparator<HashMap<OrdnungIndex, Object>>() {
    @Override
    public int compare(HashMap<OrdnungIndex, Object> o1, HashMap<OrdnungIndex, Object> o2) {
      return getEntry_relevanz(o1) - getEntry_relevanz(o2);
    }
  };

  /**
   * Sortiere nach relevanz
   */
  protected void sort() {
    grid.sort(this.ComparatorRelevanz);
  }

  /**
   * Sortiere mit custom Comparitor
   * generell gilt für Comparator.compare: hohe werte = tief in der liste
   * 
   * @param comparator
   */
  protected void sort(Comparator<HashMap<OrdnungIndex, Object>> comparator) {
    grid.sort(comparator);
  }

  // #endregion
  // #region generelles
  public int size() {
    return grid.size();
  }
  /** gibt einen iterator über das grid zurück */
  public void iterator() {
    grid.iterator();
  }
  // #endregion

}
