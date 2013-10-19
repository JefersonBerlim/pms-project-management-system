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
@Table(name = "TB_TIPOPESSOA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TbTipopessoa.findAll", query = "SELECT t FROM TbTipopessoa t"),
    @NamedQuery(name = "TbTipopessoa.findByHand", query = "SELECT t FROM TbTipopessoa t WHERE t.hand = :hand"),
    @NamedQuery(name = "TbTipopessoa.findByTipo", query = "SELECT t FROM TbTipopessoa t WHERE t.tipo = :tipo"),
    @NamedQuery(name = "TbTipopessoa.findByDescricao", query = "SELECT t FROM TbTipopessoa t WHERE t.descricao = :descricao")})
public class TbTipopessoa implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "HAND")
    private Integer hand;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "TIPO")
    private String tipo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "DESCRICAO")
    private String descricao;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tbTipopessoaHand")
    private Collection<TbPessoa> tbPessoaCollection;

    public TbTipopessoa() {
    }

    public TbTipopessoa(Integer hand) {
        this.hand = hand;
    }

    public TbTipopessoa(Integer hand, String tipo, String descricao) {
        this.hand = hand;
        this.tipo = tipo;
        this.descricao = descricao;
    }

    public Integer getHand() {
        return hand;
    }

    public void setHand(Integer hand) {
        this.hand = hand;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @XmlTransient
    public Collection<TbPessoa> getTbPessoaCollection() {
        return tbPessoaCollection;
    }

    public void setTbPessoaCollection(Collection<TbPessoa> tbPessoaCollection) {
        this.tbPessoaCollection = tbPessoaCollection;
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
        if (!(object instanceof TbTipopessoa)) {
            return false;
        }
        TbTipopessoa other = (TbTipopessoa) object;
        if ((this.hand == null && other.hand != null) || (this.hand != null && !this.hand.equals(other.hand))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.TbTipopessoa[ hand=" + hand + " ]";
    }
    
}
