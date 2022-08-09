# S2_Prog2_E-Shop

## Users

Erstellte Test User sind:

| Users             | Username | password |
| ----------------- | -------- | -------- |
| Mitarbeiter-Admin | a        | a        |
| Kunde             | t        | t        |

## Online <-> Lokal

Zwischen Online und Lokal kann leicht gewechselt werden.

Das einzige was bestimmt ob der Eshop online ist, ist welchen Eshop die MAIN Klasse importiert.

Wenn der `Domain.Eshop` importiert wird, kommuniziert der Client direkt mit dem Eshop Objekt und die Eshop Klasse muss vorher nicht als Server gestartet werden.

Bei import der `Client.Eshop` Klasse wird diese als ein Pseudo-Eshop behandelt und sendet alle befehle weiter an der Server Eshop.

Hierzu sind beide imports schon in der MAIN vorhanden und müssen nur aus/-kommentiert werden.

```
import Domain.Eshop;
// import Client.Eshop;
```

## Online

Die MAIN Klasse muss den `Client.Eshop` importieren.

```
import Client.Eshop;
```

Dann muss erst der Server und dann der Client gestartet werden.

- Main importiert den Client.Eshop
- src.Domain.Eshop.main ausführen.
- src.Main.main ausführen.

## Local

Die MAIN Klasse muss den `Domain.Eshop` importieren.

```
import Domain.Eshop;
```

- Main importiert den Domain.Eshop
- src.Main.main ausführen.

## Userinterface

Das userinterfaace wird von der `src.Domain.Eshop.usedUI` variable bestimmt.
Diese steht ganz oben im Domain.Eshop.
Ändere den String zu `"CUI"`/`"GUI"`;

```
private final String usedUI = "GUI";
```

Funktioniert Lokal und mit Server.
