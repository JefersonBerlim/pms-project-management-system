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
@Table(name = "TB_MATERIAIS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TbMateriais.findAll", query = "SELECT t FROM TbMateriais t"),
    @NamedQuery(name = "TbMateriais.findByHand", query = "SELECT t FROM TbMateriais t WHERE t.hand = :hand"),
    @NamedQuery(name = "TbMateriais.findByDescricao", query = "SELECT t FROM TbMateriais t WHERE t.descricao = :descricao"),
    @NamedQuery(name = "TbMateriais.findByObservacao", query = "SELECT t FROM TbMateriais t WHERE t.observacao = :observacao"),
    @NamedQuery(name = "TbMateriais.findByValor", query = "SELECT t FROM TbMateriais t WHERE t.valor = :valor"),
    @NamedQuery(name = "TbMateriais.findByEhInativo", query = "SELECT t FROM TbMateriais t WHERE t.ehInativo = :ehInativo")})
public class TbMateriais implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "HAND")
    private Integer hand;
    @Column(name = "DESCRICAO")
    private String descricao;
    @Column(name = "OBSERVACAO")
    private String observacao;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "VALOR")
    private BigDecimal valor;
    @Column(name = "EH_INATIVO")
    private String ehInativo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tbMateriaisHand")
    private Collection<TbApontamentosMateriais> tbApontamentosMateriaisCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tbMateriaisHand")
    private Collection<TbOsMateriais> tbOsMateriaisCollection;
    @JoinColumn(name = "TB_UNIDADE_MEDIDA_HAND", referencedColumnName = "HAND")
    @ManyToOne(optional = false)
    private TbUnidadeMedida tbUnidadeMedidaHand;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tbMateriaisHand")
    private Collection<TbProjetosMateriais> tbProjetosMateriaisCollection;

    public TbMateriais() {
    }

    public TbMateriais(Integer hand) {
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

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getEhInativo() {
        return ehInativo;
    }

    public void setEhInativo(String ehInativo) {
        this.ehInativo = ehInativo;
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

    public TbUnidadeMedida getTbUnidadeMedidaHand() {
        return tbUnidadeMedidaHand;
    }

    public void setTbUnidadeMedidaHand(TbUnidadeMedida tbUnidadeMedidaHand) {
        this.tbUnidadeMedidaHand = tbUnidadeMedidaHand;
    }

    @XmlTransient
    public Collection<TbProjetosMateriais> getTbProjetosMateriaisCollection() {
        return tbProjetosMateriaisCollection;
    }

    public void setTbProjetosMateriaisCollection(Collection<TbProjetosMateriais> tbProjetosMateriaisCollection) {
        this.tbProjetosMateriaisCollection = tbProjetosMateriaisCollection;
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
        if (!(object instanceof TbMateriais)) {
            return false;
        }
        TbMateriais other = (TbMateriais) object;
        if ((this.hand == null && other.hand != null) || (this.hand != null && !this.hand.equals(other.hand))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.TbMateriais[ hand=" + hand + " ]";
    }
    
}
