package com.fastfooddelivery.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Bebida.
 */
@Entity
@Table(name = "bebida")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Bebida implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "bebida")
    private String bebida;

    // atencao: alterado para eager para acelerar o desenvolvimeto
    // o ideal eh refatorar para recuperar os tipos de alimento de uma refeicao atraves de uma consulta especifica
    @ManyToMany(fetch = FetchType.EAGER)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "bebida_doce",
               joinColumns = @JoinColumn(name="bebidas_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="doces_id", referencedColumnName="id"))
    private Set<Doce> doces = new HashSet<>();

    @OneToOne
    @JoinColumn(unique = true)
    private ValorNutricional valorNutricional;

    @ManyToOne
    private ValorRefeicao valorRefeicao;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBebida() {
        return bebida;
    }

    public Bebida bebida(String bebida) {
        this.bebida = bebida;
        return this;
    }

    public void setBebida(String bebida) {
        this.bebida = bebida;
    }

    public Set<Doce> getDoces() {
        return doces;
    }

    public Bebida doces(Set<Doce> doces) {
        this.doces = doces;
        return this;
    }

    public void setDoces(Set<Doce> doces) {
        this.doces = doces;
    }

    public ValorNutricional getValorNutricional() {
        return valorNutricional;
    }

    public Bebida valorNutricional(ValorNutricional valorNutricional) {
        this.valorNutricional = valorNutricional;
        return this;
    }

    public void setValorNutricional(ValorNutricional valorNutricional) {
        this.valorNutricional = valorNutricional;
    }

    public ValorRefeicao getValorRefeicao() {
        return valorRefeicao;
    }

    public Bebida valorRefeicao(ValorRefeicao valorRefeicao) {
        this.valorRefeicao = valorRefeicao;
        return this;
    }

    public void setValorRefeicao(ValorRefeicao valorRefeicao) {
        this.valorRefeicao = valorRefeicao;
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
        Bebida bebida = (Bebida) o;
        if (bebida.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bebida.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Bebida{" +
            "id=" + getId() +
            ", bebida='" + getBebida() + "'" +
            "}";
    }
}
