package Entidades;

import Entidades.TbCidades;
import Entidades.TbPaises;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2013-12-01T17:02:58")
@StaticMetamodel(TbEstados.class)
public class TbEstados_ { 

    public static volatile SingularAttribute<TbEstados, Integer> hand;
    public static volatile SingularAttribute<TbEstados, String> estado;
    public static volatile SingularAttribute<TbEstados, String> sigla;
    public static volatile CollectionAttribute<TbEstados, TbCidades> tbCidadesCollection;
    public static volatile SingularAttribute<TbEstados, TbPaises> tbPaisesHand;

}