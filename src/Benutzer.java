import java.util.Date;

public class Benutzer {

    //Klassenvariablen
    private String username;
    private String password;
    private String firstname;
    private String lastname;

    public Benutzer(String username, String password, String firstname, String lastname, Date registerDate, Date lastLoginDate) {
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    /**
     * @return Gibt den Benutzernamen aus.
     */
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    /**
     * @return Gibt den Vornamen aus.
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * @return Gibt den Nachnamen aus.
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * @return Erstellt den Vornamen.
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     * @return Erstellt den Nachnamen.
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /**
     * @return Erstellt Passwort, wenn es nicht dem alten entspricht.
     */
    public boolean setPassword(String oldPassword, String newPassword) {
        if(oldPassword.compareTo(this.password) == 0) {
            this.password = newPassword;
            return true;
        } else {
            return false;
        }
    }
}
