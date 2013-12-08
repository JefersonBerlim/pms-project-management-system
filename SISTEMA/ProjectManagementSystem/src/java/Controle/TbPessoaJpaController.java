/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import Controle.exceptions.PreexistingEntityException;
import java.io.Serializable;
import Entidades.TbPessoa;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.ManagedBean;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import static javax.faces.component.UIInput.isEmpty;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

@ManagedBean
@ViewScoped
public class TbPessoaJpaController implements Serializable {

    private TbPessoa tbPessoa;
    private String mensagem;
    private final EntityManagerFactory emf;
    private List<TbPessoa> pessoas;
    private TbCidadesJpaController controleTbCidades;
    private TbTipopessoaJpaController controleTbTipoPessoa;
    private Integer tipoPessoaSelecionado;
    private Integer cidadeSelecionado;
    private boolean ehFornecedor;
    private boolean ehCliente;
    private boolean ehInativo;
    public boolean inclusao;

    public TbPessoaJpaController() {
        emf = Persistence.createEntityManagerFactory("ProjectManagementSystemPU");
        tbPessoa = new TbPessoa();
        controleTbCidades = new TbCidadesJpaController();
        controleTbTipoPessoa = new TbTipopessoaJpaController();
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

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public boolean isEhFornecedor() {
        return ehFornecedor;
    }

    public void setEhFornecedor(boolean ehFornecedor) {
        this.ehFornecedor = ehFornecedor;
    }

    public boolean isEhCliente() {
        return ehCliente;
    }

    public void setEhCliente(boolean ehCliente) {
        this.ehCliente = ehCliente;
    }

    public boolean isEhInativo() {
        return ehInativo;
    }

    public void setEhInativo(boolean ehInativo) {
        this.ehInativo = ehInativo;
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

    public TbPessoa getTbPessoa() {
        if (tbPessoa.getHand() == null) {
            tbPessoa.setHand(preparaInclusao());
            inclusao = true;
        }
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

    public void create() throws PreexistingEntityException, Exception {

        String cliente;
        String fornecedor;
        String inativo;

        //Obtendo o EntityManager
        EntityManager em = getEntityManager();

        try {
            em = getEntityManager();
            em.getTransaction().begin();

            tbPessoa.setTbTipopessoaHand(this.controleTbTipoPessoa.findTbTipopessoa(this.tipoPessoaSelecionado));
            tbPessoa.setTbCidadesHand(this.controleTbCidades.findTbCidades(this.cidadeSelecionado));
            tbPessoa.setEhcliente(this.ehCliente ? "S" : "N");
            tbPessoa.setEhfornecedor(this.ehFornecedor ? "S" : "N");
            tbPessoa.setEhInativo(this.ehInativo ? "S" : "N");

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
            this.tipoPessoaSelecionado = this.tbPessoa.getTbTipopessoaHand().getHand();
            this.cidadeSelecionado = this.tbPessoa.getTbCidadesHand().getHand();
            this.ehCliente = this.tbPessoa.getEhcliente().equals("S");
            this.ehFornecedor = this.tbPessoa.getEhfornecedor().equals("S");
            this.ehInativo = this.tbPessoa.getEhInativo().equals("S");            
            inclusao = false;
        }

        return tbPessoa;
    }

    public List<TbPessoa> findPessoas() {
        EntityManager em = getEntityManager();
        Query pessoa = em.createNamedQuery("TbPessoa.findAll");
        return pessoa.getResultList();
    }

    public Integer preparaInclusao() {
        EntityManager em = getEntityManager();
        Integer proxCod = 0;
        List<TbPessoa> listaPessoas = new ArrayList<TbPessoa>();

        listaPessoas = findPessoas();

        for (Integer i = 0; i < listaPessoas.size(); i++) {
            if (listaPessoas.get(i).getHand() > proxCod) {
                proxCod = listaPessoas.get(i).getHand();
            }
        }

        return proxCod + 1;
    }

    public void save() {
        try {
            this.create();
            mensagem = "Registro salvo com sucesso";
        } catch (Exception ex) {
            mensagem = "Problemas ao Salvar o registro";
        }
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Atenção", mensagem));
    }
}
