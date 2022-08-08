package Domain.Search;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Vector;

/**
 * Management Klasse für eine geordnete liste mit Searchaable objekten
 */
public class SuchOrdnung {

  private ArrayList<HashMap<OrdnungIndex, ? extends Object>> grid = new ArrayList<HashMap<OrdnungIndex, ? extends Object>>();

  /**
   * neue Such ordnung
   */
  public SuchOrdnung() {

  }

  /**
   * shallow clone
   */
  public SuchOrdnung(SuchOrdnung andereOrdnung) {

    for (int i = 0; i < andereOrdnung.size(); i++) {
      add(andereOrdnung.getObjekt(i), andereOrdnung.getRelevanz(i));
    }
  }

  /**
   * ordnungs index
   */
  public static enum OrdnungIndex {
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
  public Searchable getObjekt(HashMap<OrdnungIndex, ? extends Object> hashMap) {
    return (Searchable) hashMap.get(OrdnungIndex.OBJEKT);
  }

  public Searchable getObjekt(int index) {
    return getObjekt(grid.get(index));
  }

  public Vector<Searchable> getObjektList(){
    Vector<Searchable> list = new Vector<Searchable>();
    for (HashMap<OrdnungIndex, ? extends Object> hashMap : grid) {
      list.add(getObjekt(hashMap));
    }
    return list;
  }

  public int getRelevanz(HashMap<OrdnungIndex, ? extends Object> hashMap) {
    return (int) hashMap.get(OrdnungIndex.RELEVANZ);
  }

  public int getRelevanz(int index) {
    return getRelevanz(grid.get(index));
  }

  // #region sorting

  /**
   * Sortiere nach relevanz
   */
  protected void sort() {
    grid.sort((o1, o2) -> {
      return getRelevanz(o1) - getRelevanz(o2);
    });
  }

  /**
   * Sortiere mit custom Comparitor
   * generell gilt für Comparator.compare: hohe werte = tief in der liste
   * 
   * @param comparator der die ordnung vorgibt
   */
  protected void sort(Comparator<HashMap<OrdnungIndex, ? extends Object>> comparator) {
    grid.sort(comparator);
  }

  // #endregion

  public int size() {
    return grid.size();
  }

  /**
   * stellt die Objecte mit ihrer toString oder toStringDetailed methoden da
   * 
   * @param detailed      boolean ob die einträge in detaaillierter form
   *                      dargestellte werden soll
   * @param leereNachicht nachicht die angezeigt wird wenn die liste leer ist
   * @return
   */
  public String display(boolean detailed, String leereNachicht) {
    // seperation string
    String sepStr = "///////////////////////////////////////////////\n\n";
    String str = "";

    if (this.grid.isEmpty()) {
      // empty
      str += "\t" + leereNachicht + "\n";
    } else {
      if (detailed) {
        for (HashMap<OrdnungIndex, ? extends Object> hashMap : grid) {
          str += sepStr;
          str += getObjekt(hashMap).toStringDetailed() + "\n";
        }

      } else {
        for (HashMap<OrdnungIndex, ? extends Object> hashMap : grid) {
          str += getObjekt(hashMap).toString() + "\n";
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
