package Refact.CalvinRefact.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMember_Subject is a Querydsl query type for Member_Subject
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember_Subject extends EntityPathBase<Member_Subject> {

    private static final long serialVersionUID = -1713792577L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMember_Subject member_Subject = new QMember_Subject("member_Subject");

    public final Refact.CalvinRefact.entity.baseEntity.QBaseEntity _super = new Refact.CalvinRefact.entity.baseEntity.QBaseEntity(this);

    public final DatePath<java.time.LocalDate> app_date = createDate("app_date", java.time.LocalDate.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMember member;

    public final EnumPath<Refact.CalvinRefact.entity.entityEnum.Pay_Stat> pay_stat = createEnum("pay_stat", Refact.CalvinRefact.entity.entityEnum.Pay_Stat.class);

    public final QSubject subject;

    public QMember_Subject(String variable) {
        this(Member_Subject.class, forVariable(variable), INITS);
    }

    public QMember_Subject(Path<? extends Member_Subject> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMember_Subject(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMember_Subject(PathMetadata metadata, PathInits inits) {
        this(Member_Subject.class, metadata, inits);
    }

    public QMember_Subject(Class<? extends Member_Subject> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
        this.subject = inits.isInitialized("subject") ? new QSubject(forProperty("subject"), inits.get("subject")) : null;
    }

}

