package src;

public class Eshop {
    private Benutzerverwaltung NutzerVw;

    public Eshop(){
        NutzerVw = new Benutzerverwaltung();
        ArtikelVw = new Artikelverwaltung();
    }

    public void KundeHinzufügen(String name, String username, String password, int nr, String email, String adress){
        Benutzer b = new Kunde( name,  username,  password,  nr,  email,  adress); 
        NutzerVw.registrieren(b);
    }
    public void MitarbeiterHinzufügen(String username){

    }
    public void NutzerEntfernen(String username){

    }
    

    
}
