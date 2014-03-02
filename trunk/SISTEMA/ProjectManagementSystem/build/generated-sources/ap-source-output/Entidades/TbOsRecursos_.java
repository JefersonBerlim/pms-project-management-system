package Entidades;

import Entidades.TbOsFuncionarios;
import Entidades.TbOsServico;
import Entidades.TbProjetosRecursos;
import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2013-12-01T17:02:58")
@StaticMetamodel(TbOsRecursos.class)
public class TbOsRecursos_ { 

    public static volatile SingularAttribute<TbOsRecursos, Integer> hand;
    public static volatile CollectionAttribute<TbOsRecursos, TbOsFuncionarios> tbOsFuncionariosCollection;
    public static volatile SingularAttribute<TbOsRecursos, BigDecimal> quantidadeHoras;
    public static volatile SingularAttribute<TbOsRecursos, TbProjetosRecursos> tbProjetosRecursosHand;
    public static volatile SingularAttribute<TbOsRecursos, TbOsServico> tbOsServicoHand;

}