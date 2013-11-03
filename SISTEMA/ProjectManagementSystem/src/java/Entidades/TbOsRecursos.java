/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "TB_OS_RECURSOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TbOsRecursos.findAll", query = "SELECT t FROM TbOsRecursos t"),
    @NamedQuery(name = "TbOsRecursos.findByHand", query = "SELECT t FROM TbOsRecursos t WHERE t.hand = :hand"),
    @NamedQuery(name = "TbOsRecursos.findByQuantidadeHoras", query = "SELECT t FROM TbOsRecursos t WHERE t.quantidadeHoras = :quantidadeHoras")})
public class TbOsRecursos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "HAND")
    private Integer hand;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "QUANTIDADE_HORAS")
    private BigDecimal quantidadeHoras;
    @JoinColumn(name = "TB_PROJETOS_RECURSOS_HAND", referencedColumnName = "HAND")
    @ManyToOne(optional = false)
    private TbProjetosRecursos tbProjetosRecursosHand;
    @JoinColumn(name = "TB_OS_SERVICO_HAND", referencedColumnName = "HAND")
    @ManyToOne(optional = false)
    private TbOsServico tbOsServicoHand;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tbOsRecursosHand")
    private Collection<TbOsFuncionarios> tbOsFuncionariosCollection;

    public TbOsRecursos() {
    }

    public TbOsRecursos(Integer hand) {
        this.hand = hand;
    }

    public Integer getHand() {
        return hand;
    }

    public void setHand(Integer hand) {
        this.hand = hand;
    }

    public BigDecimal getQuantidadeHoras() {
        return quantidadeHoras;
    }

    public void setQuantidadeHoras(BigDecimal quantidadeHoras) {
        this.quantidadeHoras = quantidadeHoras;
    }

    public TbProjetosRecursos getTbProjetosRecursosHand() {
        return tbProjetosRecursosHand;
    }

    public void setTbProjetosRecursosHand(TbProjetosRecursos tbProjetosRecursosHand) {
        this.tbProjetosRecursosHand = tbProjetosRecursosHand;
    }

    public TbOsServico getTbOsServicoHand() {
        return tbOsServicoHand;
    }

    public void setTbOsServicoHand(TbOsServico tbOsServicoHand) {
        this.tbOsServicoHand = tbOsServicoHand;
    }

    @XmlTransient
    public Collection<TbOsFuncionarios> getTbOsFuncionariosCollection() {
        return tbOsFuncionariosCollection;
    }

    public void setTbOsFuncionariosCollection(Collection<TbOsFuncionarios> tbOsFuncionariosCollection) {
        this.tbOsFuncionariosCollection = tbOsFuncionariosCollection;
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
        if (!(object instanceof TbOsRecursos)) {
            return false;
        }
        TbOsRecursos other = (TbOsRecursos) object;
        if ((this.hand == null && other.hand != null) || (this.hand != null && !this.hand.equals(other.hand))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.TbOsRecursos[ hand=" + hand + " ]";
    }
    
}
