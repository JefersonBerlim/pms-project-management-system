package Entidades;

import Entidades.TbEstados;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2013-12-01T17:02:58")
@StaticMetamodel(TbPaises.class)
public class TbPaises_ { 

    public static volatile SingularAttribute<TbPaises, Integer> hand;
    public static volatile SingularAttribute<TbPaises, String> pais;
    public static volatile CollectionAttribute<TbPaises, TbEstados> tbEstadosCollection;

}