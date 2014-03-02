package Entidades;

import Entidades.TbFuncionarioTurnoSemana;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2013-12-01T17:02:58")
@StaticMetamodel(TbTurnos.class)
public class TbTurnos_ { 

    public static volatile SingularAttribute<TbTurnos, Integer> hand;
    public static volatile SingularAttribute<TbTurnos, Date> horarioFinal;
    public static volatile SingularAttribute<TbTurnos, Date> almocoInicio;
    public static volatile CollectionAttribute<TbTurnos, TbFuncionarioTurnoSemana> tbFuncionarioTurnoSemanaCollection;
    public static volatile SingularAttribute<TbTurnos, Date> almocoFim;
    public static volatile SingularAttribute<TbTurnos, Date> horarioInicial;
    public static volatile SingularAttribute<TbTurnos, String> descricao;

}