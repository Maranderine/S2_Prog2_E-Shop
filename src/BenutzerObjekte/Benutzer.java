package BenutzerObjekte;

public abstract class Benutzer {

	// Klassenvariablen
	private String username;
	private String password;
	private String name;

	public Benutzer(String name, String username, String password) {
		this.name = name;
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean setPassword(String oldPassword, String newPassword) {
		if (oldPassword.compareTo(this.password) == 0) {
			this.password = newPassword;
			return true;
		} else {
			return false;
		}
	}
}
