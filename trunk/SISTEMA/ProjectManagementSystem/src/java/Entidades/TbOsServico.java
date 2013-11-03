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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author berlim
 */
@Entity
@Table(name = "TB_OS_SERVICO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TbOsServico.findAll", query = "SELECT t FROM TbOsServico t"),
    @NamedQuery(name = "TbOsServico.findByHand", query = "SELECT t FROM TbOsServico t WHERE t.hand = :hand")})
public class TbOsServico implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "HAND")
    private Integer hand;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tbOsServicoHand")
    private Collection<TbOsRecursos> tbOsRecursosCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tbOsServicoHand")
    private Collection<TbApontamentosRecursos> tbApontamentosRecursosCollection;
    @JoinColumn(name = "TB_STATUS_HAND", referencedColumnName = "HAND")
    @ManyToOne(optional = false)
    private TbStatus tbStatusHand;
    @JoinColumn(name = "TB_PROJETOS_SERVICOS_HAND", referencedColumnName = "HAND")
    @ManyToOne(optional = false)
    private TbProjetosServicos tbProjetosServicosHand;
    @JoinColumn(name = "TB_ORDEM_SERVICO_HAND", referencedColumnName = "HAND")
    @ManyToOne(optional = false)
    private TbOrdemServico tbOrdemServicoHand;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tbOsServicoHand")
    private Collection<TbApontamentosMateriais> tbApontamentosMateriaisCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tbOsServicoHand")
    private Collection<TbOsMateriais> tbOsMateriaisCollection;

    public TbOsServico() {
    }

    public TbOsServico(Integer hand) {
        this.hand = hand;
    }

    public Integer getHand() {
        return hand;
    }

    public void setHand(Integer hand) {
        this.hand = hand;
    }

    @XmlTransient
    public Collection<TbOsRecursos> getTbOsRecursosCollection() {
        return tbOsRecursosCollection;
    }

    public void setTbOsRecursosCollection(Collection<TbOsRecursos> tbOsRecursosCollection) {
        this.tbOsRecursosCollection = tbOsRecursosCollection;
    }

    @XmlTransient
    public Collection<TbApontamentosRecursos> getTbApontamentosRecursosCollection() {
        return tbApontamentosRecursosCollection;
    }

    public void setTbApontamentosRecursosCollection(Collection<TbApontamentosRecursos> tbApontamentosRecursosCollection) {
        this.tbApontamentosRecursosCollection = tbApontamentosRecursosCollection;
    }

    public TbStatus getTbStatusHand() {
        return tbStatusHand;
    }

    public void setTbStatusHand(TbStatus tbStatusHand) {
        this.tbStatusHand = tbStatusHand;
    }

    public TbProjetosServicos getTbProjetosServicosHand() {
        return tbProjetosServicosHand;
    }

    public void setTbProjetosServicosHand(TbProjetosServicos tbProjetosServicosHand) {
        this.tbProjetosServicosHand = tbProjetosServicosHand;
    }

    public TbOrdemServico getTbOrdemServicoHand() {
        return tbOrdemServicoHand;
    }

    public void setTbOrdemServicoHand(TbOrdemServico tbOrdemServicoHand) {
        this.tbOrdemServicoHand = tbOrdemServicoHand;
    }

    @XmlTransient
    public Collection<TbApontamentosMateriais> getTbApontamentosMateriaisCollection() {
        return tbApontamentosMateriaisCollection;
    }

    public void setTbApontamentosMateriaisCollection(Collection<TbApontamentosMateriais> tbApontamentosMateriaisCollection) {
        this.tbApontamentosMateriaisCollection = tbApontamentosMateriaisCollection;
    }

    @XmlTransient
    public Collection<TbOsMateriais> getTbOsMateriaisCollection() {
        return tbOsMateriaisCollection;
    }

    public void setTbOsMateriaisCollection(Collection<TbOsMateriais> tbOsMateriaisCollection) {
        this.tbOsMateriaisCollection = tbOsMateriaisCollection;
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
        if (!(object instanceof TbOsServico)) {
            return false;
        }
        TbOsServico other = (TbOsServico) object;
        if ((this.hand == null && other.hand != null) || (this.hand != null && !this.hand.equals(other.hand))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.TbOsServico[ hand=" + hand + " ]";
    }
    
}
