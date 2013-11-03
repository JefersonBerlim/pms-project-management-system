/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author berlim
 */
@Entity
@Table(name = "TB_PROJETO_MARCOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TbProjetoMarcos.findAll", query = "SELECT t FROM TbProjetoMarcos t"),
    @NamedQuery(name = "TbProjetoMarcos.findByHand", query = "SELECT t FROM TbProjetoMarcos t WHERE t.hand = :hand"),
    @NamedQuery(name = "TbProjetoMarcos.findByDataInicio", query = "SELECT t FROM TbProjetoMarcos t WHERE t.dataInicio = :dataInicio"),
    @NamedQuery(name = "TbProjetoMarcos.findByHoraInicio", query = "SELECT t FROM TbProjetoMarcos t WHERE t.horaInicio = :horaInicio"),
    @NamedQuery(name = "TbProjetoMarcos.findByDataFim", query = "SELECT t FROM TbProjetoMarcos t WHERE t.dataFim = :dataFim"),
    @NamedQuery(name = "TbProjetoMarcos.findByHoraFim", query = "SELECT t FROM TbProjetoMarcos t WHERE t.horaFim = :horaFim")})
public class TbProjetoMarcos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tbProjetoMarcosHand")
    private Collection<TbProjetosServicos> tbProjetosServicosCollection;
    @JoinColumn(name = "TB_PROJETO_HAND", referencedColumnName = "HAND")
    @ManyToOne(optional = false)
    private TbProjetos tbProjetoHand;
    @JoinColumn(name = "TB_MARCOS_HAND", referencedColumnName = "HAND")
    @ManyToOne(optional = false)
    private TbMarcos tbMarcosHand;

    public TbProjetoMarcos() {
    }

    public TbProjetoMarcos(Integer hand) {
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

    @XmlTransient
    public Collection<TbProjetosServicos> getTbProjetosServicosCollection() {
        return tbProjetosServicosCollection;
    }

    public void setTbProjetosServicosCollection(Collection<TbProjetosServicos> tbProjetosServicosCollection) {
        this.tbProjetosServicosCollection = tbProjetosServicosCollection;
    }

    public TbProjetos getTbProjetoHand() {
        return tbProjetoHand;
    }

    public void setTbProjetoHand(TbProjetos tbProjetoHand) {
        this.tbProjetoHand = tbProjetoHand;
    }

    public TbMarcos getTbMarcosHand() {
        return tbMarcosHand;
    }

    public void setTbMarcosHand(TbMarcos tbMarcosHand) {
        this.tbMarcosHand = tbMarcosHand;
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
        if (!(object instanceof TbProjetoMarcos)) {
            return false;
        }
        TbProjetoMarcos other = (TbProjetoMarcos) object;
        if ((this.hand == null && other.hand != null) || (this.hand != null && !this.hand.equals(other.hand))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.TbProjetoMarcos[ hand=" + hand + " ]";
    }
    
}
