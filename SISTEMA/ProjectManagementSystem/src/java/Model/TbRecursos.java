/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author BERLIM
 */
@Entity
@Table(name = "TB_RECURSOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TbRecursos.findAll", query = "SELECT t FROM TbRecursos t"),
    @NamedQuery(name = "TbRecursos.findByHand", query = "SELECT t FROM TbRecursos t WHERE t.hand = :hand"),
    @NamedQuery(name = "TbRecursos.findByDescricao", query = "SELECT t FROM TbRecursos t WHERE t.descricao = :descricao"),
    @NamedQuery(name = "TbRecursos.findByValorHora", query = "SELECT t FROM TbRecursos t WHERE t.valorHora = :valorHora"),
    @NamedQuery(name = "TbRecursos.findByEhInativo", query = "SELECT t FROM TbRecursos t WHERE t.ehInativo = :ehInativo"),
    @NamedQuery(name = "TbRecursos.findByFuncionarioSimultaneo", query = "SELECT t FROM TbRecursos t WHERE t.funcionarioSimultaneo = :funcionarioSimultaneo")})
public class TbRecursos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "HAND")
    private Integer hand;
    @Size(max = 50)
    @Column(name = "DESCRICAO")
    private String descricao;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "VALOR_HORA")
    private BigDecimal valorHora;
    @Size(max = 1)
    @Column(name = "EH_INATIVO")
    private String ehInativo;
    @Size(max = 1)
    @Column(name = "FUNCIONARIO_SIMULTANEO")
    private String funcionarioSimultaneo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tbRecursosHand")
    private Collection<TbApontamentosRecursos> tbApontamentosRecursosCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tbRecursosHand")
    private Collection<TbFuncionariosRecursos> tbFuncionariosRecursosCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tbRecursosHand")
    private Collection<TbProjetosRecursos> tbProjetosRecursosCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tbRecursosHand")
    private Collection<TbRecursosServicos> tbRecursosServicosCollection;

    public TbRecursos() {
    }

    public TbRecursos(Integer hand) {
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

    public String getFuncionarioSimultaneo() {
        return funcionarioSimultaneo;
    }

    public void setFuncionarioSimultaneo(String funcionarioSimultaneo) {
        this.funcionarioSimultaneo = funcionarioSimultaneo;
    }

    @XmlTransient
    public Collection<TbApontamentosRecursos> getTbApontamentosRecursosCollection() {
        return tbApontamentosRecursosCollection;
    }

    public void setTbApontamentosRecursosCollection(Collection<TbApontamentosRecursos> tbApontamentosRecursosCollection) {
        this.tbApontamentosRecursosCollection = tbApontamentosRecursosCollection;
    }

    @XmlTransient
    public Collection<TbFuncionariosRecursos> getTbFuncionariosRecursosCollection() {
        return tbFuncionariosRecursosCollection;
    }

    public void setTbFuncionariosRecursosCollection(Collection<TbFuncionariosRecursos> tbFuncionariosRecursosCollection) {
        this.tbFuncionariosRecursosCollection = tbFuncionariosRecursosCollection;
    }

    @XmlTransient
    public Collection<TbProjetosRecursos> getTbProjetosRecursosCollection() {
        return tbProjetosRecursosCollection;
    }

    public void setTbProjetosRecursosCollection(Collection<TbProjetosRecursos> tbProjetosRecursosCollection) {
        this.tbProjetosRecursosCollection = tbProjetosRecursosCollection;
    }

    @XmlTransient
    public Collection<TbRecursosServicos> getTbRecursosServicosCollection() {
        return tbRecursosServicosCollection;
    }

    public void setTbRecursosServicosCollection(Collection<TbRecursosServicos> tbRecursosServicosCollection) {
        this.tbRecursosServicosCollection = tbRecursosServicosCollection;
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
        if (!(object instanceof TbRecursos)) {
            return false;
        }
        TbRecursos other = (TbRecursos) object;
        if ((this.hand == null && other.hand != null) || (this.hand != null && !this.hand.equals(other.hand))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.TbRecursos[ hand=" + hand + " ]";
    }
    
}
