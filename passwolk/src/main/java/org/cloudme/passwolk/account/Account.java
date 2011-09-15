package org.cloudme.passwolk.account;

import org.cloudme.passwolk.json.JsonSerializable;
import org.cloudme.sugar.Entity;

/**
 * {@link Entity} that represents one account and contains additional
 * information.
 * 
 * @author Moritz Petersen
 */
@SuppressWarnings("serial")
public class Account extends Entity implements JsonSerializable {
	private static final String[] SERIALIZABLE_PROPS = { "description", "email", "id", "login", "password", "title" };
	private String title;
    private String login;
    private String password;
    private String email;
    private String description;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + ((login == null) ? 0 : login.hashCode());
        result = prime * result + ((password == null) ? 0 : password.hashCode());
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Account)) {
            return false;
        }
        Account other = (Account) obj;
        if (description == null) {
            if (other.description != null) {
                return false;
            }
        }
        else if (!description.equals(other.description)) {
            return false;
        }
        if (email == null) {
            if (other.email != null) {
                return false;
            }
        }
        else if (!email.equals(other.email)) {
            return false;
        }
        if (login == null) {
            if (other.login != null) {
                return false;
            }
        }
        else if (!login.equals(other.login)) {
            return false;
        }
        if (password == null) {
            if (other.password != null) {
                return false;
            }
        }
        else if (!password.equals(other.password)) {
            return false;
        }
        if (title == null) {
            if (other.title != null) {
                return false;
            }
        }
        else if (!title.equals(other.title)) {
            return false;
        }
        return true;
    }

	@Override
	public String[] serializableProperties() {
		return SERIALIZABLE_PROPS;
	}
}
