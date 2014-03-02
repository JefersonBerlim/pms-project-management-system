package Entidades;

import Entidades.TbApontamentosRecursos;
import Entidades.TbFuncionarios;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2013-12-01T17:02:58")
@StaticMetamodel(TbApontamentosFuncionarios.class)
public class TbApontamentosFuncionarios_ { 

    public static volatile SingularAttribute<TbApontamentosFuncionarios, Integer> hand;
    public static volatile SingularAttribute<TbApontamentosFuncionarios, Date> dataFim;
    public static volatile SingularAttribute<TbApontamentosFuncionarios, Date> horaInicio;
    public static volatile SingularAttribute<TbApontamentosFuncionarios, TbFuncionarios> tbFuncionariosHand;
    public static volatile SingularAttribute<TbApontamentosFuncionarios, String> finalizaApontamento;
    public static volatile SingularAttribute<TbApontamentosFuncionarios, Date> dataInicio;
    public static volatile SingularAttribute<TbApontamentosFuncionarios, Date> horaFim;
    public static volatile SingularAttribute<TbApontamentosFuncionarios, TbApontamentosRecursos> tbApontamentosRecursosHand;

}