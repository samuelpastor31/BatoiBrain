package es.progcipfpbatoi.modelo.repositories;

import es.progcipfpbatoi.modelo.dao.AnswerDAO;
import es.progcipfpbatoi.modelo.dao.CategoriesDAO;
import es.progcipfpbatoi.modelo.dao.LevelDAO;
import es.progcipfpbatoi.modelo.dao.QuestionDAO;
import es.progcipfpbatoi.modelo.dto.Categories;
import es.progcipfpbatoi.modelo.dto.Levels;
import es.progcipfpbatoi.modelo.dto.Question;
import es.progcipfpbatoi.util.exceptions.DatabaseErrorException;

import java.util.ArrayList;

public class QuestionRepository {

    private AnswerDAO answerDAO;

    private CategoriesDAO categoriesDAO;

    private QuestionDAO questionDAO;

    private LevelDAO levelDAO;

    public QuestionRepository( QuestionDAO questionDAO, LevelDAO levelDAO, CategoriesDAO categoriesDAO) {
        this.questionDAO = questionDAO;
        this.levelDAO = levelDAO;
        this.categoriesDAO = categoriesDAO;
    }

    public void save(Question question) throws DatabaseErrorException {
        questionDAO.save(question);
    }


    public Question findByCod(int id) throws DatabaseErrorException {
       return questionDAO.findByCod(id);
    }

    public Question getByCod(int id) throws DatabaseErrorException {
        Question question = questionDAO.getByCod(id);
        Categories categories = categoriesDAO.findById(question.getCategory().getId());
        Levels levels = levelDAO.findByDescription(question.getLevel().getDescription());
        question.setCategory(categories);
        question.setLevel(levels);
        return question;
    }

    public ArrayList<Question> getByCategory(int category) throws DatabaseErrorException {
        Categories categories = categoriesDAO.findById(category);
        ArrayList<Question>arrayList = questionDAO.getByCategory(categories.getId());
        for (int i = 0; i < arrayList.size() ; i++) {
            Levels levels = levelDAO.findById(arrayList.get(i).getLevel().getId());
            arrayList.get(i).setLevel(levels);
        }
        return arrayList;
    }
}
