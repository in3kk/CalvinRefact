package Refact.CalvinRefact.repository;

import Refact.CalvinRefact.entity.Member;
import Refact.CalvinRefact.entity.entityEnum.Member_Type;
import Refact.CalvinRefact.repository.dto.member.MemberEmailDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberDataJpaRepository extends JpaRepository<Member,Long> {

    Optional<Member> findByEmailAndPwd(String email, String pwd);

    Long countByEmail(String email);

    Optional<Member> findByEmail(String email);
    @Query("select m from Member m join fetch m.subjects where m.email = :email")
    Optional<Member> findFetchByEmail(@Param("email") String email);

    //멤버 email 검색
    Page<Member> findByEmailContaining(String email, Pageable pageable);

    //멤버 username 검색
    Page<Member> findByNameContaining(String name, Pageable pageable);

    //    @Query("select m.id from member m where m.email = :email")
//    Long findIdByEmail(@Param("email")String email);
    @Query("select new Refact.CalvinRefact.repository.dto.member.MemberEmailDto(m.email, m.name,m.id) from Member m where m.memberType = :memberType")
    List<MemberEmailDto> findProfessorByMemberType(@Param("memberType") Member_Type memberType);

}
