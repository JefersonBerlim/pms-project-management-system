/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author jeferson
 */
@Entity
@Table(name = "TB_PROJETOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TbProjetos.findAll", query = "SELECT t FROM TbProjetos t"),
    @NamedQuery(name = "TbProjetos.findByHand", query = "SELECT t FROM TbProjetos t WHERE t.hand = :hand"),
    @NamedQuery(name = "TbProjetos.findByVersaoProjeto", query = "SELECT t FROM TbProjetos t WHERE t.versaoProjeto = :versaoProjeto"),
    @NamedQuery(name = "TbProjetos.findByDecsricao", query = "SELECT t FROM TbProjetos t WHERE t.decsricao = :decsricao"),
    @NamedQuery(name = "TbProjetos.findByDataAbertura", query = "SELECT t FROM TbProjetos t WHERE t.dataAbertura = :dataAbertura"),
    @NamedQuery(name = "TbProjetos.findByHoraAbertura", query = "SELECT t FROM TbProjetos t WHERE t.horaAbertura = :horaAbertura"),
    @NamedQuery(name = "TbProjetos.findByDataInicio", query = "SELECT t FROM TbProjetos t WHERE t.dataInicio = :dataInicio"),
    @NamedQuery(name = "TbProjetos.findByHoraInicio", query = "SELECT t FROM TbProjetos t WHERE t.horaInicio = :horaInicio"),
    @NamedQuery(name = "TbProjetos.findByDataFim", query = "SELECT t FROM TbProjetos t WHERE t.dataFim = :dataFim"),
    @NamedQuery(name = "TbProjetos.findByHoraFim", query = "SELECT t FROM TbProjetos t WHERE t.horaFim = :horaFim"),
    @NamedQuery(name = "TbProjetos.findByDataFechamento", query = "SELECT t FROM TbProjetos t WHERE t.dataFechamento = :dataFechamento"),
    @NamedQuery(name = "TbProjetos.findByHoraFechamento", query = "SELECT t FROM TbProjetos t WHERE t.horaFechamento = :horaFechamento"),
    @NamedQuery(name = "TbProjetos.findByQtdHoras", query = "SELECT t FROM TbProjetos t WHERE t.qtdHoras = :qtdHoras"),
    @NamedQuery(name = "TbProjetos.findByDataCriacao", query = "SELECT t FROM TbProjetos t WHERE t.dataCriacao = :dataCriacao"),
    @NamedQuery(name = "TbProjetos.findByHoraCriacao", query = "SELECT t FROM TbProjetos t WHERE t.horaCriacao = :horaCriacao"),
    @NamedQuery(name = "TbProjetos.findByEhInativo", query = "SELECT t FROM TbProjetos t WHERE t.ehInativo = :ehInativo")})
