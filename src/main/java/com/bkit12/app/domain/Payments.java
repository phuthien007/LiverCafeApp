package com.bkit12.app.domain;

import com.bkit12.app.domain.enumeration.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;

/**
 * A Payments.
 */
@Entity
@Table(name = "payments")
public class Payments implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "total_price")
    private Double totalPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private PaymentStatus status;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "updated_date")
    private Instant updatedDate;

    @Column(name = "updated_by")
    private Long updatedBy;

    @JsonIgnoreProperties(value = { "order", "payment", "services", "customers", "workingSpaces" }, allowSetters = true)
    @OneToOne(mappedBy = "payment")
    private WorkingSpaceForms workingSpaceForm;

    @JsonIgnoreProperties(value = { "payment", "orderDetails", "workingSpaceForm", "customers" }, allowSetters = true)
    @OneToOne(mappedBy = "payment")
    private Orders order;

    @ManyToOne
    @JsonIgnoreProperties(value = { "payments", "customers" }, allowSetters = true)
    private Vouchers vouchers;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Payments id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getTotalPrice() {
        return this.totalPrice;
    }

    public Payments totalPrice(Double totalPrice) {
        this.setTotalPrice(totalPrice);
        return this;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public PaymentStatus getStatus() {
        return this.status;
    }

    public Payments status(PaymentStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public Payments createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public Payments createdBy(Long createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getUpdatedDate() {
        return this.updatedDate;
    }

    public Payments updatedDate(Instant updatedDate) {
        this.setUpdatedDate(updatedDate);
        return this;
    }

    public void setUpdatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Long getUpdatedBy() {
        return this.updatedBy;
    }

    public Payments updatedBy(Long updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public WorkingSpaceForms getWorkingSpaceForm() {
        return this.workingSpaceForm;
    }

    public void setWorkingSpaceForm(WorkingSpaceForms workingSpaceForms) {
        if (this.workingSpaceForm != null) {
            this.workingSpaceForm.setPayment(null);
        }
        if (workingSpaceForms != null) {
            workingSpaceForms.setPayment(this);
        }
        this.workingSpaceForm = workingSpaceForms;
    }

    public Payments workingSpaceForm(WorkingSpaceForms workingSpaceForms) {
        this.setWorkingSpaceForm(workingSpaceForms);
        return this;
    }

    public Orders getOrder() {
        return this.order;
    }

    public void setOrder(Orders orders) {
        if (this.order != null) {
            this.order.setPayment(null);
        }
        if (orders != null) {
            orders.setPayment(this);
        }
        this.order = orders;
    }

    public Payments order(Orders orders) {
        this.setOrder(orders);
        return this;
    }

    public Vouchers getVouchers() {
        return this.vouchers;
    }

    public void setVouchers(Vouchers vouchers) {
        this.vouchers = vouchers;
    }

    public Payments vouchers(Vouchers vouchers) {
        this.setVouchers(vouchers);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Payments)) {
            return false;
        }
        return id != null && id.equals(((Payments) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Payments{" +
            "id=" + getId() +
            ", totalPrice=" + getTotalPrice() +
            ", status='" + getStatus() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", createdBy=" + getCreatedBy() +
            ", updatedDate='" + getUpdatedDate() + "'" +
            ", updatedBy=" + getUpdatedBy() +
            "}";
    }
}
