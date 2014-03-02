package Entidades;

import Entidades.TbMateriais;
import Entidades.TbOsServico;
import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2013-12-01T17:02:58")
@StaticMetamodel(TbApontamentosMateriais.class)
public class TbApontamentosMateriais_ { 

    public static volatile SingularAttribute<TbApontamentosMateriais, Integer> hand;
    public static volatile SingularAttribute<TbApontamentosMateriais, BigDecimal> quantidaeUtilizada;
    public static volatile SingularAttribute<TbApontamentosMateriais, TbMateriais> tbMateriaisHand;
    public static volatile SingularAttribute<TbApontamentosMateriais, TbOsServico> tbOsServicoHand;

}