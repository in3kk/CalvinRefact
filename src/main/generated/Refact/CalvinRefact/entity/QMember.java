package Refact.CalvinRefact.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = -675473422L;

    public static final QMember member = new QMember("member1");

    public final Refact.CalvinRefact.entity.baseEntity.QBaseEntity _super = new Refact.CalvinRefact.entity.baseEntity.QBaseEntity(this);

    public final StringPath address = createString("address");

    public final DatePath<java.time.LocalDate> birth = createDate("birth", java.time.LocalDate.class);

    public final ListPath<Board, QBoard> boards = this.<Board, QBoard>createList("boards", Board.class, QBoard.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<Member_Subject, QMember_Subject> member_subjects = this.<Member_Subject, QMember_Subject>createList("member_subjects", Member_Subject.class, QMember_Subject.class, PathInits.DIRECT2);

    public final EnumPath<Refact.CalvinRefact.entity.entityEnum.Member_Type> member_type = createEnum("member_type", Refact.CalvinRefact.entity.entityEnum.Member_Type.class);

    public final StringPath name = createString("name");

    public final StringPath phone_number = createString("phone_number");

    public final StringPath pwd = createString("pwd");

    public final ListPath<Subject, QSubject> subjects = this.<Subject, QSubject>createList("subjects", Subject.class, QSubject.class, PathInits.DIRECT2);

    public QMember(String variable) {
        super(Member.class, forVariable(variable));
    }

    public QMember(Path<? extends Member> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMember(PathMetadata metadata) {
        super(Member.class, metadata);
    }

}
