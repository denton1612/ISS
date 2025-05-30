package ubb.scs;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ubb.scs.controller.ControllerLogin;
import ubb.scs.repository.HibernateRepositoryClient;
import ubb.scs.repository.HibernateRepositorySeat;
import ubb.scs.repository.IRepositoryClient;
import ubb.scs.repository.IRepositorySeat;
import ubb.scs.service.ServiceCinema;

import java.io.IOException;

public class StartApp extends Application {

    private void runClient(Stage stage, ServiceCinema serviceCinema) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/login_view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        ControllerLogin controllerLogin = fxmlLoader.getController();
        controllerLogin.setStage(stage);
        controllerLogin.setServiceCinema(serviceCinema);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void start(Stage stage) throws Exception {
        IRepositoryClient repositoryClient = new HibernateRepositoryClient();
        IRepositorySeat repositorySeat = new HibernateRepositorySeat();

        ServiceCinema serviceCinema = new ServiceCinema(repositoryClient, repositorySeat);

        // rulam 2 clienti pentru a putea testa observer
        runClient(stage, serviceCinema);

        Stage stage2 = new Stage();
        runClient(stage2, serviceCinema);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
