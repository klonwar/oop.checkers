package ru.klonwar.checkers.models.database;

import ru.klonwar.checkers.helpers.Pair;

public class UserPair extends Pair<User, User> {
    public static final UserPair NULL_PAIR = new UserPair(null, null);

    public UserPair(User first, User second) {
        super(first, second);
    }
}
