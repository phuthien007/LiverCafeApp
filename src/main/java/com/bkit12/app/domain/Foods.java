package com.bkit12.app.domain;

import com.bkit12.app.domain.enumeration.CommonStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Foods.
 */
@Entity
@Table(name = "foods")
public class Foods implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(min = 6)
    @Column(name = "id_food", nullable = false)
    private String idFood;

    @NotNull
    @Size(max = 3000)
    @Column(name = "nam", length = 3000, nullable = false)
    private String nam;

    @NotNull
    @Column(name = "price", nullable = false)
    private Double price;

    @Size(max = 5000)
    @Column(name = "description", length = 5000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private CommonStatus status;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "updated_date")
    private Instant updatedDate;

    @Column(name = "updated_by")
    private Long updatedBy;

    @ManyToMany(mappedBy = "foods")
    @JsonIgnoreProperties(value = { "drinks", "books", "foods", "orders" }, allowSetters = true)
    private Set<OrderDetails> orderDetails = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Foods id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdFood() {
        return this.idFood;
    }

    public Foods idFood(String idFood) {
        this.setIdFood(idFood);
        return this;
    }

    public void setIdFood(String idFood) {
        this.idFood = idFood;
    }

    public String getNam() {
        return this.nam;
    }

    public Foods nam(String nam) {
        this.setNam(nam);
        return this;
    }

    public void setNam(String nam) {
        this.nam = nam;
    }

    public Double getPrice() {
        return this.price;
    }

    public Foods price(Double price) {
        this.setPrice(price);
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return this.description;
    }

    public Foods description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CommonStatus getStatus() {
        return this.status;
    }

    public Foods status(CommonStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(CommonStatus status) {
        this.status = status;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public Foods createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public Foods createdBy(Long createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getUpdatedDate() {
        return this.updatedDate;
    }

    public Foods updatedDate(Instant updatedDate) {
        this.setUpdatedDate(updatedDate);
        return this;
    }

    public void setUpdatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Long getUpdatedBy() {
        return this.updatedBy;
    }

    public Foods updatedBy(Long updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Set<OrderDetails> getOrderDetails() {
        return this.orderDetails;
    }

    public void setOrderDetails(Set<OrderDetails> orderDetails) {
        if (this.orderDetails != null) {
            this.orderDetails.forEach(i -> i.removeFoods(this));
        }
        if (orderDetails != null) {
            orderDetails.forEach(i -> i.addFoods(this));
        }
        this.orderDetails = orderDetails;
    }

    public Foods orderDetails(Set<OrderDetails> orderDetails) {
        this.setOrderDetails(orderDetails);
        return this;
    }

    public Foods addOrderDetails(OrderDetails orderDetails) {
        this.orderDetails.add(orderDetails);
        orderDetails.getFoods().add(this);
        return this;
    }

    public Foods removeOrderDetails(OrderDetails orderDetails) {
        this.orderDetails.remove(orderDetails);
        orderDetails.getFoods().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Foods)) {
            return false;
        }
        return id != null && id.equals(((Foods) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Foods{" +
            "id=" + getId() +
            ", idFood='" + getIdFood() + "'" +
            ", nam='" + getNam() + "'" +
            ", price=" + getPrice() +
            ", description='" + getDescription() + "'" +
            ", status='" + getStatus() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", createdBy=" + getCreatedBy() +
            ", updatedDate='" + getUpdatedDate() + "'" +
            ", updatedBy=" + getUpdatedBy() +
            "}";
    }
}
