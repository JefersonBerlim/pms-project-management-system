package Entidades;

import Entidades.TbApontamentosFuncionarios;
import Entidades.TbFuncionarioTurnoSemana;
import Entidades.TbFuncionariosRecursos;
import Entidades.TbProjetos;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2013-12-01T17:02:58")
@StaticMetamodel(TbFuncionarios.class)
public class TbFuncionarios_ { 

    public static volatile CollectionAttribute<TbFuncionarios, TbFuncionarioTurnoSemana> tbFuncionarioTurnoSemanaCollection;
    public static volatile SingularAttribute<TbFuncionarios, String> telefone2;
    public static volatile SingularAttribute<TbFuncionarios, Date> dataAdmissao;
    public static volatile CollectionAttribute<TbFuncionarios, TbApontamentosFuncionarios> tbApontamentosFuncionariosCollection;
    public static volatile SingularAttribute<TbFuncionarios, String> ehPlanejador;
    public static volatile SingularAttribute<TbFuncionarios, String> numeroIdentidade;
    public static volatile SingularAttribute<TbFuncionarios, Integer> hand;
    public static volatile CollectionAttribute<TbFuncionarios, TbFuncionariosRecursos> tbFuncionariosRecursosCollection;
    public static volatile SingularAttribute<TbFuncionarios, String> email;
    public static volatile SingularAttribute<TbFuncionarios, BigDecimal> valorHora;
    public static volatile SingularAttribute<TbFuncionarios, String> numeroMatricula;
    public static volatile SingularAttribute<TbFuncionarios, String> telefone;
    public static volatile SingularAttribute<TbFuncionarios, String> ehInativo;
    public static volatile SingularAttribute<TbFuncionarios, Date> dataNascimento;
    public static volatile SingularAttribute<TbFuncionarios, String> cpf;
    public static volatile SingularAttribute<TbFuncionarios, String> nome;
    public static volatile CollectionAttribute<TbFuncionarios, TbProjetos> tbProjetosCollection;

}