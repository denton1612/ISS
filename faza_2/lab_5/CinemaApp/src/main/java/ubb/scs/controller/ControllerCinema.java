package ubb.scs.controller;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import ubb.scs.domain.Client;

public class ControllerCinema extends Controller {
    private Client client;

    @FXML
    private Button buttonUser;

    public void setClient(Client client) {
        this.client = client;
    }

    public Client getClient() {
        return client;
    }

    @FXML
    private void initialize() {
        customizeButton(buttonUser, FontAwesomeIcon.USER);
    }

    @FXML
    private void handleButtonUser() {
        changeScene("/views/user_view.fxml", client);
    }

    @FXML
    private void handleButtonReserve() {

    }
}
