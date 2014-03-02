package Entidades;

import Entidades.TbOsServico;
import Entidades.TbProjetos;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2013-12-01T17:02:58")
@StaticMetamodel(TbStatus.class)
public class TbStatus_ { 

    public static volatile SingularAttribute<TbStatus, Integer> hand;
    public static volatile CollectionAttribute<TbStatus, TbOsServico> tbOsServicoCollection;
    public static volatile SingularAttribute<TbStatus, String> status;
    public static volatile CollectionAttribute<TbStatus, TbProjetos> tbProjetosCollection;
    public static volatile SingularAttribute<TbStatus, String> descricao;

}