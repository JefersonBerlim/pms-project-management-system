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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author jeferson
 */
@Entity
@Table(name = "TB_CIDADES")
@NamedQueries({
    @NamedQuery(name = "TbCidades.findAll", query = "SELECT t FROM TbCidades t")})
public class TbCidades implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "HAND")
    private Integer hand;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "CIDADE")
    private String cidade;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tbCidadesHand")
    private Collection<TbPessoa> tbPessoaCollection;
    @JoinColumn(name = "TB_ESTADOS_HAND", referencedColumnName = "HAND")
    @ManyToOne(optional = false)
    private TbEstados tbEstadosHand;

    public TbCidades() {
    }

    public TbCidades(Integer hand) {
        this.hand = hand;
    }

    public TbCidades(Integer hand, String cidade) {
        this.hand = hand;
        this.cidade = cidade;
    }

    public Integer getHand() {
        return hand;
    }

    public void setHand(Integer hand) {
        this.hand = hand;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public Collection<TbPessoa> getTbPessoaCollection() {
        return tbPessoaCollection;
    }

    public void setTbPessoaCollection(Collection<TbPessoa> tbPessoaCollection) {
        this.tbPessoaCollection = tbPessoaCollection;
    }

    public TbEstados getTbEstadosHand() {
        return tbEstadosHand;
    }

    public void setTbEstadosHand(TbEstados tbEstadosHand) {
        this.tbEstadosHand = tbEstadosHand;
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
        if (!(object instanceof TbCidades)) {
            return false;
        }
        TbCidades other = (TbCidades) object;
        if ((this.hand == null && other.hand != null) || (this.hand != null && !this.hand.equals(other.hand))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getCidade();
    }

}
