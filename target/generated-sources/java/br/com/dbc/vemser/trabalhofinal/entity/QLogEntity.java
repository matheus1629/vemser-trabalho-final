package br.com.dbc.vemser.trabalhofinal.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QLogEntity is a Querydsl query type for LogEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLogEntity extends EntityPathBase<LogEntity> {

    private static final long serialVersionUID = -1472225475L;

    public static final QLogEntity logEntity = new QLogEntity("logEntity");

    public final DateTimePath<java.time.LocalDateTime> dataHora = createDateTime("dataHora", java.time.LocalDateTime.class);

    public final NumberPath<Integer> idAgendamento = createNumber("idAgendamento", Integer.class);

    public final StringPath idLog = createString("idLog");

    public final StringPath idSolicitacao = createString("idSolicitacao");

    public final NumberPath<Integer> idUsuario = createNumber("idUsuario", Integer.class);

    public final EnumPath<TipoLog> tipoLog = createEnum("tipoLog", TipoLog.class);

    public QLogEntity(String variable) {
        super(LogEntity.class, forVariable(variable));
    }

    public QLogEntity(Path<? extends LogEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QLogEntity(PathMetadata metadata) {
        super(LogEntity.class, metadata);
    }

}

