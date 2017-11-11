package com.fastfooddelivery.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
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
            "}";
    }

	public Set<Preparo> getAlimentos() {
		return null;
	}
}
