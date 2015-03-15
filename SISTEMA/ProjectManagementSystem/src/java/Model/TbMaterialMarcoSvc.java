/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author BERLIM
 */
@Entity
@Table(name = "TB_MATERIAL_MARCO_SVC")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TbMaterialMarcoSvc.findAll", query = "SELECT t FROM TbMaterialMarcoSvc t"),
    @NamedQuery(name = "TbMaterialMarcoSvc.findByHand", query = "SELECT t FROM TbMaterialMarcoSvc t WHERE t.hand = :hand"),
    @NamedQuery(name = "TbMaterialMarcoSvc.findByAutomatizaProcesso", query = "SELECT t FROM TbMaterialMarcoSvc t WHERE t.automatizaProcesso = :automatizaProcesso")})
public class TbMaterialMarcoSvc implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "HAND")
    private Integer hand;
    @Size(max = 1)
    @Column(name = "AUTOMATIZA_PROCESSO")
    private String automatizaProcesso;
    @JoinColumn(name = "TB_MARCOS_SERVICOS_HAND", referencedColumnName = "HAND")
    @ManyToOne(optional = false)
    private TbMarcosServicos tbMarcosServicosHand;
    @JoinColumn(name = "TB_MATERIAIS_HAND", referencedColumnName = "HAND")
    @ManyToOne(optional = false)
    private TbMateriais tbMateriaisHand;

    public TbMaterialMarcoSvc() {
    }

    public TbMaterialMarcoSvc(Integer hand) {
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

    public TbMarcosServicos getTbMarcosServicosHand() {
        return tbMarcosServicosHand;
    }

    public void setTbMarcosServicosHand(TbMarcosServicos tbMarcosServicosHand) {
        this.tbMarcosServicosHand = tbMarcosServicosHand;
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
        if (!(object instanceof TbMaterialMarcoSvc)) {
            return false;
        }
        TbMaterialMarcoSvc other = (TbMaterialMarcoSvc) object;
        if ((this.hand == null && other.hand != null) || (this.hand != null && !this.hand.equals(other.hand))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.TbMaterialMarcoSvc[ hand=" + hand + " ]";
    }
    
}
