/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author berlim
 */
@Entity
@Table(name = "TB_PESSOA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TbPessoa.findAll", query = "SELECT t FROM TbPessoa t"),
    @NamedQuery(name = "TbPessoa.findByHand", query = "SELECT t FROM TbPessoa t WHERE t.hand = :hand"),
    @NamedQuery(name = "TbPessoa.findByNomeFantasia", query = "SELECT t FROM TbPessoa t WHERE t.nomeFantasia = :nomeFantasia"),
    @NamedQuery(name = "TbPessoa.findByRazaoSocial", query = "SELECT t FROM TbPessoa t WHERE t.razaoSocial = :razaoSocial"),
    @NamedQuery(name = "TbPessoa.findByTelefone", query = "SELECT t FROM TbPessoa t WHERE t.telefone = :telefone"),
    @NamedQuery(name = "TbPessoa.findByEndereco", query = "SELECT t FROM TbPessoa t WHERE t.endereco = :endereco"),
    @NamedQuery(name = "TbPessoa.findByNumero", query = "SELECT t FROM TbPessoa t WHERE t.numero = :numero"),
    @NamedQuery(name = "TbPessoa.findByComplemento", query = "SELECT t FROM TbPessoa t WHERE t.complemento = :complemento"),
    @NamedQuery(name = "TbPessoa.findByBairro", query = "SELECT t FROM TbPessoa t WHERE t.bairro = :bairro"),
    @NamedQuery(name = "TbPessoa.findByCpfCnpj", query = "SELECT t FROM TbPessoa t WHERE t.cpfCnpj = :cpfCnpj"),
    @NamedQuery(name = "TbPessoa.findByEhfornecedor", query = "SELECT t FROM TbPessoa t WHERE t.ehfornecedor = :ehfornecedor"),
    @NamedQuery(name = "TbPessoa.findByEhcliente", query = "SELECT t FROM TbPessoa t WHERE t.ehcliente = :ehcliente"),
    @NamedQuery(name = "TbPessoa.findByObservacoes", query = "SELECT t FROM TbPessoa t WHERE t.observacoes = :observacoes"),
    @NamedQuery(name = "TbPessoa.findByEnderecoWeb", query = "SELECT t FROM TbPessoa t WHERE t.enderecoWeb = :enderecoWeb"),
    @NamedQuery(name = "TbPessoa.findByEhInativo", query = "SELECT t FROM TbPessoa t WHERE t.ehInativo = :ehInativo")})
public class TbPessoa implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "HAND")
    private Integer hand;
    @Basic(optional = false)
    @Column(name = "NOME_FANTASIA")
    private String nomeFantasia;
    @Basic(optional = false)
    @Column(name = "RAZAO_SOCIAL")
    private String razaoSocial;
    @Column(name = "TELEFONE")
    private String telefone;
    @Column(name = "ENDERECO")
    private String endereco;
    @Column(name = "NUMERO")
    private String numero;
    @Column(name = "COMPLEMENTO")
    private String complemento;
    @Column(name = "BAIRRO")
    private String bairro;
    @Column(name = "CPF_CNPJ")
    private String cpfCnpj;
    @Column(name = "EHFORNECEDOR")
    private String ehfornecedor;
    @Column(name = "EHCLIENTE")
    private String ehcliente;
    @Column(name = "OBSERVACOES")
    private String observacoes;
    @Column(name = "ENDERECO_WEB")
    private String enderecoWeb;
    @Column(name = "EH_INATIVO")
    private String ehInativo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tbPessoaHand")
    private Collection<TbPessoaProjeto> tbPessoaProjetoCollection;
    @JoinColumn(name = "TB_TIPOPESSOA_HAND", referencedColumnName = "HAND")
    @ManyToOne(optional = false)
    private TbTipopessoa tbTipopessoaHand;
    @JoinColumn(name = "TB_CIDADES_HAND", referencedColumnName = "HAND")
    @ManyToOne(optional = false)
    private TbCidades tbCidadesHand;

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

    @XmlTransient
    public Collection<TbPessoaProjeto> getTbPessoaProjetoCollection() {
        return tbPessoaProjetoCollection;
    }

    public void setTbPessoaProjetoCollection(Collection<TbPessoaProjeto> tbPessoaProjetoCollection) {
        this.tbPessoaProjetoCollection = tbPessoaProjetoCollection;
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
        return "Entidades.TbPessoa[ hand=" + hand + " ]";
    }
    
}
