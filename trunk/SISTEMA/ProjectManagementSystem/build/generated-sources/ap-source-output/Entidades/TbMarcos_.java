package Entidades;

import Entidades.TbMarcosServicos;
import Entidades.TbProjetoMarcos;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2013-12-01T17:02:58")
@StaticMetamodel(TbMarcos.class)
public class TbMarcos_ { 

    public static volatile SingularAttribute<TbMarcos, Integer> hand;
    public static volatile CollectionAttribute<TbMarcos, TbMarcosServicos> tbMarcosServicosCollection;
    public static volatile SingularAttribute<TbMarcos, String> ehInativo;
    public static volatile CollectionAttribute<TbMarcos, TbProjetoMarcos> tbProjetoMarcosCollection;
    public static volatile SingularAttribute<TbMarcos, String> descricao;

}