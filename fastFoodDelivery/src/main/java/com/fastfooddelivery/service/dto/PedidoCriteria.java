package com.fastfooddelivery.service.dto;

import java.io.Serializable;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

import io.github.jhipster.service.filter.InstantFilter;




/**
 * Criteria class for the Pedido entity. This class is used in PedidoResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /pedidos?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PedidoCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private InstantFilter horarioDeRetirada;

    private StringFilter valorTotal;

    private LongFilter formaDeEntregaId;

    public PedidoCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public InstantFilter getHorarioDeRetirada() {
        return horarioDeRetirada;
    }

    public void setHorarioDeRetirada(InstantFilter horarioDeRetirada) {
        this.horarioDeRetirada = horarioDeRetirada;
    }

    public StringFilter getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(StringFilter valorTotal) {
        this.valorTotal = valorTotal;
    }

    public LongFilter getFormaDeEntregaId() {
        return formaDeEntregaId;
    }

    public void setFormaDeEntregaId(LongFilter formaDeEntregaId) {
        this.formaDeEntregaId = formaDeEntregaId;
    }

    @Override
    public String toString() {
        return "PedidoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (horarioDeRetirada != null ? "horarioDeRetirada=" + horarioDeRetirada + ", " : "") +
                (valorTotal != null ? "valorTotal=" + valorTotal + ", " : "") +
                (formaDeEntregaId != null ? "formaDeEntregaId=" + formaDeEntregaId + ", " : "") +
            "}";
    }

}
