package ubb.scs.domain;

import jakarta.persistence.*;
import jakarta.persistence.Entity;

import java.util.Objects;

@Entity
@Table(
        name = "Seats",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"row", "col"})
        }
)
public class Seat implements ubb.scs.domain.Entity<Long> {
    @Id
    @Column(name = "id_seat")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "row", nullable = false)
    private int row;
    @Column(name = "col", nullable = false)
    private int col;
    @Column(name = "price", nullable = false)
    private int price;

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @ManyToOne
    @JoinColumn(name = "id_client")
    private Client client;

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Seat() {}
    public Seat(int row, int col, int price, Client client) {
        this.row = row;
        this.col = col;
        this.price = price;
        this.client = client;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "{ " + "row: " + row + ", col: " + col + ", price: " + price + " }";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Seat seat = (Seat) o;
        return Objects.equals(seat.getId(), id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
