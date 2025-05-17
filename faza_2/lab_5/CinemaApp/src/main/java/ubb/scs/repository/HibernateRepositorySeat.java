package ubb.scs.repository;

import org.hibernate.Session;
import ubb.scs.domain.Client;
import ubb.scs.domain.Seat;
import ubb.scs.repository.utils.HibernateUtils;

import java.util.HashSet;
import java.util.Set;

public class HibernateRepositorySeat implements IRepositorySeat {

    @Override
    public void save(Seat entity) {
        try {
            HibernateUtils.getSessionFactory().inTransaction(session -> session.persist(entity));
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(Seat entity) {
        try {
            HibernateUtils.getSessionFactory().inTransaction(session -> session.remove(entity));
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Seat findOne(Long id) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.get(Seat.class, id);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public void update(Seat entity) {
        try {
            HibernateUtils.getSessionFactory().inTransaction(session -> session.merge(entity));
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Iterable<Seat> findAll() {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.createQuery("from Seat").list();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Iterable<Seat> getSeatsByClient(Client client) {
        Set<Seat> seats = new HashSet<>();
        for (Seat seat : findAll()) {
            if (seat.getClient().equals(client)) {
                seats.add(seat);
            }
        }
        return seats;
    }
}
