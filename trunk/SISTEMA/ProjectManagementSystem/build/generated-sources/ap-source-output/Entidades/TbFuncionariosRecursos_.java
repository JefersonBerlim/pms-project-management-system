package Entidades;

import Entidades.TbFuncionarios;
import Entidades.TbProjetoFuncionarios;
import Entidades.TbRecursos;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2013-12-01T17:02:58")
@StaticMetamodel(TbFuncionariosRecursos.class)
public class TbFuncionariosRecursos_ { 

    public static volatile SingularAttribute<TbFuncionariosRecursos, Integer> hand;
    public static volatile SingularAttribute<TbFuncionariosRecursos, TbFuncionarios> tbFuncionariosHand;
    public static volatile SingularAttribute<TbFuncionariosRecursos, String> automatizaProcesso;
    public static volatile CollectionAttribute<TbFuncionariosRecursos, TbProjetoFuncionarios> tbProjetoFuncionariosCollection;
    public static volatile SingularAttribute<TbFuncionariosRecursos, TbRecursos> tbRecursosHand;

}