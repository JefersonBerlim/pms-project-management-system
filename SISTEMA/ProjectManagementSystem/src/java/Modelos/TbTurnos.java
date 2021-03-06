/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;

import Modelos.TbFuncionarioTurnoSemana;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author jeferson
 */
@Entity
@Table(name = "TB_TURNOS")
@NamedQueries({
    @NamedQuery(name = "TbTurnos.findAll", query = "SELECT t FROM TbTurnos t")})
public class TbTurnos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "HAND")
    private Integer hand;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "DESCRICAO")
    private String descricao;
    @Column(name = "HORARIO_INICIAL")
    @Temporal(TemporalType.TIME)
    private Date horarioInicial;
    @Column(name = "HORARIO_FINAL")
    @Temporal(TemporalType.TIME)
    private Date horarioFinal;
    @Column(name = "ALMOCO_INICIO")
    @Temporal(TemporalType.TIME)
    private Date almocoInicio;
    @Column(name = "ALMOCO_FIM")
    @Temporal(TemporalType.TIME)
    private Date almocoFim;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tbTurnosHand")
    private Collection<TbFuncionarioTurnoSemana> tbFuncionarioTurnoSemanaCollection;

    public TbTurnos() {
    }

    public TbTurnos(Integer hand) {
        this.hand = hand;
    }

    public TbTurnos(Integer hand, String descricao) {
        this.hand = hand;
        this.descricao = descricao;
    }

    public Integer getHand() {
        return hand;
    }

    public void setHand(Integer hand) {
        this.hand = hand;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getHorarioInicial() {
        return horarioInicial;
    }

    public void setHorarioInicial(Date horarioInicial) {
        this.horarioInicial = horarioInicial;
    }

    public Date getHorarioFinal() {
        return horarioFinal;
    }

    public void setHorarioFinal(Date horarioFinal) {
        this.horarioFinal = horarioFinal;
    }

    public Date getAlmocoInicio() {
        return almocoInicio;
    }

    public void setAlmocoInicio(Date almocoInicio) {
        this.almocoInicio = almocoInicio;
    }

    public Date getAlmocoFim() {
        return almocoFim;
    }

    public void setAlmocoFim(Date almocoFim) {
        this.almocoFim = almocoFim;
    }

    public Collection<TbFuncionarioTurnoSemana> getTbFuncionarioTurnoSemanaCollection() {
        return tbFuncionarioTurnoSemanaCollection;
    }

    public void setTbFuncionarioTurnoSemanaCollection(Collection<TbFuncionarioTurnoSemana> tbFuncionarioTurnoSemanaCollection) {
        this.tbFuncionarioTurnoSemanaCollection = tbFuncionarioTurnoSemanaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (hand != null ? hand.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TbTurnos)) {
            return false;
        }
        TbTurnos other = (TbTurnos) object;
        if ((this.hand == null && other.hand != null) || (this.hand != null && !this.hand.equals(other.hand))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getDescricao();
    }

}
