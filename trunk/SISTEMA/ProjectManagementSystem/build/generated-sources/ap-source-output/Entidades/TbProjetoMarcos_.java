package Entidades;

import Entidades.TbMarcos;
import Entidades.TbProjetos;
import Entidades.TbProjetosServicos;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2013-12-01T17:02:58")
@StaticMetamodel(TbProjetoMarcos.class)
public class TbProjetoMarcos_ { 

    public static volatile SingularAttribute<TbProjetoMarcos, Integer> hand;
    public static volatile SingularAttribute<TbProjetoMarcos, Date> dataFim;
    public static volatile CollectionAttribute<TbProjetoMarcos, TbProjetosServicos> tbProjetosServicosCollection;
    public static volatile SingularAttribute<TbProjetoMarcos, Date> horaInicio;
    public static volatile SingularAttribute<TbProjetoMarcos, Date> dataInicio;
    public static volatile SingularAttribute<TbProjetoMarcos, Date> horaFim;
    public static volatile SingularAttribute<TbProjetoMarcos, TbProjetos> tbProjetoHand;
    public static volatile SingularAttribute<TbProjetoMarcos, TbMarcos> tbMarcosHand;

}