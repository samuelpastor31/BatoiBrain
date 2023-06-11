package es.progcipfpbatoi.modelo.dao;

import es.progcipfpbatoi.modelo.dto.Levels;
import es.progcipfpbatoi.services.MySqlConnection;
import es.progcipfpbatoi.util.exceptions.DatabaseErrorException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLLevelDAO implements LevelDAO{

    private Connection connection;

    private static final String TABLE_NAME = "levels";

    @Override
    public Levels findByDescription(String descripcion) throws DatabaseErrorException {
        String sql = String.format("SELECT * FROM %s WHERE description = ?",TABLE_NAME);
        connection =  new MySqlConnection("192.168.56.101", "BatoiBrain", "batoi", "1234").getConnection();

        try (PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, descripcion);
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()) {
                Levels levels = getLevelFromResultset(resultSet);
                if (levels.getDescription().equals(descripcion)) {
                    return levels;
                }
            }

            return null;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseErrorException("Ha ocurrido un error en el acceso o conexión a la base de datos (select)");
        }
    }

    @Override
    public Levels findById(int id) throws DatabaseErrorException {
        String sql = String.format("SELECT * FROM %s WHERE id = ?",TABLE_NAME);
        connection =  new MySqlConnection("192.168.56.101", "BatoiBrain", "batoi", "1234").getConnection();

        try (PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()) {
                Levels levels = getLevelFromResultset(resultSet);
                if (levels.getId() == id) {
                    return levels;
                }
            }

            return null;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseErrorException("Ha ocurrido un error en el acceso o conexión a la base de datos (select)");
        }
    }

    private Levels getLevelFromResultset(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String descripcion = rs.getString("description");
        return new Levels(id, descripcion);
    }
}
