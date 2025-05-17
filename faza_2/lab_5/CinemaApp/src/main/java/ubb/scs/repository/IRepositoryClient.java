package ubb.scs.repository;

import ubb.scs.domain.Client;

public interface IRepositoryClient extends IRepository<Long, Client> {
    Client findByUsernameAndPassword(String username, String password);
    Client findByUsername(String username);
}
