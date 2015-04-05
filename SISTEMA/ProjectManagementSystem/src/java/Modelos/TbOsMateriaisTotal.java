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
@Table(name = "TB_OS_MATERIAIS_TOTAL")
@NamedQueries({
    @NamedQuery(name = "TbOsMateriaisTotal.findAll", query = "SELECT t FROM TbOsMateriaisTotal t")})
public class TbOsMateriaisTotal implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "HAND")
    private Integer hand;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "QUANTIDADE_UTILIZADA")
    private BigDecimal quantidadeUtilizada;
    @JoinColumn(name = "TB_PROJETOS_MATERIAIS_HAND", referencedColumnName = "HAND")
    @ManyToOne(optional = false)
    private TbProjetosMateriais tbProjetosMateriaisHand;
    @JoinColumn(name = "TB_OS_SERVICO_HAND", referencedColumnName = "HAND")
    @ManyToOne(optional = false)
    private TbOsServico tbOsServicoHand;
    @JoinColumn(name = "TB_MATERIAIS_HAND", referencedColumnName = "HAND")
    @ManyToOne(optional = false)
    private TbMateriais tbMateriaisHand;

    public TbOsMateriaisTotal() {
    }

    public TbOsMateriaisTotal(Integer hand) {
        this.hand = hand;
    }

    public Integer getHand() {
        return hand;
    }

    public void setHand(Integer hand) {
        this.hand = hand;
    }

    public BigDecimal getQuantidadeUtilizada() {
        return quantidadeUtilizada;
    }

    public void setQuantidadeUtilizada(BigDecimal quantidadeUtilizada) {
        this.quantidadeUtilizada = quantidadeUtilizada;
    }

    public TbProjetosMateriais getTbProjetosMateriaisHand() {
        return tbProjetosMateriaisHand;
    }

    public void setTbProjetosMateriaisHand(TbProjetosMateriais tbProjetosMateriaisHand) {
        this.tbProjetosMateriaisHand = tbProjetosMateriaisHand;
    }

    public TbOsServico getTbOsServicoHand() {
        return tbOsServicoHand;
    }

    public void setTbOsServicoHand(TbOsServico tbOsServicoHand) {
        this.tbOsServicoHand = tbOsServicoHand;
    }

    public TbMateriais getTbMateriaisHand() {
        return tbMateriaisHand;
    }

    public void setTbMateriaisHand(TbMateriais tbMateriaisHand) {
        this.tbMateriaisHand = tbMateriaisHand;
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
        if (!(object instanceof TbOsMateriaisTotal)) {
            return false;
        }
        TbOsMateriaisTotal other = (TbOsMateriaisTotal) object;
        if ((this.hand == null && other.hand != null) || (this.hand != null && !this.hand.equals(other.hand))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelos.TbOsMateriaisTotal[ hand=" + hand + " ]";
    }

}
