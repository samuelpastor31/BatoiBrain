package es.progcipfpbatoi.controlador;

import es.progcipfpbatoi.modelo.dto.Partida;
import es.progcipfpbatoi.modelo.dto.Question;
import es.progcipfpbatoi.modelo.repositories.AnswerRepository;
import es.progcipfpbatoi.modelo.repositories.PartidaRepository;
import es.progcipfpbatoi.modelo.repositories.QuestionRepository;
import es.progcipfpbatoi.modelo.repositories.UserRepository;
import es.progcipfpbatoi.util.exceptions.DatabaseErrorException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class VistaRankingDiario implements Initializable {

    private QuestionRepository questionRepository;
    private UserRepository userRepository;
    private AnswerRepository answerRepository;
    private PartidaRepository partidaRepository;

    @FXML
    private Button buttonRankingHistorico;
    @FXML
    private Button buttonMenuPrincipal;
    @FXML
    private ListView<Partida> listView;

    public VistaRankingDiario(QuestionRepository questionRepository, UserRepository userRepository, AnswerRepository answerRepository,PartidaRepository partidaRepository) {
        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
        this.answerRepository = answerRepository;
        this.partidaRepository = partidaRepository;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            listView.setItems(getData());
        } catch (DatabaseErrorException e) {
            throw new RuntimeException(e);
        }
    }

    private ObservableList<Partida> getData() throws DatabaseErrorException {
        return FXCollections.observableArrayList(partidaRepository.findAllPointsDate());
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

    @FXML
    public void rankingHistorico(MouseEvent event) {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            VistaRankingHistorico vistaRankingHistorico = new VistaRankingHistorico(questionRepository,userRepository,answerRepository,partidaRepository);
            ChangeScene.change(stage, vistaRankingHistorico, "/vistas/ranking_historico.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
