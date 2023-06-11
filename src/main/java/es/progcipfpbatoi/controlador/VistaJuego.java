package es.progcipfpbatoi.controlador;

import es.progcipfpbatoi.modelo.dto.Answer;
import es.progcipfpbatoi.modelo.dto.Partida;
import es.progcipfpbatoi.modelo.dto.Question;
import es.progcipfpbatoi.modelo.dto.Users;
import es.progcipfpbatoi.modelo.repositories.AnswerRepository;
import es.progcipfpbatoi.modelo.repositories.PartidaRepository;
import es.progcipfpbatoi.modelo.repositories.QuestionRepository;
import es.progcipfpbatoi.modelo.repositories.UserRepository;
import es.progcipfpbatoi.util.AlertMessages;
import es.progcipfpbatoi.util.exceptions.DatabaseErrorException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.util.*;

public class VistaJuego implements Initializable {


    @FXML
    private ListView<Question> listViewQuestion;
    @FXML
    private ListView<Answer> listViewAnswer;
    @FXML
    private Text x;
    @FXML
    private Button buttonRespuesta;
    @FXML
    private ComboBox<Answer> comboBox;

    private Users user;
    private QuestionRepository questionRepository;
    private UserRepository userRepository;
    private AnswerRepository answerRepository;
    private Question selectedQuestion;
    private int indiceJuegoActual;
    private PartidaRepository partidaRepository;
    private Partida partida;

    public VistaJuego(Users user, QuestionRepository questionRepository, UserRepository userRepository, AnswerRepository answerRepository, int indiceJuegoActual,Partida partida,PartidaRepository partidaRepository) {
        this.user = user;
        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
        this.answerRepository = answerRepository;
        this.indiceJuegoActual = indiceJuegoActual;
        this.partidaRepository = partidaRepository;
        this.partida = partida;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        x.setText(String.valueOf(indiceJuegoActual));

        int numeroCategoria = obtenerCategoriaAleatoria();
        try {
            ObservableList<Question> dataQuestion = getDataQuestion(numeroCategoria);
            listViewQuestion.setItems(dataQuestion);
            listViewAnswer.setItems(getDataAnswer(dataQuestion));
            comboBox.setItems(getDataAnswer(dataQuestion));
        } catch (DatabaseErrorException e) {
            throw new RuntimeException(e);
        }


    }

    private ObservableList<Question> getDataQuestion(int numeroCategoria) throws DatabaseErrorException {
        selectedQuestion = obtenerPreguntaAleatoria(user, numeroCategoria);
        return FXCollections.observableArrayList(selectedQuestion);
    }

    private ObservableList<Answer> getDataAnswer(ObservableList<Question> questions) throws DatabaseErrorException {
        ArrayList<Answer> answers = answerRepository.getByQuestion(questions.get(0).getId());
        return FXCollections.observableArrayList(answers);
    }

    private Question obtenerPreguntaAleatoria(Users user, int numeroCategoria) {
        ArrayList<Question> preguntasFiltradas;

        try {
            preguntasFiltradas = questionRepository.getByCategory(numeroCategoria);
        } catch (DatabaseErrorException e) {
            throw new RuntimeException(e);
        }

        ArrayList<Question> preguntasNivelUsuario = new ArrayList<>();

        for (Question pregunta : preguntasFiltradas) {
            if (pregunta.getLevel().getId()==user.getLevel().getId()) {
                preguntasNivelUsuario.add(pregunta);
            }
        }

        if (preguntasNivelUsuario.isEmpty()) {
            throw new RuntimeException("No hay preguntas para el nivel del usuario");
        }

        Random random = new Random();
        int indiceAleatorio = random.nextInt(preguntasNivelUsuario.size());

        return preguntasNivelUsuario.get(indiceAleatorio);
    }
    public static int obtenerCategoriaAleatoria() {
        int[] numbers = {1, 2, 3, 4, 5, 9, 10, 12, 14, 15, 17, 21};

        Random random = new Random();
        int randomIndex = random.nextInt(numbers.length);

        return numbers[randomIndex];
    }

    @FXML
    private void confirmarRespuesta(MouseEvent mouseEvent) {
        Answer selectedType = comboBox.getSelectionModel().getSelectedItem();
        int selectedInt = comboBox.getSelectionModel().getSelectedIndex();
        if (selectedType != null) {
            int puntosPartida = 0;
            if (selectedType.getCorrect() == selectedInt) {
                AlertMessages.mostrarAlert("¡Sí!", "Respuesta acertada", Alert.AlertType.INFORMATION);
                switch (user.getLevel().getId()) {
                    case 1:
                        puntosPartida += 10;
                        break;
                    case 2:
                        puntosPartida += 15;
                        break;
                    case 3:
                        puntosPartida += 20;
                        break;
                    case 4:
                        puntosPartida += 25;
                        break;
                }
            } else {
                AlertMessages.mostrarAlert("¡No!", "Respuesta fallada", Alert.AlertType.ERROR);
                switch (user.getLevel().getId()) {
                    case 1:
                        puntosPartida -= 2;
                        break;
                    case 2:
                        puntosPartida -= 4;
                        break;
                    case 3:
                        puntosPartida -= 6;
                        break;
                    case 4:
                        puntosPartida -= 8;
                        break;
                }
            }
            if (indiceJuegoActual <= 5) {
                try {
                    Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
                    partida.setPoints(partida.getPoints()+puntosPartida);
                    VistaJuego vistaJuego = new VistaJuego(user, questionRepository, userRepository, answerRepository, indiceJuegoActual + 1,partida,partidaRepository);
                    ChangeScene.change(stage, vistaJuego, "/vistas/juego_partida.fxml");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (indiceJuegoActual == 6) {
                buttonRespuesta.setText("Finalizar juego");
                partida.setPoints(partida.getPoints()+puntosPartida);
                try {
                    partidaRepository.save(partida);
                } catch (DatabaseErrorException e) {
                    throw new RuntimeException(e);
                }
                resultadosPartida(mouseEvent, partida);
            }
        } else {
            AlertMessages.mostrarAlertWarning("¡Elige una respuesta!");
        }
    }

    @FXML
    private void resultadosPartida(MouseEvent event, Partida partida) {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            VistaResultadosPartida vistaResultadosPartida = new VistaResultadosPartida(questionRepository,userRepository,answerRepository,partidaRepository,partida);
            ChangeScene.change(stage,vistaResultadosPartida,"/vistas/finalizar_partida.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
