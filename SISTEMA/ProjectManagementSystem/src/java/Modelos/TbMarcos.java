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
@Table(name = "TB_MARCOS")
@NamedQueries({
    @NamedQuery(name = "TbMarcos.findAll", query = "SELECT t FROM TbMarcos t")})
public class TbMarcos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "HAND")
    private Integer hand;
    @Size(max = 50)
    @Column(name = "DESCRICAO")
    private String descricao;
    @Size(max = 1)
    @Column(name = "EH_INATIVO")
    private String ehInativo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tbMarcosHand")
    private Collection<TbMarcosServicos> tbMarcosServicosCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tbMarcosHand")
    private Collection<TbProjetoMarcos> tbProjetoMarcosCollection;

    public TbMarcos() {
    }

    public TbMarcos(Integer hand) {
        this.hand = hand;
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

    public String getEhInativo() {
        return ehInativo;
    }

    public void setEhInativo(String ehInativo) {
        this.ehInativo = ehInativo;
    }

    public Collection<TbMarcosServicos> getTbMarcosServicosCollection() {
        return tbMarcosServicosCollection;
    }

    public void setTbMarcosServicosCollection(Collection<TbMarcosServicos> tbMarcosServicosCollection) {
        this.tbMarcosServicosCollection = tbMarcosServicosCollection;
    }

    public Collection<TbProjetoMarcos> getTbProjetoMarcosCollection() {
        return tbProjetoMarcosCollection;
    }

    public void setTbProjetoMarcosCollection(Collection<TbProjetoMarcos> tbProjetoMarcosCollection) {
        this.tbProjetoMarcosCollection = tbProjetoMarcosCollection;
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
        if (!(object instanceof TbMarcos)) {
            return false;
        }
        TbMarcos other = (TbMarcos) object;
        if ((this.hand == null && other.hand != null) || (this.hand != null && !this.hand.equals(other.hand))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelos.TbMarcos[ hand=" + hand + " ]";
    }
    
}
