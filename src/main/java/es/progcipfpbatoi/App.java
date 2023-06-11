package es.progcipfpbatoi;

import es.progcipfpbatoi.controlador.ChangeScene;
import es.progcipfpbatoi.controlador.VistaPrincipalController;
import es.progcipfpbatoi.modelo.dao.*;
import es.progcipfpbatoi.modelo.repositories.AnswerRepository;
import es.progcipfpbatoi.modelo.repositories.PartidaRepository;
import es.progcipfpbatoi.modelo.repositories.QuestionRepository;
import es.progcipfpbatoi.modelo.repositories.UserRepository;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {


        SQLQuestionDAO sqlQuestionDAO = new SQLQuestionDAO();
        SQLUsersDAO sqlUsersDAO = new SQLUsersDAO();
        SQLLevelDAO sqlLevelDAO = new SQLLevelDAO();
        SQLAnswerDAO sqlAnswerDAO = new SQLAnswerDAO();
        SQLCategoriesDAO sqlCategoriesDAO = new SQLCategoriesDAO();
        SQLPartidaDAO sqlPartidaDAO = new SQLPartidaDAO();
        UserRepository userRepository = new UserRepository(sqlUsersDAO,sqlLevelDAO);
        QuestionRepository questionRepository = new QuestionRepository(sqlQuestionDAO,sqlLevelDAO,sqlCategoriesDAO);
        AnswerRepository answerRepository = new AnswerRepository(sqlAnswerDAO,sqlQuestionDAO);
        PartidaRepository partidaRepository = new PartidaRepository(sqlPartidaDAO,sqlUsersDAO,sqlLevelDAO);
        VistaPrincipalController vistaPrincipalController = new VistaPrincipalController(questionRepository,userRepository,answerRepository,partidaRepository);
        // Muestra de la escena principal.
        ChangeScene.change(stage, vistaPrincipalController, "/vistas/menu_principal.fxml");
    }

    public static void main(String[] args) {
        launch();
    }
}