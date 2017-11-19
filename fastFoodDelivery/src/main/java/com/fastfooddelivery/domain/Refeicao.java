package com.fastfooddelivery.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Refeicao.
 */
@Entity
@Table(name = "refeicao")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Refeicao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "refeicao")
    private String refeicao;

    @ManyToOne
    private ValorRefeicao valorRefeicao;

    // atencao: alterado para eager para acelerar o desenvolvimeto
    // o ideal eh refatorar para recuperar os tipos de alimento de uma refeicao atraves de uma consulta especifica
    @ManyToMany(fetch = FetchType.EAGER)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "refeicao_tipo_alimento",
               joinColumns = @JoinColumn(name="refeicaos_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="tipo_alimentos_id", referencedColumnName="id"))
    private Set<TipoAlimento> tipoAlimentos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRefeicao() {
        return refeicao;
    }

    public Refeicao refeicao(String refeicao) {
        this.refeicao = refeicao;
        return this;
    }

    public void setRefeicao(String refeicao) {
        this.refeicao = refeicao;
    }

    public ValorRefeicao getValorRefeicao() {
        return valorRefeicao;
    }

    public Refeicao valorRefeicao(ValorRefeicao valorRefeicao) {
        this.valorRefeicao = valorRefeicao;
        return this;
    }

    public void setValorRefeicao(ValorRefeicao valorRefeicao) {
        this.valorRefeicao = valorRefeicao;
    }

    public Set<TipoAlimento> getTipoAlimentos() {
        return tipoAlimentos;
    }

    public Refeicao tipoAlimentos(Set<TipoAlimento> tipoAlimentos) {
        this.tipoAlimentos = tipoAlimentos;
        return this;
    }

    public void setTipoAlimentos(Set<TipoAlimento> tipoAlimentos) {
        this.tipoAlimentos = tipoAlimentos;
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
        Refeicao refeicao = (Refeicao) o;
        if (refeicao.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), refeicao.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Refeicao{" +
            "id=" + getId() +
            ", refeicao='" + getRefeicao() + "'" +
            "}";
    }
}
