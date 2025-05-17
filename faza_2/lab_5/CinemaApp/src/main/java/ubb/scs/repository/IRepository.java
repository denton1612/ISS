package ubb.scs.repository;

import ubb.scs.domain.Entity;

public interface IRepository<ID, E extends Entity<ID>> {
    void save(E entity);
    void delete(E entity);
    E findOne(ID id);
    void update(E entity);
    Iterable<E> findAll();
}
