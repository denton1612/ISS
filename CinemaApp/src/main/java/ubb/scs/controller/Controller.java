package ubb.scs.controller;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import ubb.scs.domain.Client;
import ubb.scs.service.ServiceCinema;

public class Controller {
    protected ServiceCinema serviceCinema;
    protected Client client;
    protected Stage stage;

    public Controller() {}

    public void setServiceCinema(ServiceCinema serviceCinema) {
        this.serviceCinema = serviceCinema;
    }

    public ServiceCinema getServiceCinema() {
        return serviceCinema;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Client getClient() {
        return client;
    }

    protected void changeScene(String fxmlFile, Client client) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));
            Scene scene = new Scene(fxmlLoader.load());
            Controller controller = fxmlLoader.getController();
            controller.setServiceCinema(serviceCinema);
            controller.setClient(client);
            controller.setStage(stage);
            stage.setScene(scene);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    protected void customizeButton(Button button, FontAwesomeIcon icon) {
        FontAwesomeIconView iconView = new FontAwesomeIconView(icon);
        button.setGraphic(iconView);
    }
}
