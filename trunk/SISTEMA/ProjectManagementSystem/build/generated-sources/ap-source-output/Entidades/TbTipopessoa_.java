package Entidades;

import Entidades.TbPessoa;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2013-12-01T17:02:58")
@StaticMetamodel(TbTipopessoa.class)
public class TbTipopessoa_ { 

    public static volatile SingularAttribute<TbTipopessoa, Integer> hand;
    public static volatile SingularAttribute<TbTipopessoa, String> tipo;
    public static volatile SingularAttribute<TbTipopessoa, String> descricao;
    public static volatile CollectionAttribute<TbTipopessoa, TbPessoa> tbPessoaCollection;

}