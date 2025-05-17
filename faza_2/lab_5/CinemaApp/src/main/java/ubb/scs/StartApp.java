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

public class StartApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        IRepositoryClient repositoryClient = new HibernateRepositoryClient();
        IRepositorySeat repositorySeat = new HibernateRepositorySeat();

        ServiceCinema serviceCinema = new ServiceCinema(repositoryClient, repositorySeat);

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/login_view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        ControllerLogin controllerLogin = fxmlLoader.getController();
        controllerLogin.setStage(stage);
        controllerLogin.setServiceCinema(serviceCinema);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
