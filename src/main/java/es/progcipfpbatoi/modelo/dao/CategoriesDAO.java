package es.progcipfpbatoi.modelo.dao;

import es.progcipfpbatoi.modelo.dto.Categories;
import es.progcipfpbatoi.util.exceptions.DatabaseErrorException;

public interface CategoriesDAO {

    public Categories findById(int id) throws DatabaseErrorException;
}
