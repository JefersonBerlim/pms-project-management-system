package Entidades;

import Entidades.TbFuncionarioTurnoSemana;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2013-12-01T17:02:58")
@StaticMetamodel(TbDiaSemana.class)
public class TbDiaSemana_ { 

    public static volatile SingularAttribute<TbDiaSemana, Integer> hand;
    public static volatile CollectionAttribute<TbDiaSemana, TbFuncionarioTurnoSemana> tbFuncionarioTurnoSemanaCollection;
    public static volatile SingularAttribute<TbDiaSemana, String> descricao;

}