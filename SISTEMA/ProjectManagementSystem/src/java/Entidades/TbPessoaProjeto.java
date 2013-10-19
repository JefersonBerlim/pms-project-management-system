/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author berlim
 */
@Entity
@Table(name = "TB_PESSOA_PROJETO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TbPessoaProjeto.findAll", query = "SELECT t FROM TbPessoaProjeto t"),
    @NamedQuery(name = "TbPessoaProjeto.findByHand", query = "SELECT t FROM TbPessoaProjeto t WHERE t.hand = :hand")})
public class TbPessoaProjeto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "HAND")
    private Integer hand;
    @JoinColumn(name = "TB_PROJETO_HAND", referencedColumnName = "HAND")
    @ManyToOne(optional = false)
    private TbProjetos tbProjetoHand;
    @JoinColumn(name = "TB_PESSOA_HAND", referencedColumnName = "HAND")
    @ManyToOne(optional = false)
    private TbPessoa tbPessoaHand;

    public TbPessoaProjeto() {
    }

    public TbPessoaProjeto(Integer hand) {
        this.hand = hand;
    }

    public Integer getHand() {
        return hand;
    }

    public void setHand(Integer hand) {
        this.hand = hand;
    }

    public TbProjetos getTbProjetoHand() {
        return tbProjetoHand;
    }

    public void setTbProjetoHand(TbProjetos tbProjetoHand) {
        this.tbProjetoHand = tbProjetoHand;
    }

    public TbPessoa getTbPessoaHand() {
        return tbPessoaHand;
    }

    public void setTbPessoaHand(TbPessoa tbPessoaHand) {
        this.tbPessoaHand = tbPessoaHand;
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
        if (!(object instanceof TbPessoaProjeto)) {
            return false;
        }
        TbPessoaProjeto other = (TbPessoaProjeto) object;
        if ((this.hand == null && other.hand != null) || (this.hand != null && !this.hand.equals(other.hand))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.TbPessoaProjeto[ hand=" + hand + " ]";
    }
    
}
