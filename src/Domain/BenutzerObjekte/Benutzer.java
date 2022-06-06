package Domain.BenutzerObjekte;

import java.io.Serializable;

public abstract class Benutzer implements Serializable{

  // Klassenvariablen
  private String username;
  private byte[] password;
  private String name;

  protected Benutzer(String name, String username, byte[] password) {
    this.name = name;
    this.username = username;
    this.password = password;
  }

  protected String getUsername() {
    return username;
  }

  protected byte[] getPassword() {
    return password;
  }

  protected String getName() {
    return name;
  }

  protected void setName(String name) {
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
