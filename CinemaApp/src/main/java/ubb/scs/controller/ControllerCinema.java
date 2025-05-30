package ubb.scs.controller;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import ubb.scs.controller.utils.AlertFactory;
import ubb.scs.domain.Client;
import ubb.scs.domain.Seat;
import ubb.scs.observer.IObserver;
import ubb.scs.service.ServiceCinema;
import ubb.scs.service.ServiceException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ControllerCinema extends Controller implements IObserver {

    @FXML
    private GridPane gridPaneSeats;
    @FXML
    private Button buttonUser;
    @FXML
    private TableView<Seat> tableViewReservations;
    @FXML
    private TableColumn<Seat, Integer> tableColumnRow;
    @FXML
    private TableColumn<Seat, Integer> tableColumnCol;
    @FXML
    private TextField textFieldPrice;

    private final List<Seat> selectedSeats = new ArrayList<>();
    private final ObservableList<Seat> reservedSeats = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        // initializare butoane
        customizeButton(buttonUser, FontAwesomeIcon.USER);

        // initializare gridPaneSeats;
        gridPaneSeats.setHgap(10);
        gridPaneSeats.setVgap(10);

        // initializare tableViewReservations
        tableColumnRow.setCellValueFactory(new PropertyValueFactory<>("row"));
        tableColumnCol.setCellValueFactory(new PropertyValueFactory<>("col"));
        tableViewReservations.setItems(reservedSeats);
        tableViewReservations.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        // initializare textFieldPrice
        textFieldPrice.setText("0");
    }

    @Override
    public void setServiceCinema(ServiceCinema serviceCinema) {
        super.setServiceCinema(serviceCinema);
        serviceCinema.addObserver(this);
        loadSeats(); // putem face asta doar dupa ce avem acces la service
    }

    @Override
    public void setClient(Client client) {
        super.setClient(client);
        reservedSeats.setAll(client.getReservedSeats());
    }

    private Button createButton(Seat seat) {
        Button buttonSeat = new Button();
        buttonSeat.setPrefSize(40, 50);
        buttonSeat.setUserData(seat);
        buttonSeat.setText(seat.getRow() + " " + seat.getCol());
        if (seat.getClient() != null) {
            if (seat.getClient() != client) {
                selectedSeats.remove(seat);
            }
            buttonSeat.setDisable(true);
            buttonSeat.setStyle("-fx-background-color: red; -fx-opacity: 1;");
        }
        else {
            if (selectedSeats.contains(seat)) {
                buttonSeat.setStyle("-fx-background-color: orange");
            }
            else {
                buttonSeat.setStyle("-fx-background-color: green");
            }
        }

        buttonSeat.setOnAction(event -> {
            Button button = (Button) event.getSource();
            int index = selectedSeats.indexOf((Seat) button.getUserData());
            if (index != -1) {
                selectedSeats.remove(index);
                int currentPrice = Integer.parseInt(textFieldPrice.getText());
                currentPrice -= seat.getPrice();
                textFieldPrice.setText(String.valueOf(currentPrice));
                buttonSeat.setStyle("-fx-background-color: green");
            }
            else {
                selectedSeats.add((Seat) button.getUserData());
                int currentPrice = Integer.parseInt(textFieldPrice.getText());
                currentPrice += seat.getPrice();
                textFieldPrice.setText(String.valueOf(currentPrice));
                buttonSeat.setStyle("-fx-background-color: orange");
            }
        });

        return buttonSeat;
    }

    private void loadSeats() {
        gridPaneSeats.getChildren().clear(); // curatam butoanele vechi
        for (Seat seat : serviceCinema.getSeats()) {
            Button buttonSeat = createButton(seat);
            gridPaneSeats.add(buttonSeat, seat.getCol(), seat.getRow());
        }
        loadPrice();
    }

    private void loadPrice() {
        int price = 0;
        for (Seat seat : selectedSeats) {
            price += seat.getPrice();
        }
        textFieldPrice.setText(String.valueOf(price));
    }

    @FXML
    private void handleButtonUser() {
        serviceCinema.removeObserver(this);
        changeScene("/views/user_view.fxml", client);
    }

    @FXML
    private void handleButtonReserve() {
        try {
            serviceCinema.reserveSeats(getClient(), selectedSeats);
            textFieldPrice.setText("0");
            Alert alert = AlertFactory.createAlert("Reserve success", "", client.toString() + " reserved " + selectedSeats.size() + " seats", Alert.AlertType.INFORMATION);
            selectedSeats.clear();
            alert.showAndWait();
        }
        catch (ServiceException e) {
            Alert alert = AlertFactory.createAlert("Reserve error", "", e.getMessage(), Alert.AlertType.ERROR);
            alert.showAndWait();
        }
    }

    @FXML
    private void handleButtonCancel() {
        ObservableList<Seat> reservedSeats = tableViewReservations.getSelectionModel().getSelectedItems();
        try {
            serviceCinema.cancelReservedSeats(reservedSeats);
            Alert alert = AlertFactory.createAlert("Cancel success", "", client.toString() + " cancelled", Alert.AlertType.INFORMATION);
            alert.showAndWait();
        }
        catch (ServiceException e) {
            Alert alert = AlertFactory.createAlert("Cancel error", "", e.getMessage(), Alert.AlertType.ERROR);
            alert.showAndWait();
        }
    }

    @Override
    public void update() {
        loadSeats();
        reservedSeats.setAll(client.getReservedSeats());
    }
}
