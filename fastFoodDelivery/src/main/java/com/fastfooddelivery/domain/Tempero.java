package com.fastfooddelivery.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

/**
 * A Tempero.
 */
@Entity
@Table(name = "tempero")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Tempero implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "tempero")
    private String tempero;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTempero() {
        return tempero;
    }

    public Tempero tempero(String tempero) {
        this.tempero = tempero;
        return this;
    }

    public void setTempero(String tempero) {
        this.tempero = tempero;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Tempero tempero = (Tempero) o;
        if (tempero.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tempero.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Tempero{" +
            "id=" + getId() +
            ", tempero='" + getTempero() + "'" +
            "}";
    }

	public Set<Preparo> getAlimentos() {
		return null;
	}
}
