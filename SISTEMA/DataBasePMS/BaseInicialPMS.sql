-- -----------------------------------------------------
-- Table TB_PAISES
-- -----------------------------------------------------

CREATE TABLE TB_PAISES (
  HAND INT NOT NULL ,
  PAIS VARCHAR(150) NOT NULL ,
  PRIMARY KEY ( HAND ) ) ;


-- -----------------------------------------------------
-- Table TB_ESTADOS
-- -----------------------------------------------------

CREATE TABLE TB_ESTADOS (
  HAND INT NOT NULL ,
  TB_PAISES_HAND INT NOT NULL ,
  ESTADO VARCHAR(150) NOT NULL ,
  SIGLA VARCHAR(2) NOT NULL ,
  PRIMARY KEY ( HAND ) ,
  FOREIGN KEY( TB_PAISES_HAND )
      REFERENCES TB_PAISES ( HAND )
         ON DELETE NO ACTION
         ON UPDATE NO ACTION );

-- -----------------------------------------------------
-- Table TB_CIDADES
-- -----------------------------------------------------

CREATE TABLE TB_CIDADES (
  HAND INT NOT NULL ,
  TB_ESTADOS_HAND INT NOT NULL ,
  CIDADE VARCHAR(150) NOT NULL ,
  PRIMARY KEY ( HAND ) ,
  FOREIGN KEY( TB_ESTADOS_HAND )
      REFERENCES TB_ESTADOS ( HAND )
         ON DELETE NO ACTION
         ON UPDATE NO ACTION );


-- -----------------------------------------------------
-- Table TB_TIPOPESSOA
-- -----------------------------------------------------
CREATE TABLE TB_TIPOPESSOA (
  HAND INT NOT NULL ,
  TIPO VARCHAR(1) NOT NULL ,
  DESCRICAO VARCHAR(50) NOT NULL ,
  PRIMARY KEY ( HAND ) ) ;


-- -----------------------------------------------------
-- Table TB_PESSOA
-- -----------------------------------------------------
CREATE TABLE TB_PESSOA (
  HAND INT NOT NULL ,
  TB_CIDADES_HAND INT NOT NULL ,
  TB_TIPOPESSOA_HAND INT NOT NULL ,
  NOME_FANTASIA VARCHAR(150) NOT NULL ,
  RAZAO_SOCIAL VARCHAR(150) NOT NULL ,
  TELEFONE VARCHAR(45) ,
  ENDERECO VARCHAR(150) ,
  NUMERO VARCHAR(10)  ,
  COMPLEMENTO VARCHAR(50)  ,
  BAIRRO VARCHAR(50)  ,
  CPF_CNPJ VARCHAR(18)  ,
  EHFORNECEDOR VARCHAR(1)  ,
  EHCLIENTE VARCHAR(1)  ,
  OBSERVACOES VARCHAR(255)  ,
  ENDERECO_WEB VARCHAR(50)  ,
  EH_INATIVO VARCHAR(1)  ,
  PRIMARY KEY ( HAND ) ,
  FOREIGN KEY( TB_TIPOPESSOA_HAND )
      REFERENCES TB_TIPOPESSOA ( HAND )
         ON DELETE NO ACTION
         ON UPDATE NO ACTION,
   FOREIGN KEY( TB_CIDADES_HAND )
      REFERENCES TB_CIDADES ( HAND )
         ON DELETE NO ACTION
         ON UPDATE NO ACTION ) ;

-- -----------------------------------------------------
-- Table TB_USUARIO
-- -----------------------------------------------------
CREATE TABLE TB_USUARIO (
  HAND INT NOT NULL ,
  NOME VARCHAR(150) NOT NULL ,
  SENHA VARCHAR(8) NOT NULL ,
  PRIMARY KEY ( HAND ) ) ;


-- -----------------------------------------------------
-- Table TB_STATUS
-- -----------------------------------------------------
CREATE TABLE TB_STATUS (
  HAND INT NOT NULL ,
  STATUS VARCHAR(1) NOT NULL ,
  DESCRICAO VARCHAR(50) NOT NULL ,
  PRIMARY KEY ( HAND ) ) ;


