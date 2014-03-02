package Entidades;

import Entidades.TbFuncionarios;
import Entidades.TbOrdemServico;
import Entidades.TbPessoaProjeto;
import Entidades.TbProjetoMarcos;
import Entidades.TbStatus;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2013-12-01T17:02:58")
@StaticMetamodel(TbProjetos.class)
public class TbProjetos_ { 

    public static volatile CollectionAttribute<TbProjetos, TbPessoaProjeto> tbPessoaProjetoCollection;
    public static volatile SingularAttribute<TbProjetos, String> versaoProjeto;
    public static volatile SingularAttribute<TbProjetos, TbFuncionarios> tbFuncionariosHand;
    public static volatile SingularAttribute<TbProjetos, Date> horaInicio;
    public static volatile SingularAttribute<TbProjetos, TbStatus> tbStatusHand;
    public static volatile SingularAttribute<TbProjetos, Date> dataAbertura;
    public static volatile SingularAttribute<TbProjetos, Date> horaFechamento;
    public static volatile SingularAttribute<TbProjetos, String> decsricao;
    public static volatile SingularAttribute<TbProjetos, Date> horaAbertura;
    public static volatile SingularAttribute<TbProjetos, Integer> hand;
    public static volatile SingularAttribute<TbProjetos, Date> dataFechamento;
    public static volatile SingularAttribute<TbProjetos, Date> dataFim;
    public static volatile SingularAttribute<TbProjetos, BigDecimal> qtdHoras;
    public static volatile SingularAttribute<TbProjetos, Date> horaFim;
    public static volatile SingularAttribute<TbProjetos, Date> dataInicio;
    public static volatile SingularAttribute<TbProjetos, String> ehInativo;
    public static volatile CollectionAttribute<TbProjetos, TbOrdemServico> tbOrdemServicoCollection;
    public static volatile SingularAttribute<TbProjetos, Date> dataCriacao;
    public static volatile CollectionAttribute<TbProjetos, TbProjetoMarcos> tbProjetoMarcosCollection;
    public static volatile SingularAttribute<TbProjetos, Date> horaCriacao;

}