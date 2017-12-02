package com.fastfooddelivery.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A Preparo.
 */
@Entity
@Table(name = "preparo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Preparo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "preparo")
    private String preparo;

    @Column(name = "tempo_preparo", precision=10, scale=2)
    private BigDecimal tempoPreparo;
    
    @ManyToMany(mappedBy = "preparos", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REMOVE, CascadeType.REFRESH})
    private Set<Alimento> alimentos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPreparo() {
        return preparo;
    }

    public Preparo preparo(String preparo) {
        this.preparo = preparo;
        return this;
    }

    public void setPreparo(String preparo) {
        this.preparo = preparo;
    }

    public BigDecimal getTempoPreparo() {
        return tempoPreparo;
    }

    public Preparo tempoPreparo(BigDecimal tempoPreparo) {
        this.tempoPreparo = tempoPreparo;
        return this;
    }

    public void setTempoPreparo(BigDecimal tempoPreparo) {
        this.tempoPreparo = tempoPreparo;
    }
    
    public Set<Alimento> getAlimentos() {
		return alimentos;
	}
    
    public void setAlimentos(Set<Alimento> alimentos) {
		this.alimentos = alimentos;
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
        Preparo preparo = (Preparo) o;
        if (preparo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), preparo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Preparo{" +
            "id=" + getId() +
            ", preparo='" + getPreparo() + "'" +
            ", tempoPreparo='" + getTempoPreparo() + "'" +
            "}";
    }
}
