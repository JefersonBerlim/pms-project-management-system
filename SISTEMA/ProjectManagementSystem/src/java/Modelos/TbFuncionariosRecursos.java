/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;

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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author jeferson
 */
@Entity
@Table(name = "TB_FUNCIONARIOS_RECURSOS")
@NamedQueries({
    @NamedQuery(name = "TbFuncionariosRecursos.findAll", query = "SELECT t FROM TbFuncionariosRecursos t"),
    @NamedQuery(name = "TbFuncionariosRecursos.retornaRegistros", query = "SELECT t FROM TbFuncionariosRecursos t "
            + "WHERE t.tbRecursosHand = :recurso AND t.tbFuncionariosHand = :funcionario"),})
public class TbFuncionariosRecursos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "HAND")
    private Integer hand;
    @Size(max = 1)
    @Column(name = "AUTOMATIZA_PROCESSO")
    private String automatizaProcesso;
    @JoinColumn(name = "TB_RECURSOS_HAND", referencedColumnName = "HAND")
    @ManyToOne(optional = false)
    private TbRecursos tbRecursosHand;
    @JoinColumn(name = "TB_FUNCIONARIOS_HAND", referencedColumnName = "HAND")
    @ManyToOne(optional = false)
    private TbFuncionarios tbFuncionariosHand;

    @Transient
    private boolean tmpAutomatizaProcesso;

    public TbFuncionariosRecursos() {
    }

    public TbFuncionariosRecursos(Integer hand) {
        this.hand = hand;
    }

    public Integer getHand() {
        return hand;
    }

    public void setHand(Integer hand) {
        this.hand = hand;
    }

    public String getAutomatizaProcesso() {
        return automatizaProcesso;
    }

    public void setAutomatizaProcesso(String automatizaProcesso) {
        this.automatizaProcesso = automatizaProcesso;
    }

    public TbRecursos getTbRecursosHand() {
        return tbRecursosHand;
    }

    public void setTbRecursosHand(TbRecursos tbRecursosHand) {
        this.tbRecursosHand = tbRecursosHand;
    }

    public TbFuncionarios getTbFuncionariosHand() {
        return tbFuncionariosHand;
    }

    public void setTbFuncionariosHand(TbFuncionarios tbFuncionariosHand) {
        this.tbFuncionariosHand = tbFuncionariosHand;
    }

    public boolean isTmpAutomatizaProcesso() {
        return tmpAutomatizaProcesso;
    }

    public void setTmpAutomatizaProcesso(boolean tmpAutomatizaProcesso) {
        this.tmpAutomatizaProcesso = tmpAutomatizaProcesso;
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
        if (!(object instanceof TbFuncionariosRecursos)) {
            return false;
        }
        TbFuncionariosRecursos other = (TbFuncionariosRecursos) object;
        if ((this.hand == null && other.hand != null) || (this.hand != null && !this.hand.equals(other.hand))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelos.TbFuncionariosRecursos[ hand=" + hand + " ]";
    }

}
