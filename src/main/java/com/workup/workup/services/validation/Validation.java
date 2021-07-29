package com.workup.workup.services.validation;

import com.workup.workup.models.User;

import java.util.List;

public class Validation {

  public boolean emailHasError(String email) {
    return email.isEmpty() || (!email.contains("@"));
  }

  public boolean emailExists(String email, List<User> users) {
    for (User user : users) {
      if (email.equals(user.getEmail())) {
        return true;
      }
    }
    return false;
  }

  public boolean passwordHasError(String password) {

    return password.isEmpty()
        || (password.length() < 8)
        || (!(password.contains("@")
            || password.contains("#")
            || password.contains("!")
            || password.contains("~")
            || password.contains("$")
            || password.contains("%")
            || password.contains("^")
            || password.contains("&")
            || password.contains("*")
            || password.contains("(")
            || password.contains(")")
            || password.contains("-")
            || password.contains("+")
            || password.contains("/")
            || password.contains(":")
            || password.contains(".")
            || password.contains(", ")
            || password.contains("<")
            || password.contains(">")
            || password.contains("?")
            || password.contains("|")));
  }
}
