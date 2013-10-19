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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author berlim
 */
@Entity
@Table(name = "TB_DIA_SEMANA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TbDiaSemana.findAll", query = "SELECT t FROM TbDiaSemana t"),
    @NamedQuery(name = "TbDiaSemana.findByHand", query = "SELECT t FROM TbDiaSemana t WHERE t.hand = :hand"),
    @NamedQuery(name = "TbDiaSemana.findByDescricao", query = "SELECT t FROM TbDiaSemana t WHERE t.descricao = :descricao")})
public class TbDiaSemana implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "HAND")
    private Integer hand;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "DESCRICAO")
    private String descricao;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tbDiaSemanaHand")
    private Collection<TbFuncionarioTurnoSemana> tbFuncionarioTurnoSemanaCollection;

    public TbDiaSemana() {
    }

    public TbDiaSemana(Integer hand) {
        this.hand = hand;
    }

    public TbDiaSemana(Integer hand, String descricao) {
        this.hand = hand;
        this.descricao = descricao;
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

    @XmlTransient
    public Collection<TbFuncionarioTurnoSemana> getTbFuncionarioTurnoSemanaCollection() {
        return tbFuncionarioTurnoSemanaCollection;
    }

    public void setTbFuncionarioTurnoSemanaCollection(Collection<TbFuncionarioTurnoSemana> tbFuncionarioTurnoSemanaCollection) {
        this.tbFuncionarioTurnoSemanaCollection = tbFuncionarioTurnoSemanaCollection;
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
        if (!(object instanceof TbDiaSemana)) {
            return false;
        }
        TbDiaSemana other = (TbDiaSemana) object;
        if ((this.hand == null && other.hand != null) || (this.hand != null && !this.hand.equals(other.hand))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.TbDiaSemana[ hand=" + hand + " ]";
    }
    
}
