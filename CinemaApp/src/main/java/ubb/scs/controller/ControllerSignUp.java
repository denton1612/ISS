package ubb.scs.controller;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import ubb.scs.controller.utils.AlertFactory;
import ubb.scs.domain.Client;
import ubb.scs.service.ServiceException;

public class ControllerSignUp extends Controller {

    @FXML
    private Button buttonBack;

    @FXML
    private TextField textFieldUsername;

    @FXML
    private PasswordField textFieldPassword;

    @FXML
    private PasswordField textFieldConfirmPassword;

    @FXML
    private void initialize() {
        customizeButton(buttonBack, FontAwesomeIcon.ARROW_LEFT);
    }

    @FXML
    private void handleButtonBack() {
        changeScene("/views/login_view.fxml", null);
    }

    @FXML
    private void handleButtonCreateAccount() {
        String username = textFieldUsername.getText();
        String password = textFieldPassword.getText();
        String confirmPassword = textFieldConfirmPassword.getText();

        try {
            serviceCinema.createAccount(username, password, confirmPassword);

            // daca contul a fost creat cu succes, noul client va fi redirectionat pe pagina de login cu username-ul incarcat
            Client client = new Client();
            client.setUsername(username);
            changeScene("/views/login_view.fxml", client);

            Alert alert = AlertFactory.createAlert("Account created", null, username + " created an account", Alert.AlertType.INFORMATION);
            alert.showAndWait();
        }
        catch (ServiceException e) {
            Alert alert = AlertFactory.createAlert("Create account error", null, e.getMessage(), Alert.AlertType.ERROR);
            alert.showAndWait();
        }
    }
}
