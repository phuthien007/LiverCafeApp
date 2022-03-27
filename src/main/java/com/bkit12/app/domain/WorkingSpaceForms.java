package com.bkit12.app.domain;

import com.bkit12.app.domain.enumeration.WorkingSpaceStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A WorkingSpaceForms.
 */
@Entity
@Table(name = "working_space_forms")
public class WorkingSpaceForms implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "time_start")
    private Instant timeStart;

    @Column(name = "time_end")
    private Instant timeEnd;

    @Column(name = "price_total")
    private Long priceTotal;

    @Size(max = 5000)
    @Column(name = "name_event", length = 5000)
    private String nameEvent;

    @Size(max = 5000)
    @Column(name = "note", length = 5000)
    private String note;

    @Column(name = "quantity_people")
    private Long quantityPeople;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private WorkingSpaceStatus status;

    @Column(name = "percent_deposit")
    private Double percentDeposit;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "updated_date")
    private Instant updatedDate;

    @Column(name = "updated_by")
    private Long updatedBy;

    @JsonIgnoreProperties(value = { "payment", "orderDetails", "workingSpaceForm", "customers" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Orders order;

    @JsonIgnoreProperties(value = { "workingSpaceForm", "order", "vouchers" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Payments payment;

    @ManyToMany
    @JoinTable(
        name = "rel_working_space_forms__services",
        joinColumns = @JoinColumn(name = "working_space_forms_id"),
        inverseJoinColumns = @JoinColumn(name = "services_id")
    )
    @JsonIgnoreProperties(value = { "workingSpaceForms" }, allowSetters = true)
    private Set<Services> services = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "user", "orders", "workingSpaceForms", "vouchers" }, allowSetters = true)
    private Customers customers;

    @ManyToMany(mappedBy = "workingSpaceForms")
    @JsonIgnoreProperties(value = { "workingSpaceForms" }, allowSetters = true)
    private Set<WorkingSpaces> workingSpaces = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public WorkingSpaceForms id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getTimeStart() {
        return this.timeStart;
    }

    public WorkingSpaceForms timeStart(Instant timeStart) {
        this.setTimeStart(timeStart);
        return this;
    }

    public void setTimeStart(Instant timeStart) {
        this.timeStart = timeStart;
    }

    public Instant getTimeEnd() {
        return this.timeEnd;
    }

    public WorkingSpaceForms timeEnd(Instant timeEnd) {
        this.setTimeEnd(timeEnd);
        return this;
    }

    public void setTimeEnd(Instant timeEnd) {
        this.timeEnd = timeEnd;
    }

    public Long getPriceTotal() {
        return this.priceTotal;
    }

    public WorkingSpaceForms priceTotal(Long priceTotal) {
        this.setPriceTotal(priceTotal);
        return this;
    }

    public void setPriceTotal(Long priceTotal) {
        this.priceTotal = priceTotal;
    }

    public String getNameEvent() {
        return this.nameEvent;
    }

    public WorkingSpaceForms nameEvent(String nameEvent) {
        this.setNameEvent(nameEvent);
        return this;
    }

    public void setNameEvent(String nameEvent) {
        this.nameEvent = nameEvent;
    }

    public String getNote() {
        return this.note;
    }

    public WorkingSpaceForms note(String note) {
        this.setNote(note);
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Long getQuantityPeople() {
        return this.quantityPeople;
    }

    public WorkingSpaceForms quantityPeople(Long quantityPeople) {
        this.setQuantityPeople(quantityPeople);
        return this;
    }

    public void setQuantityPeople(Long quantityPeople) {
        this.quantityPeople = quantityPeople;
    }

    public WorkingSpaceStatus getStatus() {
        return this.status;
    }

    public WorkingSpaceForms status(WorkingSpaceStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(WorkingSpaceStatus status) {
        this.status = status;
    }

    public Double getPercentDeposit() {
        return this.percentDeposit;
    }

    public WorkingSpaceForms percentDeposit(Double percentDeposit) {
        this.setPercentDeposit(percentDeposit);
        return this;
    }

    public void setPercentDeposit(Double percentDeposit) {
        this.percentDeposit = percentDeposit;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public WorkingSpaceForms createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public WorkingSpaceForms createdBy(Long createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getUpdatedDate() {
        return this.updatedDate;
    }

    public WorkingSpaceForms updatedDate(Instant updatedDate) {
        this.setUpdatedDate(updatedDate);
        return this;
    }

    public void setUpdatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Long getUpdatedBy() {
        return this.updatedBy;
    }

    public WorkingSpaceForms updatedBy(Long updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Orders getOrder() {
        return this.order;
    }

    public void setOrder(Orders orders) {
        this.order = orders;
    }

    public WorkingSpaceForms order(Orders orders) {
        this.setOrder(orders);
        return this;
    }

    public Payments getPayment() {
        return this.payment;
    }

    public void setPayment(Payments payments) {
        this.payment = payments;
    }

    public WorkingSpaceForms payment(Payments payments) {
        this.setPayment(payments);
        return this;
    }

    public Set<Services> getServices() {
        return this.services;
    }

    public void setServices(Set<Services> services) {
        this.services = services;
    }

    public WorkingSpaceForms services(Set<Services> services) {
        this.setServices(services);
        return this;
    }

    public WorkingSpaceForms addServices(Services services) {
        this.services.add(services);
        services.getWorkingSpaceForms().add(this);
        return this;
    }

    public WorkingSpaceForms removeServices(Services services) {
        this.services.remove(services);
        services.getWorkingSpaceForms().remove(this);
        return this;
    }

    public Customers getCustomers() {
        return this.customers;
    }

    public void setCustomers(Customers customers) {
        this.customers = customers;
    }

    public WorkingSpaceForms customers(Customers customers) {
        this.setCustomers(customers);
        return this;
    }

    public Set<WorkingSpaces> getWorkingSpaces() {
        return this.workingSpaces;
    }

    public void setWorkingSpaces(Set<WorkingSpaces> workingSpaces) {
        if (this.workingSpaces != null) {
            this.workingSpaces.forEach(i -> i.removeWorkingSpaceForms(this));
        }
        if (workingSpaces != null) {
            workingSpaces.forEach(i -> i.addWorkingSpaceForms(this));
        }
        this.workingSpaces = workingSpaces;
    }

    public WorkingSpaceForms workingSpaces(Set<WorkingSpaces> workingSpaces) {
        this.setWorkingSpaces(workingSpaces);
        return this;
    }

    public WorkingSpaceForms addWorkingSpaces(WorkingSpaces workingSpaces) {
        this.workingSpaces.add(workingSpaces);
        workingSpaces.getWorkingSpaceForms().add(this);
        return this;
    }

    public WorkingSpaceForms removeWorkingSpaces(WorkingSpaces workingSpaces) {
        this.workingSpaces.remove(workingSpaces);
        workingSpaces.getWorkingSpaceForms().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WorkingSpaceForms)) {
            return false;
        }
        return id != null && id.equals(((WorkingSpaceForms) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WorkingSpaceForms{" +
            "id=" + getId() +
            ", timeStart='" + getTimeStart() + "'" +
            ", timeEnd='" + getTimeEnd() + "'" +
            ", priceTotal=" + getPriceTotal() +
            ", nameEvent='" + getNameEvent() + "'" +
            ", note='" + getNote() + "'" +
            ", quantityPeople=" + getQuantityPeople() +
            ", status='" + getStatus() + "'" +
            ", percentDeposit=" + getPercentDeposit() +
            ", createdDate='" + getCreatedDate() + "'" +
            ", createdBy=" + getCreatedBy() +
            ", updatedDate='" + getUpdatedDate() + "'" +
            ", updatedBy=" + getUpdatedBy() +
            "}";
    }
}
