package es.progcipfpbatoi.controlador;

import es.progcipfpbatoi.modelo.dto.Partida;
import es.progcipfpbatoi.modelo.dto.Users;
import es.progcipfpbatoi.modelo.repositories.AnswerRepository;
import es.progcipfpbatoi.modelo.repositories.PartidaRepository;
import es.progcipfpbatoi.modelo.repositories.QuestionRepository;
import es.progcipfpbatoi.modelo.repositories.UserRepository;
import es.progcipfpbatoi.util.exceptions.DatabaseErrorException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class VistaDetallesUsuario implements Initializable {


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

    private Users users;

    public VistaDetallesUsuario(QuestionRepository questionRepository, UserRepository userRepository, AnswerRepository answerRepository, PartidaRepository partidaRepository, Users users) {
        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
        this.answerRepository = answerRepository;
        this.partidaRepository = partidaRepository;
        this.users = users;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String puntos = null;
        try {
            puntos = String.valueOf(partidaRepository.getByUsuario(users.getUsername()).get(0).getPoints());
        } catch (DatabaseErrorException e) {
            throw new RuntimeException(e);
        }
        textLabelPuntos.setText(puntos);
        int puntuacionTotal = obtenerPuntuacionTotalUsuario();
        textLabelPuntosTotales.setText(String.valueOf(puntuacionTotal));
        textLabelUsuario.setText(users.getUsername());
        textLabelEmail.setText(users.getEmail());

    }

    private int obtenerPuntuacionTotalUsuario(){
        int puntosTotales = 0;
        try {
            ArrayList<Partida> partidasJugadasUsuario = partidaRepository.getByUsuario(users.getUsername());
            for (int i = 0; i <partidasJugadasUsuario.size() ; i++) {
                puntosTotales += partidasJugadasUsuario.get(i).getPoints();
            }
        } catch (DatabaseErrorException e) {
            throw new RuntimeException(e);
        }
        return puntosTotales;
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