public class TbProjetos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "HAND")
    private Integer hand;
    @Size(max = 15)
    @Column(name = "VERSAO_PROJETO")
    private String versaoProjeto;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "DECSRICAO")
    private String decsricao;
    @Column(name = "DATA_ABERTURA")
    @Temporal(TemporalType.DATE)
    private Date dataAbertura;
    @Column(name = "HORA_ABERTURA")
    @Temporal(TemporalType.TIME)
    private Date horaAbertura;
    @Column(name = "DATA_INICIO")
    @Temporal(TemporalType.DATE)
    private Date dataInicio;
    @Column(name = "HORA_INICIO")
    @Temporal(TemporalType.TIME)
    private Date horaInicio;
    @Column(name = "DATA_FIM")
    @Temporal(TemporalType.DATE)
    private Date dataFim;
    @Column(name = "HORA_FIM")
    @Temporal(TemporalType.TIME)
    private Date horaFim;
    @Column(name = "DATA_FECHAMENTO")
    @Temporal(TemporalType.DATE)
    private Date dataFechamento;
    @Column(name = "HORA_FECHAMENTO")
    @Temporal(TemporalType.TIME)
    private Date horaFechamento;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "QTD_HORAS")
    private BigDecimal qtdHoras;
    @Column(name = "DATA_CRIACAO")
    @Temporal(TemporalType.DATE)
    private Date dataCriacao;
    @Column(name = "HORA_CRIACAO")
    @Temporal(TemporalType.TIME)
    private Date horaCriacao;
    @Size(max = 1)
    @Column(name = "EH_INATIVO")
    private String ehInativo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tbProjetosHand")
    private Collection<TbOrdemServico> tbOrdemServicoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tbProjetoHand")
    private Collection<TbProjetoMarcos> tbProjetoMarcosCollection;
    @JoinColumn(name = "TB_STATUS_HAND", referencedColumnName = "HAND")
    @ManyToOne(optional = false)
    private TbStatus tbStatusHand;
    @JoinColumn(name = "TB_PESSOA_HAND", referencedColumnName = "HAND")
    @ManyToOne(optional = false)
    private TbPessoa tbPessoaHand;
    @JoinColumn(name = "TB_FUNCIONARIOS_HAND", referencedColumnName = "HAND")
    @ManyToOne(optional = false)
    private TbFuncionarios tbFuncionariosHand;

    public TbProjetos() {
    }

    public TbProjetos(Integer hand) {
        this.hand = hand;
    }

    public TbProjetos(Integer hand, String decsricao) {
        this.hand = hand;
        this.decsricao = decsricao;
    }

    public Integer getHand() {
        return hand;
    }

    public void setHand(Integer hand) {
        this.hand = hand;
    }

    public String getVersaoProjeto() {
        return versaoProjeto;
    }

    public void setVersaoProjeto(String versaoProjeto) {
        this.versaoProjeto = versaoProjeto;
    }

    public String getDecsricao() {
        return decsricao;
    }

    public void setDecsricao(String decsricao) {
        this.decsricao = decsricao;
    }

    public Date getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(Date dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public Date getHoraAbertura() {
        return horaAbertura;
    }

    public void setHoraAbertura(Date horaAbertura) {
        this.horaAbertura = horaAbertura;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Date horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public Date getHoraFim() {
        return horaFim;
    }

    public void setHoraFim(Date horaFim) {
        this.horaFim = horaFim;
    }

    public Date getDataFechamento() {
        return dataFechamento;
    }

    public void setDataFechamento(Date dataFechamento) {
        this.dataFechamento = dataFechamento;
    }

    public Date getHoraFechamento() {
        return horaFechamento;
    }

    public void setHoraFechamento(Date horaFechamento) {
        this.horaFechamento = horaFechamento;
    }

    public BigDecimal getQtdHoras() {
        return qtdHoras;
    }

    public void setQtdHoras(BigDecimal qtdHoras) {
        this.qtdHoras = qtdHoras;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Date getHoraCriacao() {
        return horaCriacao;
    }

    public void setHoraCriacao(Date horaCriacao) {
        this.horaCriacao = horaCriacao;
    }

    public String getEhInativo() {
        return ehInativo;
    }

    public void setEhInativo(String ehInativo) {
        this.ehInativo = ehInativo;
    }

    @XmlTransient
    public Collection<TbOrdemServico> getTbOrdemServicoCollection() {
        return tbOrdemServicoCollection;
    }

    public void setTbOrdemServicoCollection(Collection<TbOrdemServico> tbOrdemServicoCollection) {
        this.tbOrdemServicoCollection = tbOrdemServicoCollection;
    }

    @XmlTransient
    public Collection<TbProjetoMarcos> getTbProjetoMarcosCollection() {
        return tbProjetoMarcosCollection;
    }

    public void setTbProjetoMarcosCollection(Collection<TbProjetoMarcos> tbProjetoMarcosCollection) {
        this.tbProjetoMarcosCollection = tbProjetoMarcosCollection;
    }

    public TbStatus getTbStatusHand() {
        return tbStatusHand;
    }

    public void setTbStatusHand(TbStatus tbStatusHand) {
        this.tbStatusHand = tbStatusHand;
    }

    public TbPessoa getTbPessoaHand() {
        return tbPessoaHand;
    }

    public void setTbPessoaHand(TbPessoa tbPessoaHand) {
        this.tbPessoaHand = tbPessoaHand;
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
        if (!(object instanceof TbProjetos)) {
            return false;
        }
        TbProjetos other = (TbProjetos) object;
        if ((this.hand == null && other.hand != null) || (this.hand != null && !this.hand.equals(other.hand))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelos.TbProjetos[ hand=" + hand + " ]";
    }
    
}
