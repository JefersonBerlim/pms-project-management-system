/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author jeferson
 */
@Entity
@Table(name = "TB_SERVICOS")
@NamedQueries({
    @NamedQuery(name = "TbServicos.findAll", query = "SELECT t FROM TbServicos t"),
    @NamedQuery(name = "TbServicos.servicosVinculados", query = "SELECT t FROM TbServicos t "
            + "INNER JOIN TbMarcosServicos ms ON t.hand = ms.tbServicosHand.hand "
            + "WHERE ms.tbMarcosHand.hand = :marco and t.ehInativo <> 'S'")})
public class TbServicos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "HAND")
    private Integer hand;
    @Size(max = 50)
    @Column(name = "DESCRICAO")
    private String descricao;
    @Column(name = "VALOR_HORA")
    private BigDecimal valorHora;
    @Size(max = 1)
    @Column(name = "EH_INATIVO")
    private String ehInativo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tbServicosHand")
    private Collection<TbMarcosServicos> tbMarcosServicosCollection;
    @OneToMany(mappedBy = "servicoDependente")
    private Collection<TbProjetosServicos> tbProjetosServicosCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tbServicosHand")
    private Collection<TbProjetosServicos> tbProjetosServicosCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tbServicosHand")
    private Collection<TbRecursosServicos> tbRecursosServicosCollection;

    @Transient
    private boolean tmpEhInativo;

    public TbServicos() {
    }

    public TbServicos(Integer hand) {
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

    public BigDecimal getValorHora() {
        return valorHora;
    }

    public void setValorHora(BigDecimal valorHora) {
        this.valorHora = valorHora;
    }

    public String getEhInativo() {
        return ehInativo;
    }

    public void setEhInativo(String ehInativo) {
        this.ehInativo = ehInativo;
    }

    public Collection<TbMarcosServicos> getTbMarcosServicosCollection() {
        return tbMarcosServicosCollection;
    }

    public void setTbMarcosServicosCollection(Collection<TbMarcosServicos> tbMarcosServicosCollection) {
        this.tbMarcosServicosCollection = tbMarcosServicosCollection;
    }

    public Collection<TbProjetosServicos> getTbProjetosServicosCollection() {
        return tbProjetosServicosCollection;
    }

    public void setTbProjetosServicosCollection(Collection<TbProjetosServicos> tbProjetosServicosCollection) {
        this.tbProjetosServicosCollection = tbProjetosServicosCollection;
    }

    public Collection<TbProjetosServicos> getTbProjetosServicosCollection1() {
        return tbProjetosServicosCollection1;
    }

    public void setTbProjetosServicosCollection1(Collection<TbProjetosServicos> tbProjetosServicosCollection1) {
        this.tbProjetosServicosCollection1 = tbProjetosServicosCollection1;
    }

    public Collection<TbRecursosServicos> getTbRecursosServicosCollection() {
        return tbRecursosServicosCollection;
    }

    public void setTbRecursosServicosCollection(Collection<TbRecursosServicos> tbRecursosServicosCollection) {
        this.tbRecursosServicosCollection = tbRecursosServicosCollection;
    }

    public boolean isTmpEhInativo() {
        return tmpEhInativo;
    }

    public void setTmpEhInativo(boolean tmpEhInativo) {
        this.tmpEhInativo = tmpEhInativo;
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
        if (!(object instanceof TbServicos)) {
            return false;
        }
        TbServicos other = (TbServicos) object;
        if ((this.hand == null && other.hand != null) || (this.hand != null && !this.hand.equals(other.hand))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return descricao;
    }

}
