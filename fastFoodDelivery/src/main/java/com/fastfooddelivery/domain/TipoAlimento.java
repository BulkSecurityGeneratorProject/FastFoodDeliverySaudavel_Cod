package com.fastfooddelivery.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A TipoAlimento.
 */
@Entity
@Table(name = "tipo_alimento")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TipoAlimento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "tipo_alimento")
    private String tipoAlimento;
    
    @ManyToMany(mappedBy = "tipoAlimentos", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REMOVE, CascadeType.REFRESH})
    private Set<Refeicao> refeicoes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoAlimento() {
        return tipoAlimento;
    }

    public TipoAlimento tipoAlimento(String tipoAlimento) {
        this.tipoAlimento = tipoAlimento;
        return this;
    }

    public void setTipoAlimento(String tipoAlimento) {
        this.tipoAlimento = tipoAlimento;
    }
    
    public Set<Refeicao> getRefeicoes() {
		return refeicoes;
	}
    
    public void setRefeicoes(Set<Refeicao> refeicoes) {
		this.refeicoes = refeicoes;
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
        TipoAlimento tipoAlimento = (TipoAlimento) o;
        if (tipoAlimento.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tipoAlimento.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TipoAlimento{" +
            "id=" + getId() +
            ", tipoAlimento='" + getTipoAlimento() + "'" +
            "}";
    }
}
