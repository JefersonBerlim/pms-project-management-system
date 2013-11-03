/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author berlim
 */
@Entity
@Table(name = "TB_MARCOS_SERVICOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TbMarcosServicos.findAll", query = "SELECT t FROM TbMarcosServicos t"),
    @NamedQuery(name = "TbMarcosServicos.findByHand", query = "SELECT t FROM TbMarcosServicos t WHERE t.hand = :hand"),
    @NamedQuery(name = "TbMarcosServicos.findByAutomatizaProcesso", query = "SELECT t FROM TbMarcosServicos t WHERE t.automatizaProcesso = :automatizaProcesso")})
public class TbMarcosServicos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "HAND")
    private Integer hand;
    @Column(name = "AUTOMATIZA_PROCESSO")
    private String automatizaProcesso;
    @JoinColumn(name = "TB_SERVICOS_HAND", referencedColumnName = "HAND")
    @ManyToOne(optional = false)
    private TbServicos tbServicosHand;
    @JoinColumn(name = "TB_MARCOS_HAND", referencedColumnName = "HAND")
    @ManyToOne(optional = false)
    private TbMarcos tbMarcosHand;

    public TbMarcosServicos() {
    }

    public TbMarcosServicos(Integer hand) {
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

    public TbServicos getTbServicosHand() {
        return tbServicosHand;
    }

    public void setTbServicosHand(TbServicos tbServicosHand) {
        this.tbServicosHand = tbServicosHand;
    }

    public TbMarcos getTbMarcosHand() {
        return tbMarcosHand;
    }

    public void setTbMarcosHand(TbMarcos tbMarcosHand) {
        this.tbMarcosHand = tbMarcosHand;
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
        if (!(object instanceof TbMarcosServicos)) {
            return false;
        }
        TbMarcosServicos other = (TbMarcosServicos) object;
        if ((this.hand == null && other.hand != null) || (this.hand != null && !this.hand.equals(other.hand))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.TbMarcosServicos[ hand=" + hand + " ]";
    }
    
}
