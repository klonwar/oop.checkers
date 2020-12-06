package ru.klonwar.checkers.models.database;

import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;
import ru.klonwar.checkers.models.game.Game;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class H2Database implements CheckersDatabase {
    public static final String USER_TABLE_CREATE_SCRIPT = "create table USER\n" +
            "(\n" +
            "\tID INT auto_increment\n" +
            "\t\tprimary key,\n" +
            "\tLOGIN VARCHAR not null\n" +
            "\t\tunique,\n" +
            "\tPASSWORD VARCHAR not null\n" +
            ");\n" +
            "\n";
    public static final String GAME_TABLE_CREATE_SCRIPT = "create table GAME\n" +
            "(\n" +
            "\tID INT auto_increment\n" +
            "\t\tprimary key,\n" +
            "\tWHITE_USER_ID INT not null\n" +
            "\t\treferences USER (ID)\n" +
            "\t\t\ton update cascade on delete cascade,\n" +
            "\tBLACK_USER_ID INT not null\n" +
            "\t\treferences USER (ID)\n" +
            "\t\t\ton update cascade on delete cascade,\n" +
            "\tWINNER INT not null,\n" +
            "\tFINISH_TIME TIMESTAMP\n" +
            ");\n" +
            "\n";
    private final String sqlLink;

    public H2Database() {
        try {
            Properties p = new Properties();
            p.load(new FileInputStream("./app.properties"));
            sqlLink = p.get("sql-link").toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public H2Database(String sqlLink) {
        this.sqlLink = sqlLink;
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(sqlLink, "", "");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public QueryResponse addUser(User user) {
        try (Connection connection = getConnection()) {
            PreparedStatement st = connection.prepareStatement(
                    "insert into " +
                            "USER (" +
                            "login, " +
                            "password" +
                            ") " +
                            "values (?, ?)"
            );
            st.setString(1, user.getLogin());
            st.setString(2, MD5.getMD5(user.getPassword()));
            st.executeUpdate();

            return new QueryResponse(true, "Пользователь успешно зарегистрирован");
        } catch (JdbcSQLIntegrityConstraintViolationException e) {
            return new QueryResponse(false, "Такой пользователь уже зарегистрирован");
        } catch (SQLException e) {
            return new QueryResponse(false, e.getMessage());
        }
    }

    @Override
    public QueryResponse addGame(Game game) {
        if (!game.haveWinner()) {
            return new QueryResponse(false, "Игра еще не завершена");
        }

        try (Connection connection = getConnection()) {
            PreparedStatement st = connection.prepareStatement(
                    "insert into " +
                            "GAME (" +
                            "white_user_id, " +
                            "black_user_id, " +
                            "WINNER, " +
                            "FINISH_TIME" +
                            ") " +
                            "values (?, ?, ?, ?)"
            );
            st.setInt(1, game.getWhitePlayer().getId());
            st.setInt(2, game.getBlackPlayer().getId());
            st.setInt(3, game.getActivePlayerIndex());
            st.setTimestamp(4, new Timestamp(game.getFinishTime()));
            st.executeUpdate();
            return new QueryResponse(true, "Игра успешно создана");
        } catch (SQLException e) {
            return new QueryResponse(false, e.getMessage());
        }
    }

    @Override
    public List<GameInfo> getGamesInfoForUserID(int userId) {
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
            List<GameInfo> rl = new ArrayList<>();
            while (rs.next()) {
                GameInfo gi = new GameInfo(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getInt(3),
                        rs.getInt(4),
                        rs.getTimestamp(5).getTime()
                );
                rl.add(gi);
            }
            return rl;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User getUserByLoginAndPassword(String login, String password) {
        try (Connection connection = getConnection()) {
            PreparedStatement st = connection.prepareStatement(
                    "select * " +
                            "from USER " +
                            "where LOGIN = ? " +
                            "and PASSWORD = ?"
            );
            st.setString(1, login);
            st.setString(2, MD5.getMD5(password));

            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return new User(
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
