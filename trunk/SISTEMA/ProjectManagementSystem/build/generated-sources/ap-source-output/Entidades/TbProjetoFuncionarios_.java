package Entidades;

import Entidades.TbFuncionariosRecursos;
import Entidades.TbOsFuncionarios;
import Entidades.TbProjetosRecursos;
import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2013-12-01T17:02:58")
@StaticMetamodel(TbProjetoFuncionarios.class)
public class TbProjetoFuncionarios_ { 

    public static volatile SingularAttribute<TbProjetoFuncionarios, Integer> hand;
    public static volatile CollectionAttribute<TbProjetoFuncionarios, TbOsFuncionarios> tbOsFuncionariosCollection;
    public static volatile SingularAttribute<TbProjetoFuncionarios, BigDecimal> quantidadeHoras;
    public static volatile SingularAttribute<TbProjetoFuncionarios, TbProjetosRecursos> tbProjetosRecursosHand;
    public static volatile SingularAttribute<TbProjetoFuncionarios, TbFuncionariosRecursos> tbFuncionariosRecursosHand;

}