package Entidades;

import Entidades.TbEstados;
import Entidades.TbPessoa;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2013-12-01T17:02:58")
@StaticMetamodel(TbCidades.class)
public class TbCidades_ { 

    public static volatile SingularAttribute<TbCidades, Integer> hand;
    public static volatile SingularAttribute<TbCidades, String> cidade;
    public static volatile SingularAttribute<TbCidades, TbEstados> tbEstadosHand;
    public static volatile CollectionAttribute<TbCidades, TbPessoa> tbPessoaCollection;

}