-- -----------------------------------------------------
-- Table TB_FUNCIONARIOS
-- -----------------------------------------------------
CREATE TABLE TB_FUNCIONARIOS (
  HAND INT NOT NULL ,
  NOME VARCHAR(150) ,
  NUMERO_MATRICULA VARCHAR(50) ,
  DATA_ADMISSAO DATE ,
  CPF VARCHAR(15) ,
  TELEFONE VARCHAR(20) ,
  TELEFONE2 VARCHAR(20) ,
  VALOR_HORA NUMERIC( 15 ,2) ,
  NUMERO_IDENTIDADE VARCHAR(15) ,
  EMAIL VARCHAR(50) ,
  DATA_NASCIMENTO DATE ,
  EH_PLANEJADOR VARCHAR(1) ,
  EH_INATIVO VARCHAR(1) ,
  PRIMARY KEY ( HAND ) ) ;


-- -----------------------------------------------------
-- Table TB_PROJETOS
-- -----------------------------------------------------
CREATE TABLE TB_PROJETOS (
  HAND INT NOT NULL ,
  TB_STATUS_HAND INT NOT NULL ,
  TB_FUNCIONARIOS_HAND INT NOT NULL ,
  VERSAO_PROJETO VARCHAR( 15 ) ,
  DECSRICAO VARCHAR(50) NOT NULL ,
  DATA_ABERTURA DATE ,
  HORA_ABERTURA TIME  ,
  DATA_INICIO DATE  ,
  HORA_INICIO TIME  ,
  DATA_FIM DATE  ,
  HORA_FIM TIME  ,
  DATA_FECHAMENTO DATE  ,
  HORA_FECHAMENTO TIME  ,
  QTD_HORAS NUMERIC( 5,2)   ,
  DATA_CRIACAO DATE ,
  HORA_CRIACAO TIME,
  EH_INATIVO VARCHAR(1) ,
  PRIMARY KEY ( HAND ) ,
  FOREIGN KEY( TB_STATUS_HAND )
      REFERENCES TB_STATUS ( HAND )
         ON DELETE NO ACTION
         ON UPDATE NO ACTION,
   FOREIGN KEY( TB_FUNCIONARIOS_HAND )
      REFERENCES TB_FUNCIONARIOS ( HAND )
         ON DELETE NO ACTION
         ON UPDATE NO ACTION ) ;


-- -----------------------------------------------------
-- Table TB_PESSOA_PROJETO
-- -----------------------------------------------------
CREATE TABLE TB_PESSOA_PROJETO (
  HAND INT NOT NULL ,
  TB_PESSOA_HAND INT NOT NULL ,
  TB_PROJETO_HAND INT NOT NULL ,
  PRIMARY KEY ( HAND ) ,
  FOREIGN KEY( TB_PESSOA_HAND )
      REFERENCES TB_PESSOA ( HAND )
         ON DELETE NO ACTION
         ON UPDATE NO ACTION,
   FOREIGN KEY( TB_PROJETO_HAND )
      REFERENCES TB_PROJETOS ( HAND )
         ON DELETE NO ACTION
         ON UPDATE NO ACTION ) ;


-- -----------------------------------------------------
-- Table TB_MARCOS
-- -----------------------------------------------------
CREATE TABLE TB_MARCOS (
  HAND  INT NOT NULL ,
  DESCRICAO VARCHAR(50) ,
  EH_INATIVO VARCHAR(1) ,
  PRIMARY KEY ( HAND ) ) ;


-- -----------------------------------------------------
-- Table TB_PROJETO_MARCOS
-- -----------------------------------------------------
CREATE TABLE TB_PROJETO_MARCOS (
  HAND INT NOT NULL ,
  TB_PROJETO_HAND INT NOT NULL ,
  TB_MARCOS_HAND INT NOT NULL ,
  DATA_INICIO DATE ,
  HORA_INICIO TIME ,
  DATA_FIM DATE ,
  HORA_FIM TIME ,
  PRIMARY KEY ( HAND ),
    FOREIGN KEY( TB_MARCOS_HAND )
      REFERENCES TB_MARCOS ( HAND )
         ON DELETE NO ACTION
         ON UPDATE NO ACTION,
   FOREIGN KEY( TB_PROJETO_HAND )
      REFERENCES TB_PROJETOS ( HAND )
         ON DELETE NO ACTION
         ON UPDATE NO ACTION ) ;


