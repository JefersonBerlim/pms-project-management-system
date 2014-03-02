package Entidades;

import Entidades.TbApontamentosMateriais;
import Entidades.TbApontamentosRecursos;
import Entidades.TbOrdemServico;
import Entidades.TbOsMateriais;
import Entidades.TbOsRecursos;
import Entidades.TbProjetosServicos;
import Entidades.TbStatus;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2013-12-01T17:02:58")
@StaticMetamodel(TbOsServico.class)
public class TbOsServico_ { 

    public static volatile SingularAttribute<TbOsServico, Integer> hand;
    public static volatile SingularAttribute<TbOsServico, TbProjetosServicos> tbProjetosServicosHand;
    public static volatile SingularAttribute<TbOsServico, TbStatus> tbStatusHand;
    public static volatile CollectionAttribute<TbOsServico, TbApontamentosMateriais> tbApontamentosMateriaisCollection;
    public static volatile CollectionAttribute<TbOsServico, TbApontamentosRecursos> tbApontamentosRecursosCollection;
    public static volatile CollectionAttribute<TbOsServico, TbOsMateriais> tbOsMateriaisCollection;
    public static volatile SingularAttribute<TbOsServico, TbOrdemServico> tbOrdemServicoHand;
    public static volatile CollectionAttribute<TbOsServico, TbOsRecursos> tbOsRecursosCollection;

}