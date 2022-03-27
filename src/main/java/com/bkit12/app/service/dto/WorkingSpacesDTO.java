package com.bkit12.app.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.bkit12.app.domain.WorkingSpaces} entity.
 */
public class WorkingSpacesDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 3000)
    private String name;

    @NotNull
    @Size(max = 5000)
    private String location;

    private Long quantityCanHold;

    private Long pricePerHour;

    private Instant createdDate;

    private Long createdBy;

    private Instant updatedDate;

    private Long updatedBy;

    private Set<WorkingSpaceFormsDTO> workingSpaceForms = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Long getQuantityCanHold() {
        return quantityCanHold;
    }

    public void setQuantityCanHold(Long quantityCanHold) {
        this.quantityCanHold = quantityCanHold;
    }

    public Long getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(Long pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Set<WorkingSpaceFormsDTO> getWorkingSpaceForms() {
        return workingSpaceForms;
    }

    public void setWorkingSpaceForms(Set<WorkingSpaceFormsDTO> workingSpaceForms) {
        this.workingSpaceForms = workingSpaceForms;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WorkingSpacesDTO)) {
            return false;
        }

        WorkingSpacesDTO workingSpacesDTO = (WorkingSpacesDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, workingSpacesDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WorkingSpacesDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", location='" + getLocation() + "'" +
            ", quantityCanHold=" + getQuantityCanHold() +
            ", pricePerHour=" + getPricePerHour() +
            ", createdDate='" + getCreatedDate() + "'" +
            ", createdBy=" + getCreatedBy() +
            ", updatedDate='" + getUpdatedDate() + "'" +
            ", updatedBy=" + getUpdatedBy() +
            ", workingSpaceForms=" + getWorkingSpaceForms() +
            "}";
    }
}
