/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;

import Modelos.TbTurnos;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author jeferson
 */
@Entity
@Table(name = "TB_FUNCIONARIO_TURNO_SEMANA")
@NamedQueries({
    @NamedQuery(name = "TbFuncionarioTurnoSemana.findAll", query = "SELECT t FROM TbFuncionarioTurnoSemana t")})
public class TbFuncionarioTurnoSemana implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "HAND")
    private Integer hand;
    @JoinColumn(name = "TB_TURNOS_HAND", referencedColumnName = "HAND")
    @ManyToOne(optional = false)
    private TbTurnos tbTurnosHand;
    @JoinColumn(name = "TB_FUNCIONARIOS_HAND", referencedColumnName = "HAND")
    @ManyToOne(optional = false)
    private TbFuncionarios tbFuncionariosHand;
    @JoinColumn(name = "TB_DIA_SEMANA_HAND", referencedColumnName = "HAND")
    @ManyToOne(optional = false)
    private TbDiaSemana tbDiaSemanaHand;

    public TbFuncionarioTurnoSemana() {
    }

    public TbFuncionarioTurnoSemana(Integer hand) {
        this.hand = hand;
    }

    public Integer getHand() {
        return hand;
    }

    public void setHand(Integer hand) {
        this.hand = hand;
    }

    public TbTurnos getTbTurnosHand() {
        return tbTurnosHand;
    }

    public void setTbTurnosHand(TbTurnos tbTurnosHand) {
        this.tbTurnosHand = tbTurnosHand;
    }

    public TbFuncionarios getTbFuncionariosHand() {
        return tbFuncionariosHand;
    }

    public void setTbFuncionariosHand(TbFuncionarios tbFuncionariosHand) {
        this.tbFuncionariosHand = tbFuncionariosHand;
    }

    public TbDiaSemana getTbDiaSemanaHand() {
        return tbDiaSemanaHand;
    }

    public void setTbDiaSemanaHand(TbDiaSemana tbDiaSemanaHand) {
        this.tbDiaSemanaHand = tbDiaSemanaHand;
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
        if (!(object instanceof TbFuncionarioTurnoSemana)) {
            return false;
        }
        TbFuncionarioTurnoSemana other = (TbFuncionarioTurnoSemana) object;
        if ((this.hand == null && other.hand != null) || (this.hand != null && !this.hand.equals(other.hand))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelos.TbFuncionarioTurnoSemana[ hand=" + hand + " ]";
    }

}
