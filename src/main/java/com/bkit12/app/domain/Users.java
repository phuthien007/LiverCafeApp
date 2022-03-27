package com.bkit12.app.domain;

import com.bkit12.app.domain.enumeration.UserStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Users.
 */
@Entity
@Table(name = "users")
public class Users implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "username", nullable = false)
    private String username;

    @NotNull
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "forgot_password_token")
    private String forgotPasswordToken;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "failed_counter")
    private Long failedCounter;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private UserStatus status;

    @Column(name = "updated_by")
    private Long updatedBy;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "updated_date")
    private Instant updatedDate;

    @ManyToMany
    @JoinTable(name = "rel_users__roles", joinColumns = @JoinColumn(name = "users_id"), inverseJoinColumns = @JoinColumn(name = "roles_id"))
    @JsonIgnoreProperties(value = { "users" }, allowSetters = true)
    private Set<Roles> roles = new HashSet<>();

    @JsonIgnoreProperties(value = { "user", "orders", "workingSpaceForms", "vouchers" }, allowSetters = true)
    @OneToOne(mappedBy = "user")
    private Customers customer;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Users id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public Users username(String username) {
        this.setUsername(username);
        return this;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public Users password(String password) {
        this.setPassword(password);
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getForgotPasswordToken() {
        return this.forgotPasswordToken;
    }

    public Users forgotPasswordToken(String forgotPasswordToken) {
        this.setForgotPasswordToken(forgotPasswordToken);
        return this;
    }

    public void setForgotPasswordToken(String forgotPasswordToken) {
        this.forgotPasswordToken = forgotPasswordToken;
    }

    public String getEmail() {
        return this.email;
    }

    public Users email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getFailedCounter() {
        return this.failedCounter;
    }

    public Users failedCounter(Long failedCounter) {
        this.setFailedCounter(failedCounter);
        return this;
    }

    public void setFailedCounter(Long failedCounter) {
        this.failedCounter = failedCounter;
    }

    public UserStatus getStatus() {
        return this.status;
    }

    public Users status(UserStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public Long getUpdatedBy() {
        return this.updatedBy;
    }

    public Users updatedBy(Long updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public Users createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getUpdatedDate() {
        return this.updatedDate;
    }

    public Users updatedDate(Instant updatedDate) {
        this.setUpdatedDate(updatedDate);
        return this;
    }

    public void setUpdatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Set<Roles> getRoles() {
        return this.roles;
    }

    public void setRoles(Set<Roles> roles) {
        this.roles = roles;
    }

    public Users roles(Set<Roles> roles) {
        this.setRoles(roles);
        return this;
    }

    public Users addRoles(Roles roles) {
        this.roles.add(roles);
        roles.getUsers().add(this);
        return this;
    }

    public Users removeRoles(Roles roles) {
        this.roles.remove(roles);
        roles.getUsers().remove(this);
        return this;
    }

    public Customers getCustomer() {
        return this.customer;
    }

    public void setCustomer(Customers customers) {
        if (this.customer != null) {
            this.customer.setUser(null);
        }
        if (customers != null) {
            customers.setUser(this);
        }
        this.customer = customers;
    }

    public Users customer(Customers customers) {
        this.setCustomer(customers);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Users)) {
            return false;
        }
        return id != null && id.equals(((Users) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Users{" +
            "id=" + getId() +
            ", username='" + getUsername() + "'" +
            ", password='" + getPassword() + "'" +
            ", forgotPasswordToken='" + getForgotPasswordToken() + "'" +
            ", email='" + getEmail() + "'" +
            ", failedCounter=" + getFailedCounter() +
            ", status='" + getStatus() + "'" +
            ", updatedBy=" + getUpdatedBy() +
            ", createdDate='" + getCreatedDate() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            "}";
    }
}
