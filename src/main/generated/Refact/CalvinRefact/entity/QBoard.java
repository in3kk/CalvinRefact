package Refact.CalvinRefact.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBoard is a Querydsl query type for Board
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBoard extends EntityPathBase<Board> {

    private static final long serialVersionUID = 938169998L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBoard board = new QBoard("board");

    public final Refact.CalvinRefact.entity.baseEntity.QBaseEntity _super = new Refact.CalvinRefact.entity.baseEntity.QBaseEntity(this);

    public final EnumPath<Refact.CalvinRefact.entity.entityEnum.Board_Type> board_type = createEnum("board_type", Refact.CalvinRefact.entity.entityEnum.Board_Type.class);

    public final StringPath contents = createString("contents");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final QFile file1;

    public final QFile file2;

    public final QFile file3;

    public final QFile file4;

    public final QFile file5;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMember member;

    public final StringPath title = createString("title");

    public QBoard(String variable) {
        this(Board.class, forVariable(variable), INITS);
    }

    public QBoard(Path<? extends Board> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBoard(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBoard(PathMetadata metadata, PathInits inits) {
        this(Board.class, metadata, inits);
    }

    public QBoard(Class<? extends Board> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.file1 = inits.isInitialized("file1") ? new QFile(forProperty("file1"), inits.get("file1")) : null;
        this.file2 = inits.isInitialized("file2") ? new QFile(forProperty("file2"), inits.get("file2")) : null;
        this.file3 = inits.isInitialized("file3") ? new QFile(forProperty("file3"), inits.get("file3")) : null;
        this.file4 = inits.isInitialized("file4") ? new QFile(forProperty("file4"), inits.get("file4")) : null;
        this.file5 = inits.isInitialized("file5") ? new QFile(forProperty("file5"), inits.get("file5")) : null;
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
    }

}

