package com.fastfooddelivery.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Alimento.
 */
@Entity
@Table(name = "alimento")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Alimento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "alimento_col")
    private String alimentoCol;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "alimento_preparo",
               joinColumns = @JoinColumn(name="alimentos_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="preparos_id", referencedColumnName="id"))
    private Set<Preparo> preparos = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "alimento_tempero",
               joinColumns = @JoinColumn(name="alimentos_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="temperos_id", referencedColumnName="id"))
    private Set<Tempero> temperos = new HashSet<>();

    @OneToOne
    @JoinColumn(unique = true)
    private ValorNutricional valorNutricional;

    @ManyToOne
    private TipoAlimento tipoAlimento;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAlimentoCol() {
        return alimentoCol;
    }

    public Alimento alimentoCol(String alimentoCol) {
        this.alimentoCol = alimentoCol;
        return this;
    }

    public void setAlimentoCol(String alimentoCol) {
        this.alimentoCol = alimentoCol;
    }

    public Set<Preparo> getPreparos() {
        return preparos;
    }

    public Alimento preparos(Set<Preparo> preparos) {
        this.preparos = preparos;
        return this;
    }

    public void setPreparos(Set<Preparo> preparos) {
        this.preparos = preparos;
    }

    public Set<Tempero> getTemperos() {
        return temperos;
    }

    public Alimento temperos(Set<Tempero> temperos) {
        this.temperos = temperos;
        return this;
    }

    public void setTemperos(Set<Tempero> temperos) {
        this.temperos = temperos;
    }

    public ValorNutricional getValorNutricional() {
        return valorNutricional;
    }

    public Alimento valorNutricional(ValorNutricional valorNutricional) {
        this.valorNutricional = valorNutricional;
        return this;
    }

    public void setValorNutricional(ValorNutricional valorNutricional) {
        this.valorNutricional = valorNutricional;
    }

    public TipoAlimento getTipoAlimento() {
        return tipoAlimento;
    }

    public Alimento tipoAlimento(TipoAlimento tipoAlimento) {
        this.tipoAlimento = tipoAlimento;
        return this;
    }

    public void setTipoAlimento(TipoAlimento tipoAlimento) {
        this.tipoAlimento = tipoAlimento;
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
        Alimento alimento = (Alimento) o;
        if (alimento.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), alimento.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Alimento{" +
            "id=" + getId() +
            ", alimentoCol='" + getAlimentoCol() + "'" +
            "}";
    }
}
