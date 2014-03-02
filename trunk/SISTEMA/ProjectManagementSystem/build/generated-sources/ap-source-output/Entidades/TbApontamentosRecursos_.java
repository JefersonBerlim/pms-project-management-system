package Entidades;

import Entidades.TbApontamentosFuncionarios;
import Entidades.TbOsServico;
import Entidades.TbRecursos;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2013-12-01T17:02:58")
@StaticMetamodel(TbApontamentosRecursos.class)
public class TbApontamentosRecursos_ { 

    public static volatile SingularAttribute<TbApontamentosRecursos, Integer> hand;
    public static volatile CollectionAttribute<TbApontamentosRecursos, TbApontamentosFuncionarios> tbApontamentosFuncionariosCollection;
    public static volatile SingularAttribute<TbApontamentosRecursos, TbOsServico> tbOsServicoHand;
    public static volatile SingularAttribute<TbApontamentosRecursos, TbRecursos> tbRecursosHand;

}