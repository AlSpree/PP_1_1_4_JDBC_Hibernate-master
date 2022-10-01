package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private Connection conn = Util.getConnection(); /*Connection вынеси один на класс -- Надеюсь, я правильно понял это замечание. Вопрос: а разве мы не должны в итоге закрывать connection?*/

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String sqlCommand = "CREATE TABLE IF NOT EXISTS users (\n" +
                "id serial PRIMARY KEY,\n" +
                "name varchar(50),\n" +
                "lastName varchar(50),\n" +
                "age tinyint)";
        try (Statement statement = conn.createStatement()) {
            statement.executeUpdate(sqlCommand);
            System.out.println("Таблица создана");

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void dropUsersTable() {
        String sqlCommand = "DROP TABLE IF EXISTS users";
        try (Statement statement = conn.createStatement()) {
            statement.executeUpdate(sqlCommand);
            System.out.println("Таблица удалена");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sqlCommand = "INSERT INTO users (name, lastName, age) VALUES ( ?, ?, ?)";

        try (PreparedStatement preparedStatement = conn.prepareStatement(sqlCommand)){
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);

            preparedStatement.executeUpdate();
            System.out.println("User добавлен");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String sqlCommand = "DELETE FROM users WHERE id = ?";

        try (PreparedStatement preparedStatement = conn.prepareStatement(sqlCommand)) {
            preparedStatement.setLong(1,id);

            preparedStatement.executeUpdate();
            System.out.printf("User(id%d) удален\n", id);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> usersList = new ArrayList<>();
        String sqlCommand = "SELECT id, name, lastName, age FROM users";

        try (Statement statement = conn.createStatement(); ResultSet resultSet = statement.executeQuery(sqlCommand)) {

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

        for(int i=0;i<usersList.size();i++){
                System.out.println(usersList.get(i));
            }

        return usersList;
    }

    public void cleanUsersTable() {
        String sqlCommand = "TRUNCATE users";

        try (Statement statement = conn.createStatement()) {
            statement.executeUpdate(sqlCommand);
            System.out.println("Данные таблицы удалены");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
