package ru.klonwar.checkers.models.database;

import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper {
    public DatabaseHelper() {
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:h2:C:/Users/klonw/IdeaProjects/oop.checkers;AUTO_SERVER=true", "", "");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private String getMD5(String from) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(from.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            StringBuilder hashtext = new StringBuilder(no.toString(16));
            while (hashtext.length() < 32) {
                hashtext.insert(0, "0");
            }
            return hashtext.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public int addUser(String login, String password) {
        try (Connection connection = getConnection()) {
            PreparedStatement st = connection.prepareStatement(
                    "insert into " +
                            "USER (" +
                            "login, " +
                            "password" +
                            ") " +
                            "values (?, ?)"
            );
            st.setString(1, login);
            st.setString(2, getMD5(password));
            st.executeUpdate();
            return 0;
        } catch (Exception e) {
            if (e instanceof JdbcSQLIntegrityConstraintViolationException) {
                return 1;
            } else {
                throw new RuntimeException(e);
            }
        }
    }

    public void addGame(int whiteUserId, int blackUserId, int activePlayer, String fieldState, boolean isActive) {
        try (Connection connection = getConnection()) {
            PreparedStatement st = connection.prepareStatement(
                    "insert into " +
                            "GAME (" +
                            "white_user_id, " +
                            "black_user_id, " +
                            "active_player, " +
                            "field_state, " +
                            "is_active" +
                            ") " +
                            "values (?, ?, ?, ?, ?)"
            );
            st.setInt(1, whiteUserId);
            st.setInt(2, blackUserId);
            st.setInt(3, activePlayer);
            st.setString(4, fieldState);
            st.setBoolean(5, isActive);
            st.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<GameItem> getGamesForUserId(int userId) {
        try (Connection connection = getConnection()) {
            PreparedStatement st = connection.prepareStatement(
                    "select * " +
                            "from GAME " +
                            "where WHITE_USER_ID = ? " +
                            "or BLACK_USER_ID = ?"
            );
            st.setInt(1, userId);
            st.setInt(2, userId);
            ResultSet rs = st.executeQuery();
            List<GameItem> rl = new ArrayList<>();
            while (rs.next()) {
                GameItem gi = new GameItem(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getInt(3),
                        rs.getInt(4),
                        rs.getString(5),
                        rs.getBoolean(6)
                );
                rl.add(gi);
            }
            return rl;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<GameItem> getGamesForTwoUsers(int userId1, int userId2) {
        try (Connection connection = getConnection()) {
            PreparedStatement st = connection.prepareStatement(
                    "select * " +
                            "from GAME " +
                            "where (WHITE_USER_ID = ? " +
                            "and BLACK_USER_ID = ?) or " +
                            "(WHITE_USER_ID = ? " +
                            "and BLACK_USER_ID = ?) "
            );
            st.setInt(1, userId1);
            st.setInt(2, userId2);
            st.setInt(1, userId2);
            st.setInt(2, userId1);
            ResultSet rs = st.executeQuery();
            List<GameItem> rl = new ArrayList<>();
            while (rs.next()) {
                GameItem gi = new GameItem(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getInt(3),
                        rs.getInt(4),
                        rs.getString(5),
                        rs.getBoolean(6)
                );
                rl.add(gi);
            }
            return rl;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public UserItem getUserByLoginAndPassword(String login, String password) {
        try (Connection connection = getConnection()) {
            PreparedStatement st = connection.prepareStatement(
                    "select * " +
                            "from USER " +
                            "where LOGIN = ? " +
                            "and PASSWORD = ?"
            );
            st.setString(1, login);
            st.setString(2, getMD5(password));

            ResultSet rs = st.executeQuery();
            List<GameItem> rl = new ArrayList<>();
            if (rs.next()) {
                return new UserItem(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3)
                );
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
