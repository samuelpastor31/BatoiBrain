package es.progcipfpbatoi.modelo.dao;

import es.progcipfpbatoi.modelo.dto.Question;
import es.progcipfpbatoi.util.exceptions.DatabaseErrorException;
import javafx.scene.chart.PieChart;

import java.io.IOException;
import java.util.ArrayList;

public interface QuestionDAO {

    Question save(Question question) throws DatabaseErrorException;

    //Question insert(Question question) throws DatabaseErrorException;

    Question findByCod(int id) throws DatabaseErrorException;

     Question getByCod(int id) throws DatabaseErrorException;

     ArrayList<Question> getByCategory (int category) throws DatabaseErrorException;
}
