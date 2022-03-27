package com.bkit12.app.domain;

import com.bkit12.app.domain.enumeration.CustomerStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Customers.
 */
@Entity
@Table(name = "customers")
public class Customers implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Size(max = 3000)
    @Column(name = "customer_name", length = 3000)
    private String customerName;

    @Size(max = 5000)
    @Column(name = "address", length = 5000)
    private String address;

    @Column(name = "telephone")
    private String telephone;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private CustomerStatus status;

    @Column(name = "accumulated_points")
    private Double accumulatedPoints;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "updated_date")
    private Instant updatedDate;

    @Column(name = "updated_by")
    private Long updatedBy;

    @JsonIgnoreProperties(value = { "roles", "customer" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Users user;

    @OneToMany(mappedBy = "customers")
    @JsonIgnoreProperties(value = { "payment", "orderDetails", "workingSpaceForm", "customers" }, allowSetters = true)
    private Set<Orders> orders = new HashSet<>();

    @OneToMany(mappedBy = "customers")
    @JsonIgnoreProperties(value = { "order", "payment", "services", "customers", "workingSpaces" }, allowSetters = true)
    private Set<WorkingSpaceForms> workingSpaceForms = new HashSet<>();

    @ManyToMany(mappedBy = "customers")
    @JsonIgnoreProperties(value = { "payments", "customers" }, allowSetters = true)
    private Set<Vouchers> vouchers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Customers id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerName() {
        return this.customerName;
    }

    public Customers customerName(String customerName) {
        this.setCustomerName(customerName);
        return this;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getAddress() {
        return this.address;
    }

    public Customers address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public Customers telephone(String telephone) {
        this.setTelephone(telephone);
        return this;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public CustomerStatus getStatus() {
        return this.status;
    }

    public Customers status(CustomerStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(CustomerStatus status) {
        this.status = status;
    }

    public Double getAccumulatedPoints() {
        return this.accumulatedPoints;
    }

    public Customers accumulatedPoints(Double accumulatedPoints) {
        this.setAccumulatedPoints(accumulatedPoints);
        return this;
    }

    public void setAccumulatedPoints(Double accumulatedPoints) {
        this.accumulatedPoints = accumulatedPoints;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public Customers createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getUpdatedDate() {
        return this.updatedDate;
    }

    public Customers updatedDate(Instant updatedDate) {
        this.setUpdatedDate(updatedDate);
        return this;
    }

    public void setUpdatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Long getUpdatedBy() {
        return this.updatedBy;
    }

    public Customers updatedBy(Long updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Users getUser() {
        return this.user;
    }

    public void setUser(Users users) {
        this.user = users;
    }

    public Customers user(Users users) {
        this.setUser(users);
        return this;
    }

    public Set<Orders> getOrders() {
        return this.orders;
    }

    public void setOrders(Set<Orders> orders) {
        if (this.orders != null) {
            this.orders.forEach(i -> i.setCustomers(null));
        }
        if (orders != null) {
            orders.forEach(i -> i.setCustomers(this));
        }
        this.orders = orders;
    }

    public Customers orders(Set<Orders> orders) {
        this.setOrders(orders);
        return this;
    }

    public Customers addOrders(Orders orders) {
        this.orders.add(orders);
        orders.setCustomers(this);
        return this;
    }

    public Customers removeOrders(Orders orders) {
        this.orders.remove(orders);
        orders.setCustomers(null);
        return this;
    }

    public Set<WorkingSpaceForms> getWorkingSpaceForms() {
        return this.workingSpaceForms;
    }

    public void setWorkingSpaceForms(Set<WorkingSpaceForms> workingSpaceForms) {
        if (this.workingSpaceForms != null) {
            this.workingSpaceForms.forEach(i -> i.setCustomers(null));
        }
        if (workingSpaceForms != null) {
            workingSpaceForms.forEach(i -> i.setCustomers(this));
        }
        this.workingSpaceForms = workingSpaceForms;
    }

    public Customers workingSpaceForms(Set<WorkingSpaceForms> workingSpaceForms) {
        this.setWorkingSpaceForms(workingSpaceForms);
        return this;
    }

    public Customers addWorkingSpaceForms(WorkingSpaceForms workingSpaceForms) {
        this.workingSpaceForms.add(workingSpaceForms);
        workingSpaceForms.setCustomers(this);
        return this;
    }

    public Customers removeWorkingSpaceForms(WorkingSpaceForms workingSpaceForms) {
        this.workingSpaceForms.remove(workingSpaceForms);
        workingSpaceForms.setCustomers(null);
        return this;
    }

    public Set<Vouchers> getVouchers() {
        return this.vouchers;
    }

    public void setVouchers(Set<Vouchers> vouchers) {
        if (this.vouchers != null) {
            this.vouchers.forEach(i -> i.removeCustomers(this));
        }
        if (vouchers != null) {
            vouchers.forEach(i -> i.addCustomers(this));
        }
        this.vouchers = vouchers;
    }

    public Customers vouchers(Set<Vouchers> vouchers) {
        this.setVouchers(vouchers);
        return this;
    }

    public Customers addVouchers(Vouchers vouchers) {
        this.vouchers.add(vouchers);
        vouchers.getCustomers().add(this);
        return this;
    }

    public Customers removeVouchers(Vouchers vouchers) {
        this.vouchers.remove(vouchers);
        vouchers.getCustomers().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Customers)) {
            return false;
        }
        return id != null && id.equals(((Customers) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Customers{" +
            "id=" + getId() +
            ", customerName='" + getCustomerName() + "'" +
            ", address='" + getAddress() + "'" +
            ", telephone='" + getTelephone() + "'" +
            ", status='" + getStatus() + "'" +
            ", accumulatedPoints=" + getAccumulatedPoints() +
            ", createdDate='" + getCreatedDate() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            ", updatedBy=" + getUpdatedBy() +
            "}";
    }
}
