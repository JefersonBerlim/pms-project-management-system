/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;

import java.io.Serializable;
import java.util.Collection;
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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author jeferson
 */
@Entity
@Table(name = "TB_PESSOA")
@NamedQueries({
    @NamedQuery(name = "TbPessoa.findAll", query = "SELECT t FROM TbPessoa t")})
public class TbPessoa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "HAND")
    private Integer hand;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "NOME_FANTASIA")
    private String nomeFantasia;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "RAZAO_SOCIAL")
    private String razaoSocial;
    @Size(max = 45)
    @Column(name = "TELEFONE")
    private String telefone;
    @Size(max = 150)
    @Column(name = "ENDERECO")
    private String endereco;
    @Size(max = 10)
    @Column(name = "NUMERO")
    private String numero;
    @Size(max = 50)
    @Column(name = "COMPLEMENTO")
    private String complemento;
    @Size(max = 50)
    @Column(name = "BAIRRO")
    private String bairro;
    @Size(max = 18)
    @Column(name = "CPF_CNPJ")
    private String cpfCnpj;
    @Size(max = 1)
    @Column(name = "EHFORNECEDOR")
    private String ehfornecedor;
    @Size(max = 1)
    @Column(name = "EHCLIENTE")
    private String ehcliente;
    @Size(max = 255)
    @Column(name = "OBSERVACOES")
    private String observacoes;
    @Size(max = 50)
    @Column(name = "ENDERECO_WEB")
    private String enderecoWeb;
    @Size(max = 1)
    @Column(name = "EH_INATIVO")
    private String ehInativo;
    @JoinColumn(name = "TB_TIPOPESSOA_HAND", referencedColumnName = "HAND")
    @ManyToOne(optional = false)
    private TbTipopessoa tbTipopessoaHand;
    @JoinColumn(name = "TB_CIDADES_HAND", referencedColumnName = "HAND")
    @ManyToOne(optional = false)
    private TbCidades tbCidadesHand;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tbPessoaHand")
    private Collection<TbProjetos> tbProjetosCollection;
    @Transient
    private boolean tmpEhFornecedor;
    @Transient
    private boolean tmpEhCliente;
    @Transient
    private boolean tmpEhInativo;

    public TbPessoa() {
    }

    public TbPessoa(Integer hand) {
        this.hand = hand;
    }

    public TbPessoa(Integer hand, String nomeFantasia, String razaoSocial) {
        this.hand = hand;
        this.nomeFantasia = nomeFantasia;
        this.razaoSocial = razaoSocial;
    }

    public Integer getHand() {
        return hand;
    }

    public void setHand(Integer hand) {
        this.hand = hand;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    public String getEhfornecedor() {
        return ehfornecedor;
    }

    public void setEhfornecedor(String ehfornecedor) {
        this.ehfornecedor = ehfornecedor;
    }

    public String getEhcliente() {
        return ehcliente;
    }

    public void setEhcliente(String ehcliente) {
        this.ehcliente = ehcliente;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public String getEnderecoWeb() {
        return enderecoWeb;
    }

    public void setEnderecoWeb(String enderecoWeb) {
        this.enderecoWeb = enderecoWeb;
    }

    public String getEhInativo() {
        return ehInativo;
    }

    public void setEhInativo(String ehInativo) {
        this.ehInativo = ehInativo;
    }

    public TbTipopessoa getTbTipopessoaHand() {
        return tbTipopessoaHand;
    }

    public void setTbTipopessoaHand(TbTipopessoa tbTipopessoaHand) {
        this.tbTipopessoaHand = tbTipopessoaHand;
    }

    public TbCidades getTbCidadesHand() {
        return tbCidadesHand;
    }

    public void setTbCidadesHand(TbCidades tbCidadesHand) {
        this.tbCidadesHand = tbCidadesHand;
    }

    public Collection<TbProjetos> getTbProjetosCollection() {
        return tbProjetosCollection;
    }

    public void setTbProjetosCollection(Collection<TbProjetos> tbProjetosCollection) {
        this.tbProjetosCollection = tbProjetosCollection;
    }

    public boolean isTmpEhFornecedor() {
        return tmpEhFornecedor;
    }

    public void setTmpEhFornecedor(boolean tmpEhFornecedor) {
        this.tmpEhFornecedor = tmpEhFornecedor;
    }

    public boolean isTmpEhCliente() {
        return tmpEhCliente;
    }

    public void setTmpEhCliente(boolean tmpEhCliente) {
        this.tmpEhCliente = tmpEhCliente;
    }

    public boolean isTmpEhInativo() {
        return tmpEhInativo;
    }

    public void setTmpEhInativo(boolean tmpEhInativo) {
        this.tmpEhInativo = tmpEhInativo;
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
        if (!(object instanceof TbPessoa)) {
            return false;
        }
        TbPessoa other = (TbPessoa) object;
        if ((this.hand == null && other.hand != null) || (this.hand != null && !this.hand.equals(other.hand))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelos.TbPessoa[ hand=" + hand + " ]";
    }

}