-- -----------------------------------------------------
-- Table TB_SERVICOS
-- -----------------------------------------------------
CREATE TABLE TB_SERVICOS (
  HAND INT NOT NULL ,
  DESCRICAO VARCHAR(50) ,
  VALOR_HORA NUMERIC( 15 ,2) ,
  EH_INATIVO VARCHAR(1) ,
  PRIMARY KEY ( HAND ) ) ;


-- -----------------------------------------------------
-- Table TB_PROJETOS_SERVICOS
-- -----------------------------------------------------
CREATE TABLE TB_PROJETOS_SERVICOS (
  HAND INT NOT NULL ,
  SERVICO_DEPENDENTE INT ,
  TB_PROJETO_MARCOS_HAND INT NOT NULL ,
  TB_SERVICOS_HAND INT NOT NULL ,
  DATA_INICIO DATE  ,
  HORA_INICIO TIME  ,
  DATA_FIM DATE  ,
  HORA_FIM TIME  ,
  OBSERVACAO VARCHAR(255) ,
  ARQUIVOS VARCHAR(50) ,
  QTD_HORAS NUMERIC( 5,2),
  PRIMARY KEY ( HAND ) ,
    FOREIGN KEY( SERVICO_DEPENDENTE )
      REFERENCES TB_SERVICOS ( HAND )
         ON DELETE NO ACTION
         ON UPDATE NO ACTION,
  FOREIGN KEY( TB_PROJETO_MARCOS_HAND )
      REFERENCES TB_PROJETO_MARCOS ( HAND )
         ON DELETE NO ACTION
         ON UPDATE NO ACTION,
  FOREIGN KEY( TB_SERVICOS_HAND )
      REFERENCES TB_SERVICOS ( HAND )
         ON DELETE NO ACTION
         ON UPDATE NO ACTION );


-- -----------------------------------------------------
-- Table TB_UNIDADE_MEDIDA
-- -----------------------------------------------------
CREATE TABLE TB_UNIDADE_MEDIDA (
  HAND INT NOT NULL ,
  SIGLA VARCHAR(10) ,
  DESCRICAO VARCHAR(50) ,
  PRIMARY KEY ( HAND ) ) ;


-- -----------------------------------------------------
-- Table TB_MATERIAIS
-- -----------------------------------------------------
CREATE TABLE TB_MATERIAIS (
  HAND INT NOT NULL ,
  TB_UNIDADE_MEDIDA_HAND INT NOT NULL ,
  DESCRICAO VARCHAR(50) ,
  OBSERVACAO VARCHAR(255) ,
  VALOR NUMERIC( 15 , 2 ) ,
  EH_INATIVO VARCHAR(1) ,
  PRIMARY KEY ( HAND ) ,
  FOREIGN KEY( TB_UNIDADE_MEDIDA_HAND )
      REFERENCES TB_UNIDADE_MEDIDA ( HAND )
         ON DELETE NO ACTION
         ON UPDATE NO ACTION ) ;


-- -----------------------------------------------------
-- Table TB_RECURSOS
-- -----------------------------------------------------
CREATE TABLE TB_RECURSOS (
  HAND INT NOT NULL ,
  DESCRICAO VARCHAR(50) ,
  VALOR_HORA NUMERIC( 15 , 2 ) ,
  EH_INATIVO VARCHAR(1)  ,
  FUNCIONARIO_SIMULTANEO VARCHAR(1) ,
  PRIMARY KEY ( HAND ) ) ;


