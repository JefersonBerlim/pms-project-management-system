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
 * @author jeferson
 */
@Entity
@Table(name = "TB_PROJETOS_MATERIAIS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TbProjetosMateriais.findAll", query = "SELECT t FROM TbProjetosMateriais t"),
    @NamedQuery(name = "TbProjetosMateriais.findByHand", query = "SELECT t FROM TbProjetosMateriais t WHERE t.hand = :hand"),
    @NamedQuery(name = "TbProjetosMateriais.findByQuantidade", query = "SELECT t FROM TbProjetosMateriais t WHERE t.quantidade = :quantidade"),
    @NamedQuery(name = "TbProjetosMateriais.findByInformacaoComplementar", query = "SELECT t FROM TbProjetosMateriais t WHERE t.informacaoComplementar = :informacaoComplementar")})
public class TbProjetosMateriais implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "HAND")
    private Integer hand;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "QUANTIDADE")
    private BigDecimal quantidade;
    @Size(max = 255)
    @Column(name = "INFORMACAO_COMPLEMENTAR")
    private String informacaoComplementar;
    @JoinColumn(name = "TB_PROJETOS_SERVICOS_HAND", referencedColumnName = "HAND")
    @ManyToOne(optional = false)
    private TbProjetosServicos tbProjetosServicosHand;
    @JoinColumn(name = "TB_MATERIAIS_HAND", referencedColumnName = "HAND")
    @ManyToOne(optional = false)
    private TbMateriais tbMateriaisHand;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tbProjetosMateriaisHand")
    private Collection<TbOsMateriaisTotal> tbOsMateriaisTotalCollection;

    public TbProjetosMateriais() {
    }

    public TbProjetosMateriais(Integer hand) {
        this.hand = hand;
    }

    public Integer getHand() {
        return hand;
    }

    public void setHand(Integer hand) {
        this.hand = hand;
    }

    public BigDecimal getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(BigDecimal quantidade) {
        this.quantidade = quantidade;
    }

    public String getInformacaoComplementar() {
        return informacaoComplementar;
    }

    public void setInformacaoComplementar(String informacaoComplementar) {
        this.informacaoComplementar = informacaoComplementar;
    }

    public TbProjetosServicos getTbProjetosServicosHand() {
        return tbProjetosServicosHand;
    }

    public void setTbProjetosServicosHand(TbProjetosServicos tbProjetosServicosHand) {
        this.tbProjetosServicosHand = tbProjetosServicosHand;
    }

    public TbMateriais getTbMateriaisHand() {
        return tbMateriaisHand;
    }

    public void setTbMateriaisHand(TbMateriais tbMateriaisHand) {
        this.tbMateriaisHand = tbMateriaisHand;
    }

    @XmlTransient
    public Collection<TbOsMateriaisTotal> getTbOsMateriaisTotalCollection() {
        return tbOsMateriaisTotalCollection;
    }

    public void setTbOsMateriaisTotalCollection(Collection<TbOsMateriaisTotal> tbOsMateriaisTotalCollection) {
        this.tbOsMateriaisTotalCollection = tbOsMateriaisTotalCollection;
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
        if (!(object instanceof TbProjetosMateriais)) {
            return false;
        }
        TbProjetosMateriais other = (TbProjetosMateriais) object;
        if ((this.hand == null && other.hand != null) || (this.hand != null && !this.hand.equals(other.hand))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelos.TbProjetosMateriais[ hand=" + hand + " ]";
    }
    
}
