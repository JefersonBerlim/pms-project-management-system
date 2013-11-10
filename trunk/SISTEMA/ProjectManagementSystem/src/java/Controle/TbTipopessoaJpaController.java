/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import java.io.Serializable;
import javax.persistence.Query;
import Entidades.TbTipopessoa;
import java.util.List;
import javax.annotation.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@ManagedBean
@ViewScoped
public class TbTipopessoaJpaController implements Serializable {

    private TbTipopessoa tbTipoPessoa;
    private final EntityManagerFactory emf;
    private List<TbTipopessoa> tiposPessoa;

    public TbTipopessoaJpaController() {
        emf = Persistence.createEntityManagerFactory("ProjectManagementSystemPU");
        tbTipoPessoa = new TbTipopessoa();
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List<TbTipopessoa> getTiposPessoa() {
        return tiposPessoa;
    }

    public void setTiposPessoa(List<TbTipopessoa> tiposPessoa) {
        this.tiposPessoa = tiposPessoa;
    }

    public TbTipopessoa getTbTipoPessoa() {
        return tbTipoPessoa;
    }

    public void setTbTipoPessoa(TbTipopessoa tbTipoPessoa) {
        this.tbTipoPessoa = tbTipoPessoa;
    }

    // Método Usado para Listar os Tipos de Pessoas
    public List<TbTipopessoa> getTipoPessoaLista() {
        tiposPessoa = findTipoPessoa();
        return tiposPessoa;
    }

    public TbTipopessoa findTbTipopessoa(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TbTipopessoa.class, id);
        } finally {
            em.close();
        }
    }

    public List<TbTipopessoa> findTipoPessoa() {
        EntityManager em = getEntityManager();
        Query tipos = em.createNamedQuery("TbTipopessoa.findAll");
        return tipos.getResultList();
    }
}
