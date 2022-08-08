package UserInterface;

import common.EshopInterface;

public class UserSession {
  // a unique hash value to identify the user when logged in
  public byte[] userHash;
  protected EshopInterface eshop;

  public UserSession(EshopInterface eshop) {
    this.eshop = eshop;
  }
}
