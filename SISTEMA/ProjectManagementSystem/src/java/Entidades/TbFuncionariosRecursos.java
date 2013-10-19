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
@Table(name = "TB_FUNCIONARIOS_RECURSOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TbFuncionariosRecursos.findAll", query = "SELECT t FROM TbFuncionariosRecursos t"),
    @NamedQuery(name = "TbFuncionariosRecursos.findByHand", query = "SELECT t FROM TbFuncionariosRecursos t WHERE t.hand = :hand"),
    @NamedQuery(name = "TbFuncionariosRecursos.findByAutomatizaProcesso", query = "SELECT t FROM TbFuncionariosRecursos t WHERE t.automatizaProcesso = :automatizaProcesso")})
public class TbFuncionariosRecursos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "HAND")
    private Integer hand;
    @Size(max = 1)
    @Column(name = "AUTOMATIZA_PROCESSO")
    private String automatizaProcesso;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tbFuncionariosRecursosHand")
    private Collection<TbProjetoFuncionarios> tbProjetoFuncionariosCollection;
    @JoinColumn(name = "TB_RECURSOS_HAND", referencedColumnName = "HAND")
    @ManyToOne(optional = false)
    private TbRecursos tbRecursosHand;
    @JoinColumn(name = "TB_FUNCIONARIOS_HAND", referencedColumnName = "HAND")
    @ManyToOne(optional = false)
    private TbFuncionarios tbFuncionariosHand;

    public TbFuncionariosRecursos() {
    }

    public TbFuncionariosRecursos(Integer hand) {
        this.hand = hand;
    }

    public Integer getHand() {
        return hand;
    }

    public void setHand(Integer hand) {
        this.hand = hand;
    }

    public String getAutomatizaProcesso() {
        return automatizaProcesso;
    }

    public void setAutomatizaProcesso(String automatizaProcesso) {
        this.automatizaProcesso = automatizaProcesso;
    }

    @XmlTransient
    public Collection<TbProjetoFuncionarios> getTbProjetoFuncionariosCollection() {
        return tbProjetoFuncionariosCollection;
    }

    public void setTbProjetoFuncionariosCollection(Collection<TbProjetoFuncionarios> tbProjetoFuncionariosCollection) {
        this.tbProjetoFuncionariosCollection = tbProjetoFuncionariosCollection;
    }

    public TbRecursos getTbRecursosHand() {
        return tbRecursosHand;
    }

    public void setTbRecursosHand(TbRecursos tbRecursosHand) {
        this.tbRecursosHand = tbRecursosHand;
    }

    public TbFuncionarios getTbFuncionariosHand() {
        return tbFuncionariosHand;
    }

    public void setTbFuncionariosHand(TbFuncionarios tbFuncionariosHand) {
        this.tbFuncionariosHand = tbFuncionariosHand;
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
        if (!(object instanceof TbFuncionariosRecursos)) {
            return false;
        }
        TbFuncionariosRecursos other = (TbFuncionariosRecursos) object;
        if ((this.hand == null && other.hand != null) || (this.hand != null && !this.hand.equals(other.hand))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.TbFuncionariosRecursos[ hand=" + hand + " ]";
    }
    
}
