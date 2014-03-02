package Entidades;

import Entidades.TbMateriais;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2013-12-01T17:02:58")
@StaticMetamodel(TbUnidadeMedida.class)
public class TbUnidadeMedida_ { 

    public static volatile SingularAttribute<TbUnidadeMedida, Integer> hand;
    public static volatile CollectionAttribute<TbUnidadeMedida, TbMateriais> tbMateriaisCollection;
    public static volatile SingularAttribute<TbUnidadeMedida, String> sigla;
    public static volatile SingularAttribute<TbUnidadeMedida, String> descricao;

}