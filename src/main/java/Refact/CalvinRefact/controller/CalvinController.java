package Refact.CalvinRefact.controller;

import Refact.CalvinRefact.entity.entityEnum.Board_Type;
import Refact.CalvinRefact.repository.*;
import Refact.CalvinRefact.repository.dto.board.BoardListDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class CalvinController {

    @Autowired
    BoardDataJpaRepository boardDataJpaRepository;
    @Autowired
    FileDataJpaRepository fileDataJpaRepository;
    @Autowired
    Member_SubjectDataJpaRepository member_subjectDataJpaRepository;
    @Autowired
    MemberDataJpaRepository memberDataJpaRepository;
    @Autowired
    SubjectDataJpaRepository subjectDataJpaRepository;
    @Autowired
    BoardRepository boardRepository;

    //메인 화면
    @GetMapping("/")
    public String home(Model model){
        List<BoardListDto> notice = boardRepository.findTop6ByBoard_type(Board_Type.공지사항);
        model.addAttribute("notice", notice);

        return "index2";
    }

    //학점은행제 소개 페이지
    @GetMapping("/menu/subject/ACBsystem")
    public String SystemPage(Model model){
        model.addAttribute("page_type", "2.1");
        return "menu/subject/ACBsystem";
    }
    //원장 인사말 페이지 1.1
    @GetMapping("/menu/info/greeting")
    public String GreetingPage(Model model){
        model.addAttribute("page_type","1.1");
        return "menu/information/greetings";
    }

    //연혁 페이지 1.2
    @GetMapping("/menu/info/history")
    public String HistoryPage(Model model){
        model.addAttribute("page_type","1.2");
        return "menu/information/history";
    }

    //조직도 페이지 1.3
    @GetMapping("/menu/info/organization")
    public String OrganizationPage(Model model){
        model.addAttribute("page_type","1.3");
        return "menu/information/organization";
    }

    //캠퍼스 안내 페이지 1.4
    @GetMapping("/menu/info/campus")
    public String CampusPage(Model model){
        model.addAttribute("page_type","1.4");
        return "menu/information/campus";
    }
    //찾아오시는 길 페이지 1.5
    @GetMapping("/menu/info/path")
    public String PathPage(Model model){
        model.addAttribute("page_type","1.5");
        return "menu/information/path";
    }
    //메인 팝업존
    @GetMapping("/popupzone")
    public String popupzone(){
        return "popupzone";
    }

    //2장로권사포럼 7.2
    @GetMapping("/menu/ministry/presbyter")
    public String presbyter(Model model){
        model.addAttribute("page_type","7.2");
        return "menu/ministry/presbyter";
    }

    //여교역자포럼 7.3
    @GetMapping("/menu/ministry/f_pastor")
    public String f_pastor(Model model){
        model.addAttribute("page_type","7.3");
        return "menu/ministry/f_pastor";
    }

    //모집안내 8.1
    @GetMapping("/menu/info2/recruit")
    public String recruit(Model model){
        model.addAttribute("page_type", "8.1");
        return "menu/info2/recruit_info";
    }

    //학사일정 8.2
    @GetMapping("/menu/info2/calendar")
    public String calendar(Model model){
        model.addAttribute("page_type","8.2");
        return "menu/info2/calendar";
    }

    //장학제도 8.3
    @GetMapping("/menu/info2/scholarship")
    public String scholarship(Model model){
        model.addAttribute("page_type","8.3");
        return "menu/info2/scholarship_system";
    }
    //수강신청안내 8.4
    @GetMapping("/menu/info2/apply_guide")
    public String apply_guide(Model model){
        model.addAttribute("page_type", "8.4");
        return "menu/info2/apply_guide";
    }
}
