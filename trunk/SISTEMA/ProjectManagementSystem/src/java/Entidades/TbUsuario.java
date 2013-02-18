/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import javax.annotation.ManagedBean;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author JEFERSON
 */
@Entity
@ManagedBean
@Table(name = "TB_USUARIO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TbUsuario.findAll", query = "SELECT t FROM TbUsuario t"),
    @NamedQuery(name = "TbUsuario.findByHand", query = "SELECT t FROM TbUsuario t WHERE t.hand = :hand"),
    @NamedQuery(name = "TbUsuario.login", query = "SELECT t FROM TbUsuario t WHERE t.senha = :senha and Upper( t.nome ) = Upper( :nome )"),
    @NamedQuery(name = "TbUsuario.findByNome", query = "SELECT t FROM TbUsuario t WHERE t.nome = :nome"),
    @NamedQuery(name = "TbUsuario.findBySenha", query = "SELECT t FROM TbUsuario t WHERE t.senha = :senha")})
public class TbUsuario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "HAND")
    private Integer hand;
    @Column(name = "NOME")
    private String nome;
    @Column(name = "SENHA")
    private String senha;

    public TbUsuario() {
    }

    public TbUsuario(Integer hand) {
        this.hand = hand;
    }

    public Integer getHand() {
        return hand;
    }

    public void setHand(Integer hand) {
        this.hand = hand;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
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
        if (!(object instanceof TbUsuario)) {
            return false;
        }
        TbUsuario other = (TbUsuario) object;
        if ((this.hand == null && other.hand != null) || (this.hand != null && !this.hand.equals(other.hand))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.TbUsuario[ hand=" + hand + " ]";
    }
    
}
