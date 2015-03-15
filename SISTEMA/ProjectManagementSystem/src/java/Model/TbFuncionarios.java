/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author BERLIM
 */
@Entity
@Table(name = "TB_FUNCIONARIOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TbFuncionarios.findAll", query = "SELECT t FROM TbFuncionarios t"),
    @NamedQuery(name = "TbFuncionarios.findByHand", query = "SELECT t FROM TbFuncionarios t WHERE t.hand = :hand"),
    @NamedQuery(name = "TbFuncionarios.findByNome", query = "SELECT t FROM TbFuncionarios t WHERE t.nome = :nome"),
    @NamedQuery(name = "TbFuncionarios.findByNumeroMatricula", query = "SELECT t FROM TbFuncionarios t WHERE t.numeroMatricula = :numeroMatricula"),
    @NamedQuery(name = "TbFuncionarios.findByDataAdmissao", query = "SELECT t FROM TbFuncionarios t WHERE t.dataAdmissao = :dataAdmissao"),
    @NamedQuery(name = "TbFuncionarios.findByCpf", query = "SELECT t FROM TbFuncionarios t WHERE t.cpf = :cpf"),
    @NamedQuery(name = "TbFuncionarios.findByTelefone", query = "SELECT t FROM TbFuncionarios t WHERE t.telefone = :telefone"),
    @NamedQuery(name = "TbFuncionarios.findByTelefone2", query = "SELECT t FROM TbFuncionarios t WHERE t.telefone2 = :telefone2"),
    @NamedQuery(name = "TbFuncionarios.findByValorHora", query = "SELECT t FROM TbFuncionarios t WHERE t.valorHora = :valorHora"),
    @NamedQuery(name = "TbFuncionarios.findByNumeroIdentidade", query = "SELECT t FROM TbFuncionarios t WHERE t.numeroIdentidade = :numeroIdentidade"),
    @NamedQuery(name = "TbFuncionarios.findByEmail", query = "SELECT t FROM TbFuncionarios t WHERE t.email = :email"),
    @NamedQuery(name = "TbFuncionarios.findByDataNascimento", query = "SELECT t FROM TbFuncionarios t WHERE t.dataNascimento = :dataNascimento"),
    @NamedQuery(name = "TbFuncionarios.findByEhPlanejador", query = "SELECT t FROM TbFuncionarios t WHERE t.ehPlanejador = :ehPlanejador"),
    @NamedQuery(name = "TbFuncionarios.findByEhInativo", query = "SELECT t FROM TbFuncionarios t WHERE t.ehInativo = :ehInativo")})
