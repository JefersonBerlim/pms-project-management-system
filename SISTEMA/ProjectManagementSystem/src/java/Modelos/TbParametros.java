/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
@Table(name = "TB_PARAMETROS")
@NamedQueries({
    @NamedQuery(name = "TbParametros.findAll", query = "SELECT t FROM TbParametros t")})
public class TbParametros implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "HAND")
    private Integer hand;
    @Column(name = "HORA_INICIO_NOTURNO")
    @Temporal(TemporalType.TIME)
    private Date horaInicioNoturno;
    @Column(name = "HORA_FIM_NOTURNO")
    @Temporal(TemporalType.TIME)
    private Date horaFimNoturno;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "PERCENT_ADICIONAL_NOTURNO")
    private BigDecimal percentAdicionalNoturno;
    @Column(name = "PERCENT_HR_EXTRA_NOTURNA")
    private BigDecimal percentHrExtraNoturna;
    @Column(name = "PERCENT_HR_EXTRA_DIURNA")
    private BigDecimal percentHrExtraDiurna;
    @Size(max = 50)
    @Column(name = "PREFIXO_VERSAO")
    private String prefixoVersao;
    @Size(max = 50)
    @Column(name = "PREFIXO_ORDEM_SERVICO")
    private String prefixoOrdemServico;
    @Size(max = 50)
    @Column(name = "PREFIXO_PROJETO")
    private String prefixoProjeto;
    @Column(name = "SEQUENCIA_OS")
    private Integer sequenciaOs;
    @Column(name = "SEQUENCIA_VERSAO")
    private Integer sequenciaVersao;
    @Column(name = "SEQUENCIA_PROJETO")
    private Integer sequenciaProjeto;

    public TbParametros() {
    }

    public TbParametros(Integer hand) {
        this.hand = hand;
    }

    public Integer getHand() {
        return hand;
    }

    public void setHand(Integer hand) {
        this.hand = hand;
    }

    public Date getHoraInicioNoturno() {
        return horaInicioNoturno;
    }

    public void setHoraInicioNoturno(Date horaInicioNoturno) {
        this.horaInicioNoturno = horaInicioNoturno;
    }

    public Date getHoraFimNoturno() {
        return horaFimNoturno;
    }

    public void setHoraFimNoturno(Date horaFimNoturno) {
        this.horaFimNoturno = horaFimNoturno;
    }

    public BigDecimal getPercentAdicionalNoturno() {
        return percentAdicionalNoturno;
    }

    public void setPercentAdicionalNoturno(BigDecimal percentAdicionalNoturno) {
        this.percentAdicionalNoturno = percentAdicionalNoturno;
    }

    public BigDecimal getPercentHrExtraNoturna() {
        return percentHrExtraNoturna;
    }

    public void setPercentHrExtraNoturna(BigDecimal percentHrExtraNoturna) {
        this.percentHrExtraNoturna = percentHrExtraNoturna;
    }

    public BigDecimal getPercentHrExtraDiurna() {
        return percentHrExtraDiurna;
    }

    public void setPercentHrExtraDiurna(BigDecimal percentHrExtraDiurna) {
        this.percentHrExtraDiurna = percentHrExtraDiurna;
    }

    public String getPrefixoVersao() {
        return prefixoVersao;
    }

    public void setPrefixoVersao(String prefixoVersao) {
        this.prefixoVersao = prefixoVersao;
    }

    public String getPrefixoOrdemServico() {
        return prefixoOrdemServico;
    }

    public void setPrefixoOrdemServico(String prefixoOrdemServico) {
        this.prefixoOrdemServico = prefixoOrdemServico;
    }

    public String getPrefixoProjeto() {
        return prefixoProjeto;
    }

    public void setPrefixoProjeto(String prefixoProjeto) {
        this.prefixoProjeto = prefixoProjeto;
    }

    public Integer getSequenciaOs() {
        return sequenciaOs;
    }

    public void setSequenciaOs(Integer sequenciaOs) {
        this.sequenciaOs = sequenciaOs;
    }

    public Integer getSequenciaVersao() {
        return sequenciaVersao;
    }

    public void setSequenciaVersao(Integer sequenciaVersao) {
        this.sequenciaVersao = sequenciaVersao;
    }

    public Integer getSequenciaProjeto() {
        return sequenciaProjeto;
    }

    public void setSequenciaProjeto(Integer sequenciaProjeto) {
        this.sequenciaProjeto = sequenciaProjeto;
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
        if (!(object instanceof TbParametros)) {
            return false;
        }
        TbParametros other = (TbParametros) object;
        if ((this.hand == null && other.hand != null) || (this.hand != null && !this.hand.equals(other.hand))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelos.TbParametros[ hand=" + hand + " ]";
    }

}
