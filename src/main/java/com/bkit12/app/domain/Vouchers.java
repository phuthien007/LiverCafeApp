package com.bkit12.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Vouchers.
 */
@Entity
@Table(name = "vouchers")
public class Vouchers implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(min = 10)
    @Column(name = "id_voucher", nullable = false)
    private String idVoucher;

    @Size(max = 5000)
    @Column(name = "name", length = 5000)
    private String name;

    @Size(max = 5000)
    @Column(name = "description", length = 5000)
    private String description;

    @Column(name = "percent_promotion")
    private Double percentPromotion;

    @Column(name = "max_total_money_promotion")
    private Double maxTotalMoneyPromotion;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "start_time")
    private Instant startTime;

    @Column(name = "end_time")
    private Instant endTime;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "updated_date")
    private Instant updatedDate;

    @Column(name = "updated_by")
    private Long updatedBy;

    @OneToMany(mappedBy = "vouchers")
    @JsonIgnoreProperties(value = { "workingSpaceForm", "order", "vouchers" }, allowSetters = true)
    private Set<Payments> payments = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "rel_vouchers__customers",
        joinColumns = @JoinColumn(name = "vouchers_id"),
        inverseJoinColumns = @JoinColumn(name = "customers_id")
    )
    @JsonIgnoreProperties(value = { "user", "orders", "workingSpaceForms", "vouchers" }, allowSetters = true)
    private Set<Customers> customers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Vouchers id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdVoucher() {
        return this.idVoucher;
    }

    public Vouchers idVoucher(String idVoucher) {
        this.setIdVoucher(idVoucher);
        return this;
    }

    public void setIdVoucher(String idVoucher) {
        this.idVoucher = idVoucher;
    }

    public String getName() {
        return this.name;
    }

    public Vouchers name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public Vouchers description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPercentPromotion() {
        return this.percentPromotion;
    }

    public Vouchers percentPromotion(Double percentPromotion) {
        this.setPercentPromotion(percentPromotion);
        return this;
    }

    public void setPercentPromotion(Double percentPromotion) {
        this.percentPromotion = percentPromotion;
    }

    public Double getMaxTotalMoneyPromotion() {
        return this.maxTotalMoneyPromotion;
    }

    public Vouchers maxTotalMoneyPromotion(Double maxTotalMoneyPromotion) {
        this.setMaxTotalMoneyPromotion(maxTotalMoneyPromotion);
        return this;
    }

    public void setMaxTotalMoneyPromotion(Double maxTotalMoneyPromotion) {
        this.maxTotalMoneyPromotion = maxTotalMoneyPromotion;
    }

    public Boolean getActive() {
        return this.active;
    }

    public Vouchers active(Boolean active) {
        this.setActive(active);
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Instant getStartTime() {
        return this.startTime;
    }

    public Vouchers startTime(Instant startTime) {
        this.setStartTime(startTime);
        return this;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getEndTime() {
        return this.endTime;
    }

    public Vouchers endTime(Instant endTime) {
        this.setEndTime(endTime);
        return this;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public Vouchers createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public Vouchers createdBy(Long createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getUpdatedDate() {
        return this.updatedDate;
    }

    public Vouchers updatedDate(Instant updatedDate) {
        this.setUpdatedDate(updatedDate);
        return this;
    }

    public void setUpdatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Long getUpdatedBy() {
        return this.updatedBy;
    }

    public Vouchers updatedBy(Long updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Set<Payments> getPayments() {
        return this.payments;
    }

    public void setPayments(Set<Payments> payments) {
        if (this.payments != null) {
            this.payments.forEach(i -> i.setVouchers(null));
        }
        if (payments != null) {
            payments.forEach(i -> i.setVouchers(this));
        }
        this.payments = payments;
    }

    public Vouchers payments(Set<Payments> payments) {
        this.setPayments(payments);
        return this;
    }

    public Vouchers addPayments(Payments payments) {
        this.payments.add(payments);
        payments.setVouchers(this);
        return this;
    }

    public Vouchers removePayments(Payments payments) {
        this.payments.remove(payments);
        payments.setVouchers(null);
        return this;
    }

    public Set<Customers> getCustomers() {
        return this.customers;
    }

    public void setCustomers(Set<Customers> customers) {
        this.customers = customers;
    }

    public Vouchers customers(Set<Customers> customers) {
        this.setCustomers(customers);
        return this;
    }

    public Vouchers addCustomers(Customers customers) {
        this.customers.add(customers);
        customers.getVouchers().add(this);
        return this;
    }

    public Vouchers removeCustomers(Customers customers) {
        this.customers.remove(customers);
        customers.getVouchers().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vouchers)) {
            return false;
        }
        return id != null && id.equals(((Vouchers) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Vouchers{" +
            "id=" + getId() +
            ", idVoucher='" + getIdVoucher() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", percentPromotion=" + getPercentPromotion() +
            ", maxTotalMoneyPromotion=" + getMaxTotalMoneyPromotion() +
            ", active='" + getActive() + "'" +
            ", startTime='" + getStartTime() + "'" +
            ", endTime='" + getEndTime() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", createdBy=" + getCreatedBy() +
            ", updatedDate='" + getUpdatedDate() + "'" +
            ", updatedBy=" + getUpdatedBy() +
            "}";
    }
}