public class TbFuncionarios implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "HAND")
    private Integer hand;
    @Size(max = 150)
    @Column(name = "NOME")
    private String nome;
    @Size(max = 50)
    @Column(name = "NUMERO_MATRICULA")
    private String numeroMatricula;
    @Column(name = "DATA_ADMISSAO")
    @Temporal(TemporalType.DATE)
    private Date dataAdmissao;
    @Size(max = 15)
    @Column(name = "CPF")
    private String cpf;
    @Size(max = 20)
    @Column(name = "TELEFONE")
    private String telefone;
    @Size(max = 20)
    @Column(name = "TELEFONE2")
    private String telefone2;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "VALOR_HORA")
    private BigDecimal valorHora;
    @Size(max = 15)
    @Column(name = "NUMERO_IDENTIDADE")
    private String numeroIdentidade;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="E-mail inválido")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 50)
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "DATA_NASCIMENTO")
    @Temporal(TemporalType.DATE)
    private Date dataNascimento;
    @Size(max = 1)
    @Column(name = "EH_PLANEJADOR")
    private String ehPlanejador;
    @Size(max = 1)
    @Column(name = "EH_INATIVO")
    private String ehInativo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tbFuncionariosHand")
    private Collection<TbFuncionarioTurnoSemana> tbFuncionarioTurnoSemanaCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tbFuncionariosHand")
    private Collection<TbFuncionariosRecursos> tbFuncionariosRecursosCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tbFuncionariosHand")
    private Collection<TbApontamentosMateriais> tbApontamentosMateriaisCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tbFuncionariosHand")
    private Collection<TbProjetos> tbProjetosCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tbFuncionariosHand")
    private Collection<TbApontamentosFuncionarios> tbApontamentosFuncionariosCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tbFuncionariosHand")
    private Collection<TbProjetoFuncionarios> tbProjetoFuncionariosCollection;

    public TbFuncionarios() {
    }

    public TbFuncionarios(Integer hand) {
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

    public String getNumeroMatricula() {
        return numeroMatricula;
    }

    public void setNumeroMatricula(String numeroMatricula) {
        this.numeroMatricula = numeroMatricula;
    }

    public Date getDataAdmissao() {
        return dataAdmissao;
    }

    public void setDataAdmissao(Date dataAdmissao) {
        this.dataAdmissao = dataAdmissao;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getTelefone2() {
        return telefone2;
    }

    public void setTelefone2(String telefone2) {
        this.telefone2 = telefone2;
    }

    public BigDecimal getValorHora() {
        return valorHora;
    }

    public void setValorHora(BigDecimal valorHora) {
        this.valorHora = valorHora;
    }

    public String getNumeroIdentidade() {
        return numeroIdentidade;
    }

    public void setNumeroIdentidade(String numeroIdentidade) {
        this.numeroIdentidade = numeroIdentidade;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getEhPlanejador() {
        return ehPlanejador;
    }

    public void setEhPlanejador(String ehPlanejador) {
        this.ehPlanejador = ehPlanejador;
    }

    public String getEhInativo() {
        return ehInativo;
    }

    public void setEhInativo(String ehInativo) {
        this.ehInativo = ehInativo;
    }

    @XmlTransient
    public Collection<TbFuncionarioTurnoSemana> getTbFuncionarioTurnoSemanaCollection() {
        return tbFuncionarioTurnoSemanaCollection;
    }

    public void setTbFuncionarioTurnoSemanaCollection(Collection<TbFuncionarioTurnoSemana> tbFuncionarioTurnoSemanaCollection) {
        this.tbFuncionarioTurnoSemanaCollection = tbFuncionarioTurnoSemanaCollection;
    }

    @XmlTransient
    public Collection<TbFuncionariosRecursos> getTbFuncionariosRecursosCollection() {
        return tbFuncionariosRecursosCollection;
    }

    public void setTbFuncionariosRecursosCollection(Collection<TbFuncionariosRecursos> tbFuncionariosRecursosCollection) {
        this.tbFuncionariosRecursosCollection = tbFuncionariosRecursosCollection;
    }

    @XmlTransient
    public Collection<TbApontamentosMateriais> getTbApontamentosMateriaisCollection() {
        return tbApontamentosMateriaisCollection;
    }

    public void setTbApontamentosMateriaisCollection(Collection<TbApontamentosMateriais> tbApontamentosMateriaisCollection) {
        this.tbApontamentosMateriaisCollection = tbApontamentosMateriaisCollection;
    }

    @XmlTransient
    public Collection<TbProjetos> getTbProjetosCollection() {
        return tbProjetosCollection;
    }

    public void setTbProjetosCollection(Collection<TbProjetos> tbProjetosCollection) {
        this.tbProjetosCollection = tbProjetosCollection;
    }

    @XmlTransient
    public Collection<TbApontamentosFuncionarios> getTbApontamentosFuncionariosCollection() {
        return tbApontamentosFuncionariosCollection;
    }

    public void setTbApontamentosFuncionariosCollection(Collection<TbApontamentosFuncionarios> tbApontamentosFuncionariosCollection) {
        this.tbApontamentosFuncionariosCollection = tbApontamentosFuncionariosCollection;
    }

    @XmlTransient
    public Collection<TbProjetoFuncionarios> getTbProjetoFuncionariosCollection() {
        return tbProjetoFuncionariosCollection;
    }

    public void setTbProjetoFuncionariosCollection(Collection<TbProjetoFuncionarios> tbProjetoFuncionariosCollection) {
        this.tbProjetoFuncionariosCollection = tbProjetoFuncionariosCollection;
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
        if (!(object instanceof TbFuncionarios)) {
            return false;
        }
        TbFuncionarios other = (TbFuncionarios) object;
        if ((this.hand == null && other.hand != null) || (this.hand != null && !this.hand.equals(other.hand))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.TbFuncionarios[ hand=" + hand + " ]";
    }
    
}
