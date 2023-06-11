package es.progcipfpbatoi.modelo.repositories;

import es.progcipfpbatoi.modelo.dao.LevelDAO;
import es.progcipfpbatoi.modelo.dao.PartidaDAO;
import es.progcipfpbatoi.modelo.dao.UserDAO;
import es.progcipfpbatoi.modelo.dto.*;
import es.progcipfpbatoi.util.exceptions.DatabaseErrorException;

import java.util.ArrayList;

public class PartidaRepository {

    private PartidaDAO partidaDAO;
    private UserDAO userDAO;

    private LevelDAO levelDAO;

    public PartidaRepository(PartidaDAO partidaDAO, UserDAO userDAO,LevelDAO levelDAO) {
        this.partidaDAO = partidaDAO;
        this.userDAO = userDAO;
        this.levelDAO=levelDAO;
    }

    public ArrayList<Partida> findAll() throws DatabaseErrorException {
        return partidaDAO.findAll();
    }
    public ArrayList<Partida> findAllPoints() throws DatabaseErrorException {
     return partidaDAO.findAllPoints();
    }

    public ArrayList<Partida> findAllPointsDate() throws DatabaseErrorException {
        return partidaDAO.findAllPointsDate();
    }

    public ArrayList<Partida> getByUsuario(String nombreUsuario) throws DatabaseErrorException {
        Users users = userDAO.getByUserame(nombreUsuario);
        ArrayList<Partida> partidas = partidaDAO.getByUsuario(users.getUsername());
        for (int i = 0; i < partidas.size() ; i++) {
            Levels levels = levelDAO.findById(users.getLevel().getId());
            partidas.get(i).setLevels(levels);
        }
        return partidas;
    }

    public Partida save(Partida partida) throws DatabaseErrorException {
        return partidaDAO.save(partida);
    }

    public void deleteAll() throws DatabaseErrorException{
        partidaDAO.deleteAll();
    }
}