-- -----------------------------------------------------
-- Table TB_PROJETOS_MATERIAIS
-- -----------------------------------------------------
CREATE TABLE TB_PROJETOS_MATERIAIS (
  HAND INT NOT NULL ,
  TB_PROJETOS_SERVICOS_HAND INT NOT NULL ,
  TB_MATERIAIS_HAND INT NOT NULL ,
  QUANTIDADE NUMERIC( 15 , 2 ) ,
  INFORMACAO_COMPLEMENTAR VARCHAR(255) ,
  PRIMARY KEY ( HAND ),
  FOREIGN KEY( TB_PROJETOS_SERVICOS_HAND )
      REFERENCES TB_PROJETOS_SERVICOS ( HAND )
         ON DELETE NO ACTION
         ON UPDATE NO ACTION, 
  FOREIGN KEY( TB_MATERIAIS_HAND )
      REFERENCES TB_MATERIAIS ( HAND )
         ON DELETE NO ACTION
         ON UPDATE NO ACTION ) ;


-- -----------------------------------------------------
-- Table TB_RECURSOS_SERVICOS
-- -----------------------------------------------------
CREATE TABLE TB_RECURSOS_SERVICOS (
  HAND INT NOT NULL ,
  TB_RECURSOS_HAND INT NOT NULL ,
  TB_SERVICOS_HAND INT NOT NULL ,
  AUTOMATIZA_PROCESSO VARCHAR(1)  ,
  PRIMARY KEY ( HAND ) ,
  FOREIGN KEY( TB_RECURSOS_HAND )
      REFERENCES TB_RECURSOS ( HAND )
         ON DELETE NO ACTION
         ON UPDATE NO ACTION, 
  FOREIGN KEY( TB_SERVICOS_HAND )
      REFERENCES TB_SERVICOS ( HAND )
         ON DELETE NO ACTION
         ON UPDATE NO ACTION ) ;


-- -----------------------------------------------------
-- Table TB_FUNCIONARIOS_RECURSOS
-- -----------------------------------------------------
CREATE TABLE TB_FUNCIONARIOS_RECURSOS (
  HAND INT NOT NULL ,
  TB_FUNCIONARIOS_HAND INT NOT NULL ,
  TB_RECURSOS_HAND INT NOT NULL ,
  AUTOMATIZA_PROCESSO VARCHAR(1) ,
  PRIMARY KEY ( HAND ) ,
    FOREIGN KEY( TB_FUNCIONARIOS_HAND )
      REFERENCES TB_FUNCIONARIOS ( HAND )
         ON DELETE NO ACTION
         ON UPDATE NO ACTION, 
  FOREIGN KEY( TB_RECURSOS_HAND )
      REFERENCES TB_RECURSOS ( HAND )
         ON DELETE NO ACTION
         ON UPDATE NO ACTION ) ;


-- -----------------------------------------------------
-- Table TB_PROJETOS_RECURSOS
-- -----------------------------------------------------
CREATE TABLE TB_PROJETOS_RECURSOS (
  HAND INT NOT NULL ,
  TB_PROJETOS_SERVICOS_HAND INT NOT NULL ,
  TB_RECURSOS_HAND INT NOT NULL ,
  QUANTIDADE_HORAS NUMERIC( 5, 2 ) ,
  PRIMARY KEY ( HAND ) ,
      FOREIGN KEY( TB_PROJETOS_SERVICOS_HAND )
      REFERENCES TB_PROJETOS_SERVICOS ( HAND )
         ON DELETE NO ACTION
         ON UPDATE NO ACTION, 
  FOREIGN KEY( TB_RECURSOS_HAND )
      REFERENCES TB_RECURSOS ( HAND )
         ON DELETE NO ACTION
         ON UPDATE NO ACTION ) ;


-- -----------------------------------------------------
-- Table TB_TURNOS
-- -----------------------------------------------------
CREATE TABLE TB_TURNOS (
  HAND INT NOT NULL ,
  DESCRICAO VARCHAR(50) NOT NULL ,
  HORARIO_INICIAL TIME  ,
  HORARIO_FINAL TIME  ,
  ALMOCO_INICIO TIME  ,
  ALMOCO_FIM TIME  ,
  PRIMARY KEY ( HAND ) ) ;

