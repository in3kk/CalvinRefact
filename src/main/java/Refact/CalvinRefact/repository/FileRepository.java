package Refact.CalvinRefact.repository;

import Refact.CalvinRefact.entity.QFile;
import Refact.CalvinRefact.repository.dto.file.FileSimpleDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import static Refact.CalvinRefact.entity.QFile.file;

@Repository
public class FileRepository {
    EntityManager em;
    JPAQueryFactory queryFactory;
    public FileRepository(EntityManager em){
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    public FileSimpleDto findFileSimpleById(Long id) {
        return queryFactory.select(Projections.fields(FileSimpleDto.class,
                file.id,
                file.original_name,
                file.save_name,
                file.size
                ))
                .from(file)
                .where(file.id.eq(id))
                .fetchOne();
    }
}
