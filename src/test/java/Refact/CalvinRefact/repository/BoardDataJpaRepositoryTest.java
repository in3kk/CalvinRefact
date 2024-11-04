package Refact.CalvinRefact.repository;

import Refact.CalvinRefact.entity.*;
import Refact.CalvinRefact.entity.embed.Address;
import Refact.CalvinRefact.entity.entityEnum.*;
import Refact.CalvinRefact.repository.dto.board.BoardListDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class BoardDataJpaRepositoryTest {

    @Autowired
    BoardDataJpaRepository boardDataJpaRepository;
    @Autowired
    MemberDataJpaRepository memberDataJpaRepository;
    @Autowired
    FileDataJpaRepository fileDataJpaRepository;
    @Autowired
    SubjectDataJpaRepository subjectDataJpaRepository;
    @Autowired
    Member_SubjectDataJpaRepository member_subjectDataJpaRepository;

    @Autowired
    BoardRepository boardRepository;

    @PersistenceContext
    EntityManager em;

    @Test
    public void basicTest() throws Exception{
        Member member = new Member("shy4792@naveer.com", "rlawlstp128", "김진세", Member_Type.member, LocalDate.now(), "01089422159", new Address("경기도","용인"));
        em.persist(member);

        Board board = new Board(member, "제목","내용", Board_Type.공지사항);
        em.persist(board);

        File file = new File("123.jpg","123123123",123456, YN.no,board);
        em.persist(file);


        Subject subject = new Subject(member, 123, "jkk", Subject_Field.영어, Subject_Stat.접수중, "월,화 9:00 ~ 13:00", "12주", 30,Subject_Type.언어);
        em.persist(subject);

        Member_Subject member_subject = new Member_Subject(member, subject, LocalDate.now(),Pay_Stat.n);
        em.persist(member_subject);

        em.flush();
        em.clear();

        int boardCnt = boardDataJpaRepository.findAll().size();
        int memberCnt = memberDataJpaRepository.findAll().size();
        int fileCnt = fileDataJpaRepository.findAll().size();
        int subjectCnt = subjectDataJpaRepository.findAll().size();
        int member_subjectCnt = member_subjectDataJpaRepository.findAll().size();

        assertThat(boardCnt).isEqualTo(1);
        assertThat(memberCnt).isEqualTo(1);
        assertThat(fileCnt).isEqualTo(1);
        assertThat(subjectCnt).isEqualTo(1);
        assertThat(member_subjectCnt).isEqualTo(1);

    }

    @Test
    public void findBoardTop6Test(){
        Member member = new Member("shy4792@naveer.com", "rlawlstp128", "김진세", Member_Type.member, LocalDate.now(), "01089422159", new Address("경기도","용인"));
        em.persist(member);

        Board board = new Board(member, "제목","내용", Board_Type.공지사항);
        em.persist(board);

        File file = new File("123.jpg","123123123",123456, YN.no,board);
        em.persist(file);


        Subject subject = new Subject(member, 123, "jkk", Subject_Field.영어, Subject_Stat.접수중, "월,화 9:00 ~ 13:00", "12주", 30,Subject_Type.언어);
        em.persist(subject);

        Member_Subject member_subject = new Member_Subject(member, subject, LocalDate.now(),Pay_Stat.n);
        em.persist(member_subject);

        em.flush();
        em.clear();

        List<BoardListDto> results = boardRepository.findTop6ByBoard_type(Board_Type.공지사항);

        for (BoardListDto result : results) {
            System.out.println("result = "+result.getName());
        }
    }

    //로그인 테스트
    @Test
    public void findMemberByEmailAndPwdTest(){
        Member member = new Member("shy4792@naver.com", "rlawlstp128", "김진세", Member_Type.member, LocalDate.now(), "01089422159", new Address("경기도","용인"));
        em.persist(member);

        Board board = new Board(member, "제목","내용", Board_Type.공지사항);
        em.persist(board);

        File file = new File("123.jpg","123123123",123456, YN.no,board);
        em.persist(file);


        Subject subject = new Subject(member, 123, "jkk", Subject_Field.영어, Subject_Stat.접수중, "월,화 9:00 ~ 13:00", "12주", 30,Subject_Type.언어);
        em.persist(subject);

        Member_Subject member_subject = new Member_Subject(member, subject, LocalDate.now(),Pay_Stat.n);
        em.persist(member_subject);

        em.flush();
        em.clear();

        Optional<Member> member1 = memberDataJpaRepository.findByEmailAndPwd("shy4792@naver.com", "rlawlstp128");

        if(member1.isPresent()){
            System.out.println("이름 : "+ member1.get().getName());
        }
    }

    @Test
    public void memberSetTest(){
        Member member = new Member("shy4792@naver.com", "rlawlstp128", "김진세", Member_Type.member, LocalDate.now(), "01089422159", new Address("경기도","용인"));
        em.persist(member);

        Board board = new Board(member, "제목","내용", Board_Type.공지사항);
        em.persist(board);

        File file = new File("123.jpg","123123123",123456, YN.no,board);
        em.persist(file);


        Subject subject = new Subject(member, 123, "jkk", Subject_Field.영어, Subject_Stat.접수중, "월,화 9:00 ~ 13:00", "12주", 30,Subject_Type.언어);
        em.persist(subject);

        Member_Subject member_subject = new Member_Subject(member, subject, LocalDate.now(),Pay_Stat.n);
        em.persist(member_subject);

        em.flush();
        em.clear();

        Optional<Member> member1 = memberDataJpaRepository.findByEmail("shy4792");
        if (member1.isPresent()) {
            member = member1.get();
        }
        memberDataJpaRepository.deleteAll();

        member = new Member(member.getEmail(),member.getPwd(),member.getName(),Member_Type.admin,member.getBirth(),member.getPhone_number(),member.getAddress());

    }
}