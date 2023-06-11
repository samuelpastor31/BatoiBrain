package es.progcipfpbatoi.modelo.repositories;

import es.progcipfpbatoi.modelo.dao.AnswerDAO;
import es.progcipfpbatoi.modelo.dao.CategoriesDAO;
import es.progcipfpbatoi.modelo.dao.QuestionDAO;
import es.progcipfpbatoi.modelo.dto.Answer;
import es.progcipfpbatoi.modelo.dto.Question;
import es.progcipfpbatoi.util.exceptions.DatabaseErrorException;

import java.util.ArrayList;

public class AnswerRepository {

    private AnswerDAO answerDAO;
    private QuestionDAO questionDAO;

    public AnswerRepository(AnswerDAO answerDAO, QuestionDAO questionDAO) {
        this.answerDAO = answerDAO;
        this.questionDAO = questionDAO;
    }

    public void save(Answer answer) throws DatabaseErrorException {
        answerDAO.save(answer);
    }

    public void insert(Answer answer) throws DatabaseErrorException {
        answerDAO.insert(answer);
    }

    public ArrayList<Answer> getByQuestion(int id) throws DatabaseErrorException {
        ArrayList<Answer> answers = answerDAO.getByQuestion(id);
        Question question = questionDAO.getByCod(id);
        for (int i = 0; i < answers.size() ; i++) {
            answers.get(i).setQuestion(question);
        }
        return answers;
    }
}
