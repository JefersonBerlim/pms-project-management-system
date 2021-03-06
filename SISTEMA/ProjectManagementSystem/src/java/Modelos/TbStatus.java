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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author jeferson
 */
@Entity
@Table(name = "TB_STATUS")
@NamedQueries({
    @NamedQuery(name = "TbStatus.findAll", query = "SELECT t FROM TbStatus t")})
public class TbStatus implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "HAND")
    private Integer hand;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "STATUS")
    private String status;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "DESCRICAO")
    private String descricao;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tbStatusHand")
    private Collection<TbOsServico> tbOsServicoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tbStatusHand")
    private Collection<TbProjetos> tbProjetosCollection;

    public TbStatus() {
    }

    public TbStatus(Integer hand) {
        this.hand = hand;
    }

    public TbStatus(Integer hand, String status, String descricao) {
        this.hand = hand;
        this.status = status;
        this.descricao = descricao;
    }

    public Integer getHand() {
        return hand;
    }

    public void setHand(Integer hand) {
        this.hand = hand;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Collection<TbOsServico> getTbOsServicoCollection() {
        return tbOsServicoCollection;
    }

    public void setTbOsServicoCollection(Collection<TbOsServico> tbOsServicoCollection) {
        this.tbOsServicoCollection = tbOsServicoCollection;
    }

    public Collection<TbProjetos> getTbProjetosCollection() {
        return tbProjetosCollection;
    }

    public void setTbProjetosCollection(Collection<TbProjetos> tbProjetosCollection) {
        this.tbProjetosCollection = tbProjetosCollection;
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
        if (!(object instanceof TbStatus)) {
            return false;
        }
        TbStatus other = (TbStatus) object;
        if ((this.hand == null && other.hand != null) || (this.hand != null && !this.hand.equals(other.hand))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelos.TbStatus[ hand=" + hand + " ]";
    }

}
