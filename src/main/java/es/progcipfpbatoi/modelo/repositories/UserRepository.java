package es.progcipfpbatoi.modelo.repositories;

import es.progcipfpbatoi.modelo.dao.LevelDAO;
import es.progcipfpbatoi.modelo.dao.UserDAO;
import es.progcipfpbatoi.modelo.dto.Categories;
import es.progcipfpbatoi.modelo.dto.Levels;
import es.progcipfpbatoi.modelo.dto.Question;
import es.progcipfpbatoi.modelo.dto.Users;
import es.progcipfpbatoi.util.exceptions.DatabaseErrorException;

public class UserRepository {

    private UserDAO userDAO;

    private LevelDAO levelDAO;

    public UserRepository(UserDAO userDAO, LevelDAO levelDAO) {
        this.userDAO = userDAO;
        this.levelDAO = levelDAO;
    }

    public void save(Users users) throws DatabaseErrorException {
            userDAO.save(users);
    }

    public void insert(Users users) throws DatabaseErrorException {
        userDAO.insert(users);
    }

    public Users findByEmail(String email) throws DatabaseErrorException {
      return userDAO.findByEmail(email);
    }

    public Users getByEmail(String email) throws DatabaseErrorException {
        Users users = userDAO.getByEmail(email);
        Levels levels = levelDAO.findById(users.getLevel().getId());
        users.setLevel(levels);
        return users;
    }

    public Users getByUserame(String username) throws DatabaseErrorException{
        Users users = userDAO.getByUserame(username);
        Levels levels = levelDAO.findById(users.getLevel().getId());
        users.setLevel(levels);
        return users;
    }

    public void deleteAll() throws DatabaseErrorException {
        userDAO.deleteAll();
    }
}


