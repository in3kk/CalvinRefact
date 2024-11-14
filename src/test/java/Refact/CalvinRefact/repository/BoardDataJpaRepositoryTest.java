package Refact.CalvinRefact.repository;

import Refact.CalvinRefact.entity.*;
import Refact.CalvinRefact.entity.embed.Address;
import Refact.CalvinRefact.entity.entityEnum.*;
import Refact.CalvinRefact.repository.dto.board.BoardDetailDto;
import Refact.CalvinRefact.repository.dto.board.BoardListDto;
import Refact.CalvinRefact.repository.dto.file.FileSimpleDto;
import Refact.CalvinRefact.repository.dto.member.MemberEmailDto;
import Refact.CalvinRefact.repository.dto.member.MemberSubjectListDto;
import Refact.CalvinRefact.service.BoardService;
import Refact.CalvinRefact.service.SubjectService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    @Autowired
    Member_SubjectRepository memberSubjectRepository;
    @Autowired
    BoardService boardService;
    @Autowired
    SubjectService subjectService;

    @PersistenceContext
    EntityManager em;

    @Test
    public void basicTest() throws Exception{
        Member member = new Member("shy4792@naveer.com", "rlawlstp128", "김진세", Member_Type.member, LocalDate.now(), "01089422159", new Address("경기도","용인"));
        em.persist(member);

        Board board = new Board(member, "제목","내용", Board_Type.공지사항);
        em.persist(board);

        File file = new File("123.jpg","123123123",123456L, YN.no,board);
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

        File file = new File("123.jpg","123123123",123456L, YN.no,board);
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

        File file = new File("123.jpg","123123123",123456L, YN.no,board);
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

        File file = new File("123.jpg","123123123",123456L, YN.no,board);
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

    @Test
    public void mySubjectListTest() throws Exception{
        //given
        Member member = new Member("shy4792@naver.com", "rlawlstp128", "김진세", Member_Type.member, LocalDate.now(), "01089422159", new Address("경기도","용인"));
        em.persist(member);

        Board board = new Board(member, "제목","내용", Board_Type.공지사항);
        em.persist(board);

        File file = new File("123.jpg","123123123",123456L, YN.no,board);
        em.persist(file);

        Subject subject = new Subject();
        for (int x = 0; x < 100; x++) {
            subject = new Subject(member, x, "jkk", Subject_Field.영어, Subject_Stat.접수중, "월,화 9:00 ~ 13:00", "12주", 30,Subject_Type.언어);
            em.persist(subject);
            em.persist(new Member_Subject(member, subject, LocalDate.now(),Pay_Stat.n));
        }

        em.flush();
        em.clear();
        //when
        Pageable pageable = PageRequest.of(0,20);
        List<MemberSubjectListDto> page = memberSubjectRepository.findMySubjectByEmail("shy4792@naver.com",pageable);
        for (MemberSubjectListDto memberSubjectListDto : page) {
            System.out.println("result : "+memberSubjectListDto.getMember_subject_id()+" "+memberSubjectListDto.getPay_stat()+" "+memberSubjectListDto.getMember_id()+" "+memberSubjectListDto.getSubject_name()+" "+memberSubjectListDto.getSubject_field()+" "+memberSubjectListDto.getFee());
        }

    }
    @Test
    public void findAllByTitleAndBoard_typeTest(){
        Member member = new Member("shy4792@naver.com", "rlawlstp128", "김진세", Member_Type.member, LocalDate.now(), "01089422159", new Address("경기도","용인"));
        em.persist(member);


        Board board = new Board();
        for (int x = 0; x < 100; x++) {
            board = new Board(member, " "+x, "123", Board_Type.공지사항);
            em.persist(board);
        }

        em.flush();
        em.clear();

        Pageable pageable = PageRequest.of(0,20);
        Page<BoardListDto> boardListDtos = boardService.findAllByTitleAndBoard_type("2",Board_Type.공지사항,pageable);
//        Page<BoardListDto> boardListDtos = boardService.findAllByBoard_type(Board_Type.공지사항,pageable);

        for (BoardListDto boardListDto : boardListDtos) {
            System.out.println("result : "+boardListDto.getBoard_type()+" "+boardListDto.getBoard_code()+" "+boardListDto.getTitle());
        }
    }

    @Test
    public void findBoardDetailByIdTest(){
        Member member = new Member("shy4792@naver.com", "rlawlstp128", "김진세", Member_Type.member, LocalDate.now(), "01089422159", new Address("경기도","용인"));
        em.persist(member);


        Board board = new Board(member, "123", "123", Board_Type.공지사항);
        em.persist(board);
        File file = new File("1.jpg","1",123456L, YN.no,board);
        em.persist(file);
        file = new File("2.jpg","2",123456L, YN.no,board);
        em.persist(file);
        file = new File("3.jpg","3",123456L, YN.no,board);
        em.persist(file);
        file = new File("4.jpg","4",123456L, YN.no,board);
        em.persist(file);
        file = new File("5.jpg","5",123456L, YN.no,board);
        em.persist(file);


        em.flush();
        em.clear();

//        BoardDetailDto boardDetailDto = boardRepository.findBoardDetailById(1L);
//        System.out.println("board data : "+boardDetailDto.getBoard_id()+" "+boardDetailDto.getBoardType()+" "+boardDetailDto.getTitle()+" "+boardDetailDto.getContents()+" ");
//
//        List<FileSimpleDto> files = boardDetailDto.getFiles();
//        for (FileSimpleDto file1 : files) {
//            System.out.println("file data : "+file1.getSave_name()+" "+file1.getId()+" "+file1.getOriginal_name()+" "+file1.getSize());
//        }
        Optional<Board> board1 = boardDataJpaRepository.findById(1L);
        System.out.println(">>>구분선<<<");
        if (board1.isPresent()) {
            List<File> files = board1.get().getFiles();
            for (File file1 : files) {
                System.out.println("file data : "+file1.getSave_name()+" "+file1.getId()+" "+file1.getOriginal_name()+" "+file1.getSize());
            }
        }
    }

    @Test
    public void findFileSimpleDtoByBoardIdTest() {
        Member member = new Member("shy4792@naver.com", "rlawlstp128", "김진세", Member_Type.member, LocalDate.now(), "01089422159", new Address("경기도","용인"));
        em.persist(member);

        Board board = new Board(member, "제목","내용", Board_Type.공지사항);
        em.persist(board);

        File file = new File("123.jpg","123123123",123456L, YN.no,board);
        em.persist(file);

        em.flush();
        em.clear();

        FileSimpleDto fileSimpleDto = fileDataJpaRepository.findSimpleFileByBoardId(1L);
        System.out.println("result = "+fileSimpleDto.getOriginal_name());
    }
    @Test
    public void applyProTest(){
        Member member = new Member("shy4792@naver.com", "rlawlstp128", "김진세", Member_Type.member, LocalDate.now(), "01089422159", new Address("경기도","용인"));
        em.persist(member);
        member = new Member("shy4792", "rlawlstp128", "김진세2", Member_Type.member, LocalDate.now(), "01089422159", new Address("경기도","용인"));
        em.persist(member);
        Subject subject = new Subject(member, 123, "jkk", Subject_Field.영어, Subject_Stat.접수중, "월,화 9:00 ~ 13:00", "12주", 30,Subject_Type.언어);
        em.persist(subject);
        em.flush();
        em.clear();

        subjectService.applyPro("shy4792",1L);
        em.flush();
        em.clear();

        Optional<Member> memberOptional = memberDataJpaRepository.findByEmail("shy4792");
        if (memberOptional.isPresent()) {
            Member member1 = memberOptional.get();
            List<Member_Subject> member_subjects = member1.getMember_subjects();
            for (Member_Subject memberSubject : member_subjects) {
                System.out.println("result = "+memberSubject.getSubject());
            }

        }
    }
    @Test
    public void findProfessorListTest() {
        Member member;
        for (int x = 0; x < 100; x++) {
            member = new Member("shy4792"+x, "rlawlstp128", "김진세"+x, Member_Type.professor, LocalDate.now(), "01089422159", new Address("경기도","용인"));
            em.persist(member);
        }
        em.flush();
        em.clear();
        List<MemberEmailDto> list = memberDataJpaRepository.findProfessorByMemberType(Member_Type.professor);
        for (MemberEmailDto memberEmailDto : list) {
            System.out.println("result : "+memberEmailDto.getName()+", "+memberEmailDto.getEmail());
        }
    }
}