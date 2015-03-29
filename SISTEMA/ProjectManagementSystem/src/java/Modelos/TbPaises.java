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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author jeferson
 */
@Entity
@Table(name = "TB_PAISES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TbPaises.findAll", query = "SELECT t FROM TbPaises t"),
    @NamedQuery(name = "TbPaises.findByHand", query = "SELECT t FROM TbPaises t WHERE t.hand = :hand"),
    @NamedQuery(name = "TbPaises.findByPais", query = "SELECT t FROM TbPaises t WHERE t.pais = :pais")})
public class TbPaises implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "HAND")
    private Integer hand;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "PAIS")
    private String pais;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tbPaisesHand")
    private Collection<TbEstados> tbEstadosCollection;

    public TbPaises() {
    }

    public TbPaises(Integer hand) {
        this.hand = hand;
    }

    public TbPaises(Integer hand, String pais) {
        this.hand = hand;
        this.pais = pais;
    }

    public Integer getHand() {
        return hand;
    }

    public void setHand(Integer hand) {
        this.hand = hand;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    @XmlTransient
    public Collection<TbEstados> getTbEstadosCollection() {
        return tbEstadosCollection;
    }

    public void setTbEstadosCollection(Collection<TbEstados> tbEstadosCollection) {
        this.tbEstadosCollection = tbEstadosCollection;
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
        if (!(object instanceof TbPaises)) {
            return false;
        }
        TbPaises other = (TbPaises) object;
        if ((this.hand == null && other.hand != null) || (this.hand != null && !this.hand.equals(other.hand))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelos.TbPaises[ hand=" + hand + " ]";
    }
    
}
