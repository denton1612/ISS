package ubb.scs.service;

import ubb.scs.domain.Client;
import ubb.scs.domain.Seat;
import ubb.scs.domain.validator.ValidatorFactory;
import ubb.scs.observer.IObservable;
import ubb.scs.observer.Observable;
import ubb.scs.repository.IRepositoryClient;
import ubb.scs.repository.IRepositorySeat;

import java.util.List;

public class ServiceCinema extends Observable {
    private final IRepositoryClient repositoryClient;
    private final IRepositorySeat repositorySeat;


    public ServiceCinema(IRepositoryClient repositoryClient, IRepositorySeat repositorySeat) {
        this.repositoryClient = repositoryClient;
        this.repositorySeat = repositorySeat;
    }

    public void createAccount(String username, String password, String confirmPassword) {
        Client client = new Client(username, password);
        ValidatorFactory.getValidatorClient().validate(client);

        // cazul in care username-ul este folosit
        if (repositoryClient.findByUsername(username) != null) {
            throw new ServiceException("Username already exists");
        }
        if (!password.equals(confirmPassword)) {
            throw new ServiceException("Passwords do not match");
        }
        repositoryClient.save(client);
    }

    public Client login(String username, String password) {
        Client client = repositoryClient.findByUsernameAndPassword(username, password);
        if (client == null) {
            throw new ServiceException("Invalid username or password!");
        }
        return client;
    }

    public void deleteAccount(Client client) {
        for (Seat seat : client.getReservedSeats()) {
            seat.setClient(null);
            repositorySeat.update(seat);
        }
        repositoryClient.delete(client);
    }

    public void changePassword(Client client, String newPassword, String confirmNewPassword) {
        if (!newPassword.equals(confirmNewPassword)) {
            throw new ServiceException("Passwords do not match");
        }

        client.setPassword(newPassword);
        ValidatorFactory.getValidatorClient().validate(client); // ne asiguram ca si parola noua este valida

        repositoryClient.update(client);
    }

    public List<Seat> getSeats() {
        return (List<Seat>) repositorySeat.findAll();
    }

    public void reserveSeats(Client client, List<Seat> selectedSeats) {
        if (selectedSeats.isEmpty()) {
            throw new ServiceException("Not selected seats!");
        }
        for (Seat seat : selectedSeats) {
            seat.setClient(client);
            client.getReservedSeats().add(seat);
            repositorySeat.update(seat);
        }
        notifyObservers();
    }

    public void cancelReservedSeats(List<Seat> selectedSeats) {
        if (selectedSeats.isEmpty()) {
            throw new ServiceException("Not selected seats!");
        }
        for (Seat seat : selectedSeats) {
            seat.getClient().getReservedSeats().remove(seat);
            seat.setClient(null);
            repositorySeat.update(seat);
        }
        notifyObservers();
    }
}
