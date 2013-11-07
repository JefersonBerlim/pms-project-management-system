/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author berlim
 */
@Entity
@Table(name = "TB_APONTAMENTOS_MATERIAIS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TbApontamentosMateriais.findAll", query = "SELECT t FROM TbApontamentosMateriais t"),
    @NamedQuery(name = "TbApontamentosMateriais.findByHand", query = "SELECT t FROM TbApontamentosMateriais t WHERE t.hand = :hand"),
    @NamedQuery(name = "TbApontamentosMateriais.findByQuantidaeUtilizada", query = "SELECT t FROM TbApontamentosMateriais t WHERE t.quantidaeUtilizada = :quantidaeUtilizada")})
public class TbApontamentosMateriais implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "HAND")
    private Integer hand;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "QUANTIDAE_UTILIZADA")
    private BigDecimal quantidaeUtilizada;
    @JoinColumn(name = "TB_OS_SERVICO_HAND", referencedColumnName = "HAND")
    @ManyToOne(optional = false)
    private TbOsServico tbOsServicoHand;
    @JoinColumn(name = "TB_MATERIAIS_HAND", referencedColumnName = "HAND")
    @ManyToOne(optional = false)
    private TbMateriais tbMateriaisHand;

    public TbApontamentosMateriais() {
    }

    public TbApontamentosMateriais(Integer hand) {
        this.hand = hand;
    }

    public Integer getHand() {
        return hand;
    }

    public void setHand(Integer hand) {
        this.hand = hand;
    }

    public BigDecimal getQuantidaeUtilizada() {
        return quantidaeUtilizada;
    }

    public void setQuantidaeUtilizada(BigDecimal quantidaeUtilizada) {
        this.quantidaeUtilizada = quantidaeUtilizada;
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
        if (!(object instanceof TbApontamentosMateriais)) {
            return false;
        }
        TbApontamentosMateriais other = (TbApontamentosMateriais) object;
        if ((this.hand == null && other.hand != null) || (this.hand != null && !this.hand.equals(other.hand))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.TbApontamentosMateriais[ hand=" + hand + " ]";
    }
    
}