package br.com.dbc.vemser.trabalhofinal.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QSolicitacaoEntity is a Querydsl query type for SolicitacaoEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSolicitacaoEntity extends EntityPathBase<SolicitacaoEntity> {

    private static final long serialVersionUID = 1107390430L;

    public static final QSolicitacaoEntity solicitacaoEntity = new QSolicitacaoEntity("solicitacaoEntity");

    public final DateTimePath<java.time.LocalDateTime> dataHora = createDateTime("dataHora", java.time.LocalDateTime.class);

    public final NumberPath<Integer> idCliente = createNumber("idCliente", Integer.class);

    public final NumberPath<Integer> idMedico = createNumber("idMedico", Integer.class);

    public final StringPath idSoliciatacao = createString("idSoliciatacao");

    public final StringPath motivo = createString("motivo");

    public final EnumPath<StatusSolicitacao> statusSolicitacao = createEnum("statusSolicitacao", StatusSolicitacao.class);

    public QSolicitacaoEntity(String variable) {
        super(SolicitacaoEntity.class, forVariable(variable));
    }

    public QSolicitacaoEntity(Path<? extends SolicitacaoEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSolicitacaoEntity(PathMetadata metadata) {
        super(SolicitacaoEntity.class, metadata);
    }

}

