package Entidades;

import Entidades.TbPaises;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-06-30T23:44:06")
@StaticMetamodel(TbEstados.class)
public class TbEstados_ { 

    public static volatile SingularAttribute<TbEstados, Integer> hand;
    public static volatile SingularAttribute<TbEstados, String> estado;
    public static volatile SingularAttribute<TbEstados, String> sigla;
    public static volatile SingularAttribute<TbEstados, TbPaises> tbPaisesHand;

}