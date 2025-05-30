package ubb.scs.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import ubb.scs.controller.utils.AlertFactory;
import ubb.scs.domain.Client;
import ubb.scs.service.ServiceException;

public class ControllerLogin extends Controller {

    @FXML
    private TextField textFieldUsername;
    @FXML
    private PasswordField textFieldPassword;

    @FXML
    private void handleLoginButton() {
        String username = textFieldUsername.getText();
        String password = textFieldPassword.getText();

        try {
            Client client = serviceCinema.login(username, password); // client contine si id-ul din baza de date
            changeScene("/views/app_view.fxml", client);
        }
        catch (ServiceException e) {
            Alert alert = AlertFactory.createAlert("Login error", null, e.getMessage(), Alert.AlertType.ERROR);
            alert.showAndWait();
        }
    }

    @FXML
    private void handleSignUpButton() {
        changeScene("/views/signup_view.fxml", null);
    }

    @Override
    public void setClient(Client client) {
        super.setClient(client);
        if (client != null) {
            textFieldUsername.setText(client.getUsername());
        }
    }
}
