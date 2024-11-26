package Refact.CalvinRefact.controller;

import Refact.CalvinRefact.entity.entityEnum.Subject_Field;
import Refact.CalvinRefact.entity.entityEnum.Subject_Type;
import Refact.CalvinRefact.exception.InvalidPermissionException;
import Refact.CalvinRefact.repository.SubjectDataJpaRepository;
import Refact.CalvinRefact.repository.SubjectRepository;
import Refact.CalvinRefact.repository.dto.Member_Subject.ApplyListDto;
import Refact.CalvinRefact.repository.dto.file.FileSimpleDto;
import Refact.CalvinRefact.repository.dto.member.MemberEmailDto;
import Refact.CalvinRefact.repository.dto.subject.SubjectDetailDto;
import Refact.CalvinRefact.repository.dto.subject.SubjectListDto;
import Refact.CalvinRefact.service.CalvinService;
import Refact.CalvinRefact.service.MemberService;
import Refact.CalvinRefact.service.SubjectService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class SubjectController {
    @Autowired
    SubjectService subjectService;
    @Autowired
    CalvinService calvinService;
    @Autowired
    MemberService memberService;

    @GetMapping("/menu/subject/list") //강의 리스트 페이지
    public String SubjectList(@RequestParam(value = "field", required = false, defaultValue = "") String field,
                              @RequestParam(value = "type") String type, Model model){
        List<SubjectListDto> subject_list;
        if(field.equals("")){
            subject_list= subjectService.findSubjectList(Subject_Type.valueOf(type));
        }else{
            System.out.println("result"+field);
            subject_list= subjectService.findSubjectList(Subject_Type.valueOf(type),Subject_Field.valueOf(field));
        }
        String result = "menu/subject/subject_list";
        model.addAttribute("subject_list", subject_list);
        model.addAttribute("page_type","2.2");
        return  result;
    }
    //강의 리스트 페이지 (학점은행제, 일반교양, 자격증/취창업)
    @GetMapping({"/menu/subject/list", "/menu/liberal_arts/list","/menu/certificate/list","/menu/special/list","/menu/language/list","/menu/ministry/list"})
    public String SubjectList(@RequestParam(value = "field", required = false, defaultValue = "") String field,
                              @RequestParam(value = "type") String type,
                              @RequestParam(value = "name", required = false, defaultValue = "")String name,Model model){
        List<SubjectListDto> subject_list;
        if(field.equals("")){
            subject_list= subjectService.findSubjectList(Subject_Type.valueOf(type));
        }else if(!name.equals("")){
            subject_list = subjectService.findSubjectList(Subject_Type.valueOf(type),Subject_Field.valueOf(field),name);
            model.addAttribute("subject_name", name);
        }else{
            subject_list= subjectService.findSubjectList(Subject_Type.valueOf(type),Subject_Field.valueOf(field));
        }
        String result="";
        model.addAttribute("subject_list", subject_list);
        if(type.equals("학점은행제")){
            result = "menu/subject/subject_list";
            model.addAttribute("page_type","2.2");
        }else if(type.equals("일반교양")){
            result = "menu/liberal_arts/subject_list";
            model.addAttribute("page_type","3.1");
        }else if(type.equals("자격증/취창업")){
            result = "menu/certificate/subject_list";
            if(field.equals("전문자격증")){
                model.addAttribute("page_type","4.1");
            }else if(field.equals("민간자격증")){
                model.addAttribute("page_type","4.2");
            }else if(field.equals("기술자격증")){
                model.addAttribute("page_type","4.3");
            }else if(field.equals("취창업")){
                model.addAttribute("page_type","4.4");
            }
        }else if(type.equals("특별교육과정")){
            //용인학아카데미, 서현정치경제아카데미, 경기교육아카데미, 사모아카데미, 레이번스축구아카데미, 연예아카데미
            result = "menu/special/subject_list";
            if(field.equals("용인")){
                model.addAttribute("page_type","5.1");
            }else if(field.equals("서현정치경제")){
                model.addAttribute("page_type","5.2");
            }else if(field.equals("경기교육")){
                model.addAttribute("page_type","5.3");
            }else if(field.equals("사모포럼")){
                model.addAttribute("page_type","5.4");
                result = "menu/ministry/subject_list";
            }else if(field.equals("레이번스축구아카데미")){
                model.addAttribute("page_type","5.5");
            }else if(field.equals("연예")){
                model.addAttribute("page_type","5.6");
            } else if (field.equals("미디어")) {
                model.addAttribute("page_type","5.7");
            }
        }else if(type.equals("언어")){
            result = "menu/language/subject_list";
            if(field.equals("성경고전어")){
                model.addAttribute("page_type","6.1");
            }else if(field.equals("제2외국어")){
                model.addAttribute("page_type","6.2");
            }else if(field.equals("한국어")){
                model.addAttribute("page_type","6.3");
            }
        }else if(type.equals("목회")){
            result = "menu/ministry/subject_list";
            model.addAttribute("page_type","7.1");
        }
        return  result;
    }

    //수강신청 페이지
    @GetMapping({"/menu/subject/apply", "/menu/liberal_arts/apply","/menu/certificate/apply","/menu/language/apply"})
    public String ApplyPage(@RequestParam(value = "id", required = false, defaultValue = "-1") Long id, Model model){
        SubjectDetailDto subject = subjectService.findSubjectDetail(id);
        FileSimpleDto fileSimpleDto;
        if(subject.getFileSimpleDto() != null){
            fileSimpleDto = subject.getFileSimpleDto();
            model.addAttribute("file", fileSimpleDto);
        }
        model.addAttribute("subject",subject);
        String result = "";
        if(subject.getSubject_type().equals("학점은행제")){
            model.addAttribute("page_type","2.2");
            result = "menu/subject/apply";
        }else if(subject.getSubject_type().equals("일반교양")){
            model.addAttribute("page_type","3.1");
            result = "menu/liberal_arts/apply";
        }else if(subject.getSubject_type().equals("자격증/취창업")){
            result = "menu/certificate/apply";
            if(subject.getSubject_field().equals("반려동물")){
                model.addAttribute("page_type","4.1");
            }else if(subject.getSubject_field().equals("사회복지")){
                model.addAttribute("page_type","4.2");
            }else if(subject.getSubject_field().equals("실용음악")){
                model.addAttribute("page_type","4.3");
            }else if(subject.getSubject_field().equals("자격증")){
                model.addAttribute("page_type","4.4");
            }else if(subject.getSubject_field().equals("취창업")){
                model.addAttribute("page_type","4.5");
            }
        }else if(subject.getSubject_type().equals("특별교육과정")){
            result = "menu/special/apply";
            if(subject.getSubject_field().equals("용인")){
                model.addAttribute("page_type","5.1");
            }else if(subject.getSubject_field().equals("서현정치경제")){
                model.addAttribute("page_type","5.2");
            }else if(subject.getSubject_field().equals("경기교육")){
                model.addAttribute("page_type","5.3");
            }else if(subject.getSubject_field().equals("레이번스축구아카데미")){
                model.addAttribute("page_type","5.5");
            }else if(subject.getSubject_field().equals("연예")){
                model.addAttribute("page_type","5.6");
            } else if (subject.getSubject_field().equals("미디어")) {
                model.addAttribute("page_type","5.7");
            }
        }else if(subject.getSubject_type().equals("언어")){
            result = "menu/language/apply";
            if(subject.getSubject_field().equals("히브리어")){
                model.addAttribute("page_type","6.1");
            }else if(subject.getSubject_field().equals("헬라어")){
                model.addAttribute("page_type","6.2");
            }else if(subject.getSubject_field().equals("라틴어")){
                model.addAttribute("page_type","6.3");
            }else if(subject.getSubject_field().equals("독일어")){
                model.addAttribute("page_type","6.4");
            }else if(subject.getSubject_field().equals("한국어")){
                model.addAttribute("page_type","6.5");
            } else if (subject.getSubject_field().equals("영어")) {
                model.addAttribute("page_type","6.6");
            }
        }
        return result;
    }

//    수강신청
    @GetMapping("/menu/subject/apply/pro")
    @ResponseBody
    public String ApplyPro(HttpSession httpSession, @RequestParam(value = "subject_code") Long subject_code){
        String result = "";
        if(httpSession.getAttribute("member_id")==null){
            result = "<script>alert('로그인이 필요한 서비스 입니다..');window.location.href = '/member/login';</script>";
        }else{
            String member_id = httpSession.getAttribute("member_id").toString();
            if(subjectService.applyWhether(member_id,subject_code)){
                boolean insert_result = subjectService.applyPro(member_id,subject_code);
                if(insert_result){
                    result = "<script>alert('수강신청이 완료되었습니다.');window.location.href = '/menu/subject/apply/done';</script>";
                }else{
                    result = "<script>alert('수강신청에 실패하였습니다.');history.go(-2);</script>";
                }
            }else{
                result = "<script>alert('이미 신청한 강의 입니다.');window.location.href = document.referrer;</script>";
            }
        }
        return result;
    }
    //수강신청 완료
    @GetMapping("/menu/subject/apply/done")
    public String ApplyDone(){
        return "menu/subject/apply_done";
    }

    //신청 관리 페이지 9.3
    @GetMapping("/mypage/admin/apply")
    public String ApplyManagePage(Model model,
                                  @PageableDefault(size = 20,page = 0) Pageable pageable,
                                  @RequestParam(value = "search_type", required = false, defaultValue = "1") int search_type,
                                  @RequestParam(value = "search_word", required = false, defaultValue = "")String search_word,
                                  HttpSession httpSession, RedirectAttributes redirectAttributes){
        if(!search_word.equals("")){
            search_word = calvinService.searchWordFilter(search_word);
        }
        String result = "";
        if(httpSession.getAttribute("member_id") == null || httpSession.getAttribute("member_type") == null){
            result = "redirect:/member/login";
        }else{
            try {
                Page<ApplyListDto> apply = Page.empty();

                if (search_word.equals("")) {
                    apply = subjectService.findApplyList(pageable, httpSession.getAttribute("member_id").toString());
                } else {
                    if (search_type == 1) {//강의명
                        apply = subjectService.findApplyListBy(pageable, search_type, search_word, httpSession.getAttribute("member_id").toString());
                    } else if (search_type == 2) {//아이디(email
                        apply = subjectService.findApplyListBy(pageable, search_type, search_word, httpSession.getAttribute("membeR_id").toString());
                    }
                }
                int begin_page;

                int page = pageable.getPageNumber()+1;
                if (page % 10 == 0) {
                    begin_page = page - 9;
                } else {
                    begin_page = page / 10 * 10 + 1;
                }
                int max_page = apply.getTotalPages();
                model.addAttribute("search_word", search_word);
                model.addAttribute("search_type", search_type);
                model.addAttribute("page", page);
                model.addAttribute("begin_page", begin_page);
                model.addAttribute("max_page", max_page);
                model.addAttribute("apply_list", apply);
                model.addAttribute("page_type", "9.3");
                result = "menu/mypage/admin_apply";
            } catch (InvalidPermissionException e) {
                redirectAttributes.addFlashAttribute("msg", e.getMessage());
                result = "redirect:/";
            }
        }
        return result;
    }

    //신청 취소
    @GetMapping("/mypage/admin/apply/manage")
    @ResponseBody
    public String ApplyManage(Model model, @RequestParam List<Long> apply_list,HttpSession httpSession){
        String result = "";
        if(httpSession.getAttribute("member_id") == null || httpSession.getAttribute("member_type")==null){
            result = "<script>alert('로그인이 필요한 서비스 입니다.');window.location.href = '/member/login';</script>";
        }else{
            try {
                subjectService.deleteApply(apply_list, httpSession.getAttribute("member_id").toString());
                result = "<script>alert('수강신청이 정상적으로 취소되었습니다.');window.location.href = document.referrer;</script>";
            } catch (InvalidPermissionException e) {
                result = "<script>alert('"+e.getMessage()+"'); window.location.href = '/';</script>";
            } catch (Exception e) {
                result = "<script>alert('수강신청 취소에 실패했습니다. 이미 취소된 수강신청이 아닌지 확인해주세요.'); window.location.href = document.referrer;</script>";
            }
        }
        return result;
    }
    //결제 상태 변경
    @GetMapping("/mypage/admin/apply/pay")// 1 : 납부완료, 2 : 납부취소, 3 : 환불
    @ResponseBody
    public String PayManage(Model model, @RequestParam List<Long> apply_list, @RequestParam int type,HttpSession httpSession){
        String result = "<script>alert('";
        String word = "";
        if(httpSession.getAttribute("member_id") == null || httpSession.getAttribute("member_type") == null){
            result = "<script>alert('로그인이 필요한 서비스 입니다.');window.location.href = '/member/login';</script>";
        }else{
            try {
                subjectService.updatePayStat(apply_list, type, httpSession.getAttribute("member_id").toString());
                switch (type) {
                    case 1:
                        word = "납부완료 처리";
                        break;
                    case 2:
                        word = "납부취소 처리";
                        break;
                    case 3:
                        word = "환불 처리";
                        break;
                }
                result += "수강신청이 정상적으로 " + word + "되었습니다.";
            } catch (InvalidPermissionException e) {
                result += e.getMessage();
            } catch (Exception e) {
                result += "수강신청이 정상적으로 "+ word + "되지 않았습니다.  수강신청 상태를 확인해주세요.";
            }
            result += "');window.location.href = document.referrer;</script>";
        }

        return result;
    }

    //개설 강의 관리
    @GetMapping("/mypage/admin/subject")
    public String SubjectManage(Model model,
                                @PageableDefault(size = 20) Pageable pageable,
                                @RequestParam(value = "search_word", required = false, defaultValue = "")String search_word,
                                @RequestParam(value = "search_type", required = false, defaultValue = "1") int search_type,
                                HttpSession httpSession, RedirectAttributes redirectAttributes){
        if(!search_word.equals("")){
            Pattern RegPattern1 = Pattern.compile("/[^(A-Za-z가-힣0-9\\s.,)]/");
            Matcher m = RegPattern1.matcher(search_word);
            search_word = m.replaceAll(" ");
        }
        String result ="";
        if(httpSession.getAttribute("member_id") == null || httpSession.getAttribute("member_type")==null){
            result = "redirect:/member/login";
        }else{
            try {
                Page<SubjectListDto> list = Page.empty();
                int count = 0;
                if (search_word.equals("")) {
                    list = subjectService.findSubjectList(pageable, httpSession.getAttribute("member_id").toString());
                } else {
                    if (search_type == 1) {
                        list = subjectService.findSubjectListByName(pageable, search_word, httpSession.getAttribute("member_id").toString());
                    } else if (search_type == 2) {
                        list = subjectService.findSubjectListByField(pageable, search_word, httpSession.getAttribute("member_id").toString());
                    } else {//3일때
                        list = subjectService.findSubjectListByType(pageable, search_word, httpSession.getAttribute("member_id").toString());
                    }
                }
                int begin_page;
                int page = list.getNumber()+1;
                count = list.getTotalPages();
                if (page % 10 == 0) {
                    begin_page = page - 9;
                } else {
                    begin_page = page / 10 * 10 + 1;
                }
                int max_page;
                if (count / 20 == 0 && count % 20 > 0) {
                    max_page = 1;
                } else if (count / 20 > 0 && count % 20 > 0) {
                    max_page = count / 20 + 1;
                } else {
                    max_page = count / 20;
                }
                model.addAttribute("search_word", search_word);
                model.addAttribute("search_type", search_type);
                model.addAttribute("page", page);
                model.addAttribute("begin_page", begin_page);
                model.addAttribute("max_page", max_page);
                model.addAttribute("subject_list", list);
                model.addAttribute("page_type", "9.3");
                result = "menu/mypage/admin_subject";
            } catch (InvalidPermissionException e) {
                redirectAttributes.addFlashAttribute("msg", e.getMessage());
                result = "redirect:/";
            }
        }

        return result;
    }

    //강의상태 변경
    @GetMapping("/mypage/admin/subject/manage")
    @ResponseBody
    public String SubjectStatManage(@RequestParam(value = "subject_code")Long subject_code,
                                    @RequestParam(value = "stat")int stat, HttpSession httpSession,
                                    RedirectAttributes redirectAttributes){
        String result = "";
        if(httpSession.getAttribute("member_id") == null || httpSession.getAttribute("member_type")==null){
            result = "<script>alert('로그인이 필요한 서비스 입니다.'); window.location.href = '/member/login';</script>";
        }else{
            try {
                subjectService.updateSubjectStat(subject_code, stat, httpSession.getAttribute("member_id").toString());
                result = "<script>alert('강의상태가 성공적으로 변경되었습니다.'); window.location.href = document.referrer;</script>";
            } catch (InvalidPermissionException e) {
                redirectAttributes.addFlashAttribute("msg", e.getMessage());
            } catch (Exception e) {
                result = "<script>alert('강의상태 변경에 실패했습니다.'); window.location.href = document.referrer;</script>";
            }

        }
        return result;
    }


    //강의 개설 페이지 & 강의 수정
    @GetMapping("/menu/subject/write_page")
    public String NewSubjectWritePage(Model model, @RequestParam(value = "subject_code", required = false, defaultValue = "-1") Long subject_code, HttpSession httpSession, RedirectAttributes redirectAttributes){
        String result = "";
        if(httpSession.getAttribute("member_id") == null || httpSession.getAttribute("member_type") == null){
            result ="redirect:/member/login";
        }else{
            try {
                SubjectDetailDto calvin_subject = new SubjectDetailDto();
                if (subject_code != -1) {
                    calvin_subject = subjectService.findSubjectDetail(subject_code);
                    model.addAttribute("subject_code", subject_code);
                }
                model.addAttribute("subject", calvin_subject);
                List<MemberEmailDto> list = memberService.findProfessorList(httpSession.getAttribute("member_id").toString());
                model.addAttribute("professor", list);
                model.addAttribute("page_type", "9.3");
                result = "menu/subject/subject_write";
            } catch (InvalidPermissionException e) {
                redirectAttributes.addFlashAttribute("msg", e.getMessage());
                result = "redirect:/";
            }
        }
        return result;
    }

    @PostMapping("/menu/subject/write")
    @ResponseBody
    public String NewSubjectWrite(@RequestParam(value = "subject_code", required = false, defaultValue = "-1") Long subject_code,
                                  @RequestParam(value = "subject_name") String subject_name,
                                  @RequestParam(value = "subject_field") String subject_field,
                                  @RequestParam(value = "subject_type") String subject_type,
                                  @RequestParam(value = "personnel") int personnel,
                                  @RequestParam(value = "lecture_time") String lecture_time,
                                  @RequestParam(value = "period") String period,
                                  @RequestParam(value = "member_code") Long member_code,
                                  @RequestParam(value = "fee") int fee,
                                  @RequestParam(value = "file1", required = false) MultipartFile file,
                                  HttpSession httpSession){
        boolean insert_result = false;
        String result = "";
        try {
            if (httpSession.getAttribute("member_id") == null || httpSession.getAttribute("member_type") == null) {
                result = "<script>alert('로그인이 필요한 서비스입니다..');window.location.href='/member/login';</script>";
            } else {
                try {
                    insert_result = subjectService.saveSubject(httpSession.getAttribute("member_id").toString(),subject_name, Subject_Field.valueOf(subject_field), Subject_Type.valueOf(subject_type), personnel, lecture_time, period, member_code, fee, subject_code, file);
                    if (insert_result) {
                        if (subject_code != -1) {
                            result = "<script>alert('강의가 성공적으로 수정되었습니다. 변경사항은 새로고침 후 적용됩니다.');history.go(-2);</script>";
                        } else {
                            result = "<script>alert('신규 강의가 성공적으로 개설되었습니다. 변경사항은 새로고침 후 적용됩니다.');history.go(-2);</script>";
                        }
                    } else {
                        result = "<script>alert('신규 강의 개설 또는 수정에 실패하였습니다. 작성한 내용에 오류가 없는지 확인해주세요');history.back();</script>";
                    }
                } catch (InvalidPermissionException e) {
                    result = "<script>alert('"+e.getMessage()+"');window.location.href='/';</script>";
                } catch (Exception e) {
                    result = "<script>alert('신규 강의 개설 또는 수정에 실패하였습니다. 작성한 내용에 오류가 없는지 확인해주세요');history.back();</script>";
                }

            }
        } catch (Exception e) {
            result = "<script>alert('신규 강의 개설 또는 수정에 실패하였습니다. 작성한 내용에 오류가 없는지 확인해주세요');history.back();</script>";
        }
        return result;
    }

    //강의 삭제
    @GetMapping("/menu/subject/delete")
    @ResponseBody
    public String DeleteSubject(HttpSession httpSession, @RequestParam(value = "subject_code") Long subject_code){
        String result = "";
        if(httpSession.getAttribute("member_id") == null || httpSession.getAttribute("member_type") == null){
            result = "<script>alert('로그인이 필요한 서비스입니다..');window.location.href='/member/login';</script>";
        }else{
            try {
                subjectService.deleteSubject(subject_code, httpSession.getAttribute("member_id").toString());
                result = "<script>alert('강의가 성공적으로 삭제되었습니다. 해당 창을 닫고 새로고침시 변경사항이 적용됩니다.');window.location.href=history.go(-2);</script>";
            } catch (InvalidPermissionException e) {
                result = "<script>alert('"+e.getMessage()+"');window.location.href=document.referrer;</script>";
            } catch (Exception e) {
                result = "<script>alert('강의 삭제에 실패했습니다.');window.location.href=document.referrer;</script>";
            }
        }
        return result;
    }

}
