package com.fastfooddelivery.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A Doce.
 */
@Entity
@Table(name = "doce")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Doce implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "doce")
    private String doce;
    
    @ManyToMany(mappedBy = "doces", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REMOVE, CascadeType.REFRESH})
    private Set<Bebida> bebidas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDoce() {
        return doce;
    }

    public Doce doce(String doce) {
        this.doce = doce;
        return this;
    }

    public void setDoce(String doce) {
        this.doce = doce;
    }
    
    public Set<Bebida> getBebidas() {
		return bebidas;
	}
    
    public void setBebidas(Set<Bebida> bebidas) {
		this.bebidas = bebidas;
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
        Doce doce = (Doce) o;
        if (doce.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), doce.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Doce{" +
            "id=" + getId() +
            ", doce='" + getDoce() + "'" +
            "}";
    }
}
