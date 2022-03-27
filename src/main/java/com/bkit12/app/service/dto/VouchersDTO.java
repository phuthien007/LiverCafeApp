package com.bkit12.app.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.bkit12.app.domain.Vouchers} entity.
 */
public class VouchersDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 10)
    private String idVoucher;

    @Size(max = 5000)
    private String name;

    @Size(max = 5000)
    private String description;

    private Double percentPromotion;

    private Double maxTotalMoneyPromotion;

    private Boolean active;

    private Instant startTime;

    private Instant endTime;

    private Instant createdDate;

    private Long createdBy;

    private Instant updatedDate;

    private Long updatedBy;

    private Set<CustomersDTO> customers = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdVoucher() {
        return idVoucher;
    }

    public void setIdVoucher(String idVoucher) {
        this.idVoucher = idVoucher;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPercentPromotion() {
        return percentPromotion;
    }

    public void setPercentPromotion(Double percentPromotion) {
        this.percentPromotion = percentPromotion;
    }

    public Double getMaxTotalMoneyPromotion() {
        return maxTotalMoneyPromotion;
    }

    public void setMaxTotalMoneyPromotion(Double maxTotalMoneyPromotion) {
        this.maxTotalMoneyPromotion = maxTotalMoneyPromotion;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getEndTime() {
        return endTime;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
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

    public Set<CustomersDTO> getCustomers() {
        return customers;
    }

    public void setCustomers(Set<CustomersDTO> customers) {
        this.customers = customers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VouchersDTO)) {
            return false;
        }

        VouchersDTO vouchersDTO = (VouchersDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, vouchersDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VouchersDTO{" +
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
            ", customers=" + getCustomers() +
            "}";
    }
}
