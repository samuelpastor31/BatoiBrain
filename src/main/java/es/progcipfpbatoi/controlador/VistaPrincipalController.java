package es.progcipfpbatoi.controlador;

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
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class VistaPrincipalController implements Initializable {

    private QuestionRepository questionRepository;
    private UserRepository userRepository;
    private AnswerRepository answerRepository;
    private PartidaRepository partidaRepository;

    @FXML
    private Button buttonJugarPartida;
    @FXML
    private Button buttonConsultarUsuario;
    @FXML
    private Button buttonRankingDiario;
    @FXML
    private Button buttonRankingHistorico;
    @FXML
    private Button buttonResetarDB;

    public VistaPrincipalController(QuestionRepository questionRepository, UserRepository userRepository, AnswerRepository answerRepository,PartidaRepository partidaRepository) {
        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
        this.answerRepository = answerRepository;
        this.partidaRepository = partidaRepository;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @FXML
    public void jugarPartida(MouseEvent event) {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            VistaIniciarSesionController vistaIniciarSesionController = new VistaIniciarSesionController(userRepository,this,"/vistas/menu_principal.fxml",answerRepository,questionRepository,partidaRepository);
            ChangeScene.change(stage, vistaIniciarSesionController, "/vistas/inicio_sesion.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void rankingDiario(MouseEvent event) {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            VistaRankingDiario vistaRankingDiario = new VistaRankingDiario(questionRepository,userRepository,answerRepository,partidaRepository);
            ChangeScene.change(stage, vistaRankingDiario, "/vistas/ranking_diario.fxml");
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

    @FXML
    public void consultarUsuario(MouseEvent event) {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            VistaBuscarUsuarioController vistaRankingDiario = new VistaBuscarUsuarioController(userRepository,answerRepository,questionRepository,partidaRepository);
            ChangeScene.change(stage, vistaRankingDiario, "/vistas/buscar_usuario.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void borrarBaseDeDatos(MouseEvent event){
        if (AlertMessages.mostrarAlertaConfirmacion("¿Borrar base de datos?","¿Estás seguro de que quieres borrar la basa de datos? Perderás todos los usuarios y sus registros")==true){
            try {
                partidaRepository.deleteAll();
                userRepository.deleteAll();

            } catch (DatabaseErrorException e) {
                throw new RuntimeException(e);
            }
            AlertMessages.mostrarAlert("Borrado de BD ","Base de datos reseteada con éxito", Alert.AlertType.INFORMATION);
        }
    }
}
