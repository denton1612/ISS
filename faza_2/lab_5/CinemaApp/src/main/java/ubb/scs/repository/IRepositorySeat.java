package ubb.scs.repository;

import ubb.scs.domain.Client;
import ubb.scs.domain.Seat;

public interface IRepositorySeat extends IRepository<Long, Seat> {
    Iterable<Seat> getSeatsByClient(Client client);
}
