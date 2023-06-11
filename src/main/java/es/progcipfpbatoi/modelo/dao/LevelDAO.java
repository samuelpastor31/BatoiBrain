package es.progcipfpbatoi.modelo.dao;

import es.progcipfpbatoi.modelo.dto.Levels;
import es.progcipfpbatoi.modelo.dto.Question;
import es.progcipfpbatoi.util.exceptions.DatabaseErrorException;

public interface LevelDAO {

    Levels findByDescription(String descripcion) throws DatabaseErrorException;

    Levels findById(int id) throws DatabaseErrorException;
}
