package com.fastfooddelivery.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Pessoa.
 */
@Entity
@Table(name = "pessoa")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Pessoa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "celular")
    private String celular;

    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    @Column(name = "sexo")
    private String sexo;

    @Column(name = "peso", precision=10, scale=2)
    private BigDecimal peso;

    @Column(name = "altura", precision=10, scale=2)
    private BigDecimal altura;

    @ManyToOne
    private Endereco endereco;

    @ManyToOne
    private Cartao cartao;

    @OneToMany(mappedBy = "pessoa")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Pedido> pedidos = new HashSet<>();

    @ManyToOne
    private Cliente cliente;
    
    @OneToOne
    @MapsId("id")
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCelular() {
        return celular;
    }

    public Pessoa celular(String celular) {
        this.celular = celular;
        return this;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public Pessoa dataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
        return this;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getSexo() {
        return sexo;
    }

    public Pessoa sexo(String sexo) {
        this.sexo = sexo;
        return this;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public BigDecimal getPeso() {
        return peso;
    }

    public Pessoa peso(BigDecimal peso) {
        this.peso = peso;
        return this;
    }

    public void setPeso(BigDecimal peso) {
        this.peso = peso;
    }

    public BigDecimal getAltura() {
        return altura;
    }

    public Pessoa altura(BigDecimal altura) {
        this.altura = altura;
        return this;
    }

    public void setAltura(BigDecimal altura) {
        this.altura = altura;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public Pessoa endereco(Endereco endereco) {
        this.endereco = endereco;
        return this;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Cartao getCartao() {
        return cartao;
    }

    public Pessoa cartao(Cartao cartao) {
        this.cartao = cartao;
        return this;
    }

    public void setCartao(Cartao cartao) {
        this.cartao = cartao;
    }

    public Set<Pedido> getPedidos() {
        return pedidos;
    }

    public Pessoa pedidos(Set<Pedido> pedidos) {
        this.pedidos = pedidos;
        return this;
    }

    public Pessoa addPedido(Pedido pedido) {
        this.pedidos.add(pedido);
        pedido.setPessoa(this);
        return this;
    }

    public Pessoa removePedido(Pedido pedido) {
        this.pedidos.remove(pedido);
        pedido.setPessoa(null);
        return this;
    }

    public void setPedidos(Set<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Pessoa cliente(Cliente cliente) {
        this.cliente = cliente;
        return this;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    
    public User getUser() {
		return user;
	}
    
    public void setUser(User user) {
		this.user = user;
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
        Pessoa pessoa = (Pessoa) o;
        if (pessoa.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pessoa.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Pessoa{" +
            "id=" + getId() +
            ", celular='" + getCelular() + "'" +
            ", dataNascimento='" + getDataNascimento() + "'" +
            ", sexo='" + getSexo() + "'" +
            ", peso='" + getPeso() + "'" +
            ", altura='" + getAltura() + "'" +
            "}";
    }
}
