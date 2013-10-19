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
 * @author berlim
 */
@Entity
@Table(name = "TB_ORDEM_SERVICO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TbOrdemServico.findAll", query = "SELECT t FROM TbOrdemServico t"),
    @NamedQuery(name = "TbOrdemServico.findByHand", query = "SELECT t FROM TbOrdemServico t WHERE t.hand = :hand"),
    @NamedQuery(name = "TbOrdemServico.findByDescricao", query = "SELECT t FROM TbOrdemServico t WHERE t.descricao = :descricao")})
public class TbOrdemServico implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "HAND")
    private Integer hand;
    @Size(max = 255)
    @Column(name = "DESCRICAO")
    private String descricao;
    @JoinColumn(name = "TB_PROJETOS_HAND", referencedColumnName = "HAND")
    @ManyToOne(optional = false)
    private TbProjetos tbProjetosHand;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tbOrdemServicoHand")
    private Collection<TbOsServico> tbOsServicoCollection;

    public TbOrdemServico() {
    }

    public TbOrdemServico(Integer hand) {
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

    public TbProjetos getTbProjetosHand() {
        return tbProjetosHand;
    }

    public void setTbProjetosHand(TbProjetos tbProjetosHand) {
        this.tbProjetosHand = tbProjetosHand;
    }

    @XmlTransient
    public Collection<TbOsServico> getTbOsServicoCollection() {
        return tbOsServicoCollection;
    }

    public void setTbOsServicoCollection(Collection<TbOsServico> tbOsServicoCollection) {
        this.tbOsServicoCollection = tbOsServicoCollection;
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
        if (!(object instanceof TbOrdemServico)) {
            return false;
        }
        TbOrdemServico other = (TbOrdemServico) object;
        if ((this.hand == null && other.hand != null) || (this.hand != null && !this.hand.equals(other.hand))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.TbOrdemServico[ hand=" + hand + " ]";
    }
    
}
