package es.progcipfpbatoi.csvworker;

import es.progcipfpbatoi.modelo.dao.SQLAnswerDAO;
import es.progcipfpbatoi.modelo.dao.SQLLevelDAO;
import es.progcipfpbatoi.modelo.dao.SQLQuestionDAO;
import es.progcipfpbatoi.modelo.dto.Answer;
import es.progcipfpbatoi.modelo.dto.Categories;
import es.progcipfpbatoi.modelo.dto.Levels;
import es.progcipfpbatoi.modelo.dto.Question;
import es.progcipfpbatoi.util.exceptions.DatabaseErrorException;

import java.io.*;
import java.util.ArrayList;

public class CsvPreguntasRespuestas {

    private static final String CSV_FILE = "/home/batoi/IdeaProjects2023/BatoiBrain/src/main/resources/database/preguntas_respuestas.txt";

    private File file;

    private SQLQuestionDAO sqlQuestionDAO;
    private SQLAnswerDAO sqlAnswerDAO;

    private SQLLevelDAO sqlLevelDAO;

    public CsvPreguntasRespuestas(SQLQuestionDAO sqlQuestionDAO, SQLAnswerDAO sqlAnswerDAO, SQLLevelDAO sqlLevelDAO) {
        this.sqlQuestionDAO = sqlQuestionDAO;
        this.sqlAnswerDAO = sqlAnswerDAO;
        this.sqlLevelDAO = sqlLevelDAO;
        this.file = new File(CSV_FILE);
    }

    public void start() {


        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            int aux=0;
            int cod = 1;
           do {
            String linea = bufferedReader.readLine();

            if (linea != null) {

                //pregunta::nivel::categoria::activa::respuestas::indiceRespuestaCorrecta
                String[] fields = linea.split("\\[]");
                String pregunta = fields[0];
                String nivel = fields[1];
                Levels levels = sqlLevelDAO.findByDescription(nivel);
                int categoria = Integer.parseInt(fields[2]);
                Categories categories = new Categories(categoria);
                int estatus;
                if (fields[3].equals("true")) {
                    estatus = 1;
                } else {
                    estatus = 0;
                }
                String respuestasSinSeparar = fields[4];
                int indiceRespuestaCorrecta = Integer.parseInt(fields[5]);
                Question question = new Question(cod, pregunta, null, categories, levels, estatus);
                question = sqlQuestionDAO.save(question);

                String[] respuestaSeparada = respuestasSinSeparar.split("\\|");
                String respuestas ="";
                for (int i = 0; i <respuestaSeparada.length ; i++) {
                    respuestas =" Respuesta " +" --> "+respuestaSeparada[i]+" ";
                    Answer answer = new Answer(cod,question,respuestas,indiceRespuestaCorrecta);

                    sqlAnswerDAO.save(answer);
                }

                cod++;
                System.out.println(question);
            }else {
                aux++;
            }
           }while (aux!=1);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        } catch (DatabaseErrorException e) {
            throw new RuntimeException(e);
        }


        // Leer el csv linea a linea
        // por cada linea sacar todos los datos
        // con los datos correspondientes a pregunta guardar en la tabla questions
            // --> sqlQuestionDAO.save(Question question);
        // con los datos correpondientes a respuest guardar en la tabla answers
         // --> sqlAnswerDAO.save(Answer answer);
    }

}