-- -----------------------------------------------------
-- Table TB_DIA_SEMANA
-- -----------------------------------------------------
CREATE TABLE TB_DIA_SEMANA (
  HAND INT NOT NULL ,
  DESCRICAO VARCHAR(50) NOT NULL ,
  PRIMARY KEY ( HAND ) ) ;

-- -----------------------------------------------------
-- Table TB_FUNCIONARIO_TURNO_SEMANA
-- -----------------------------------------------------
CREATE TABLE TB_FUNCIONARIO_TURNO_SEMANA (
  HAND INT NOT NULL ,
  TB_TURNOS_HAND INT NOT NULL ,
  TB_DIA_SEMANA_HAND INT NOT NULL ,
  TB_FUNCIONARIOS_HAND INT NOT NULL ,
  PRIMARY KEY ( HAND ) ,
  FOREIGN KEY( TB_TURNOS_HAND )
      REFERENCES TB_TURNOS ( HAND )
         ON DELETE NO ACTION
         ON UPDATE NO ACTION, 
  FOREIGN KEY( TB_DIA_SEMANA_HAND )
      REFERENCES TB_DIA_SEMANA ( HAND )
         ON DELETE NO ACTION
         ON UPDATE NO ACTION,
  FOREIGN KEY( TB_FUNCIONARIOS_HAND )
      REFERENCES TB_FUNCIONARIOS ( HAND )
         ON DELETE NO ACTION
         ON UPDATE NO ACTION ) ;


-- -----------------------------------------------------
-- Table TB_DIA_NAO_UTIL
-- -----------------------------------------------------
CREATE TABLE TB_DIA_NAO_UTIL (
  HAND INT NOT NULL ,
  DIA_NAO_UTIL DATE ,
  PRIMARY KEY ( HAND ) ) ;


-- -----------------------------------------------------
-- Table TB_ORDEM_SERVICO
-- -----------------------------------------------------
CREATE TABLE TB_ORDEM_SERVICO (
  HAND INT NOT NULL ,
  DESCRICAO VARCHAR(255) ,
  TB_PROJETOS_HAND INT NOT NULL ,
  PRIMARY KEY (HAND),
  FOREIGN KEY( TB_PROJETOS_HAND )
      REFERENCES TB_PROJETOS ( HAND )
         ON DELETE NO ACTION
         ON UPDATE NO ACTION ) ;


-- -----------------------------------------------------
-- Table TB_OS_SERVICO
-- -----------------------------------------------------
CREATE TABLE TB_OS_SERVICO (
  HAND INT NOT NULL ,
  TB_ORDEM_SERVICO_HAND INT NOT NULL ,
  TB_PROJETOS_SERVICOS_HAND INT NOT NULL ,
  TB_STATUS_HAND INT NOT NULL ,
  PRIMARY KEY (HAND),
  FOREIGN KEY( TB_ORDEM_SERVICO_HAND )
      REFERENCES TB_ORDEM_SERVICO ( HAND )
         ON DELETE NO ACTION
         ON UPDATE NO ACTION,
  FOREIGN KEY( TB_PROJETOS_SERVICOS_HAND )
      REFERENCES TB_PROJETOS_SERVICOS ( HAND )
         ON DELETE NO ACTION
         ON UPDATE NO ACTION,
  FOREIGN KEY( TB_STATUS_HAND )
      REFERENCES TB_STATUS ( HAND )
         ON DELETE NO ACTION
         ON UPDATE NO ACTION ) ;


-- -----------------------------------------------------
-- Table TB_APONTAMENTOS_MATERIAIS
-- -----------------------------------------------------
CREATE TABLE TB_APONTAMENTOS_MATERIAIS (
  HAND INT NOT NULL ,
  TB_MATERIAIS_HAND INT NOT NULL ,
  TB_OS_SERVICO_HAND INT NOT NULL ,
  QUANTIDAE_UTILIZADA NUMERIC( 15 , 2 )  ,
  PRIMARY KEY (HAND), 
  FOREIGN KEY( TB_MATERIAIS_HAND )
      REFERENCES TB_MATERIAIS ( HAND )
         ON DELETE NO ACTION
         ON UPDATE NO ACTION,
  FOREIGN KEY( TB_OS_SERVICO_HAND )
      REFERENCES TB_OS_SERVICO ( HAND )
         ON DELETE NO ACTION
         ON UPDATE NO ACTION ) ;

