package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        UserService userService = new UserServiceImpl();

        userService.createUsersTable();

        userService.saveUser("vasy", "Vasin", (byte) 35);
        userService.saveUser("Petya", "Petin", (byte) 30);
        userService.saveUser("Kolya", "Kolin", (byte) 32);
        userService.saveUser("Dima", "Dmitrov", (byte) 30);

        userService.removeUserById(3);

        userService.getAllUsers();

        userService.cleanUsersTable();

        userService.dropUsersTable();
    }
}
