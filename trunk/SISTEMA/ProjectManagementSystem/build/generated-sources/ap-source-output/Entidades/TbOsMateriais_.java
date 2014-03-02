package Entidades;

import Entidades.TbMateriais;
import Entidades.TbOsServico;
import Entidades.TbProjetosMateriais;
import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2013-12-01T17:02:58")
@StaticMetamodel(TbOsMateriais.class)
public class TbOsMateriais_ { 

    public static volatile SingularAttribute<TbOsMateriais, Integer> hand;
    public static volatile SingularAttribute<TbOsMateriais, TbProjetosMateriais> tbProjetosMateriaisHand;
    public static volatile SingularAttribute<TbOsMateriais, TbMateriais> tbMateriaisHand;
    public static volatile SingularAttribute<TbOsMateriais, TbOsServico> tbOsServicoHand;
    public static volatile SingularAttribute<TbOsMateriais, BigDecimal> quantidadeUtilizada;

}