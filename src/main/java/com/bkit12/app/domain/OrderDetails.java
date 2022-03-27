package com.bkit12.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A OrderDetails.
 */
@Entity
@Table(name = "order_details")
public class OrderDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "quantity")
    private Long quantity;

    @Size(max = 5000)
    @Column(name = "description", length = 5000)
    private String description;

    @Size(max = 5000)
    @Column(name = "note", length = 5000)
    private String note;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "updated_date")
    private Instant updatedDate;

    @Column(name = "updated_by")
    private Long updatedBy;

    @ManyToMany
    @JoinTable(
        name = "rel_order_details__drinks",
        joinColumns = @JoinColumn(name = "order_details_id"),
        inverseJoinColumns = @JoinColumn(name = "drinks_id")
    )
    @JsonIgnoreProperties(value = { "orderDetails" }, allowSetters = true)
    private Set<Drinks> drinks = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "rel_order_details__books",
        joinColumns = @JoinColumn(name = "order_details_id"),
        inverseJoinColumns = @JoinColumn(name = "books_id")
    )
    @JsonIgnoreProperties(value = { "orderDetails" }, allowSetters = true)
    private Set<Books> books = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "rel_order_details__foods",
        joinColumns = @JoinColumn(name = "order_details_id"),
        inverseJoinColumns = @JoinColumn(name = "foods_id")
    )
    @JsonIgnoreProperties(value = { "orderDetails" }, allowSetters = true)
    private Set<Foods> foods = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "payment", "orderDetails", "workingSpaceForm", "customers" }, allowSetters = true)
    private Orders orders;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public OrderDetails id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQuantity() {
        return this.quantity;
    }

    public OrderDetails quantity(Long quantity) {
        this.setQuantity(quantity);
        return this;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return this.description;
    }

    public OrderDetails description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNote() {
        return this.note;
    }

    public OrderDetails note(String note) {
        this.setNote(note);
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public OrderDetails createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public OrderDetails createdBy(Long createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getUpdatedDate() {
        return this.updatedDate;
    }

    public OrderDetails updatedDate(Instant updatedDate) {
        this.setUpdatedDate(updatedDate);
        return this;
    }

    public void setUpdatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Long getUpdatedBy() {
        return this.updatedBy;
    }

    public OrderDetails updatedBy(Long updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Set<Drinks> getDrinks() {
        return this.drinks;
    }

    public void setDrinks(Set<Drinks> drinks) {
        this.drinks = drinks;
    }

    public OrderDetails drinks(Set<Drinks> drinks) {
        this.setDrinks(drinks);
        return this;
    }

    public OrderDetails addDrinks(Drinks drinks) {
        this.drinks.add(drinks);
        drinks.getOrderDetails().add(this);
        return this;
    }

    public OrderDetails removeDrinks(Drinks drinks) {
        this.drinks.remove(drinks);
        drinks.getOrderDetails().remove(this);
        return this;
    }

    public Set<Books> getBooks() {
        return this.books;
    }

    public void setBooks(Set<Books> books) {
        this.books = books;
    }

    public OrderDetails books(Set<Books> books) {
        this.setBooks(books);
        return this;
    }

    public OrderDetails addBooks(Books books) {
        this.books.add(books);
        books.getOrderDetails().add(this);
        return this;
    }

    public OrderDetails removeBooks(Books books) {
        this.books.remove(books);
        books.getOrderDetails().remove(this);
        return this;
    }

    public Set<Foods> getFoods() {
        return this.foods;
    }

    public void setFoods(Set<Foods> foods) {
        this.foods = foods;
    }

    public OrderDetails foods(Set<Foods> foods) {
        this.setFoods(foods);
        return this;
    }

    public OrderDetails addFoods(Foods foods) {
        this.foods.add(foods);
        foods.getOrderDetails().add(this);
        return this;
    }

    public OrderDetails removeFoods(Foods foods) {
        this.foods.remove(foods);
        foods.getOrderDetails().remove(this);
        return this;
    }

    public Orders getOrders() {
        return this.orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }

    public OrderDetails orders(Orders orders) {
        this.setOrders(orders);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderDetails)) {
            return false;
        }
        return id != null && id.equals(((OrderDetails) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrderDetails{" +
            "id=" + getId() +
            ", quantity=" + getQuantity() +
            ", description='" + getDescription() + "'" +
            ", note='" + getNote() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", createdBy=" + getCreatedBy() +
            ", updatedDate='" + getUpdatedDate() + "'" +
            ", updatedBy=" + getUpdatedBy() +
            "}";
    }
}
