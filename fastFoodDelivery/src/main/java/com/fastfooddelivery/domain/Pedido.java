package com.fastfooddelivery.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Pedido.
 */
@Entity
@Table(name = "pedido")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Pedido implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "horario_de_retirada")
    private Instant horarioDeRetirada;

    @Column(name = "valor_total")
    private String valorTotal;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "pedido_bebida",
               joinColumns = @JoinColumn(name="pedidos_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="bebidas_id", referencedColumnName="id"))
    private Set<Bebida> bebidas = new HashSet<>();

    @ManyToOne
    private FormaDeEntrega formaDeEntrega;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "pedido_alimento",
               joinColumns = @JoinColumn(name="pedidos_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="alimentos_id", referencedColumnName="id"))
    private Set<Alimento> alimentos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getHorarioDeRetirada() {
        return horarioDeRetirada;
    }

    public Pedido horarioDeRetirada(Instant horarioDeRetirada) {
        this.horarioDeRetirada = horarioDeRetirada;
        return this;
    }

    public void setHorarioDeRetirada(Instant horarioDeRetirada) {
        this.horarioDeRetirada = horarioDeRetirada;
    }

    public String getValorTotal() {
        return valorTotal;
    }

    public Pedido valorTotal(String valorTotal) {
        this.valorTotal = valorTotal;
        return this;
    }

    public void setValorTotal(String valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Set<Bebida> getBebidas() {
        return bebidas;
    }

    public Pedido bebidas(Set<Bebida> bebidas) {
        this.bebidas = bebidas;
        return this;
    }

    public void setBebidas(Set<Bebida> bebidas) {
        this.bebidas = bebidas;
    }

    public FormaDeEntrega getFormaDeEntrega() {
        return formaDeEntrega;
    }

    public Pedido formaDeEntrega(FormaDeEntrega formaDeEntrega) {
        this.formaDeEntrega = formaDeEntrega;
        return this;
    }

    public void setFormaDeEntrega(FormaDeEntrega formaDeEntrega) {
        this.formaDeEntrega = formaDeEntrega;
    }

    public Set<Alimento> getAlimentos() {
        return alimentos;
    }

    public Pedido alimentos(Set<Alimento> alimentos) {
        this.alimentos = alimentos;
        return this;
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
        Pedido pedido = (Pedido) o;
        if (pedido.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pedido.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Pedido{" +
            "id=" + getId() +
            ", horarioDeRetirada='" + getHorarioDeRetirada() + "'" +
            ", valorTotal='" + getValorTotal() + "'" +
            "}";
    }
}
