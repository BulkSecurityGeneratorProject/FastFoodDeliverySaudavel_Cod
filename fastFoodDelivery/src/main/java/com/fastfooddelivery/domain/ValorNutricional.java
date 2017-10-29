package com.fastfooddelivery.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ValorNutricional.
 */
@Entity
@Table(name = "valor_nutricional")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ValorNutricional implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "caloria")
    private Integer caloria;

    @Column(name = "proteina")
    private Integer proteina;

    @Column(name = "carboidrato")
    private Integer carboidrato;

    @Column(name = "acucar")
    private Integer acucar;

    @Column(name = "gorduras_saturadas")
    private Integer gordurasSaturadas;

    @Column(name = "gorduras_totais")
    private Integer gordurasTotais;

    @Column(name = "sodio")
    private Integer sodio;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCaloria() {
        return caloria;
    }

    public ValorNutricional caloria(Integer caloria) {
        this.caloria = caloria;
        return this;
    }

    public void setCaloria(Integer caloria) {
        this.caloria = caloria;
    }

    public Integer getProteina() {
        return proteina;
    }

    public ValorNutricional proteina(Integer proteina) {
        this.proteina = proteina;
        return this;
    }

    public void setProteina(Integer proteina) {
        this.proteina = proteina;
    }

    public Integer getCarboidrato() {
        return carboidrato;
    }

    public ValorNutricional carboidrato(Integer carboidrato) {
        this.carboidrato = carboidrato;
        return this;
    }

    public void setCarboidrato(Integer carboidrato) {
        this.carboidrato = carboidrato;
    }

    public Integer getAcucar() {
        return acucar;
    }

    public ValorNutricional acucar(Integer acucar) {
        this.acucar = acucar;
        return this;
    }

    public void setAcucar(Integer acucar) {
        this.acucar = acucar;
    }

    public Integer getGordurasSaturadas() {
        return gordurasSaturadas;
    }

    public ValorNutricional gordurasSaturadas(Integer gordurasSaturadas) {
        this.gordurasSaturadas = gordurasSaturadas;
        return this;
    }

    public void setGordurasSaturadas(Integer gordurasSaturadas) {
        this.gordurasSaturadas = gordurasSaturadas;
    }

    public Integer getGordurasTotais() {
        return gordurasTotais;
    }

    public ValorNutricional gordurasTotais(Integer gordurasTotais) {
        this.gordurasTotais = gordurasTotais;
        return this;
    }

    public void setGordurasTotais(Integer gordurasTotais) {
        this.gordurasTotais = gordurasTotais;
    }

    public Integer getSodio() {
        return sodio;
    }

    public ValorNutricional sodio(Integer sodio) {
        this.sodio = sodio;
        return this;
    }

    public void setSodio(Integer sodio) {
        this.sodio = sodio;
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
        ValorNutricional valorNutricional = (ValorNutricional) o;
        if (valorNutricional.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), valorNutricional.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ValorNutricional{" +
            "id=" + getId() +
            ", caloria='" + getCaloria() + "'" +
            ", proteina='" + getProteina() + "'" +
            ", carboidrato='" + getCarboidrato() + "'" +
            ", acucar='" + getAcucar() + "'" +
            ", gordurasSaturadas='" + getGordurasSaturadas() + "'" +
            ", gordurasTotais='" + getGordurasTotais() + "'" +
            ", sodio='" + getSodio() + "'" +
            "}";
    }
}
