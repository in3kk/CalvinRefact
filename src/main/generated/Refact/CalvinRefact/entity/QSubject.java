package Refact.CalvinRefact.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSubject is a Querydsl query type for Subject
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSubject extends EntityPathBase<Subject> {

    private static final long serialVersionUID = 2013360852L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSubject subject = new QSubject("subject");

    public final Refact.CalvinRefact.entity.baseEntity.QBaseEntity _super = new Refact.CalvinRefact.entity.baseEntity.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Integer> fee = createNumber("fee", Integer.class);

    public final QFile file;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath lecture_time = createString("lecture_time");

    public final QMember member;

    public final ListPath<Member_Subject, QMember_Subject> member_subjects = this.<Member_Subject, QMember_Subject>createList("member_subjects", Member_Subject.class, QMember_Subject.class, PathInits.DIRECT2);

    public final StringPath period = createString("period");

    public final NumberPath<Integer> personnel = createNumber("personnel", Integer.class);

    public final EnumPath<Refact.CalvinRefact.entity.entityEnum.Subject_Field> subject_field = createEnum("subject_field", Refact.CalvinRefact.entity.entityEnum.Subject_Field.class);

    public final StringPath subject_name = createString("subject_name");

    public final EnumPath<Refact.CalvinRefact.entity.entityEnum.Subject_Stat> subject_stat = createEnum("subject_stat", Refact.CalvinRefact.entity.entityEnum.Subject_Stat.class);

    public final EnumPath<Refact.CalvinRefact.entity.entityEnum.Subject_Type> subject_type = createEnum("subject_type", Refact.CalvinRefact.entity.entityEnum.Subject_Type.class);

    public QSubject(String variable) {
        this(Subject.class, forVariable(variable), INITS);
    }

    public QSubject(Path<? extends Subject> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSubject(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSubject(PathMetadata metadata, PathInits inits) {
        this(Subject.class, metadata, inits);
    }

    public QSubject(Class<? extends Subject> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.file = inits.isInitialized("file") ? new QFile(forProperty("file"), inits.get("file")) : null;
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member"), inits.get("member")) : null;
    }

}

