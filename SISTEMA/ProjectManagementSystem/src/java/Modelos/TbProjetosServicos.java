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

/**
 *
 * @author jeferson
 */
@Entity
@Table(name = "TB_PROJETOS_SERVICOS")
@NamedQueries({
    @NamedQuery(name = "TbProjetosServicos.findAll", query = "SELECT t FROM TbProjetosServicos t")})
public class TbProjetosServicos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "HAND")
    private Integer hand;
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
    @Size(max = 255)
    @Column(name = "OBSERVACAO")
    private String observacao;
    @Size(max = 50)
    @Column(name = "ARQUIVOS")
    private String arquivos;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "QTD_HORAS")
    private BigDecimal qtdHoras;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tbProjetosServicosHand")
    private Collection<TbProjetosRecursos> tbProjetosRecursosCollection;
    @JoinColumn(name = "SERVICO_DEPENDENTE", referencedColumnName = "HAND")
    @ManyToOne
    private TbServicos servicoDependente;
    @JoinColumn(name = "TB_SERVICOS_HAND", referencedColumnName = "HAND")
    @ManyToOne(optional = false)
    private TbServicos tbServicosHand;
    @JoinColumn(name = "TB_PROJETO_MARCOS_HAND", referencedColumnName = "HAND")
    @ManyToOne(optional = false)
    private TbProjetoMarcos tbProjetoMarcosHand;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tbProjetosServicosHand")
    private Collection<TbOsServico> tbOsServicoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tbProjetosServicosHand")
    private Collection<TbProjetosMateriais> tbProjetosMateriaisCollection;

    public TbProjetosServicos() {
    }

    public TbProjetosServicos(Integer hand) {
        this.hand = hand;
    }

    public Integer getHand() {
        return hand;
    }

    public void setHand(Integer hand) {
        this.hand = hand;
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

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getArquivos() {
        return arquivos;
    }

    public void setArquivos(String arquivos) {
        this.arquivos = arquivos;
    }

    public BigDecimal getQtdHoras() {
        return qtdHoras;
    }

    public void setQtdHoras(BigDecimal qtdHoras) {
        this.qtdHoras = qtdHoras;
    }

    public Collection<TbProjetosRecursos> getTbProjetosRecursosCollection() {
        return tbProjetosRecursosCollection;
    }

    public void setTbProjetosRecursosCollection(Collection<TbProjetosRecursos> tbProjetosRecursosCollection) {
        this.tbProjetosRecursosCollection = tbProjetosRecursosCollection;
    }

    public TbServicos getServicoDependente() {
        return servicoDependente;
    }

    public void setServicoDependente(TbServicos servicoDependente) {
        this.servicoDependente = servicoDependente;
    }

    public TbServicos getTbServicosHand() {
        return tbServicosHand;
    }

    public void setTbServicosHand(TbServicos tbServicosHand) {
        this.tbServicosHand = tbServicosHand;
    }

    public TbProjetoMarcos getTbProjetoMarcosHand() {
        return tbProjetoMarcosHand;
    }

    public void setTbProjetoMarcosHand(TbProjetoMarcos tbProjetoMarcosHand) {
        this.tbProjetoMarcosHand = tbProjetoMarcosHand;
    }

    public Collection<TbOsServico> getTbOsServicoCollection() {
        return tbOsServicoCollection;
    }

    public void setTbOsServicoCollection(Collection<TbOsServico> tbOsServicoCollection) {
        this.tbOsServicoCollection = tbOsServicoCollection;
    }

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
        if (!(object instanceof TbProjetosServicos)) {
            return false;
        }
        TbProjetosServicos other = (TbProjetosServicos) object;
        if ((this.hand == null && other.hand != null) || (this.hand != null && !this.hand.equals(other.hand))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelos.TbProjetosServicos[ hand=" + hand + " ]";
    }

}
