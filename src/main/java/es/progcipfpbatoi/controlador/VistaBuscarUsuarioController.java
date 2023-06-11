package es.progcipfpbatoi.controlador;

import es.progcipfpbatoi.modelo.dto.Partida;
import es.progcipfpbatoi.modelo.dto.Users;
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
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.util.Date;
import java.util.ResourceBundle;

public class VistaBuscarUsuarioController implements Initializable {

    private UserRepository userRepository;
    private QuestionRepository questionRepository;
    private AnswerRepository answerRepository;
    private PartidaRepository partidaRepository;
    private Initializable controladorPadre;
    private String vistaPadre;

    @FXML
    private Button botonAtras;
    @FXML
    private Button botonLogIn;
    @FXML
    private Button botonSignIn;

    @FXML
    private TextField textFieldEmail;

    public VistaBuscarUsuarioController(UserRepository userRepository,AnswerRepository answerRepository, QuestionRepository questionRepository,PartidaRepository partidaRepository) {
        this.userRepository = userRepository;
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
        this.partidaRepository = partidaRepository;
    }

    public VistaBuscarUsuarioController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML private void iniciarSesion(MouseEvent mouseEvent){
        if (extensionEmail()) {
            try {
                if (userRepository.findByEmail(textFieldEmail.getText()) != null) {
                    AlertMessages.mostrarAlert("Sesion iniciada", "Sesion iniciada", Alert.AlertType.INFORMATION);
                    Users usuario = userRepository.findByEmail(textFieldEmail.getText());
                    handleButtonLogIn(mouseEvent,usuario);
                } else {
                    AlertMessages.mostrarAlertError("Usuario no registrado, proceda a registrarlo");
                    handleButtonSignIn(mouseEvent);
                }
            } catch (DatabaseErrorException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private boolean extensionEmail() {
        boolean emailCorrecto = false;

        if (textFieldEmail.getText().endsWith("@alu.edu.gva.es")) {
            emailCorrecto = true;
        } else {
            AlertMessages.mostrarAlertError("El email no acaba con la extensión adecuada : @alu.edu.gva.es");
            emailCorrecto = false;
        }

        return emailCorrecto;
    }

    public boolean endsWith(String cadenaPrincipal, String extension) {
        if (cadenaPrincipal == null || extension == null) {
            return false;
        }
        if (extension.length() > cadenaPrincipal.length()) {
            return false;
        }

        int longitudPrincipal = cadenaPrincipal.length();
        int longitudSufijo = extension.length();
        int indiceInicio = longitudPrincipal - longitudSufijo;

        String subcadena = cadenaPrincipal.substring(indiceInicio, longitudPrincipal);

        return subcadena.equals(extension);
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
    private void handleButtonSignIn(MouseEvent event) {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            VistaSignInController vistaSignInController = new VistaSignInController(userRepository,questionRepository,answerRepository,partidaRepository);
            ChangeScene.change(stage, vistaSignInController, "/vistas/registrar_usuario.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleButtonLogIn(MouseEvent event, Users user) {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Partida partida = new Partida(0,user,0,user.getLevel(), Date.from(Instant.now()));
            VistaDetallesUsuario vistaJuego = new VistaDetallesUsuario(questionRepository,userRepository,answerRepository,partidaRepository,user);
            ChangeScene.change(stage, vistaJuego, "/vistas/detalles_usuario.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

