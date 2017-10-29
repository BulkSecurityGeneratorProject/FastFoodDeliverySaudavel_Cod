package com.fastfooddelivery.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A FormaDeEntrega.
 */
@Entity
@Table(name = "forma_de_entrega")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FormaDeEntrega implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "forma_de_entrega")
    private Integer formaDeEntrega;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getFormaDeEntrega() {
        return formaDeEntrega;
    }

    public FormaDeEntrega formaDeEntrega(Integer formaDeEntrega) {
        this.formaDeEntrega = formaDeEntrega;
        return this;
    }

    public void setFormaDeEntrega(Integer formaDeEntrega) {
        this.formaDeEntrega = formaDeEntrega;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FormaDeEntrega formaDeEntrega = (FormaDeEntrega) o;
        if (formaDeEntrega.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), formaDeEntrega.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FormaDeEntrega{" +
            "id=" + getId() +
            ", formaDeEntrega='" + getFormaDeEntrega() + "'" +
            "}";
    }
}
