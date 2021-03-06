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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author jeferson
 */
@Entity
@Table(name = "TB_MATERIAIS")
@NamedQueries({
    @NamedQuery(name = "TbMateriais.findAll", query = " SELECT t FROM TbMateriais t "),
    @NamedQuery(name = "TbMateriais.materiaisNaoVinculados", query = "SELECT t FROM TbMateriais t "
            + " WHERE t.ehInativo <> 'S' "
            + " AND t.hand NOT IN ( SELECT i.tbMateriaisHand.hand from TbMaterialMarcoSvc i "
            + " WHERE i.tbMarcosServicosHand.hand = :marcoservico) "),
    @NamedQuery(name = "TbMateriais.materiaisVinculados", query = "SELECT t FROM TbMateriais t "
            + " INNER JOIN TbMaterialMarcoSvc mms ON t.hand = mms.tbMateriaisHand.hand "
            + " WHERE mms.tbMarcosServicosHand.hand = :marcoservico and t.ehInativo <> 'S' ")})
public class TbMateriais implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "HAND")
    private Integer hand;
    @Size(max = 50)
    @Column(name = "DESCRICAO")
    private String descricao;
    @Size(max = 255)
    @Column(name = "OBSERVACAO")
    private String observacao;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "VALOR")
    private BigDecimal valor;
    @Size(max = 1)
    @Column(name = "EH_INATIVO")
    private String ehInativo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tbMateriaisHand")
    private Collection<TbMaterialMarcoSvc> tbMaterialMarcoSvcCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tbMateriaisHand")
    private Collection<TbApontamentosMateriais> tbApontamentosMateriaisCollection;
    @JoinColumn(name = "TB_UNIDADE_MEDIDA_HAND", referencedColumnName = "HAND")
    @ManyToOne(optional = false)
    private TbUnidadeMedida tbUnidadeMedidaHand;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tbMateriaisHand")
    private Collection<TbProjetosMateriais> tbProjetosMateriaisCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tbMateriaisHand")
    private Collection<TbOsMateriaisTotal> tbOsMateriaisTotalCollection;

    @Transient
    private boolean tmpEhInativo;

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

    public Collection<TbMaterialMarcoSvc> getTbMaterialMarcoSvcCollection() {
        return tbMaterialMarcoSvcCollection;
    }

    public void setTbMaterialMarcoSvcCollection(Collection<TbMaterialMarcoSvc> tbMaterialMarcoSvcCollection) {
        this.tbMaterialMarcoSvcCollection = tbMaterialMarcoSvcCollection;
    }

    public Collection<TbApontamentosMateriais> getTbApontamentosMateriaisCollection() {
        return tbApontamentosMateriaisCollection;
    }

    public void setTbApontamentosMateriaisCollection(Collection<TbApontamentosMateriais> tbApontamentosMateriaisCollection) {
        this.tbApontamentosMateriaisCollection = tbApontamentosMateriaisCollection;
    }

    public TbUnidadeMedida getTbUnidadeMedidaHand() {
        return tbUnidadeMedidaHand;
    }

    public void setTbUnidadeMedidaHand(TbUnidadeMedida tbUnidadeMedidaHand) {
        this.tbUnidadeMedidaHand = tbUnidadeMedidaHand;
    }

    public Collection<TbProjetosMateriais> getTbProjetosMateriaisCollection() {
        return tbProjetosMateriaisCollection;
    }

    public void setTbProjetosMateriaisCollection(Collection<TbProjetosMateriais> tbProjetosMateriaisCollection) {
        this.tbProjetosMateriaisCollection = tbProjetosMateriaisCollection;
    }

    public Collection<TbOsMateriaisTotal> getTbOsMateriaisTotalCollection() {
        return tbOsMateriaisTotalCollection;
    }

    public void setTbOsMateriaisTotalCollection(Collection<TbOsMateriaisTotal> tbOsMateriaisTotalCollection) {
        this.tbOsMateriaisTotalCollection = tbOsMateriaisTotalCollection;
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
        return "Modelos.TbMateriais[ hand=" + hand + " ]";
    }

}
