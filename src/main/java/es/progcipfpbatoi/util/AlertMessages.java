package es.progcipfpbatoi.util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class AlertMessages {
    public static void mostrarAlertError(String msg) {
        mostrarAlert("Error", msg, Alert.AlertType.ERROR);
    }

    public static void mostrarAlertWarning(String msg) {
        mostrarAlert("Aviso", msg, Alert.AlertType.WARNING);
    }

    public static void mostrarAlert(String title, String msg, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setHeaderText(null);
        alert.setTitle(title);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    public static boolean mostrarAlertaConfirmacion(String title, String content) {
        Alert alert = new Alert( Alert.AlertType.CONFIRMATION );
        alert.setHeaderText( null );
        alert.setTitle( title );
        alert.setContentText( content );
        Optional<ButtonType> action = alert.showAndWait();
        if ( action.get() == ButtonType.OK ) {
            return true;
        }
        return false;
    }
}
