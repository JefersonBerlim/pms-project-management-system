/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;

import java.io.Serializable;
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

/**
 *
 * @author jeferson
 */
@Entity
@Table(name = "TB_DIA_NAO_UTIL")
@NamedQueries({
    @NamedQuery(name = "TbDiaNaoUtil.findAll", query = "SELECT t FROM TbDiaNaoUtil t")})
public class TbDiaNaoUtil implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "HAND")
    private Integer hand;
    @Column(name = "DIA_NAO_UTIL")
    @Temporal(TemporalType.DATE)
    private Date diaNaoUtil;

    public TbDiaNaoUtil() {
    }

    public TbDiaNaoUtil(Integer hand) {
        this.hand = hand;
    }

    public Integer getHand() {
        return hand;
    }

    public void setHand(Integer hand) {
        this.hand = hand;
    }

    public Date getDiaNaoUtil() {
        return diaNaoUtil;
    }

    public void setDiaNaoUtil(Date diaNaoUtil) {
        this.diaNaoUtil = diaNaoUtil;
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
        if (!(object instanceof TbDiaNaoUtil)) {
            return false;
        }
        TbDiaNaoUtil other = (TbDiaNaoUtil) object;
        if ((this.hand == null && other.hand != null) || (this.hand != null && !this.hand.equals(other.hand))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelos.TbDiaNaoUtil[ hand=" + hand + " ]";
    }

}
