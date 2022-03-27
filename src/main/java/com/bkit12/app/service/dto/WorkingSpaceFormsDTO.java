package com.bkit12.app.service.dto;

import com.bkit12.app.domain.enumeration.WorkingSpaceStatus;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.bkit12.app.domain.WorkingSpaceForms} entity.
 */
public class WorkingSpaceFormsDTO implements Serializable {

    private Long id;

    private Instant timeStart;

    private Instant timeEnd;

    private Long priceTotal;

    @Size(max = 5000)
    private String nameEvent;

    @Size(max = 5000)
    private String note;

    private Long quantityPeople;

    private WorkingSpaceStatus status;

    private Double percentDeposit;

    private Instant createdDate;

    private Long createdBy;

    private Instant updatedDate;

    private Long updatedBy;

    private OrdersDTO order;

    private PaymentsDTO payment;

    private Set<ServicesDTO> services = new HashSet<>();

    private CustomersDTO customers;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(Instant timeStart) {
        this.timeStart = timeStart;
    }

    public Instant getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(Instant timeEnd) {
        this.timeEnd = timeEnd;
    }

    public Long getPriceTotal() {
        return priceTotal;
    }

    public void setPriceTotal(Long priceTotal) {
        this.priceTotal = priceTotal;
    }

    public String getNameEvent() {
        return nameEvent;
    }

    public void setNameEvent(String nameEvent) {
        this.nameEvent = nameEvent;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Long getQuantityPeople() {
        return quantityPeople;
    }

    public void setQuantityPeople(Long quantityPeople) {
        this.quantityPeople = quantityPeople;
    }

    public WorkingSpaceStatus getStatus() {
        return status;
    }

    public void setStatus(WorkingSpaceStatus status) {
        this.status = status;
    }

    public Double getPercentDeposit() {
        return percentDeposit;
    }

    public void setPercentDeposit(Double percentDeposit) {
        this.percentDeposit = percentDeposit;
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

    public OrdersDTO getOrder() {
        return order;
    }

    public void setOrder(OrdersDTO order) {
        this.order = order;
    }

    public PaymentsDTO getPayment() {
        return payment;
    }

    public void setPayment(PaymentsDTO payment) {
        this.payment = payment;
    }

    public Set<ServicesDTO> getServices() {
        return services;
    }

    public void setServices(Set<ServicesDTO> services) {
        this.services = services;
    }

    public CustomersDTO getCustomers() {
        return customers;
    }

    public void setCustomers(CustomersDTO customers) {
        this.customers = customers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WorkingSpaceFormsDTO)) {
            return false;
        }

        WorkingSpaceFormsDTO workingSpaceFormsDTO = (WorkingSpaceFormsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, workingSpaceFormsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WorkingSpaceFormsDTO{" +
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
            ", order=" + getOrder() +
            ", payment=" + getPayment() +
            ", services=" + getServices() +
            ", customers=" + getCustomers() +
            "}";
    }
}
