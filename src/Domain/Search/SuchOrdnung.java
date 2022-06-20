package Domain.Search;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

/**
 * Management Klasse für eine geordnete liste mit Searchaable objekten
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
  protected static enum OrdnungIndex {
    OBJEKT,
    RELEVANZ;

    protected static int size() {
      return OrdnungIndex.values().length;
    }
  }

  /**
   * füge einen eintrag der ordnung hinzu
   * 
   * @param searchable
   * @param relevanz
   */
  protected void add(Searchable searchable, int relevanz) {
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
   * @param comparator der die ordnung vorgibt
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

  /**
   * stellt die Objecte mit ihrer toString oder toStringDetailed methoden da
   * 
   * @param detailed      boolean ob die einträge in detaaillierter form
   *                      dargestellte werden soll
   * @param leereNachicht nachicht die angezeigt wird wenn die liste leer ist
   * @return
   */
  public String display(boolean detailed, String leereNachicht) {
    String str = "";

    if (this.grid.isEmpty()) {
      // empty
      str += "\t" + leereNachicht + "\n";
    } else {
      if (detailed) {
        for (HashMap<OrdnungIndex, Object> hashMap : grid) {
          str += getEntry_searchable(hashMap).toStringDetailed() + "\n";
        }
      } else {
        for (HashMap<OrdnungIndex, Object> hashMap : grid) {
          str += getEntry_searchable(hashMap).toString() + "\n";
        }
      }
    }

    return str;
  }

  @Override
  public String toString() {

    String str = display(false, "Ordnung ist leer!");

    return str;
  }
}
