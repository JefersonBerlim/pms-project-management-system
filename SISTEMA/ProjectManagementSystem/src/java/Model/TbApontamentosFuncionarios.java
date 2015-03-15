/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author BERLIM
 */
@Entity
@Table(name = "TB_APONTAMENTOS_FUNCIONARIOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TbApontamentosFuncionarios.findAll", query = "SELECT t FROM TbApontamentosFuncionarios t"),
    @NamedQuery(name = "TbApontamentosFuncionarios.findByHand", query = "SELECT t FROM TbApontamentosFuncionarios t WHERE t.hand = :hand"),
    @NamedQuery(name = "TbApontamentosFuncionarios.findByDataInicio", query = "SELECT t FROM TbApontamentosFuncionarios t WHERE t.dataInicio = :dataInicio"),
    @NamedQuery(name = "TbApontamentosFuncionarios.findByDataFim", query = "SELECT t FROM TbApontamentosFuncionarios t WHERE t.dataFim = :dataFim"),
    @NamedQuery(name = "TbApontamentosFuncionarios.findByHoraInicio", query = "SELECT t FROM TbApontamentosFuncionarios t WHERE t.horaInicio = :horaInicio"),
    @NamedQuery(name = "TbApontamentosFuncionarios.findByHoraFim", query = "SELECT t FROM TbApontamentosFuncionarios t WHERE t.horaFim = :horaFim"),
    @NamedQuery(name = "TbApontamentosFuncionarios.findByFinalizaApontamento", query = "SELECT t FROM TbApontamentosFuncionarios t WHERE t.finalizaApontamento = :finalizaApontamento")})
public class TbApontamentosFuncionarios implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "HAND")
    private Integer hand;
    @Column(name = "DATA_INICIO")
    @Temporal(TemporalType.DATE)
    private Date dataInicio;
    @Column(name = "DATA_FIM")
    @Temporal(TemporalType.DATE)
    private Date dataFim;
    @Column(name = "HORA_INICIO")
    @Temporal(TemporalType.TIME)
    private Date horaInicio;
    @Column(name = "HORA_FIM")
    @Temporal(TemporalType.TIME)
    private Date horaFim;
    @Size(max = 1)
    @Column(name = "FINALIZA_APONTAMENTO")
    private String finalizaApontamento;
    @JoinColumn(name = "TB_APONTAMENTOS_RECURSOS_HAND", referencedColumnName = "HAND")
    @ManyToOne(optional = false)
    private TbApontamentosRecursos tbApontamentosRecursosHand;
    @JoinColumn(name = "TB_FUNCIONARIOS_HAND", referencedColumnName = "HAND")
    @ManyToOne(optional = false)
    private TbFuncionarios tbFuncionariosHand;

    public TbApontamentosFuncionarios() {
    }

    public TbApontamentosFuncionarios(Integer hand) {
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

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public Date getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Date horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Date getHoraFim() {
        return horaFim;
    }

    public void setHoraFim(Date horaFim) {
        this.horaFim = horaFim;
    }

    public String getFinalizaApontamento() {
        return finalizaApontamento;
    }

    public void setFinalizaApontamento(String finalizaApontamento) {
        this.finalizaApontamento = finalizaApontamento;
    }

    public TbApontamentosRecursos getTbApontamentosRecursosHand() {
        return tbApontamentosRecursosHand;
    }

    public void setTbApontamentosRecursosHand(TbApontamentosRecursos tbApontamentosRecursosHand) {
        this.tbApontamentosRecursosHand = tbApontamentosRecursosHand;
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
        if (!(object instanceof TbApontamentosFuncionarios)) {
            return false;
        }
        TbApontamentosFuncionarios other = (TbApontamentosFuncionarios) object;
        if ((this.hand == null && other.hand != null) || (this.hand != null && !this.hand.equals(other.hand))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.TbApontamentosFuncionarios[ hand=" + hand + " ]";
    }
    
}
