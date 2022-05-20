package src;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

public class Benutzerverwaltung {

	// Verwaltung der Nutzer in einer verketteten Liste
	private List<Benutzer> benutzerRegister;

	public Benutzerverwaltung(){
		benutzerRegister = new Vector<Benutzer>();
	}

	public void registrieren(Benutzer einNutzer) throws NutzerExistiertBereitsException {
		if (benutzerRegister.contains(einNutzer)) {
			throw new NutzerExistiertBereitsException(einNutzer, " - in 'einfuegen()'");
		}
        // übernimmt Vector:
		benutzerRegister.add(einNutzer);
	}

	public void loeschen(String username) {
		Benutzer b = this.sucheNutzer(username);
		// übernimmt Vector:
		benutzerRegister.remove(b);
	}

	public Benutzer sucheNutzer(String username) {
		for (Benutzer benutzer: benutzerRegister) {
			if (benutzer.getUsername().equals(username)) {
			return benutzer;
            }   
        return 0;
		}
	}

	public List getBuchBestand() {
		return new Vector(buchBestand);
	}

	// TODO: Weitere Methoden, z.B. zum Auslesen und Entfernen von Büchern
	// ...
}
