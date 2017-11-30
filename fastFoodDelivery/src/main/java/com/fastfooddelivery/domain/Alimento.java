package com.fastfooddelivery.domain;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

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
    private Set<Preparo> preparos;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "alimento_tempero",
               joinColumns = @JoinColumn(name="alimentos_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="temperos_id", referencedColumnName="id"))
    private Set<Tempero> temperos;

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

    public Alimento removePreparo(Preparo preparo) {
        this.preparos.remove(preparo);
        preparo.getAlimentos().remove(this);
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

    public Alimento removeTempero(Tempero tempero) {
        this.temperos.remove(tempero);
        tempero.getAlimentos().remove(this);
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
