package com.fastfooddelivery.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Cartao.
 */
@Entity
@Table(name = "cartao")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Cartao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "numero")
    private Long numero;

    @Column(name = "data_vencimento")
    private LocalDate dataVencimento;

    @Column(name = "cvv")
    private Integer cvv;

    @Column(name = "cartao_col")
    private String cartaoCol;
    
    @ManyToOne
    private Pais pais;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumero() {
        return numero;
    }

    public Cartao numero(Long numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }

    public Cartao dataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
        return this;
    }

    public void setDataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public Integer getCvv() {
        return cvv;
    }

    public Cartao cvv(Integer cvv) {
        this.cvv = cvv;
        return this;
    }

    public void setCvv(Integer cvv) {
        this.cvv = cvv;
    }

    public String getCartaoCol() {
        return cartaoCol;
    }

    public Cartao cartaoCol(String cartaoCol) {
        this.cartaoCol = cartaoCol;
        return this;
    }

    public void setCartaoCol(String cartaoCol) {
        this.cartaoCol = cartaoCol;
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
        Cartao cartao = (Cartao) o;
        if (cartao.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cartao.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Cartao{" +
            "id=" + getId() +
            ", numero='" + getNumero() + "'" +
            ", dataVencimento='" + getDataVencimento() + "'" +
            ", cvv='" + getCvv() + "'" +
            ", cartaoCol='" + getCartaoCol() + "'" +
            "}";
    }
    
    public Pais getPais() {
		return pais;
	}
    
    public void setPais(Pais pais) {
		this.pais = pais;
	}
    
}
