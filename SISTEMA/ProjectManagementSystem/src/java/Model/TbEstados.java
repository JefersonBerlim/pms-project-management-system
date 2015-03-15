/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author BERLIM
 */
@Entity
@Table(name = "TB_ESTADOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TbEstados.findAll", query = "SELECT t FROM TbEstados t"),
    @NamedQuery(name = "TbEstados.findByHand", query = "SELECT t FROM TbEstados t WHERE t.hand = :hand"),
    @NamedQuery(name = "TbEstados.findByEstado", query = "SELECT t FROM TbEstados t WHERE t.estado = :estado"),
    @NamedQuery(name = "TbEstados.findBySigla", query = "SELECT t FROM TbEstados t WHERE t.sigla = :sigla")})
public class TbEstados implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "HAND")
    private Integer hand;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "ESTADO")
    private String estado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "SIGLA")
    private String sigla;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tbEstadosHand")
    private Collection<TbCidades> tbCidadesCollection;
    @JoinColumn(name = "TB_PAISES_HAND", referencedColumnName = "HAND")
    @ManyToOne(optional = false)
    private TbPaises tbPaisesHand;

    public TbEstados() {
    }

    public TbEstados(Integer hand) {
        this.hand = hand;
    }

    public TbEstados(Integer hand, String estado, String sigla) {
        this.hand = hand;
        this.estado = estado;
        this.sigla = sigla;
    }

    public Integer getHand() {
        return hand;
    }

    public void setHand(Integer hand) {
        this.hand = hand;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    @XmlTransient
    public Collection<TbCidades> getTbCidadesCollection() {
        return tbCidadesCollection;
    }

    public void setTbCidadesCollection(Collection<TbCidades> tbCidadesCollection) {
        this.tbCidadesCollection = tbCidadesCollection;
    }

    public TbPaises getTbPaisesHand() {
        return tbPaisesHand;
    }

    public void setTbPaisesHand(TbPaises tbPaisesHand) {
        this.tbPaisesHand = tbPaisesHand;
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
        if (!(object instanceof TbEstados)) {
            return false;
        }
        TbEstados other = (TbEstados) object;
        if ((this.hand == null && other.hand != null) || (this.hand != null && !this.hand.equals(other.hand))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.TbEstados[ hand=" + hand + " ]";
    }
    
}
