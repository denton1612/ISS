package ubb.scs.repository;

import ubb.scs.domain.Client;
import ubb.scs.domain.Seat;

import java.util.Set;

public interface IRepositorySeat extends IRepository<Long, Seat> {
    Set<Seat> findByClient(Client client);
}
