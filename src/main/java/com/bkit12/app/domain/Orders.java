package com.bkit12.app.domain;

import com.bkit12.app.domain.enumeration.OrderStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Orders.
 */
@Entity
@Table(name = "orders")
public class Orders implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Size(max = 5000)
    @Column(name = "address", length = 5000)
    private String address;

    @Size(max = 5000)
    @Column(name = "note", length = 5000)
    private String note;

    @Column(name = "price_total")
    private Double priceTotal;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatus status;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "updated_date")
    private Instant updatedDate;

    @Column(name = "updated_by")
    private Long updatedBy;

    @JsonIgnoreProperties(value = { "workingSpaceForm", "order", "vouchers" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Payments payment;

    @OneToMany(mappedBy = "orders")
    @JsonIgnoreProperties(value = { "drinks", "books", "foods", "orders" }, allowSetters = true)
    private Set<OrderDetails> orderDetails = new HashSet<>();

    @JsonIgnoreProperties(value = { "order", "payment", "services", "customers", "workingSpaces" }, allowSetters = true)
    @OneToOne(mappedBy = "order")
    private WorkingSpaceForms workingSpaceForm;

    @ManyToOne
    @JsonIgnoreProperties(value = { "user", "orders", "workingSpaceForms", "vouchers" }, allowSetters = true)
    private Customers customers;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Orders id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return this.address;
    }

    public Orders address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNote() {
        return this.note;
    }

    public Orders note(String note) {
        this.setNote(note);
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Double getPriceTotal() {
        return this.priceTotal;
    }

    public Orders priceTotal(Double priceTotal) {
        this.setPriceTotal(priceTotal);
        return this;
    }

    public void setPriceTotal(Double priceTotal) {
        this.priceTotal = priceTotal;
    }

    public OrderStatus getStatus() {
        return this.status;
    }

    public Orders status(OrderStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public Orders createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public Orders createdBy(Long createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getUpdatedDate() {
        return this.updatedDate;
    }

    public Orders updatedDate(Instant updatedDate) {
        this.setUpdatedDate(updatedDate);
        return this;
    }

    public void setUpdatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Long getUpdatedBy() {
        return this.updatedBy;
    }

    public Orders updatedBy(Long updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Payments getPayment() {
        return this.payment;
    }

    public void setPayment(Payments payments) {
        this.payment = payments;
    }

    public Orders payment(Payments payments) {
        this.setPayment(payments);
        return this;
    }

    public Set<OrderDetails> getOrderDetails() {
        return this.orderDetails;
    }

    public void setOrderDetails(Set<OrderDetails> orderDetails) {
        if (this.orderDetails != null) {
            this.orderDetails.forEach(i -> i.setOrders(null));
        }
        if (orderDetails != null) {
            orderDetails.forEach(i -> i.setOrders(this));
        }
        this.orderDetails = orderDetails;
    }

    public Orders orderDetails(Set<OrderDetails> orderDetails) {
        this.setOrderDetails(orderDetails);
        return this;
    }

    public Orders addOrderDetails(OrderDetails orderDetails) {
        this.orderDetails.add(orderDetails);
        orderDetails.setOrders(this);
        return this;
    }

    public Orders removeOrderDetails(OrderDetails orderDetails) {
        this.orderDetails.remove(orderDetails);
        orderDetails.setOrders(null);
        return this;
    }

    public WorkingSpaceForms getWorkingSpaceForm() {
        return this.workingSpaceForm;
    }

    public void setWorkingSpaceForm(WorkingSpaceForms workingSpaceForms) {
        if (this.workingSpaceForm != null) {
            this.workingSpaceForm.setOrder(null);
        }
        if (workingSpaceForms != null) {
            workingSpaceForms.setOrder(this);
        }
        this.workingSpaceForm = workingSpaceForms;
    }

    public Orders workingSpaceForm(WorkingSpaceForms workingSpaceForms) {
        this.setWorkingSpaceForm(workingSpaceForms);
        return this;
    }

    public Customers getCustomers() {
        return this.customers;
    }

    public void setCustomers(Customers customers) {
        this.customers = customers;
    }

    public Orders customers(Customers customers) {
        this.setCustomers(customers);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Orders)) {
            return false;
        }
        return id != null && id.equals(((Orders) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Orders{" +
            "id=" + getId() +
            ", address='" + getAddress() + "'" +
            ", note='" + getNote() + "'" +
            ", priceTotal=" + getPriceTotal() +
            ", status='" + getStatus() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", createdBy=" + getCreatedBy() +
            ", updatedDate='" + getUpdatedDate() + "'" +
            ", updatedBy=" + getUpdatedBy() +
            "}";
    }
}
