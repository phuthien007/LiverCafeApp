package com.bkit12.app.domain;

import com.bkit12.app.domain.enumeration.RoleTypes;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Roles.
 */
@Entity
@Table(name = "roles")
public class Roles implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private RoleTypes type;

    @Size(max = 5000)
    @Column(name = "description", length = 5000)
    private String description;

    @ManyToMany(mappedBy = "roles")
    @JsonIgnoreProperties(value = { "roles", "customer" }, allowSetters = true)
    private Set<Users> users = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Roles id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RoleTypes getType() {
        return this.type;
    }

    public Roles type(RoleTypes type) {
        this.setType(type);
        return this;
    }

    public void setType(RoleTypes type) {
        this.type = type;
    }

    public String getDescription() {
        return this.description;
    }

    public Roles description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Users> getUsers() {
        return this.users;
    }

    public void setUsers(Set<Users> users) {
        if (this.users != null) {
            this.users.forEach(i -> i.removeRoles(this));
        }
        if (users != null) {
            users.forEach(i -> i.addRoles(this));
        }
        this.users = users;
    }

    public Roles users(Set<Users> users) {
        this.setUsers(users);
        return this;
    }

    public Roles addUsers(Users users) {
        this.users.add(users);
        users.getRoles().add(this);
        return this;
    }

    public Roles removeUsers(Users users) {
        this.users.remove(users);
        users.getRoles().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Roles)) {
            return false;
        }
        return id != null && id.equals(((Roles) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Roles{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
