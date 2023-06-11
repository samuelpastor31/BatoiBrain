package es.progcipfpbatoi.controlador;

import es.progcipfpbatoi.modelo.dto.Levels;
import es.progcipfpbatoi.modelo.dto.Partida;
import es.progcipfpbatoi.modelo.dto.Question;
import es.progcipfpbatoi.modelo.repositories.AnswerRepository;
import es.progcipfpbatoi.modelo.repositories.PartidaRepository;
import es.progcipfpbatoi.modelo.repositories.QuestionRepository;
import es.progcipfpbatoi.modelo.repositories.UserRepository;
import es.progcipfpbatoi.util.AlertMessages;
import es.progcipfpbatoi.util.exceptions.DatabaseErrorException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class VistaResultadosPartida implements Initializable {


    @FXML
    private TextField textLabelPuntos;
    @FXML
    private TextField textLabelPuntosTotales;
    @FXML
    private TextField textLabelUsuario;
    @FXML
    private TextField textLabelEmail;
    private QuestionRepository questionRepository;
    private UserRepository userRepository;
    private AnswerRepository answerRepository;
    private PartidaRepository partidaRepository;
    private Partida partida;

    public VistaResultadosPartida(QuestionRepository questionRepository, UserRepository userRepository, AnswerRepository answerRepository, PartidaRepository partidaRepository, Partida partida) {
        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
        this.answerRepository = answerRepository;
        this.partidaRepository = partidaRepository;
        this.partida = partida;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String puntos = String.valueOf(partida.getPoints());
        textLabelPuntos.setText(puntos);
        int puntuacionTotal = obtenerPuntuacionTotalUsuario();
        textLabelPuntosTotales.setText(String.valueOf(puntuacionTotal));
        textLabelUsuario.setText(partida.getUsers().getUsername());
        textLabelEmail.setText(partida.getUsers().getEmail());
        obtenerNivel(obtenerPuntuacionTotalUsuario());
    }

    private int obtenerPuntuacionTotalUsuario(){
        int puntosTotales = 0;
        try {
            ArrayList<Partida> partidasJugadasUsuario = partidaRepository.getByUsuario(partida.getUsers().getUsername());
            for (int i = 0; i <partidasJugadasUsuario.size() ; i++) {
                puntosTotales += partidasJugadasUsuario.get(i).getPoints();
            }
        } catch (DatabaseErrorException e) {
            throw new RuntimeException(e);
        }
        return puntosTotales;
    }

    private void obtenerNivel(int puntuacionTotal) {
        Levels levels = new Levels(1);
        if (puntuacionTotal < 50) {
            partida.getUsers().setLevel(levels); // Fácil
            AlertMessages.mostrarAlert("¡FELICIDADES!","¡FELICIDADES, has subido al NIVEL "+levels.getDescription()+". Ahora se va a poner un poco más difícil, preparate campeón.", Alert.AlertType.INFORMATION);
        } else if (puntuacionTotal < 100) {
            levels.setId(2);
            partida.getUsers().setLevel(levels);
            AlertMessages.mostrarAlert("¡FELICIDADES!","¡FELICIDADES, has subido al NIVEL "+levels.getDescription()+". Vas mejorando, la cosa se va a ir poniendo interesante.", Alert.AlertType.INFORMATION);
        } else if (puntuacionTotal < 150) {
            levels.setId(3);
            partida.getUsers().setLevel(levels);
            AlertMessages.mostrarAlert("¡FELICIDADES!","¡FELICIDADES, has subido al NIVEL "+levels.getDescription()+". Eres todo un as, ¿podrás con este nivel?.", Alert.AlertType.INFORMATION);
        } else {
            levels.setId(4);
            partida.getUsers().setLevel(levels);
            AlertMessages.mostrarAlert("¡FELICIDADES!","¡FELICIDADES, has subido al NIVEL "+levels.getDescription()+". ¡Esto es completamente increíble!. ¡Estás entre las elites del mundo entero!", Alert.AlertType.INFORMATION);
        }
    }

    @FXML
    private void handleButtonBack(MouseEvent event) {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            VistaPrincipalController vistaPrincipalController = new VistaPrincipalController(questionRepository,userRepository,answerRepository,partidaRepository);
            ChangeScene.change(stage, vistaPrincipalController, "/vistas/menu_principal.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
