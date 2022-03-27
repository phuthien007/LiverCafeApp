package com.bkit12.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A WorkingSpaces.
 */
@Entity
@Table(name = "working_spaces")
public class WorkingSpaces implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 3000)
    @Column(name = "name", length = 3000, nullable = false)
    private String name;

    @NotNull
    @Size(max = 5000)
    @Column(name = "location", length = 5000, nullable = false)
    private String location;

    @Column(name = "quantity_can_hold")
    private Long quantityCanHold;

    @Column(name = "price_per_hour")
    private Long pricePerHour;

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
        name = "rel_working_spaces__working_space_forms",
        joinColumns = @JoinColumn(name = "working_spaces_id"),
        inverseJoinColumns = @JoinColumn(name = "working_space_forms_id")
    )
    @JsonIgnoreProperties(value = { "order", "payment", "services", "customers", "workingSpaces" }, allowSetters = true)
    private Set<WorkingSpaceForms> workingSpaceForms = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public WorkingSpaces id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public WorkingSpaces name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return this.location;
    }

    public WorkingSpaces location(String location) {
        this.setLocation(location);
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Long getQuantityCanHold() {
        return this.quantityCanHold;
    }

    public WorkingSpaces quantityCanHold(Long quantityCanHold) {
        this.setQuantityCanHold(quantityCanHold);
        return this;
    }

    public void setQuantityCanHold(Long quantityCanHold) {
        this.quantityCanHold = quantityCanHold;
    }

    public Long getPricePerHour() {
        return this.pricePerHour;
    }

    public WorkingSpaces pricePerHour(Long pricePerHour) {
        this.setPricePerHour(pricePerHour);
        return this;
    }

    public void setPricePerHour(Long pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public WorkingSpaces createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public WorkingSpaces createdBy(Long createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getUpdatedDate() {
        return this.updatedDate;
    }

    public WorkingSpaces updatedDate(Instant updatedDate) {
        this.setUpdatedDate(updatedDate);
        return this;
    }

    public void setUpdatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Long getUpdatedBy() {
        return this.updatedBy;
    }

    public WorkingSpaces updatedBy(Long updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Set<WorkingSpaceForms> getWorkingSpaceForms() {
        return this.workingSpaceForms;
    }

    public void setWorkingSpaceForms(Set<WorkingSpaceForms> workingSpaceForms) {
        this.workingSpaceForms = workingSpaceForms;
    }

    public WorkingSpaces workingSpaceForms(Set<WorkingSpaceForms> workingSpaceForms) {
        this.setWorkingSpaceForms(workingSpaceForms);
        return this;
    }

    public WorkingSpaces addWorkingSpaceForms(WorkingSpaceForms workingSpaceForms) {
        this.workingSpaceForms.add(workingSpaceForms);
        workingSpaceForms.getWorkingSpaces().add(this);
        return this;
    }

    public WorkingSpaces removeWorkingSpaceForms(WorkingSpaceForms workingSpaceForms) {
        this.workingSpaceForms.remove(workingSpaceForms);
        workingSpaceForms.getWorkingSpaces().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WorkingSpaces)) {
            return false;
        }
        return id != null && id.equals(((WorkingSpaces) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WorkingSpaces{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", location='" + getLocation() + "'" +
            ", quantityCanHold=" + getQuantityCanHold() +
            ", pricePerHour=" + getPricePerHour() +
            ", createdDate='" + getCreatedDate() + "'" +
            ", createdBy=" + getCreatedBy() +
            ", updatedDate='" + getUpdatedDate() + "'" +
            ", updatedBy=" + getUpdatedBy() +
            "}";
    }
}
