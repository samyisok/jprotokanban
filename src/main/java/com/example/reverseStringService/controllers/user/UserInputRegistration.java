package com.example.reverseStringService.controllers.user;

public class UserInputRegistration {
  private String login;
  private String password1;
  private String password2;

  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login.toLowerCase();
  }

  public String getPassword1() {
    return password1;
  }

  public void setPassword1(String password1) {
    this.password1 = password1;
  }

  public String getPassword2() {
    return password2;
  }

  public void setPassword2(String password2) {
    this.password2 = password2;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((login == null) ? 0 : login.hashCode());
    result = prime * result + ((password1 == null) ? 0 : password1.hashCode());
    result = prime * result + ((password2 == null) ? 0 : password2.hashCode());
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
    if (getClass() != obj.getClass()) {
      return false;
    }
    UserInputRegistration other = (UserInputRegistration) obj;
    if (login == null) {
      if (other.login != null) {
        return false;
      }
    } else if (!login.equals(other.login)) {
      return false;
    }
    if (password1 == null) {
      if (other.password1 != null) {
        return false;
      }
    } else if (!password1.equals(other.password1)) {
      return false;
    }
    if (password2 == null) {
      if (other.password2 != null) {
        return false;
      }
    } else if (!password2.equals(other.password2)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "UserInputRegistration [login=" + login + ", password1=" + password1
        + ", password2=" + password2 + "]";
  }


  public boolean isValid() {
    if (this.login.length() > 3 && this.password1.length() > 3
        && this.password1.equals(this.password2)) {
      return true;
    }

    return false;
  }

}
