package Refact.CalvinRefact.controller;

import Refact.CalvinRefact.entity.entityEnum.Subject_Field;
import Refact.CalvinRefact.entity.entityEnum.Subject_Type;
import Refact.CalvinRefact.repository.SubjectDataJpaRepository;
import Refact.CalvinRefact.repository.SubjectRepository;
import Refact.CalvinRefact.repository.dto.file.FileSimpleDto;
import Refact.CalvinRefact.repository.dto.subject.SubjectDetailDto;
import Refact.CalvinRefact.repository.dto.subject.SubjectListDto;
import Refact.CalvinRefact.service.CalvinService;
import Refact.CalvinRefact.service.SubjectService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
public class SubjectController {
    @Autowired
    SubjectService subjectService;
    @Autowired
    CalvinService calvinService;

    @GetMapping("/menu/subject/list") //강의 리스트 페이지
    public String SubjectList(@RequestParam(value = "field", required = false, defaultValue = "") String field,
                              @RequestParam(value = "type") Subject_Type type, Model model){
        List<SubjectListDto> subject_list;
        if(field.equals("")){
            subject_list= subjectService.findSubjectList(type);
        }else{
            System.out.println("result"+field);
            subject_list= subjectService.findSubjectList(type,Subject_Field.valueOf(field));
        }
        String result = "menu/subject/subject_list";
        model.addAttribute("subject_list", subject_list);
        model.addAttribute("page_type","2.2");
        return  result;
    }
    //강의 리스트 페이지 (학점은행제, 일반교양, 자격증/취창업)
    @GetMapping({"/menu/subject/list", "/menu/liberal_arts/list","/menu/certificate/list","/menu/special/list","/menu/language/list","/menu/ministry/list"})
    public String SubjectList(@RequestParam(value = "field", required = false, defaultValue = "") String field,
                              @RequestParam(value = "type") Subject_Type type,
                              @RequestParam(value = "name", required = false, defaultValue = "")String name,Model model){
        List<SubjectListDto> subject_list;
        if(field.equals("")){
            subject_list= subjectService.findSubjectList(type);
        }else if(!name.equals("")){
            subject_list = subjectService.findSubjectList(type,Subject_Field.valueOf(field),name);
            model.addAttribute("subject_name", name);
        }else{
            subject_list= subjectService.findSubjectList(type,Subject_Field.valueOf(field));
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
//    @GetMapping("/mypage/admin/apply")
//    public String ApplyManagePage(Model model,
//                                  @PageableDefault(size = 20, sort = {"Member_Subject_id"}) Pageable pageable,
//                                  @RequestParam(value = "search_type", required = false, defaultValue = "1") int search_type,
//                                  @RequestParam(value = "search_word", required = false, defaultValue = "")String search_word,
//                                  HttpSession httpSession){
//        if(!search_word.equals("")){
//            search_word = calvinService.searchWordFilter(search_word);
//        }
//        String result = "";
//        if(httpSession.getAttribute("member_id") == null || httpSession.getAttribute("member_type") == null){
//            result = "redirect:/member/login";
//        }else{
//            if(httpSession.getAttribute("member_type").equals("ai")||httpSession.getAttribute("member_type").equals("dd")||httpSession.getAttribute("member_type").equals("st")){
//                List<MyPageSubjectView> apply = new ArrayList<>();
//                int count = 0;
//                if(search_word.equals("")){
//                    apply = calvinSubjectService.SelectAllApply(page);
//                    count = calvinSubjectService.admin_paging_apply();
//                }else{
//                    if(search_type == 1){//강의명
//                        apply = calvinSubjectService.SelectApplyBySubjectName(page, search_word);
//                        count = calvinSubjectService.admin_paging_apply(1,search_word);
//                    }else if(search_type == 2){//아이디
//                        apply = calvinSubjectService.SelectApplyById(page, search_word);
//                        count = calvinSubjectService.admin_paging_apply(2,search_word);
//                    }
//                }
//                int begin_page;
//
//                if(page % 10 == 0){
//                    begin_page = page-9;
//                }else{
//                    begin_page = page/10*10+1;
//                }
//                int max_page;
//                if(count/20 == 0 && count%20 > 0){
//                    max_page = 1;
//                }else if(count/20 > 0 && count%20 > 0){
//                    max_page = count/20 + 1;
//                }else{
//                    max_page = count/20;
//                }
//                model.addAttribute("search_word", search_word);
//                model.addAttribute("search_type", search_type);
//                model.addAttribute("page", page);
//                model.addAttribute("begin_page",begin_page);
//                model.addAttribute("max_page", max_page);
//                model.addAttribute("apply_list" ,apply);
//                model.addAttribute("page_type","9.3");
//                result = "menu/mypage/admin_apply";
//            }else{
//                throw new CustomException(ErrorCode.INVALID_PERMISSION);
//            }
//        }
//        return result;
//    }
//
//    //신청 취소
//    @GetMapping("/mypage/admin/apply/manage")
//    @ResponseBody
//    public String ApplyManage(Model model, @RequestParam List<Integer> apply_list,HttpSession httpSession){
//        String result = "";
//        if(httpSession.getAttribute("member_id") == null || httpSession.getAttribute("member_type")==null){
//            result = "<script>alert('로그인이 필요한 서비스 입니다.');window.location.href = '/member/login';</script>";
//        }else{
//            if(httpSession.getAttribute("member_type").equals("ai")||httpSession.getAttribute("member_type").equals("dd")||httpSession.getAttribute("member_type").equals("st")){
//                int delete_result = calvinSubjectService.ApplyManage(apply_list);
//                if(apply_list.size() == delete_result){
//                    result = "<script>alert('수강신청이 정상적으로 취소되었습니다.');window.location.href = document.referrer;</script>";
//                }else if(apply_list.size() >= delete_result && delete_result > 0) {
//                    result = "<script>alert('일부 수강신청만 취소되었습니다. 이미 취소된 수강신청이 아닌지 확인해주세요.'); window.location.href = document.referrer;</script>";
//                }else if(delete_result == 0){
//                    result = "<script>alert('수강신청 취소에 실패했습니다. 이미 취소된 수강신청이 아닌지 확인해주세요.'); window.location.href = document.referrer;</script>";
//                }
//            }else{
//                throw new CustomException(ErrorCode.INVALID_PERMISSION);
//            }
//        }
//        return result;
//    }
//    //결제 상태 변경
//    @GetMapping("/mypage/admin/apply/pay")// 1 : 납부완료, 2 : 납부취소, 3 : 환불
//    @ResponseBody
//    public String PayManage(Model model, @RequestParam List<Integer> apply_list, @RequestParam int type,HttpSession httpSession){
//        String result = "<script>alert('";
//        String word = "";
//        if(httpSession.getAttribute("member_id") == null || httpSession.getAttribute("member_type") == null){
//            result = "<script>alert('로그인이 필요한 서비스 입니다.');window.location.href = '/member/login';</script>";
//        }else{
//            if(httpSession.getAttribute("member_type").equals("ai")||httpSession.getAttribute("member_type").equals("dd")||httpSession.getAttribute("member_type").equals("st")){
//                int payManage_result = calvinSubjectService.PayManage(apply_list,type);
//                switch (type){
//                    case 1:
//                        word = "납부완료 처리";
//                        break;
//                    case 2:
//                        word = "납부취소 처리";
//                        break;
//                    case 3:
//                        word = "환불 처리";
//                        break;
//                }
//                if(apply_list.size() == payManage_result){
//                    result += "수강신청이 정상적으로 "+word+"되었습니다.";
//                }else if(apply_list.size() >= payManage_result && payManage_result > 0){
//                    result += "일부 수강신청만 " + word + "되었습니다.  수강신청 상태를 확인해주세요.";
//                }else if(payManage_result == 0){
//                    result += "수강신청이 정상적으로 "+ word + "되지 않았습니다.  수강신청 상태를 확인해주세요.";
//                }
//                result += "');window.location.href = document.referrer;</script>";
//            }else{
//                throw new CustomException(ErrorCode.INVALID_PERMISSION);
//            }
//        }
//
//        return result;
//    }
//
//    //개설 강의 관리
//    @GetMapping("/mypage/admin/subject")
//    public String SubjectManage(Model model,
//                                @RequestParam(value = "search_word", required = false, defaultValue = "")String search_word,
//                                @RequestParam(value = "page", required = false, defaultValue = "1") int page,
//                                @RequestParam(value = "search_type", required = false, defaultValue = "1") int search_type,
//                                HttpSession httpSession){
//        if(!search_word.equals("")){
//            Pattern RegPattern1 = Pattern.compile("/[^(A-Za-z가-힣0-9\\s.,)]/");
//            Matcher m = RegPattern1.matcher(search_word);
//            search_word = m.replaceAll(" ");
//        }
//        String result ="";
//        if(httpSession.getAttribute("member_id") == null || httpSession.getAttribute("member_type")==null){
//            result = "redirect:/member/login";
//        }else{
//            if(httpSession.getAttribute("member_type").equals("ai")||httpSession.getAttribute("member_type").equals("dd")||httpSession.getAttribute("member_type").equals("st")){
//                List<Calvin_subject> list;
//                int count = 0;
//                if(search_word.equals("")){
//                    list = calvinSubjectService.SelectSubject_admin(page);
//                    count = calvinSubjectService.admin_paging();
//                }else{
//                    if(search_type == 1){
//                        list = calvinSubjectService.SelectSubjectByName_admin(page,search_word);
//                        count = calvinSubjectService.admin_paging(1,search_word);
//                    }else if(search_type == 2){
//                        list = calvinSubjectService.SelectSubjectByField_admin(page,search_word);
//                        count = calvinSubjectService.admin_paging(2, search_word);
//                    }else{//3일때
//                        list = calvinSubjectService.SelectSubjectByType_admin(page, search_word);
//                        count = calvinSubjectService.admin_paging(3, search_word);
//                    }
//                }
//                int begin_page;
//
//                if(page % 10 == 0){
//                    begin_page = page-9;
//                }else{
//                    begin_page = page/10*10+1;
//                }
//                int max_page;
//                if(count/20 == 0 && count%20 > 0){
//                    max_page = 1;
//                }else if(count/20 > 0 && count%20 > 0){
//                    max_page = count/20 + 1;
//                }else{
//                    max_page = count/20;
//                }
//                model.addAttribute("search_word", search_word);
//                model.addAttribute("search_type", search_type);
//                model.addAttribute("page", page);
//                model.addAttribute("begin_page",begin_page);
//                model.addAttribute("max_page", max_page);
//                model.addAttribute("subject_list",list);
//                model.addAttribute("page_type", "9.3");
//                result ="menu/mypage/admin_subject";
//            }else{
//                throw new CustomException(ErrorCode.INVALID_PERMISSION);
//            }
//
//        }
//
//        return result;
//    }
//
//    //강의상태 변경
//    @GetMapping("/mypage/admin/subject/manage")
//    @ResponseBody
//    public String SubjectStatManage(@RequestParam(value = "subject_code")int subject_code,
//                                    @RequestParam(value = "stat")int stat, HttpSession httpSession){
//        String result = "";
//        if(httpSession.getAttribute("member_id") == null || httpSession.getAttribute("member_type")==null){
//            result = "<script>alert('로그인이 필요한 서비스 입니다.'); window.location.href = '/member/login';</script>";
//        }else{
//            if(httpSession.getAttribute("member_type").equals("ai")||httpSession.getAttribute("member_type").equals("dd")||httpSession.getAttribute("member_type").equals("st")){
//                int pro_result = calvinSubjectService.SubjectStatManage(subject_code,stat);
//
//                if(pro_result == 1){
//                    result = "<script>alert('강의상태가 성공적으로 변경되었습니다.'); window.location.href = document.referrer;</script>";
//                }else{
//                    result = "<script>alert('강의상태 변경에 실패했습니다.'); window.location.href = document.referrer;</script>";
//                }
//            }else{
//                throw new CustomException(ErrorCode.INVALID_PERMISSION);
//            }
//        }
//        return result;
//    }
//
//
//    //강의 개설 페이지 & 강의 수정
//    @GetMapping("/menu/subject/write_page")
//    public String NewSubjectWritePage(Model model,@RequestParam(value = "subject_code", required = false, defaultValue = "-1") int subject_code, HttpSession httpSession){
//        String result = "";
//        if(httpSession.getAttribute("member_id") == null || httpSession.getAttribute("member_type") == null){
//            result ="redirect:/member/login";
//        }else{
//            if(httpSession.getAttribute("member_type").equals("ai")||httpSession.getAttribute("member_type").equals("dd")||httpSession.getAttribute("member_type").equals("st")){
//                Calvin_subject calvin_subject = new Calvin_subject();
//                if(subject_code != -1){
//                    calvin_subject = calvinSubjectService.SubjectApply(subject_code);
//                    model.addAttribute("subject_code", subject_code);
//                }
//                model.addAttribute("subject",calvin_subject);
//                List<Calvin_Member> list = calvinMemberService.ProfessorList();
//                model.addAttribute("professor",list);
//                model.addAttribute("page_type","9.3");
//                result = "menu/subject/subject_write";
//            }else{
//                throw new CustomException(ErrorCode.INVALID_PERMISSION);
//            }
//        }
//        return result;
//    }
//
//    @PostMapping("/menu/subject/write")
//    @ResponseBody
//    public String NewSubjectWrite(@RequestParam(value = "subject_code", required = false, defaultValue = "-1") int subject_code,
//                                  @RequestParam(value = "subject_name") String subject_name,
//                                  @RequestParam(value = "subject_field") String subject_field,
//                                  @RequestParam(value = "subject_type") String subject_type,
//                                  @RequestParam(value = "personnel") int personnel,
//                                  @RequestParam(value = "lecture_time") String lecture_time,
//                                  @RequestParam(value = "period") int period,
//                                  @RequestParam(value = "member_code") int member_code,
//                                  @RequestParam(value = "fee") int fee,
//                                  @RequestParam(value = "file1", required = false) MultipartFile file,
//                                  HttpSession httpSession){
//        int insert_result = 0;
//        String result = "";
//        if(httpSession.getAttribute("member_id") == null || httpSession.getAttribute("member_type") == null){
//            result = "<script>alert('로그인이 필요한 서비스입니다..');window.location.href='/member/login';</script>";
//        }else{
//            if(httpSession.getAttribute("member_type").equals("ai")||httpSession.getAttribute("member_type").equals("dd")||httpSession.getAttribute("member_type").equals("st")){
//                if(subject_code != -1){
//                    if(file != null){
//                        insert_result = calvinSubjectService.ModifySubject(subject_name,subject_field,subject_type,personnel,lecture_time,period,member_code,fee,file,subject_code);
//                    }else{
//                        insert_result = calvinSubjectService.ModifySubject(subject_name,subject_field,subject_type,personnel,lecture_time,period,member_code,fee, subject_code);
//                    }
//                }else{
//                    if(file != null){
//                        insert_result = calvinSubjectService.InsertSubject(subject_name,subject_field,subject_type,personnel,lecture_time,period,member_code,fee,file);
//                    }else{
//                        insert_result = calvinSubjectService.InsertSubject(subject_name,subject_field,subject_type,personnel,lecture_time,period,member_code,fee);
//                    }
//                }
//                if(insert_result == 1){
//                    if(subject_code != -1){
//                        result = "<script>alert('강의가 성공적으로 수정되었습니다. 변경사항은 새로고침 후 적용됩니다.');history.go(-2);</script>";
//                    }else{
//                        result = "<script>alert('신규 강의가 성공적으로 개설되었습니다. 변경사항은 새로고침 후 적용됩니다.');history.go(-2);</script>";
//                    }
//                }else{
//                    result = "<script>alert('신규 강의 개설 또는 수정에 실패하였습니다. 작성한 내용에 오류가 없는지 확인해주세요');history.back();</script>";
//                }
//            }else{
//                throw new CustomException(ErrorCode.INVALID_PERMISSION);
//            }
//        }
//        return result;
//    }

//    //강의 삭제
//    @GetMapping("/menu/subject/delete")
//    @ResponseBody
//    public String DeleteSubject(HttpSession httpSession, @RequestParam(value = "subject_code") int subject_code){
//        String result = "";
//        if(httpSession.getAttribute("member_id") == null || httpSession.getAttribute("member_type") == null){
//            result = "<script>alert('로그인이 필요한 서비스입니다..');window.location.href='/member/login';</script>";
//        }else{
//            if(httpSession.getAttribute("member_type").equals("ai")||httpSession.getAttribute("member_type").equals("dd")||httpSession.getAttribute("member_type").equals("st")){
//                int delete_result = calvinSubjectService.DeleteSubject(subject_code);
//                if(delete_result == 1) {
//                    result = "<script>alert('강의가 성공적으로 삭제되었습니다. 해당 창을 닫고 새로고침시 변경사항이 적용됩니다.');window.location.href=history.go(-2);</script>";
//                }else{
//                    result = "<script>alert('강의 삭제에 실패했습니다.');window.location.href=document.referrer;</script>";
//                }
//            }else{
//                throw new CustomException(ErrorCode.INVALID_PERMISSION);
//            }
//        }
//        return result;
//    }

}
