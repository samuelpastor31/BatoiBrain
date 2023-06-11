package es.progcipfpbatoi.modelo.dao;

import es.progcipfpbatoi.modelo.dto.Answer;
import es.progcipfpbatoi.modelo.dto.Question;
import es.progcipfpbatoi.services.MySqlConnection;
import es.progcipfpbatoi.util.exceptions.DatabaseErrorException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

public class SQLAnswerDAO implements AnswerDAO {

    private Connection connection;

    private static final String TABLE_NAME = "answers";

    @Override
    public void save(Answer answer) throws DatabaseErrorException {
        insert(answer);

    }


    @Override
        public void insert(Answer answer) throws DatabaseErrorException {
            String sql = "INSERT INTO "+ TABLE_NAME + "(id_questions,description,correct) VALUES (?,?,?);";
            connection =  new MySqlConnection("192.168.56.101", "BatoiBrain", "batoi", "1234").getConnection();

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                 preparedStatement.setInt(1, answer.getQuestion().getId());
                preparedStatement.setString(2, answer.getDescription());
                preparedStatement.setInt(3, answer.getCorrect());

                preparedStatement.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
                throw new DatabaseErrorException("Ha ocurrido un error en el acceso o conexión a la base de datos (insert)");
            }
        }

    @Override
    public ArrayList<Answer> getByQuestion(int id) throws DatabaseErrorException {
        String sql = String.format("SELECT * FROM %s WHERE id_questions = ?",TABLE_NAME);
        connection =  new MySqlConnection("192.168.56.101", "BatoiBrain", "batoi", "1234").getConnection();
        ArrayList<Answer> answers = new ArrayList<>();

        try (
                PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        ) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()) {
                Answer answer = getAnswerFromResultset(resultSet);
                    answers.add(answer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseErrorException("Ha ocurrido un error en el acceso o conexión a la base de datos (select)");
        }
        return answers;
    }

    private Answer getAnswerFromResultset(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        int id_questions = rs.getInt("id_questions");
        Question question = new Question(id_questions);
        String description = rs.getString("description");
        int correct = rs.getInt("correct");

        return new Answer(id,question,description,correct) {
        };
    }
}
