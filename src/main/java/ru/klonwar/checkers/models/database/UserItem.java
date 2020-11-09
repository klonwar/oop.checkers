package ru.klonwar.checkers.models.database;

import java.util.Objects;

public class UserItem {
    private final int id;
    private final String login;
    private final String password;

    public UserItem(int id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserItem userItem = (UserItem) o;
        return id == userItem.id &&
                login.equals(userItem.login) &&
                password.equals(userItem.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password);
    }
}
