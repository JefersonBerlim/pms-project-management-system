/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author jeferson
 */
@Entity
@Table(name = "TB_MARCOS_SERVICOS")
@NamedQueries({
    @NamedQuery(name = "TbMarcosServicos.findAll", query = "SELECT t FROM TbMarcosServicos t"),
    @NamedQuery(name = "TbMarcosServicos.TbServicosVinculados", query = "SELECT t FROM TbMarcosServicos t "
            + "WHERE t.tbMarcosHand.hand = :marco"),
    @NamedQuery(name = "TbMarcosServicos.retornaRegistros", query = "SELECT t FROM TbMarcosServicos t "
            + "WHERE t.tbMarcosHand = :marco AND t.tbServicosHand = :servico"),})
public class TbMarcosServicos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "HAND")
    private Integer hand;
    @Size(max = 1)
    @Column(name = "AUTOMATIZA_PROCESSO")
    private String automatizaProcesso;
    @JoinColumn(name = "TB_SERVICOS_HAND", referencedColumnName = "HAND")
    @ManyToOne(optional = false)
    private TbServicos tbServicosHand;
    @JoinColumn(name = "TB_MARCOS_HAND", referencedColumnName = "HAND")
    @ManyToOne(optional = false)
    private TbMarcos tbMarcosHand;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tbMarcosServicosHand")
    private Collection<TbMaterialMarcoSvc> tbMaterialMarcoSvcCollection;

    @Transient
    private boolean tmpAutomatizaProcesso;

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

    public Collection<TbMaterialMarcoSvc> getTbMaterialMarcoSvcCollection() {
        return tbMaterialMarcoSvcCollection;
    }

    public void setTbMaterialMarcoSvcCollection(Collection<TbMaterialMarcoSvc> tbMaterialMarcoSvcCollection) {
        this.tbMaterialMarcoSvcCollection = tbMaterialMarcoSvcCollection;
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
        if (!(object instanceof TbMarcosServicos)) {
            return false;
        }
        TbMarcosServicos other = (TbMarcosServicos) object;
        return !((this.hand == null && other.hand != null) || (this.hand != null && !this.hand.equals(other.hand)));
    }

    @Override
    public String toString() {
        return "Serviço:" + tbServicosHand.getDescricao() + " - Marco:" + tbMarcosHand.getDescricao();
    }

}
