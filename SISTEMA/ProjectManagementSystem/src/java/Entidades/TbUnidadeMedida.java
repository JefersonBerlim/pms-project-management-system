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
@Table(name = "TB_UNIDADE_MEDIDA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TbUnidadeMedida.findAll", query = "SELECT t FROM TbUnidadeMedida t"),
    @NamedQuery(name = "TbUnidadeMedida.findByHand", query = "SELECT t FROM TbUnidadeMedida t WHERE t.hand = :hand"),
    @NamedQuery(name = "TbUnidadeMedida.findBySigla", query = "SELECT t FROM TbUnidadeMedida t WHERE t.sigla = :sigla"),
    @NamedQuery(name = "TbUnidadeMedida.findByDescricao", query = "SELECT t FROM TbUnidadeMedida t WHERE t.descricao = :descricao")})
public class TbUnidadeMedida implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "HAND")
    private Integer hand;
    @Column(name = "SIGLA")
    private String sigla;
    @Column(name = "DESCRICAO")
    private String descricao;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tbUnidadeMedidaHand")
    private Collection<TbMateriais> tbMateriaisCollection;

    public TbUnidadeMedida() {
    }

    public TbUnidadeMedida(Integer hand) {
        this.hand = hand;
    }

    public Integer getHand() {
        return hand;
    }

    public void setHand(Integer hand) {
        this.hand = hand;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @XmlTransient
    public Collection<TbMateriais> getTbMateriaisCollection() {
        return tbMateriaisCollection;
    }

    public void setTbMateriaisCollection(Collection<TbMateriais> tbMateriaisCollection) {
        this.tbMateriaisCollection = tbMateriaisCollection;
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
        if (!(object instanceof TbUnidadeMedida)) {
            return false;
        }
        TbUnidadeMedida other = (TbUnidadeMedida) object;
        if ((this.hand == null && other.hand != null) || (this.hand != null && !this.hand.equals(other.hand))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.TbUnidadeMedida[ hand=" + hand + " ]";
    }
    
}
