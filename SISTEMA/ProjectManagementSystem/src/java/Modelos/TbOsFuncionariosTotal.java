/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "TB_OS_FUNCIONARIOS_TOTAL")
@NamedQueries({
    @NamedQuery(name = "TbOsFuncionariosTotal.findAll", query = "SELECT t FROM TbOsFuncionariosTotal t")})
public class TbOsFuncionariosTotal implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "HAND")
    private Integer hand;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "QUANTIDAE_HORAS")
    private BigDecimal quantidaeHoras;
    @JoinColumn(name = "TB_PROJETO_FUNCIONARIOS_HAND", referencedColumnName = "HAND")
    @ManyToOne(optional = false)
    private TbProjetoFuncionarios tbProjetoFuncionariosHand;
    @JoinColumn(name = "TB_OS_RECURSOS_TOTAL_HAND", referencedColumnName = "HAND")
    @ManyToOne(optional = false)
    private TbOsRecursosTotal tbOsRecursosTotalHand;

    public TbOsFuncionariosTotal() {
    }

    public TbOsFuncionariosTotal(Integer hand) {
        this.hand = hand;
    }

    public Integer getHand() {
        return hand;
    }

    public void setHand(Integer hand) {
        this.hand = hand;
    }

    public BigDecimal getQuantidaeHoras() {
        return quantidaeHoras;
    }

    public void setQuantidaeHoras(BigDecimal quantidaeHoras) {
        this.quantidaeHoras = quantidaeHoras;
    }

    public TbProjetoFuncionarios getTbProjetoFuncionariosHand() {
        return tbProjetoFuncionariosHand;
    }

    public void setTbProjetoFuncionariosHand(TbProjetoFuncionarios tbProjetoFuncionariosHand) {
        this.tbProjetoFuncionariosHand = tbProjetoFuncionariosHand;
    }

    public TbOsRecursosTotal getTbOsRecursosTotalHand() {
        return tbOsRecursosTotalHand;
    }

    public void setTbOsRecursosTotalHand(TbOsRecursosTotal tbOsRecursosTotalHand) {
        this.tbOsRecursosTotalHand = tbOsRecursosTotalHand;
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
        if (!(object instanceof TbOsFuncionariosTotal)) {
            return false;
        }
        TbOsFuncionariosTotal other = (TbOsFuncionariosTotal) object;
        if ((this.hand == null && other.hand != null) || (this.hand != null && !this.hand.equals(other.hand))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelos.TbOsFuncionariosTotal[ hand=" + hand + " ]";
    }

}
