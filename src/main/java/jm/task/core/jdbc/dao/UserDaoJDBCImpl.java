package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Connection connection = Util.getInstance().getConnection();

//    public UserDaoJDBCImpl(com.mysql.cj.util.Util util) {
//
//    }
    /**
     * Создание таблицы
     */
    @Override
    public void createUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS users" +
                    "(id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(255), lastname VARCHAR(255), age INT)");
            connection.commit();
            System.out.println("Создана таблица \"users\"");
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Произошла ошибка при создании таблицы..");
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
    /**
     * Удаление таблицы
     */
    @Override
    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP TABLE IF EXISTS users");
            System.out.println("Удалена таблица \"users\"");
        } catch (SQLException g) {
            g.printStackTrace();
            System.err.println("Произошла ошибка при удалении таблицы..");
        }
    }
    /**
     * Сохранение нового юзера
     */
    @Override
    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO users (name, lastname, age) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, age);
            System.out.println("Добавлен пользователь...Имя: " + name + ", Фамилия: " + lastName
                    + ", возраст: " + age);
            preparedStatement.execute();
        } catch (SQLException e) {
            System.err.println("Ошибка добавления нового пользователя");


        }
    }
    /**
     * Удаление человека по ИД
     */
    @Override
    public void removeUserById(long id) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM users WHERE id =" + id);
            System.out.println("Пользователь удален!");
        } catch (SQLException e) {
            System.err.println("Произошла ошибка удаления пользователя");
            throw new RuntimeException(e);
        }
    }
    /**
     * Показать всех юзеров
     */
    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            System.out.println(" ");
            ResultSet resultSet = statement.executeQuery("SELECT name, lastname, age FROM users");
            while (resultSet.next()) {
                userList.add(new User(resultSet.getString("name"),
                        resultSet.getString("lastname"),
                        resultSet.getByte("age")));
            }
        } catch (SQLException e) {
            System.err.println("Произошла ошибка...");
            throw new RuntimeException(e);
        }
        return null;
    }
    /**
     * удаление данных
     */
    @Override
    public void cleanUsersTable() {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("TRUNCATE TABLE users ");
            System.out.println("Удаление данных прошло успешно!");
        } catch (SQLException e) {
            System.err.println("Ошибка удаления данных из таблицы");
            throw new RuntimeException(e);
        }
    }
}
