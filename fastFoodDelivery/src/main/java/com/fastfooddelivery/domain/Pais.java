package com.fastfooddelivery.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Pais.
 */
@Entity
@Table(name = "pais")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Pais implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "pais")
    private String pais;

//    @OneToMany(mappedBy = "pais")
//    @JsonIgnore
//    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
//    private Set<Cartao> cartaos = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPais() {
        return pais;
    }

    public Pais pais(String pais) {
        this.pais = pais;
        return this;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

//    public Set<Cartao> getCartaos() {
//        return cartaos;
//    }
//
//    public Pais cartaos(Set<Cartao> cartaos) {
//        this.cartaos = cartaos;
//        return this;
//    }
//
//    public void setCartaos(Set<Cartao> cartaos) {
//        this.cartaos = cartaos;
//    }
//
//    private void getCartaoTotais() {
//    	this.cartaos = cartaos;
//	}

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Pais pais = (Pais) o;
        if (pais.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pais.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Pais{" +
            "id=" + getId() +
            ", pais='" + getPais() + "'" +
            "}";
    }
}
