package Entidades;

import Entidades.TbOsRecursos;
import Entidades.TbProjetoFuncionarios;
import Entidades.TbProjetosServicos;
import Entidades.TbRecursos;
import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2013-12-01T17:02:58")
@StaticMetamodel(TbProjetosRecursos.class)
public class TbProjetosRecursos_ { 

    public static volatile SingularAttribute<TbProjetosRecursos, Integer> hand;
    public static volatile SingularAttribute<TbProjetosRecursos, BigDecimal> quantidadeHoras;
    public static volatile SingularAttribute<TbProjetosRecursos, TbProjetosServicos> tbProjetosServicosHand;
    public static volatile CollectionAttribute<TbProjetosRecursos, TbProjetoFuncionarios> tbProjetoFuncionariosCollection;
    public static volatile CollectionAttribute<TbProjetosRecursos, TbOsRecursos> tbOsRecursosCollection;
    public static volatile SingularAttribute<TbProjetosRecursos, TbRecursos> tbRecursosHand;

}