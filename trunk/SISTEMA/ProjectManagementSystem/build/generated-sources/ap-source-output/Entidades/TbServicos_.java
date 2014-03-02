package Entidades;

import Entidades.TbMarcosServicos;
import Entidades.TbProjetosServicos;
import Entidades.TbRecursosServicos;
import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2013-12-01T17:02:58")
@StaticMetamodel(TbServicos.class)
public class TbServicos_ { 

    public static volatile SingularAttribute<TbServicos, Integer> hand;
    public static volatile CollectionAttribute<TbServicos, TbProjetosServicos> tbProjetosServicosCollection;
    public static volatile SingularAttribute<TbServicos, BigDecimal> valorHora;
    public static volatile CollectionAttribute<TbServicos, TbMarcosServicos> tbMarcosServicosCollection;
    public static volatile SingularAttribute<TbServicos, String> ehInativo;
    public static volatile CollectionAttribute<TbServicos, TbProjetosServicos> tbProjetosServicosCollection1;
    public static volatile SingularAttribute<TbServicos, String> descricao;
    public static volatile CollectionAttribute<TbServicos, TbRecursosServicos> tbRecursosServicosCollection;

}