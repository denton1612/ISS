package ubb.scs.controller;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import ubb.scs.controller.utils.AlertFactory;
import ubb.scs.domain.Client;
import ubb.scs.service.ServiceException;

import java.util.Optional;

public class ControllerUser extends Controller {

    @FXML
    private TextField textFieldUsername;
    @FXML
    private PasswordField textFieldNewPassword;
    @FXML
    private PasswordField textFieldConfirmNewPassword;
    @FXML
    private Button buttonBack;

    @FXML
    private void initialize() {
        customizeButton(buttonBack, FontAwesomeIcon.ARROW_LEFT);
    }

    @Override
    public void setClient(Client client) {
        super.setClient(client);
        textFieldUsername.setText(client.getUsername());
    }

    @FXML
    private void handleButtonBack() {
        changeScene("/views/app_view.fxml", client);
    }

    @FXML
    private void handleButtonLogout() {
        changeScene("/views/login_view.fxml", client);
    }

    @FXML
    private void handleButtonUpdate() {
        try {
            serviceCinema.changePassword(client, textFieldNewPassword.getText(), textFieldConfirmNewPassword.getText());
        }
        catch (ServiceException e) {
            Alert alert = AlertFactory.createAlert("Update account error", null, e.getMessage(), Alert.AlertType.ERROR);
            alert.showAndWait();
        }
    }

    @FXML
    private void handleButtonDelete() {
        Alert alert = AlertFactory.createAlert("Delete confirmation", "Are you sure?", "This will delete the account", Alert.AlertType.CONFIRMATION);

        ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);
        alert.getButtonTypes().setAll(yesButton, noButton);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == yesButton) {
            serviceCinema.deleteAccount(client);
            changeScene("/views/login_view.fxml", null);
        }
    }
}
