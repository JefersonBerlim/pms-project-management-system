package Entidades;

import Entidades.TbApontamentosMateriais;
import Entidades.TbOsMateriais;
import Entidades.TbProjetosMateriais;
import Entidades.TbUnidadeMedida;
import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2013-12-01T17:02:58")
@StaticMetamodel(TbMateriais.class)
public class TbMateriais_ { 

    public static volatile SingularAttribute<TbMateriais, Integer> hand;
    public static volatile SingularAttribute<TbMateriais, TbUnidadeMedida> tbUnidadeMedidaHand;
    public static volatile SingularAttribute<TbMateriais, BigDecimal> valor;
    public static volatile CollectionAttribute<TbMateriais, TbApontamentosMateriais> tbApontamentosMateriaisCollection;
    public static volatile SingularAttribute<TbMateriais, String> ehInativo;
    public static volatile CollectionAttribute<TbMateriais, TbOsMateriais> tbOsMateriaisCollection;
    public static volatile SingularAttribute<TbMateriais, String> descricao;
    public static volatile SingularAttribute<TbMateriais, String> observacao;
    public static volatile CollectionAttribute<TbMateriais, TbProjetosMateriais> tbProjetosMateriaisCollection;

}