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

/**
 *
 * @author jeferson
 */
@Entity
@Table(name = "TB_PROJETOS_RECURSOS")
@NamedQueries({
    @NamedQuery(name = "TbProjetosRecursos.findAll", query = "SELECT t FROM TbProjetosRecursos t")})
public class TbProjetosRecursos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "HAND")
    private Integer hand;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "QUANTIDADE_HORAS")
    private BigDecimal quantidadeHoras;
    @JoinColumn(name = "TB_RECURSOS_HAND", referencedColumnName = "HAND")
    @ManyToOne(optional = false)
    private TbRecursos tbRecursosHand;
    @JoinColumn(name = "TB_PROJETOS_SERVICOS_HAND", referencedColumnName = "HAND")
    @ManyToOne(optional = false)
    private TbProjetosServicos tbProjetosServicosHand;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tbProjetosRecursosHand")
    private Collection<TbProjetoFuncionarios> tbProjetoFuncionariosCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tbProjetosRecursosHand")
    private Collection<TbOsRecursosTotal> tbOsRecursosTotalCollection;

    public TbProjetosRecursos() {
    }

    public TbProjetosRecursos(Integer hand) {
        this.hand = hand;
    }

    public Integer getHand() {
        return hand;
    }

    public void setHand(Integer hand) {
        this.hand = hand;
    }

    public BigDecimal getQuantidadeHoras() {
        return quantidadeHoras;
    }

    public void setQuantidadeHoras(BigDecimal quantidadeHoras) {
        this.quantidadeHoras = quantidadeHoras;
    }

    public TbRecursos getTbRecursosHand() {
        return tbRecursosHand;
    }

    public void setTbRecursosHand(TbRecursos tbRecursosHand) {
        this.tbRecursosHand = tbRecursosHand;
    }

    public TbProjetosServicos getTbProjetosServicosHand() {
        return tbProjetosServicosHand;
    }

    public void setTbProjetosServicosHand(TbProjetosServicos tbProjetosServicosHand) {
        this.tbProjetosServicosHand = tbProjetosServicosHand;
    }

    public Collection<TbProjetoFuncionarios> getTbProjetoFuncionariosCollection() {
        return tbProjetoFuncionariosCollection;
    }

    public void setTbProjetoFuncionariosCollection(Collection<TbProjetoFuncionarios> tbProjetoFuncionariosCollection) {
        this.tbProjetoFuncionariosCollection = tbProjetoFuncionariosCollection;
    }

    public Collection<TbOsRecursosTotal> getTbOsRecursosTotalCollection() {
        return tbOsRecursosTotalCollection;
    }

    public void setTbOsRecursosTotalCollection(Collection<TbOsRecursosTotal> tbOsRecursosTotalCollection) {
        this.tbOsRecursosTotalCollection = tbOsRecursosTotalCollection;
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
        if (!(object instanceof TbProjetosRecursos)) {
            return false;
        }
        TbProjetosRecursos other = (TbProjetosRecursos) object;
        if ((this.hand == null && other.hand != null) || (this.hand != null && !this.hand.equals(other.hand))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelos.TbProjetosRecursos[ hand=" + hand + " ]";
    }

}