-- -----------------------------------------------------
-- Table TB_APONTAMENTOS_RECURSOS
-- -----------------------------------------------------
CREATE TABLE TB_APONTAMENTOS_RECURSOS (
  HAND INT NOT NULL ,
  TB_RECURSOS_HAND INT NOT NULL ,
  TB_OS_SERVICO_HAND INT NOT NULL ,
  PRIMARY KEY (HAND) ,
  FOREIGN KEY( TB_RECURSOS_HAND )
      REFERENCES TB_RECURSOS ( HAND )
         ON DELETE NO ACTION
         ON UPDATE NO ACTION,
  FOREIGN KEY( TB_OS_SERVICO_HAND )
      REFERENCES TB_OS_SERVICO ( HAND )
         ON DELETE NO ACTION
         ON UPDATE NO ACTION ) ;

-- -----------------------------------------------------
-- Table TB_MARCOS_SERVICOS
-- -----------------------------------------------------
CREATE TABLE TB_MARCOS_SERVICOS (
  HAND INT NOT NULL ,
  TB_MARCOS_HAND INT NOT NULL ,
  TB_SERVICOS_HAND INT NOT NULL ,
  AUTOMATIZA_PROCESSO VARCHAR(1) ,
  PRIMARY KEY (HAND) ,
  FOREIGN KEY( TB_MARCOS_HAND )
      REFERENCES TB_MARCOS ( HAND )
         ON DELETE NO ACTION
         ON UPDATE NO ACTION,
  FOREIGN KEY( TB_SERVICOS_HAND )
      REFERENCES TB_SERVICOS ( HAND )
         ON DELETE NO ACTION
         ON UPDATE NO ACTION ) ;

-- -----------------------------------------------------
-- Table TB_PROJETO_FUNCIONARIOS
-- -----------------------------------------------------
CREATE TABLE TB_PROJETO_FUNCIONARIOS (
  HAND INT NOT NULL ,
  TB_PROJETOS_RECURSOS_HAND INT NOT NULL ,
  TB_FUNCIONARIOS_RECURSOS_HAND INT NOT NULL ,
  QUANTIDADE_HORAS NUMERIC( 5 , 2 ) ,
  PRIMARY KEY (HAND) ,
  FOREIGN KEY( TB_PROJETOS_RECURSOS_HAND )
      REFERENCES TB_PROJETOS_RECURSOS ( HAND )
         ON DELETE NO ACTION
         ON UPDATE NO ACTION,
  FOREIGN KEY( TB_FUNCIONARIOS_RECURSOS_HAND )
      REFERENCES TB_FUNCIONARIOS_RECURSOS ( HAND )
         ON DELETE NO ACTION
         ON UPDATE NO ACTION ) ;

-- -----------------------------------------------------
-- Table TB_APONTAMENTOS_FUNCIONARIOS
-- -----------------------------------------------------
CREATE TABLE TB_APONTAMENTOS_FUNCIONARIOS (
  HAND INT NOT NULL ,
  TB_FUNCIONARIOS_HAND INT NOT NULL ,
  TB_APONTAMENTOS_RECURSOS_HAND INT NOT NULL ,
  DATA_INICIO DATE  ,
  DATA_FIM DATE  ,
  HORA_INICIO TIME  ,
  HORA_FIM TIME  ,
  FINALIZA_APONTAMENTO VARCHAR(1)  ,
  PRIMARY KEY (HAND) ,
  FOREIGN KEY( TB_FUNCIONARIOS_HAND )
      REFERENCES TB_FUNCIONARIOS ( HAND )
         ON DELETE NO ACTION
         ON UPDATE NO ACTION,
  FOREIGN KEY( TB_APONTAMENTOS_RECURSOS_HAND )
      REFERENCES TB_APONTAMENTOS_RECURSOS ( HAND )
         ON DELETE NO ACTION
         ON UPDATE NO ACTION ) ;

