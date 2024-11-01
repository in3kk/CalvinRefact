package Refact.CalvinRefact.controller;

import Refact.CalvinRefact.entity.Member;
import Refact.CalvinRefact.entity.entityEnum.Member_Type;
import Refact.CalvinRefact.repository.dto.member.JoinMemberDto;
import Refact.CalvinRefact.repository.dto.member.MemberLoginDto;
import Refact.CalvinRefact.service.MemberService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class MemberController {

    @Autowired
    private MemberService memberService;

//    @GetMapping("/errortest")
//    public String errortest(){
//        throw new CustomException(ErrorCode.INVALID_PERMISSION);
//    }


    //회원가입 페이지
    @GetMapping("/member/join")
    public String joinPage(Model model, HttpSession httpSession){
        JoinMemberDto jm = new JoinMemberDto();
        httpSession.removeAttribute("member_id");
        httpSession.removeAttribute("member_type");
        model.addAttribute("member",jm);
        return "member/join";
    }

    //회원가입
    @PostMapping(value = "/member/join")
    @ResponseBody
    public String memberJoin(JoinMemberDto member, Model model){
        String result = "";

        boolean joinResult = memberService.joinMember(member);

        if(joinResult){
            result = "<script>alert('회원가입이 완료되었습니다.');window.location.href='/member/login'</script>";
//            result = "<script>alert('회원가입이 완료되었습니다.');window.location.href='http://localhost:8080/member/login'</script>";
        }else{
            result = "<script>alert('회원가입에 실패했습니다.');history.back();</script>";
        }
        return result;
    }

    //로그인 페이지
    @GetMapping("/member/login")
    public String loginPage(Model model, HttpSession httpSession){
        MemberLoginDto memberLoginDto = new MemberLoginDto();
        model.addAttribute("member", memberLoginDto);
        httpSession.removeAttribute("member_id");
        httpSession.removeAttribute("member_type");
        return "member/login";
    }

    //로그인
    @PostMapping("/member/login")
    @ResponseBody
    public String login(HttpSession httpSession,
                        @Valid MemberLoginDto memberLoginDto){
        String result;
        Optional<Member> member = memberService.login(memberLoginDto.getId(),memberLoginDto.getPwd());
        if(member.isPresent()){
            httpSession.setAttribute("member_id", member.get().getEmail());
//            result = "<script>window.location.href='http://localhost:8080/'</script>";//
            result = "<script>window.location.href='/'</script>";//서버
            Member_Type member_type = member.get().getMember_type();
            if(member_type.equals(Member_Type.member)){
                httpSession.setAttribute("member_type", "mb");
            }else if(member_type.equals(Member_Type.developer)){
                httpSession.setAttribute("member_type", "dd");
            }else if(member_type.equals(Member_Type.professor)){
                httpSession.setAttribute("member_type","st");
            }else if(member_type.equals(Member_Type.admin)){
                httpSession.setAttribute("member_type","ai");
            }
        }else{
            result = "<script>alert('회원정보가 일치하지 않습니다.');history.back();</script>";
        }
        return result;
    }

    //로그아웃
//    @GetMapping("/member/logout")
//    @ResponseBody
//    public String Logout(HttpSession httpSession){
//        String result;
//        httpSession.removeAttribute("member_id");
//        httpSession.removeAttribute("member_type");
//        return "<script>window.location.href='/'</script>";
////        return "<script>window.location.href='http://calvin.or.kr/'</script>";
//    }



    //    @GetMapping("/menu/subject/list") //강의 리스트 페이지
//    public String SubjectList(@RequestParam(value = "field", required = false, defaultValue = "") String field,
//                              @RequestParam(value = "type") String type, Model model){
//        List<Calvin_subject> subject_list;
//        if(field.equals("")){
//            subject_list= calvinSubjectService.SubjectList(type);
//        }else{
//            System.out.println("result"+field);
//            subject_list= calvinSubjectService.SubjectList(field,type);
//        }
//        String result = "menu/subject/subject_list";
//        model.addAttribute("subject_list", subject_list);
//        model.addAttribute("page_type","2.2");
//        return  result;
//    }



//    //회원 권한 변경
//    @PostMapping("/mypage/admin/member/grant")
//    @ResponseBody
//    public String AdminMemberGrant(@RequestParam(value = "member_type") String member_type,
//                                   @RequestParam(value = "member_code") String member_code,
//                                   HttpSession httpSession){
//        String result = "";
//        if(httpSession.getAttribute("member_id") != null && httpSession.getAttribute("member_type") != null){
//            if(httpSession.getAttribute("member_type").equals("dd")||httpSession.getAttribute("member_type").equals("st")||httpSession.getAttribute("member_type").equals("ai")) {
//                int grant_result = calvinMemberService.MemberGrant(member_code,member_type);
//                if(grant_result == 1){
//                    result = "<script>alert('권한이 성공적으로 변경되었습니다. 변경 내용은 새로고침 후 적용됩니다.');history.go(-1);</script>";
//                }else{
//                    result = "<script>alert('권한변경에 실패했습니다.');history.back();</script>";
//                }
//            }else {
//                System.out.println("에러 : "+httpSession.getAttribute("member_type"));
//            }
//        } else{
//            throw new CustomException(ErrorCode.INVALID_PERMISSION);
//        }
//
//        return result;
//    }
//
//    //회원 정보 삭제
//    @GetMapping("/mypage/admin/member/delete")
//    @ResponseBody
//    public String AdminMemberDelete(@RequestParam(value = "member_code") int member_code,HttpSession httpSession){
//
//        String result = "";
//        if(httpSession.getAttribute("member_id") != null && httpSession.getAttribute("member_type") != null){
//            if(httpSession.getAttribute("member_type").equals("dd")||httpSession.getAttribute("member_type").equals("st")||httpSession.getAttribute("member_type").equals("ai")) {
//                int delete_result = calvinMemberService.DeleteMember(member_code);
//                if(delete_result == 1){
//                    result = "<script>alert('회원 정보가 삭제되었습니다. 변경 내용은 새로고침 후 적용됩니다.');history.go(-2);</script>";
//
//                }else{
//                    result = "<script>alert('회원 정보 삭제에 실패했습니다.');history.back();</script>";
//                }
//            }
//        }else{
//            throw new CustomException(ErrorCode.INVALID_PERMISSION);
//        }
//
//        return result;
//    }
//    //어드민 회원관리
//    @GetMapping("/mypage/admin/member")
//    public String AdminMember(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
//                              @RequestParam(value = "search_word", required = false, defaultValue = "") String search_word,
//                              @RequestParam(value = "search_type", required = false, defaultValue = "1") int search_type,
//                              Model model,HttpSession httpSession){
//        String result = "menu/mypage/admin_member";
//        if(!search_word.equals("")){
//            Pattern RegPattern1 = Pattern.compile("/[^(A-Za-z가-힣0-9\\s.,@)]/");
//            Matcher m = RegPattern1.matcher(search_word);
//            search_word = m.replaceAll(" ");
//        }
//        if(httpSession.getAttribute("member_id") != null && httpSession.getAttribute("member_type") != null){
//            if(httpSession.getAttribute("member_type").equals("dd")||httpSession.getAttribute("member_type").equals("st")||httpSession.getAttribute("member_type").equals("ai")) {
//                int count;
//                List<Calvin_Member> member_list;
//
//                if(search_word.equals("")){
//                    count = calvinMemberService.paging();
//                    member_list = calvinMemberService.SelectAllMember(page,count);
//                }else{
//                    count = calvinMemberService.paging(search_type,search_word);
//                    if(search_type == 1){//아이디
//                        member_list = calvinMemberService.SelectById(search_word,page,count);
//                    }else{// 2 일때 이름
//                        member_list = calvinMemberService.SelectByName(search_word,page,count);
//                    }
//                }
//                int begin_page;
//                if(page % 10 == 0){
//                    begin_page = page-9;
//                }else{
//                    begin_page = page/10*10+1;
//                }
//
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
//                model.addAttribute("member_list",member_list);
//                model.addAttribute("page_type","9.3");
//            }
//        }else{
//            throw new CustomException(ErrorCode.INVALID_PERMISSION);
//        }
//        return result;
//    }
//
//    //회원정보 열람
//    @GetMapping("/mypage/admin/member/view")
//    public String AdminMemberView(Model model, @RequestParam(value = "member_code") int member_code,HttpSession httpSession){
//        if(httpSession.getAttribute("member_id") != null && httpSession.getAttribute("member_type") != null){
//            if(httpSession.getAttribute("member_type").equals("dd")||httpSession.getAttribute("member_type").equals("st")||httpSession.getAttribute("member_type").equals("ai")) {
//                Calvin_Member calvin_member = calvinMemberService.MemberInfo(member_code,2);
//                model.addAttribute("member", calvin_member);
//                model.addAttribute("page_type","9.3");
//            }
//        }else{
//            throw new CustomException(ErrorCode.INVALID_PERMISSION);
//        }
//
//        return "menu/mypage/admin_member_view";
//    }
//
//
//
//
//
//
//    //파일 다운로드
//    @GetMapping("/download/{save_name}/{original_name}")
//    public ResponseEntity DownloadFile(@PathVariable String save_name, @PathVariable String original_name){
//        return calvinFileService.FileDownload(save_name,original_name);
//    }
//
//    @GetMapping("/download/document/{original_name}")
//    public ResponseEntity DownloadDoc(@PathVariable String original_name){
//        return calvinFileService.FileDownload(original_name);
//    }
//
//
//
//
//
//    //내 강의 9.1
//    @GetMapping("/member/mypage/subject")
//    public String my_subject(Model model, HttpSession httpSession){
//        String result ="";
//        if(httpSession.getAttribute("member_id") == null){
//            result = "redirect:/member/login";
//        }else{
//            List<MyPageSubjectView> subject_list = calvinSubjectService.My_subject(httpSession.getAttribute("member_id").toString());
//            model.addAttribute("subject_list",subject_list);
//            model.addAttribute("page_type", "9.1");
//            result = "menu/mypage/subject_list";
//        }
//
//        return result;
//    }
//
//    //내 정보 9.2
//    @GetMapping("/member/mypage/info")
//    public String my_info(Model model, HttpSession httpSession){
//        String result = "";
//        if(httpSession.getAttribute("member_id") == null || httpSession.getAttribute("member_type") == null){
//            result = "redirect:/member/login";
//        }else{
//            Calvin_Member cm = calvinMemberService.MyInfo(httpSession.getAttribute("member_id").toString(),1);
//            model.addAttribute("info", cm);
//            model.addAttribute("page_type","9.2");
//            result = "menu/mypage/info";
//        }
//
//        return result;
//    }
//
//    //정보변경 페이지9.2
//    @GetMapping("/member/mypage/modify")
//    public String my_info_modify(Model model, HttpSession httpSession){
//        String result = "";
//        if(httpSession.getAttribute("member_id") == null || httpSession.getAttribute("member_type") == null){
//            result = "redirect:/member/login";
//        }else{
//            Calvin_Member cm = calvinMemberService.MyInfo(httpSession.getAttribute("member_id").toString(),2);
//            JoinMember jm = new JoinMember();
//            model.addAttribute("info", cm);
//            model.addAttribute("member", jm);
//            result =  "menu/mypage/modify";
//        }
//
//        return result;
//    }
//
//    //정보변경
//    @RequestMapping(value = "/member/mypage/modify/pro", method = RequestMethod.POST)
//    @ResponseBody
//    public String my_info_modify_pro(JoinMember member, HttpSession httpSession){
//        boolean update_result = false;
//        String result;
//        if(httpSession.getAttribute("member_id") == null || httpSession.getAttribute("member_type") == null){
//            result = "<script>alert('로그인이 필요한 서비스 입니다.');window.location.href='/';</script>";
//
//        }else{
//            if(!member.getPwd().equals("")){
//                update_result = calvinMemberService.MemberInfoUpdatePwd(member.getPwd(),httpSession.getAttribute("member_id").toString());
//            }
//            if(!member.getPhone_number().equals("")){
//                update_result = calvinMemberService.MemberInfoUpdatePn(member.getPhone_number(),httpSession.getAttribute("member_id").toString());
//            }
//            if(!member.getAddress().equals("")){
//                update_result = calvinMemberService.MemberInfoUpdateAddress(member.getAddress(),httpSession.getAttribute("member_id").toString());
//            }
//
//            if(update_result){
////            result = "<script>alert('회원정보가 변경되었습니다..');window.location.href='http://calvin.or.kr/member/mypage/info'</script>";//서버
////            result = "<script>alert('회원정보가 변경되었습니다.');window.location.href='http://localhost:8080/member/mypage/info'</script>";//
//                result = "<script>alert('회원정보가 변경되었습니다. 변경 내용은 새로고침 후 적용됩니다.');history.go(-2);</script>";
//            }else{
////            result = "<script>alert('회원정보 변경에 실패하였습니다.');window.location.href='http://calvin.or.kr/member/mypage/info'</script>";//서버
////            result = "<script>alert('회원정보 변경에 실패하였습니다.');window.location.href='http://localhost:8080/member/mypage/info'</script>";//
//                result = "<script>alert('회원정보 변경에 실패하였습니다.');history.go(-2);</script>";
//
//            }
//        }
//
//        return result;
//    }
//
//
//
//    //비밀번호 인증
//    @GetMapping("/member/mypage/auth")
//    public String AuthPage(Model model){
//        model.addAttribute("page_type","9.2");
//        return "menu/mypage/auth";
//    }
//
//    //인증
//    @RequestMapping(value = "/member/mypage/auth/pro", method = RequestMethod.POST)
//    @ResponseBody
//    public String AuthPro(HttpSession httpSession, @RequestParam(value = "o_pwd") String pwd){
//        String result = "";
//        if(httpSession.getAttribute("member_id") == null || httpSession.getAttribute("member_type") == null){
//            result = "<script>alert('로그인이 필요한 서비스 입니다.');window.location.href='/member/login'</script>";//서버
//        }else{
//            if(calvinMemberService.login(httpSession.getAttribute("member_id").toString(), pwd)){
//                result = "<script>window.location.href='/member/mypage/modify'</script>";//서버
////            result = "window.location.href='http://localhost:8080/member/mypage/modify'</script>";//
//            }else{
//                result = "<script>alert('비밀번호가 일치하지 않습니다.');window.location.href='/member/mypage/auth'</script>";//서버
////            result = "<script>alert('비밀번호가 일치하지 않습니다.');window.location.href='http://localhost:8080/member/mypage/auth'</script>";//
//            }
//        }
//
//        return result;
//    }
}
