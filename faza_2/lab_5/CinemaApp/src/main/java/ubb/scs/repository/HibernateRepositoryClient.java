package ubb.scs.repository;

import org.hibernate.Session;
import ubb.scs.domain.Client;
import ubb.scs.repository.utils.HibernateUtils;

public class HibernateRepositoryClient implements IRepositoryClient {
    @Override
    public Client findByUsernameAndPassword(String username, String password) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.createQuery("from Client c where c.username = :username and c.password = :password", Client.class)
                    .setParameter("username", username)
                    .setParameter("password", password)
                    .getSingleResultOrNull();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Client findByUsername(String username) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.createQuery("from Client c where c.username = :username", Client.class)
                    .setParameter("username", username)
                    .getSingleResultOrNull();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public void save(Client entity) {
        try {
            HibernateUtils.getSessionFactory().inTransaction(session -> session.persist(entity));
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(Client entity) {
        HibernateUtils.getSessionFactory().inTransaction(session -> session.remove(entity));
    }

    @Override
    public Client findOne(Long id) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.find(Client.class, id);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public void update(Client entity) {
        HibernateUtils.getSessionFactory().inTransaction(session -> session.merge(entity));
    }

    @Override
    public Iterable<Client> findAll() {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.createQuery("from Client").list();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
