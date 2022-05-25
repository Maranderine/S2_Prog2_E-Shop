package BenutzerObjekte;

public abstract class Benutzer {

  // Klassenvariablen
  private String username;
  private byte[] password;
  private String name;

  public Benutzer(String name, String username, byte[] password) {
    this.name = name;
    this.username = username;
    this.password = password;
  }

  String getUsername() {
    return username;
  }

  byte[] getPassword() {
    return password;
  }

  String getName() {
    return name;
  }

  void setName(String name) {
    this.name = name;
  }

  // public boolean setPassword(String oldPassword, String newPassword) {
  //   if (oldPassword.compareTo(this.password) == 0) {
  //     this.password = newPassword;
  //     return true;
  //   } else {
  //     return false;
  //   }
  // }

  @Override
  public String toString() {
    return "\t" + this.name + "\t" + this.username;
  }
}
