package Entidades;

import Entidades.TbCidades;
import Entidades.TbPessoaProjeto;
import Entidades.TbTipopessoa;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2013-12-01T17:02:58")
@StaticMetamodel(TbPessoa.class)
public class TbPessoa_ { 

    public static volatile CollectionAttribute<TbPessoa, TbPessoaProjeto> tbPessoaProjetoCollection;
    public static volatile SingularAttribute<TbPessoa, String> cpfCnpj;
    public static volatile SingularAttribute<TbPessoa, String> complemento;
    public static volatile SingularAttribute<TbPessoa, String> nomeFantasia;
    public static volatile SingularAttribute<TbPessoa, String> razaoSocial;
    public static volatile SingularAttribute<TbPessoa, String> observacoes;
    public static volatile SingularAttribute<TbPessoa, TbTipopessoa> tbTipopessoaHand;
    public static volatile SingularAttribute<TbPessoa, String> numero;
    public static volatile SingularAttribute<TbPessoa, Integer> hand;
    public static volatile SingularAttribute<TbPessoa, String> bairro;
    public static volatile SingularAttribute<TbPessoa, TbCidades> tbCidadesHand;
    public static volatile SingularAttribute<TbPessoa, String> ehfornecedor;
    public static volatile SingularAttribute<TbPessoa, String> telefone;
    public static volatile SingularAttribute<TbPessoa, String> enderecoWeb;
    public static volatile SingularAttribute<TbPessoa, String> ehInativo;
    public static volatile SingularAttribute<TbPessoa, String> endereco;
    public static volatile SingularAttribute<TbPessoa, String> ehcliente;

}