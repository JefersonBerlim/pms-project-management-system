package Entidades;

import Entidades.TbOsServico;
import Entidades.TbProjetoMarcos;
import Entidades.TbProjetosMateriais;
import Entidades.TbProjetosRecursos;
import Entidades.TbServicos;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2013-12-01T17:02:58")
@StaticMetamodel(TbProjetosServicos.class)
public class TbProjetosServicos_ { 

    public static volatile CollectionAttribute<TbProjetosServicos, TbOsServico> tbOsServicoCollection;
    public static volatile SingularAttribute<TbProjetosServicos, Date> horaInicio;
    public static volatile CollectionAttribute<TbProjetosServicos, TbProjetosRecursos> tbProjetosRecursosCollection;
    public static volatile CollectionAttribute<TbProjetosServicos, TbProjetosMateriais> tbProjetosMateriaisCollection;
    public static volatile SingularAttribute<TbProjetosServicos, String> observacao;
    public static volatile SingularAttribute<TbProjetosServicos, TbServicos> servicoDependente;
    public static volatile SingularAttribute<TbProjetosServicos, Integer> hand;
    public static volatile SingularAttribute<TbProjetosServicos, TbProjetoMarcos> tbProjetoMarcosHand;
    public static volatile SingularAttribute<TbProjetosServicos, Date> dataFim;
    public static volatile SingularAttribute<TbProjetosServicos, TbServicos> tbServicosHand;
    public static volatile SingularAttribute<TbProjetosServicos, BigDecimal> qtdHoras;
    public static volatile SingularAttribute<TbProjetosServicos, String> arquivos;
    public static volatile SingularAttribute<TbProjetosServicos, Date> horaFim;
    public static volatile SingularAttribute<TbProjetosServicos, Date> dataInicio;

}