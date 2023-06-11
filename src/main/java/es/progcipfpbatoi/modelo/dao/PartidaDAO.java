package es.progcipfpbatoi.modelo.dao;

import es.progcipfpbatoi.modelo.dto.Partida;
import es.progcipfpbatoi.util.exceptions.DatabaseErrorException;

import java.sql.Date;
import java.util.ArrayList;

public interface PartidaDAO {
    ArrayList<Partida> findAll() throws DatabaseErrorException;

    ArrayList<Partida> findAllPoints() throws DatabaseErrorException;

    ArrayList<Partida> findAllPointsDate() throws DatabaseErrorException;

    Partida save(Partida partida) throws DatabaseErrorException;

    ArrayList<Partida> getByUsuario(String nombreUsuario) throws DatabaseErrorException;

    ArrayList<Partida> getByFecha(Date date) throws DatabaseErrorException;

    void deleteAll() throws DatabaseErrorException;
}
