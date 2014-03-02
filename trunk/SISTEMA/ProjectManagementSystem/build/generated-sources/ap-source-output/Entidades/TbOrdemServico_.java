package Entidades;

import Entidades.TbOsServico;
import Entidades.TbProjetos;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2013-12-01T17:02:58")
@StaticMetamodel(TbOrdemServico.class)
public class TbOrdemServico_ { 

    public static volatile SingularAttribute<TbOrdemServico, Integer> hand;
    public static volatile CollectionAttribute<TbOrdemServico, TbOsServico> tbOsServicoCollection;
    public static volatile SingularAttribute<TbOrdemServico, TbProjetos> tbProjetosHand;
    public static volatile SingularAttribute<TbOrdemServico, String> descricao;

}