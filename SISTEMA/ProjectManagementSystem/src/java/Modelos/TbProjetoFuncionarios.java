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
@Table(name = "TB_PROJETO_FUNCIONARIOS")
@NamedQueries({
    @NamedQuery(name = "TbProjetoFuncionarios.findAll", query = "SELECT t FROM TbProjetoFuncionarios t")})
public class TbProjetoFuncionarios implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "HAND")
    private Integer hand;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "QUANTIDADE_HORAS")
    private BigDecimal quantidadeHoras;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tbProjetoFuncionariosHand")
    private Collection<TbOsFuncionariosTotal> tbOsFuncionariosTotalCollection;
    @JoinColumn(name = "TB_PROJETOS_RECURSOS_HAND", referencedColumnName = "HAND")
    @ManyToOne(optional = false)
    private TbProjetosRecursos tbProjetosRecursosHand;
    @JoinColumn(name = "TB_FUNCIONARIOS_HAND", referencedColumnName = "HAND")
    @ManyToOne(optional = false)
    private TbFuncionarios tbFuncionariosHand;

    public TbProjetoFuncionarios() {
    }

    public TbProjetoFuncionarios(Integer hand) {
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

    public Collection<TbOsFuncionariosTotal> getTbOsFuncionariosTotalCollection() {
        return tbOsFuncionariosTotalCollection;
    }

    public void setTbOsFuncionariosTotalCollection(Collection<TbOsFuncionariosTotal> tbOsFuncionariosTotalCollection) {
        this.tbOsFuncionariosTotalCollection = tbOsFuncionariosTotalCollection;
    }

    public TbProjetosRecursos getTbProjetosRecursosHand() {
        return tbProjetosRecursosHand;
    }

    public void setTbProjetosRecursosHand(TbProjetosRecursos tbProjetosRecursosHand) {
        this.tbProjetosRecursosHand = tbProjetosRecursosHand;
    }

    public TbFuncionarios getTbFuncionariosHand() {
        return tbFuncionariosHand;
    }

    public void setTbFuncionariosHand(TbFuncionarios tbFuncionariosHand) {
        this.tbFuncionariosHand = tbFuncionariosHand;
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
        if (!(object instanceof TbProjetoFuncionarios)) {
            return false;
        }
        TbProjetoFuncionarios other = (TbProjetoFuncionarios) object;
        if ((this.hand == null && other.hand != null) || (this.hand != null && !this.hand.equals(other.hand))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelos.TbProjetoFuncionarios[ hand=" + hand + " ]";
    }

}
