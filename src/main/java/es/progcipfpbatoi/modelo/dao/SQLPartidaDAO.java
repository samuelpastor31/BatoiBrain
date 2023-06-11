package es.progcipfpbatoi.modelo.dao;

import es.progcipfpbatoi.modelo.dto.*;
import es.progcipfpbatoi.services.MySqlConnection;
import es.progcipfpbatoi.util.exceptions.DatabaseErrorException;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class SQLPartidaDAO implements PartidaDAO {
    private static final String TABLE_NAME = "partida";
    private Connection connection;

    @Override
    public ArrayList<Partida> findAll() throws DatabaseErrorException {
            String sql = String.format("SELECT * FROM %s", TABLE_NAME);
            connection = new MySqlConnection("192.168.56.101", "BatoiBrain", "batoi", "1234").getConnection();
            ArrayList<Partida> partidas = new ArrayList<>();

            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {

                while (resultSet.next()) {
                    Partida partida = getPartidaFromResultSet(resultSet);
                    partidas.add(partida);
                }

            } catch (SQLException e) {
                e.printStackTrace();
                throw new DatabaseErrorException("Ha ocurrido un error en la conexión o acceso a la base de datos (select)");
            }

            return partidas;
    }

    @Override
    public ArrayList<Partida> findAllPoints() throws DatabaseErrorException {
        String sql = String.format("SELECT * FROM %s ORDER BY points DESC LIMIT 15", TABLE_NAME);
        connection = new MySqlConnection("192.168.56.101", "BatoiBrain", "batoi", "1234").getConnection();
        ArrayList<Partida> partidas = new ArrayList<>();

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                Partida partida = getPartidaFromResultSet(resultSet);
                partidas.add(partida);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseErrorException("Ha ocurrido un error en la conexión o acceso a la base de datos (select)");
        }

        return partidas;
    }

    @Override
    public ArrayList<Partida> findAllPointsDate() throws DatabaseErrorException {
        String currentDate = LocalDate.now().toString();  // Obtener la fecha actual en formato ISO (YYYY-MM-DD)
        String sql = String.format("SELECT * FROM %s WHERE DATE(partidadate) = '%s' ORDER BY points DESC LIMIT 10", TABLE_NAME, currentDate);
        connection = new MySqlConnection("192.168.56.101", "BatoiBrain", "batoi", "1234").getConnection();
        ArrayList<Partida> partidas = new ArrayList<>();

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                Partida partida = getPartidaFromResultSet(resultSet);
                partidas.add(partida);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseErrorException("Ha ocurrido un error en la conexión o acceso a la base de datos (select)");
        }

        return partidas;
    }

    @Override
    public Partida save(Partida partida) throws DatabaseErrorException {
        return insert(connection, partida);
    }

    private Partida insert(Connection connection, Partida partida) throws DatabaseErrorException {
        String sql = "INSERT INTO " + TABLE_NAME + "(username, points, id_level, partidadate) VALUES (?, ?, ?, ?);";
        connection = new MySqlConnection("192.168.56.101", "BatoiBrain", "batoi", "1234").getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, partida.getUsers().getUsername());
            preparedStatement.setInt(2, partida.getPoints());
            preparedStatement.setInt(3, partida.getLevels().getId());
            java.util.Date utilDate = new java.util.Date();
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            preparedStatement.setDate(4, sqlDate);

            preparedStatement.executeUpdate();

            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                int autoIncremental = rs.getInt(1);
                partida.setId(autoIncremental);
                return partida;
            } else {
                throw new DatabaseErrorException("Ha ocurrido un error en el acceso o conexión a la base de datos (insert)");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseErrorException("Ha ocurrido un error en el acceso o conexión a la base de datos (insert)");
        }
    }

    @Override
    public ArrayList<Partida> getByUsuario(String nombreUsuario) throws DatabaseErrorException {
        connection = new MySqlConnection("192.168.56.101", "BatoiBrain", "batoi", "1234").getConnection();
            String sql = String.format("SELECT * FROM %s WHERE username = ?", TABLE_NAME);

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, nombreUsuario);
                ResultSet resultSet = statement.executeQuery();
                ArrayList<Partida> partidasFiltradas = new ArrayList<>();
                while (resultSet.next()) {
                    Partida partida = getPartidaFromResultSet(resultSet);
                    partidasFiltradas.add(partida);
                }
                return partidasFiltradas;
            } catch (SQLException e) {
                e.printStackTrace();
                throw new DatabaseErrorException("Ha ocurrido un error en el acceso o conexión a la base de datos (select)");
            }
    }

    @Override
    public ArrayList<Partida> getByFecha(Date date) throws DatabaseErrorException {
        connection = new MySqlConnection("192.168.56.101", "BatoiBrain", "batoi", "1234").getConnection();
        String sql = String.format("SELECT * FROM %s WHERE partidadate = ?", TABLE_NAME);

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDate(1, date);
            ResultSet resultSet = statement.executeQuery();
            ArrayList<Partida> partidasFiltradas = new ArrayList<>();
            while (resultSet.next()) {
                Partida partida = getPartidaFromResultSet(resultSet);
                partidasFiltradas.add(partida);
            }
            return partidasFiltradas;
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

    private Partida getPartidaFromResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String username = rs.getString("username");
        Users users = new Users(username);
        int points = rs.getInt("points");
        int category = rs.getInt("id_level");
        Levels levels = new Levels(category);
        Date partidaDate = rs.getDate("partidadate");

        return new Partida(id, users, points, levels, partidaDate);
    }
}