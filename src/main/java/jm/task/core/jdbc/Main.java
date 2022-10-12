package jm.task.core.jdbc;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.util.ArrayList;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь

        UserService userOne = new UserServiceImpl();

        userOne.createUsersTable();
        userOne.saveUser("User1", "", (byte) 39);
        userOne.saveUser("User2", "User2_LastName", (byte) 20);
        userOne.saveUser("User3", "User3_LastName", (byte) 28);
        userOne.saveUser("User4", "User4_LastName", (byte) 35);


        userOne.removeUserById(4);
        userOne.getAllUsers();
        userOne.cleanUsersTable();
        userOne.dropUsersTable();

    }
}
