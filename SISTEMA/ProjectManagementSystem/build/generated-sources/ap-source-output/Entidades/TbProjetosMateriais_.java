package Entidades;

import Entidades.TbMateriais;
import Entidades.TbOsMateriais;
import Entidades.TbProjetosServicos;
import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2013-12-01T17:02:58")
@StaticMetamodel(TbProjetosMateriais.class)
public class TbProjetosMateriais_ { 

    public static volatile SingularAttribute<TbProjetosMateriais, Integer> hand;
    public static volatile SingularAttribute<TbProjetosMateriais, BigDecimal> quantidade;
    public static volatile SingularAttribute<TbProjetosMateriais, TbProjetosServicos> tbProjetosServicosHand;
    public static volatile SingularAttribute<TbProjetosMateriais, TbMateriais> tbMateriaisHand;
    public static volatile CollectionAttribute<TbProjetosMateriais, TbOsMateriais> tbOsMateriaisCollection;
    public static volatile SingularAttribute<TbProjetosMateriais, String> informacaoComplementar;

}