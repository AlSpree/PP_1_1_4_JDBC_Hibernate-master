package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Connection conn = Util.getConnection(); Statement statement = conn.createStatement()) {
            String sqlCommand = "CREATE TABLE IF NOT EXISTS USERS (\n" +
                    "id serial PRIMARY KEY,\n" +
                    "name varchar(50),\n" +
                    "lastName varchar(50),\n" +
                    "age tinyint)";
            statement.executeUpdate(sqlCommand);
//            System.out.println("Таблица создана");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Connection conn = Util.getConnection(); Statement statement = conn.createStatement()) {
            String sqlCommand = "DROP TABLE IF EXISTS USERS";
            statement.executeUpdate(sqlCommand);
//            System.out.println("Таблица удалена");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection conn = Util.getConnection(); Statement statement = conn.createStatement()) {
            String sqlCommand = String.format("INSERT INTO USERS (name, lastName, age) values ('%s','%s', %d)", name, lastName, age);
            statement.executeUpdate(sqlCommand);
//            System.out.println("User добавлен");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (Connection conn = Util.getConnection(); Statement statement = conn.createStatement()) {
            String sqlCommand = String.format("DELETE FROM USERS WHERE ID = %d", id);
            statement.executeUpdate(sqlCommand);
//            System.out.printf("User(id%d) удален\n", id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> usersList = new ArrayList<>();
        try (Connection conn = Util.getConnection(); Statement statement = conn.createStatement()) {
            String sqlCommand = "SELECT id, name, lastName, age FROM USERS";
            ResultSet resultSet = statement.executeQuery(sqlCommand);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong(1));
                user.setName(resultSet.getString(2));
                user.setLastName(resultSet.getString(3));
                user.setAge(resultSet.getByte(4));
                usersList.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
//            for(int i=0;i<usersList.size();i++){
//                System.out.println(usersList.get(i));
//            }
        return usersList;
    }

    public void cleanUsersTable() {
        try (Connection conn = Util.getConnection(); Statement statement = conn.createStatement()) {
            String sqlCommand = "TRUNCATE USERS";
            statement.executeUpdate(sqlCommand);
//            System.out.println("Данные таблицы удалены");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
