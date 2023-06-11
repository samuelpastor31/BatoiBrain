package es.progcipfpbatoi.modelo.dao;

import es.progcipfpbatoi.modelo.dto.Question;
import es.progcipfpbatoi.modelo.dto.Users;
import es.progcipfpbatoi.util.exceptions.DatabaseErrorException;

public interface UserDAO {

    void save(Users users) throws DatabaseErrorException;

    void insert(Users users) throws DatabaseErrorException;

    Users findByEmail(String email) throws DatabaseErrorException;

    Users getByEmail(String email) throws DatabaseErrorException;

    Users getByUserame(String username) throws DatabaseErrorException;

    void deleteAll() throws DatabaseErrorException;
}
