import org.junit.jupiter.api.*;
import ubb.scs.domain.Client;
import ubb.scs.domain.Seat;
import ubb.scs.repository.HibernateRepositoryClient;
import ubb.scs.repository.HibernateRepositorySeat;
import ubb.scs.service.ServiceCinema;
import ubb.scs.repository.utils.HibernateUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ServiceCinemaTest {

    private ServiceCinema service;
    private HibernateRepositoryClient repoClient;
    private HibernateRepositorySeat repoSeat;

    @BeforeAll
    public void setup() {
        repoClient = new HibernateRepositoryClient();
        repoSeat = new HibernateRepositorySeat();
        service = new ServiceCinema(repoClient, repoSeat);
    }

    @AfterAll
    public void clear() {
        Client found = service.login("testUser", "Pass1234.");
        service.deleteAccount(found);
    }

    @Test
    public void testCreateAndLoginAccount() {
        service.createAccount("testUser", "Pass1234.", "Pass1234.");

        Client found = service.login("testUser", "Pass1234.");
        assertNotNull(found);
        assertEquals("testUser", found.getUsername());
    }
}