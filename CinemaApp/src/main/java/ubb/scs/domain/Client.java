package ubb.scs.domain;

import jakarta.persistence.*;
import jakarta.persistence.Entity;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "Clients")
public class Client implements ubb.scs.domain.Entity<Long> {
    @Id
    @Column(name = "id_client")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private Set<Seat> reservedSeats;

    public Client() {}

    public Set<Seat> getReservedSeats() {
        return reservedSeats;
    }

    public void setReservedSeats(Set<Seat> reservedSeats) {
        this.reservedSeats = reservedSeats;
    }

    public Client(String username, String password) {
        this.username = username;
        this.password = password;
        this.reservedSeats = new HashSet<>();
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(id, client.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return username;
    }
}
