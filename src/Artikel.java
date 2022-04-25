public class Artikel {
    // Attribute zur Beschreibung eines Artikels:
    private String name;
    private int nummer;
    private boolean verfuegbar;


    public Artikel(String name, int nr) {
        this(name, nr, true);
    }

    public Artikel(String name, int nr, boolean verfuegbar) {
        nummer = nr;
        this.name = name;
        this.verfuegbar = verfuegbar;
    }

    /**
     * Methode wird immer automatisch aufgerufen, wenn ein Artikel als String
     * benutzt wird
     *
     * @see java.lang.Object#toString()
     */

    public String toString() {
        String verfuegbarkeit = verfuegbar ? "verfuegbar" : "ausverkauft";
        return ("Nr: " + nummer + " / name: " + name + " / " + verfuegbarkeit);
    }

    /**
     * Standard-Methode von Object überschrieben.
     * Methode dient Vergleich von zwei Artikel-Objekten anhand ihrer Werte,
     * d.h. name und Nummer.
     *
     * @see java.lang.Object#toString()
     */

    public boolean equals(Object andererArtikel) {
        if (andererArtikel instanceof Artikel)
            return ((this.nummer == ((Artikel) andererArtikel).nummer)
                    && (this.name.equals(((Artikel) andererArtikel).name)));
        else
            return false;
    }


    public int getNummer() {
        return nummer;
    }

    public String getName() {
        return name;
    }

    public boolean isVerfuegbar() {
        return verfuegbar;
    }
}
