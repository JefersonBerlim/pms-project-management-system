package Entidades;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2013-12-01T17:02:58")
@StaticMetamodel(TbParametros.class)
public class TbParametros_ { 

    public static volatile SingularAttribute<TbParametros, Integer> hand;
    public static volatile SingularAttribute<TbParametros, BigDecimal> percentHrExtraNoturna;
    public static volatile SingularAttribute<TbParametros, Date> horaFimNoturno;
    public static volatile SingularAttribute<TbParametros, BigDecimal> percentHrExtraDiurna;
    public static volatile SingularAttribute<TbParametros, Date> horaInicioNoturno;
    public static volatile SingularAttribute<TbParametros, BigDecimal> percentAdicionalNoturno;

}