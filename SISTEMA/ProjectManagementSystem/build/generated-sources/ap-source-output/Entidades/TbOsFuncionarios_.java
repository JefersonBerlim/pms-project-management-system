package Entidades;

import Entidades.TbOsRecursos;
import Entidades.TbProjetoFuncionarios;
import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2013-12-01T17:02:58")
@StaticMetamodel(TbOsFuncionarios.class)
public class TbOsFuncionarios_ { 

    public static volatile SingularAttribute<TbOsFuncionarios, Integer> hand;
    public static volatile SingularAttribute<TbOsFuncionarios, TbProjetoFuncionarios> tbProjetoFuncionariosHand;
    public static volatile SingularAttribute<TbOsFuncionarios, BigDecimal> quantidaeHoras;
    public static volatile SingularAttribute<TbOsFuncionarios, TbOsRecursos> tbOsRecursosHand;

}