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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author berlim
 */
@Entity
@Table(name = "TB_APONTAMENTOS_RECURSOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TbApontamentosRecursos.findAll", query = "SELECT t FROM TbApontamentosRecursos t"),
    @NamedQuery(name = "TbApontamentosRecursos.findByHand", query = "SELECT t FROM TbApontamentosRecursos t WHERE t.hand = :hand")})
public class TbApontamentosRecursos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "HAND")
    private Integer hand;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tbApontamentosRecursosHand")
    private Collection<TbApontamentosFuncionarios> tbApontamentosFuncionariosCollection;
    @JoinColumn(name = "TB_RECURSOS_HAND", referencedColumnName = "HAND")
    @ManyToOne(optional = false)
    private TbRecursos tbRecursosHand;
    @JoinColumn(name = "TB_OS_SERVICO_HAND", referencedColumnName = "HAND")
    @ManyToOne(optional = false)
    private TbOsServico tbOsServicoHand;

    public TbApontamentosRecursos() {
    }

    public TbApontamentosRecursos(Integer hand) {
        this.hand = hand;
    }

    public Integer getHand() {
        return hand;
    }

    public void setHand(Integer hand) {
        this.hand = hand;
    }

    @XmlTransient
    public Collection<TbApontamentosFuncionarios> getTbApontamentosFuncionariosCollection() {
        return tbApontamentosFuncionariosCollection;
    }

    public void setTbApontamentosFuncionariosCollection(Collection<TbApontamentosFuncionarios> tbApontamentosFuncionariosCollection) {
        this.tbApontamentosFuncionariosCollection = tbApontamentosFuncionariosCollection;
    }

    public TbRecursos getTbRecursosHand() {
        return tbRecursosHand;
    }

    public void setTbRecursosHand(TbRecursos tbRecursosHand) {
        this.tbRecursosHand = tbRecursosHand;
    }

    public TbOsServico getTbOsServicoHand() {
        return tbOsServicoHand;
    }

    public void setTbOsServicoHand(TbOsServico tbOsServicoHand) {
        this.tbOsServicoHand = tbOsServicoHand;
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
        if (!(object instanceof TbApontamentosRecursos)) {
            return false;
        }
        TbApontamentosRecursos other = (TbApontamentosRecursos) object;
        if ((this.hand == null && other.hand != null) || (this.hand != null && !this.hand.equals(other.hand))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.TbApontamentosRecursos[ hand=" + hand + " ]";
    }
    
}
