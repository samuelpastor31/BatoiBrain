package es.progcipfpbatoi.modelo.dao;

import es.progcipfpbatoi.modelo.dto.Levels;
import es.progcipfpbatoi.modelo.dto.Users;
import es.progcipfpbatoi.services.MySqlConnection;
import es.progcipfpbatoi.util.exceptions.DatabaseErrorException;

import java.sql.*;

public class SQLUsersDAO implements UserDAO {

    private Connection connection;

    private static final String TABLE_NAME = "users";
    @Override
    public void save(Users users) throws DatabaseErrorException {
        if (findByEmail(users.getEmail())==null){
            insert(users);
        } else {
            // update(question);
        }
    }

    @Override
    public void insert(Users users) throws DatabaseErrorException {
        String sql = "INSERT INTO "+ TABLE_NAME + "(username,email,level) VALUES (?,?,?);";
        connection =  new MySqlConnection("192.168.56.101", "BatoiBrain", "batoi", "1234").getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, users.getUsername());
            preparedStatement.setString(2, users.getEmail());
            preparedStatement.setInt(3, users.getLevel().getId());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseErrorException("Ha ocurrido un error en el acceso o conexión a la base de datos (insert)");
        }
    }

    @Override
    public Users findByEmail(String email) throws DatabaseErrorException {
        try {
            return getByEmail(email);
        }catch (DatabaseErrorException ex) {
            return null;
        }
    }

    @Override
    public Users getByEmail(String email) throws DatabaseErrorException {
        String sql = String.format("SELECT * FROM %s WHERE email = ?",TABLE_NAME);
        connection =  new MySqlConnection("192.168.56.101", "BatoiBrain", "batoi", "1234").getConnection();

        try (PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()) {
                Users users = getUserFromResultset(resultSet);
                if (users.getEmail().equals(email)) {
                    return users;
                }
            }

            return null;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseErrorException("Ha ocurrido un error en el acceso o conexión a la base de datos (select)");
        }
    }

    @Override
    public Users getByUserame(String username) throws DatabaseErrorException {
        String sql = String.format("SELECT * FROM %s WHERE username = ?",TABLE_NAME);
        connection =  new MySqlConnection("192.168.56.101", "BatoiBrain", "batoi", "1234").getConnection();

        try (PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()) {
                Users users = getUserFromResultset(resultSet);
                if (users.getUsername().equals(username)) {
                    return users;
                }
            }

            return null;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseErrorException("Ha ocurrido un error en el acceso o conexión a la base de datos (select)");
        }
    }

    @Override
    public void deleteAll() throws DatabaseErrorException {
        String sql = "DELETE FROM " + TABLE_NAME;
        connection = new MySqlConnection("192.168.56.101", "BatoiBrain", "batoi", "1234").getConnection();

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseErrorException("Ha ocurrido un error en el acceso o conexión a la base de datos (delete)");
        }
    }

    private Users getUserFromResultset(ResultSet rs) throws SQLException {
        String username = rs.getString("username");
        String email = rs.getString("email");
        int nivel = rs.getInt("level");
        Levels levels = new Levels(nivel);

        return new Users(username,email,levels);
    }
}
