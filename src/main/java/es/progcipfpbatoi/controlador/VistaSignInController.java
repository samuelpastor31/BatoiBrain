package es.progcipfpbatoi.controlador;

import es.progcipfpbatoi.modelo.dto.Levels;
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
import java.util.ResourceBundle;
import java.util.logging.Level;

public class VistaSignInController implements Initializable {

    private UserRepository userRepository;

    private QuestionRepository questionRepository;
    private AnswerRepository answerRepository;
    private PartidaRepository partidaRepository;

    @FXML
    private Button botonAtras;
    @FXML
    private Button botonSignIn;
    @FXML
    private TextField textFieldEmail;
    @FXML
    private TextField textFieldName;

    public VistaSignInController(UserRepository userRepository, QuestionRepository questionRepository, AnswerRepository answerRepository,PartidaRepository partidaRepository) {
        this.userRepository = userRepository;
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
        this.partidaRepository = partidaRepository;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private void registrarUsuario(MouseEvent mouseEvent) throws DatabaseErrorException {
        if (extensionEmail()&&nombreCorrecto()){
            Users usuarioNuevo = new Users(textFieldName.getText(),textFieldEmail.getText(),new Levels(1));
            userRepository.save(usuarioNuevo);
            AlertMessages.mostrarAlert("Correcto","Usuario registrado, proceda a iniciar sesión", Alert.AlertType.INFORMATION);
            handleButtonBack(mouseEvent);
        }
    }

    private boolean nombreCorrecto(){
        if (textFieldName.getText().isEmpty()){
            AlertMessages.mostrarAlertError("Indique nombre de usuario por favor");
            return false;

        }else{
            return true;
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

    @FXML
    private void handleButtonBack(MouseEvent event) {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            VistaPrincipalController vistaPrincipalController = new VistaPrincipalController(questionRepository,userRepository,answerRepository,partidaRepository);
            VistaIniciarSesionController vistaIniciarSesionControllers = new VistaIniciarSesionController(userRepository,vistaPrincipalController,"/vistas/menu_principal.fxml",answerRepository,questionRepository,partidaRepository);
            ChangeScene.change(stage, vistaIniciarSesionControllers, "/vistas/inicio_sesion.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
