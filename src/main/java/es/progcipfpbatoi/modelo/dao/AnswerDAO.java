package es.progcipfpbatoi.modelo.dao;

import es.progcipfpbatoi.modelo.dto.Answer;
import es.progcipfpbatoi.modelo.dto.Question;
import es.progcipfpbatoi.util.exceptions.DatabaseErrorException;

import java.util.ArrayList;

public interface AnswerDAO {

    public void save(Answer answer) throws DatabaseErrorException;

    public void insert(Answer answer) throws DatabaseErrorException;

    public ArrayList<Answer> getByQuestion(int id) throws DatabaseErrorException;

    //int getNumAnswersFromQuestion(Question question);
}