-- -----------------------------------------------------
-- Table TB_OS_MATERIAIS
-- -----------------------------------------------------
CREATE TABLE TB_OS_MATERIAIS (
  HAND INT NOT NULL ,
  TB_OS_SERVICO_HAND INT NOT NULL ,
  TB_MATERIAIS_HAND INT NOT NULL ,
  TB_PROJETOS_MATERIAIS_HAND INT NOT NULL ,
  QUANTIDADE_UTILIZADA NUMERIC( 15 , 2 )  ,
  PRIMARY KEY (HAND) ,
  FOREIGN KEY( TB_OS_SERVICO_HAND )
      REFERENCES TB_OS_SERVICO ( HAND )
         ON DELETE NO ACTION
         ON UPDATE NO ACTION,
  FOREIGN KEY( TB_MATERIAIS_HAND )
      REFERENCES TB_MATERIAIS ( HAND )
         ON DELETE NO ACTION
         ON UPDATE NO ACTION,
  FOREIGN KEY( TB_PROJETOS_MATERIAIS_HAND )
      REFERENCES TB_PROJETOS_MATERIAIS ( HAND )
         ON DELETE NO ACTION
         ON UPDATE NO ACTION ) ;

-- -----------------------------------------------------
-- Table TB_OS_RECURSOS
-- -----------------------------------------------------
CREATE TABLE TB_OS_RECURSOS (
  HAND INT NOT NULL ,
  TB_OS_SERVICO_HAND INT NOT NULL ,
  TB_PROJETOS_RECURSOS_HAND INT NOT NULL ,
  QUANTIDADE_HORAS NUMERIC( 5 , 2 )  ,
  PRIMARY KEY (HAND),
  FOREIGN KEY( TB_OS_SERVICO_HAND )
      REFERENCES TB_OS_SERVICO ( HAND )
         ON DELETE NO ACTION
         ON UPDATE NO ACTION,
  FOREIGN KEY( TB_PROJETOS_RECURSOS_HAND )
      REFERENCES TB_PROJETOS_RECURSOS ( HAND )
         ON DELETE NO ACTION
         ON UPDATE NO ACTION ) ;

-- -----------------------------------------------------
-- Table TB_OS_FUNCIONARIOS
-- -----------------------------------------------------
CREATE TABLE TB_OS_FUNCIONARIOS (
  HAND INT NOT NULL ,
  TB_OS_RECURSOS_HAND INT NOT NULL ,
  TB_PROJETO_FUNCIONARIOS_HAND INT NOT NULL ,
  QUANTIDAE_HORAS NUMERIC( 5 , 2 )  ,
  PRIMARY KEY (HAND) ,
  FOREIGN KEY( TB_OS_RECURSOS_HAND )
      REFERENCES TB_OS_RECURSOS ( HAND )
         ON DELETE NO ACTION
         ON UPDATE NO ACTION,
  FOREIGN KEY( TB_PROJETO_FUNCIONARIOS_HAND )
      REFERENCES TB_PROJETO_FUNCIONARIOS ( HAND )
         ON DELETE NO ACTION
         ON UPDATE NO ACTION ) ;

-- -----------------------------------------------------
-- Table TB_PARAMETROS
-- -----------------------------------------------------
CREATE TABLE TB_PARAMETROS (
  HAND INT NOT NULL ,
  HORA_INICIO_NOTURNO TIME  ,
  HORA_FIM_NOTURNO TIME  ,
  PERCENT_ADICIONAL_NOTURNO NUMERIC( 3 , 2 )  ,
  PERCENT_HR_EXTRA_NOTURNA NUMERIC( 3 , 2 )  ,
  PERCENT_HR_EXTRA_DIURNA NUMERIC( 3 , 2 )  ,
  PRIMARY KEY (HAND) ) ;

