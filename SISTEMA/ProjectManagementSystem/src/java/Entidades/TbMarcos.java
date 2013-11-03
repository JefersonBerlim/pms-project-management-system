/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author berlim
 */
@Entity
@Table(name = "TB_MARCOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TbMarcos.findAll", query = "SELECT t FROM TbMarcos t"),
    @NamedQuery(name = "TbMarcos.findByHand", query = "SELECT t FROM TbMarcos t WHERE t.hand = :hand"),
    @NamedQuery(name = "TbMarcos.findByDescricao", query = "SELECT t FROM TbMarcos t WHERE t.descricao = :descricao"),
    @NamedQuery(name = "TbMarcos.findByEhInativo", query = "SELECT t FROM TbMarcos t WHERE t.ehInativo = :ehInativo")})
public class TbMarcos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "HAND")
    private Integer hand;
    @Column(name = "DESCRICAO")
    private String descricao;
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

    @XmlTransient
    public Collection<TbMarcosServicos> getTbMarcosServicosCollection() {
        return tbMarcosServicosCollection;
    }

    public void setTbMarcosServicosCollection(Collection<TbMarcosServicos> tbMarcosServicosCollection) {
        this.tbMarcosServicosCollection = tbMarcosServicosCollection;
    }

    @XmlTransient
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
        return "Entidades.TbMarcos[ hand=" + hand + " ]";
    }
    
}
