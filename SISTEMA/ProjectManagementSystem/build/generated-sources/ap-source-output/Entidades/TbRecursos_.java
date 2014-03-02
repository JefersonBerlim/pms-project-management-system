package Entidades;

import Entidades.TbApontamentosRecursos;
import Entidades.TbFuncionariosRecursos;
import Entidades.TbProjetosRecursos;
import Entidades.TbRecursosServicos;
import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2013-12-01T17:02:58")
@StaticMetamodel(TbRecursos.class)
public class TbRecursos_ { 

    public static volatile SingularAttribute<TbRecursos, Integer> hand;
    public static volatile CollectionAttribute<TbRecursos, TbFuncionariosRecursos> tbFuncionariosRecursosCollection;
    public static volatile SingularAttribute<TbRecursos, BigDecimal> valorHora;
    public static volatile CollectionAttribute<TbRecursos, TbApontamentosRecursos> tbApontamentosRecursosCollection;
    public static volatile SingularAttribute<TbRecursos, String> ehInativo;
    public static volatile SingularAttribute<TbRecursos, String> funcionarioSimultaneo;
    public static volatile CollectionAttribute<TbRecursos, TbProjetosRecursos> tbProjetosRecursosCollection;
    public static volatile SingularAttribute<TbRecursos, String> descricao;
    public static volatile CollectionAttribute<TbRecursos, TbRecursosServicos> tbRecursosServicosCollection;

}