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

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public boolean setPassword(String oldPassword, String newPassword) {
        if(oldPassword.compareTo(this.password) == 0) {
            this.password = newPassword;
            return true;
        } else {
            return false;
        }
    }
}
