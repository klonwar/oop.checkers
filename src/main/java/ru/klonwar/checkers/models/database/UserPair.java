package ru.klonwar.checkers.models.database;

import ru.klonwar.checkers.helpers.Pair;

public class UserPair extends Pair<UserItem, UserItem> {
    public static final UserPair NULL_PAIR = new UserPair(null, null);

    public UserPair(UserItem first, UserItem second) {
        super(first, second);
    }
}
