package es.progcipfpbatoi.modelo.dao;

import es.progcipfpbatoi.modelo.dto.Categories;
import es.progcipfpbatoi.modelo.dto.Levels;
import es.progcipfpbatoi.modelo.dto.Question;
import es.progcipfpbatoi.services.MySqlConnection;
import es.progcipfpbatoi.util.exceptions.DatabaseErrorException;
import es.progcipfpbatoi.util.exceptions.NotFoundException;

import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;

public class SQLQuestionDAO implements QuestionDAO{

    private Connection connection;

    private static final String TABLE_NAME = "questions";


    @Override
    public Question save(Question question) throws DatabaseErrorException {
        if (findByCod(question.getId())==null){
            return insert(question);
        } else {
           // update(question);
            return null;
        }
    }

    private Question insert(Question question) throws DatabaseErrorException {
        String sql = "INSERT INTO "+ TABLE_NAME + "(id,description,id_category,id_levels,status) VALUES (?,?,?,?,?);";
        connection =  new MySqlConnection("192.168.56.101", "BatoiBrain", "batoi", "1234").getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, question.getId());
            preparedStatement.setString(2, question.getDescription());
            preparedStatement.setInt(3, question.getCategory().getId());
            preparedStatement.setInt(4, question.getLevel().getId());
            preparedStatement.setInt(5, question.getStatus());

            preparedStatement.executeUpdate();

            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                int autoIncremental = rs.getInt(1);
                question.setId(autoIncremental);
                return question;
            } else {
                throw new DatabaseErrorException("Ha ocurrido un error en el acceso o conexión a la base de datos (insert)");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseErrorException("Ha ocurrido un error en el acceso o conexión a la base de datos (insert)");
        }
    }

    @Override
    public Question findByCod(int id) throws DatabaseErrorException {
        try {
            return getByCod(id);
        } catch (DatabaseErrorException ex) {
            return null;
        }
    }

    @Override
    public Question getByCod(int id) throws DatabaseErrorException {
        String sql = String.format("SELECT * FROM %s WHERE id = ?",TABLE_NAME);
        connection =  new MySqlConnection("192.168.56.101", "BatoiBrain", "batoi", "1234").getConnection();

        try (
                PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        ) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()) {
                Question question = getTaskFromResultset(resultSet);
                if (Objects.equals(question.getId(), String.valueOf(id))) {
                    return question;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseErrorException("Ha ocurrido un error en el acceso o conexión a la base de datos (select)");
        }
        return null;
    }
    @Override
    public ArrayList<Question> getByCategory(int category) throws DatabaseErrorException {
        String sql = String.format("SELECT * FROM %s WHERE id_category = ?", TABLE_NAME);
        connection = new MySqlConnection("192.168.56.101", "BatoiBrain", "batoi", "1234").getConnection();
        ArrayList<Question> arrayQuestionFiltered = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, category);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Question question = getTaskFromResultset(resultSet);
                arrayQuestionFiltered.add(question);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseErrorException("Ha ocurrido un error en el acceso o conexión a la base de datos (select)");
        }

        return arrayQuestionFiltered;
    }

    private Question getTaskFromResultset(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String description = rs.getString("description");
        int cate = rs.getInt("id_category");
        Categories categories = new Categories(cate);
        String nivel = rs.getString("id_levels");
        Levels levels = new Levels(Integer.parseInt(nivel));
        int status = rs.getInt("status");

        return new Question(id,description,categories,levels,status) {
        };
    }
}
