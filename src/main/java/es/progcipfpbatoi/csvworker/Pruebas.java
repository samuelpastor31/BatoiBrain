package es.progcipfpbatoi.csvworker;

import es.progcipfpbatoi.modelo.dao.*;
import es.progcipfpbatoi.modelo.dto.Answer;
import es.progcipfpbatoi.modelo.dto.Partida;
import es.progcipfpbatoi.modelo.dto.Question;
import es.progcipfpbatoi.modelo.dto.Users;
import es.progcipfpbatoi.modelo.repositories.PartidaRepository;
import es.progcipfpbatoi.modelo.repositories.QuestionRepository;
import es.progcipfpbatoi.util.exceptions.DatabaseErrorException;

import java.util.ArrayList;

public class Pruebas {

    public static void main(String[] args) {
        SQLAnswerDAO sqlAnswerDAO = new SQLAnswerDAO();
        SQLQuestionDAO sqlQuestionDAO = new SQLQuestionDAO();
        SQLLevelDAO sqlLevelDAO = new SQLLevelDAO();
        SQLUsersDAO sqlUsersDAO = new SQLUsersDAO();
        SQLPartidaDAO sqlPartidaDAO = new SQLPartidaDAO();
        SQLCategoriesDAO sqlCategoriesDAO = new SQLCategoriesDAO();
        CsvPreguntasRespuestas csvPreguntasRespuestas = new CsvPreguntasRespuestas(sqlQuestionDAO, sqlAnswerDAO, sqlLevelDAO);
        QuestionRepository questionRepository = new QuestionRepository(sqlQuestionDAO, sqlLevelDAO, sqlCategoriesDAO);

        try {
            ArrayList<Partida> partidas = sqlPartidaDAO.getByUsuario("hola");
            for (int i = 0; i < partidas.size() ; i++) {
                System.out.println(partidas.get(i));
            }
        } catch (DatabaseErrorException e) {
            throw new RuntimeException(e);
        }
    }

    }

