/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import Controle.exceptions.PreexistingEntityException;
import java.io.Serializable;
import Entidades.TbPessoa;
import java.util.List;
import java.util.Map;
import static javax.faces.component.UIInput.isEmpty;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class TbPessoaJpaController implements Serializable {

    private TbPessoa tbPessoa;
    private final EntityManagerFactory emf;
    private List<TbPessoa> pessoas;
    private TbCidadesJpaController controleTbCidades;
    private Integer tipoPessoaSelecionado;
    private Integer cidadeSelecionado;
    public boolean inclusao;

    public TbPessoaJpaController() {
        emf = Persistence.createEntityManagerFactory("ProjectManagementSystemPU");
        tbPessoa = new TbPessoa();
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public boolean isInclusao() {
        return inclusao;
    }

    public void setInclusao(boolean inclusao) {
        this.inclusao = inclusao;
    }

    public Integer getTipoPessoaSelecionado() {
        return tipoPessoaSelecionado;
    }

    public void setTipoPessoaSelecionado(Integer tipoPessoaSelecionado) {
        this.tipoPessoaSelecionado = tipoPessoaSelecionado;
    }

    public Integer getCidadeSelecionado() {
        return cidadeSelecionado;
    }

    public void setCidadeSelecionado(Integer cidadeSelecionado) {
        this.cidadeSelecionado = cidadeSelecionado;
    }

    public TbCidadesJpaController getControleTbCidades() {
        return controleTbCidades;
    }

    public void setControleTbCidades(TbCidadesJpaController controleTbCidades) {
        this.controleTbCidades = controleTbCidades;
    }

    public TbTipopessoaJpaController getControleTbTipoPessoa() {
        return controleTbTipoPessoa;
    }

    public void setControleTbTipoPessoa(TbTipopessoaJpaController controleTbTipoPessoa) {
        this.controleTbTipoPessoa = controleTbTipoPessoa;
    }
    private TbTipopessoaJpaController controleTbTipoPessoa;

    public TbPessoa getTbPessoa() {
        return tbPessoa;
    }

    public void setTbPessoa(TbPessoa tbPessoa) {
        this.tbPessoa = tbPessoa;
    }

    public List<TbPessoa> getPessoas() {
        return pessoas;
    }

    public void setPessoas(List<TbPessoa> pessoas) {
        this.pessoas = pessoas;
    }

    // Método Usado para Popular a Grid de Pessoas
    public List<TbPessoa> getPessoasLista() {
        pessoas = findPessoas();
        return pessoas;
    }

    public void create(TbPessoa tbPessoa) throws PreexistingEntityException, Exception {

        //Obtendo o EntityManager
        EntityManager em = getEntityManager();

        try {
            em = getEntityManager();
            em.getTransaction().begin();

            tbPessoa.setTbTipopessoaHand(this.controleTbTipoPessoa.findTbTipopessoa(this.tipoPessoaSelecionado));
            tbPessoa.setTbCidadesHand(this.controleTbCidades.findTbCidades(this.cidadeSelecionado));

            //inicia o processo de transacao
            if ((tbPessoa.getHand() == null && tbPessoa.getTbTipopessoaHand() != null
                    && tbPessoa.getTbCidadesHand() != null) || inclusao) {
                //faz a persistencia
                em.persist(tbPessoa);
            } else {
                //faz a persistencia
                em.merge(tbPessoa);
            }

            em.getTransaction().commit();
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public TbPessoa findTbPessoa(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TbPessoa.class, id);
        } finally {
            em.close();
        }
    }

    // Método Usado para a Abertura da Tela de Pessoas
    public TbPessoa findPessoaHand() {
        EntityManager em = getEntityManager();

        Map< String, String> params = FacesContext.getCurrentInstance()
                .getExternalContext().getRequestParameterMap();
        String hand = params.get("hand");

        if (!isEmpty(hand)) {
            tbPessoa = findTbPessoa(Integer.parseInt(hand));
            this.setTbPessoa(tbPessoa);
            inclusao = false;
        }

        return tbPessoa;
    }

    public List<TbPessoa> findPessoas() {
        EntityManager em = getEntityManager();
        Query pessoa = em.createNamedQuery("TbPessoa.findAll");
        return pessoa.getResultList();
    }
}
