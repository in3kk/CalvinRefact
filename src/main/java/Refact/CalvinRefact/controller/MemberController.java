package Refact.CalvinRefact.controller;

import Refact.CalvinRefact.entity.Member;
import Refact.CalvinRefact.entity.entityEnum.Member_Type;
import Refact.CalvinRefact.repository.dto.member.*;
import Refact.CalvinRefact.service.MemberService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

        boolean joinResult = memberService.saveMember(member);

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
            Member_Type member_type = member.get().getMemberType();
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
    @GetMapping("/member/logout")
    @ResponseBody
    public String Logout(HttpSession httpSession){
        String result;
        httpSession.removeAttribute("member_id");
        httpSession.removeAttribute("member_type");
        return "<script>window.location.href='/'</script>";
//        return "<script>window.location.href='http://calvin.or.kr/'</script>";
    }

    //회원 권한 변경
    @PostMapping("/mypage/admin/member/grant")
    @ResponseBody
    public String AdminMemberGrant(@RequestParam(value = "member_type") Member_Type member_type,
                                   @RequestParam(value = "member_code") Long member_code,
                                   HttpSession httpSession){
        String result = "";
        if(httpSession.getAttribute("member_id") != null && httpSession.getAttribute("member_type") != null){
            if(httpSession.getAttribute("member_type").equals("dd")||httpSession.getAttribute("member_type").equals("st")||httpSession.getAttribute("member_type").equals("ai")) {
                boolean grant_result = memberService.memberGrant(member_code,httpSession.getAttribute("member_type").toString(),member_type);
                if(grant_result){
                    result = "<script>alert('권한이 성공적으로 변경되었습니다. 변경 내용은 새로고침 후 적용됩니다.');history.go(-1);</script>";
                }else{
                    result = "<script>alert('권한변경에 실패했습니다.');history.back();</script>";
                }
            }else {
                System.out.println("에러 : "+httpSession.getAttribute("member_type"));
            }
        } else{
            /**
             * 권한 부족 예외 추가
             */
        }
        return result;
    }

    //회원 정보 삭제
    @GetMapping("/mypage/admin/member/delete")
    @ResponseBody
    public String AdminMemberDelete(
            @RequestParam(value = "member_code") Long member_code,
            HttpSession httpSession){

        String result = "";
        String member_type = httpSession.getAttribute("member_type").toString();
        if(httpSession.getAttribute("member_id") != null && httpSession.getAttribute("member_type") != null){
            if(httpSession.getAttribute("member_type").equals("dd")||
                    httpSession.getAttribute("member_type").equals("st")||
                    httpSession.getAttribute("member_type").equals("ai")) {
                boolean delete_result = memberService.deleteMember(member_code,member_type);
                if(delete_result){
                    result = "<script>alert('회원 정보가 삭제되었습니다. 변경 내용은 새로고침 후 적용됩니다.');history.go(-2);</script>";

                }else{
                    result = "<script>alert('회원 정보 삭제에 실패했습니다.');history.back();</script>";
                }
            }
        }else{
            /**
             * 권한 부족 예외 추가
             */
        }

        return result;
    }
    //어드민 회원 관리 페이지
    @GetMapping("/mypage/admin/member")
    public String adminMember2(@PageableDefault(size = 20,sort = {"member_id"})Pageable pageable,
                               @RequestParam(value = "search_word", required = false, defaultValue = "") String search_word,
                               @RequestParam(value = "search_type", required = false, defaultValue = "1") int search_type,
                               Model model, HttpSession httpSession
                               ){
        String result = "menu/mypage/admin_member";
        if(httpSession.getAttribute("member_id") != null && httpSession.getAttribute("member_type") != null){
            if(httpSession.getAttribute("member_type").equals("dd")||
                    httpSession.getAttribute("member_type").equals("st")||
                    httpSession.getAttribute("member_type").equals("ai")) {
                Page<MemberListDto> member_list = Page.empty();
                if(search_word.equals("")){
                    member_list = memberService.findAll(httpSession.getAttribute("member_id").toString(),pageable);
                }else{
                    if(search_type == 1){//아이디
                        member_list = memberService.findAllByEmail(httpSession.getAttribute("member_id").toString(),search_word,pageable);
                    }else{// 2 일때 이름
                        member_list = memberService.findAllByUsername(httpSession.getAttribute("member_id").toString(),search_word,pageable);
                    }
                }
                int page = member_list.getNumber();
                int begin_page;
                if(page % 10 == 0){
                    begin_page = page-9;
                }else{
                    begin_page = page/10*10+1;
                }

                int max_page = member_list.getTotalPages();

                model.addAttribute("search_word", search_word);
                model.addAttribute("search_type", search_type);
                model.addAttribute("page", page);
                model.addAttribute("begin_page",begin_page);
                model.addAttribute("max_page", max_page);
                model.addAttribute("member_list",member_list);
                model.addAttribute("page_type","9.3");
            }
        }else{
            /**
             * 권한 부족 예외 추가
             */
        }
        return result;


    }
//    //어드민 회원관리 V 완료
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

    //회원정보 열람
    @GetMapping("/mypage/admin/member/view")
    public String AdminMemberView(Model model, @RequestParam(value = "member_code") Long member_code,HttpSession httpSession){
        if(httpSession.getAttribute("member_id") != null && httpSession.getAttribute("member_type") != null){
            if(httpSession.getAttribute("member_type").equals("dd")||httpSession.getAttribute("member_type").equals("st")||httpSession.getAttribute("member_type").equals("ai")) {
                MemberDetailDto memberDetailDto = memberService.findMemberDetail(httpSession.getAttribute("member_id").toString(),member_code);
                model.addAttribute("member", memberDetailDto);
                model.addAttribute("page_type","9.3");
            }
        }else{
            /**
             * 권한 부족 예외 추가
             */
        }

        return "menu/mypage/admin_member_view";
    }

    //내 강의 9.1
    @GetMapping("/member/mypage/subject")
    public String my_subject(@PageableDefault(size = 20)Pageable pageable,Model model, HttpSession httpSession){
        String result ="";
        if(httpSession.getAttribute("member_id") == null){
            result = "redirect:/member/login";
        }else{
            List<MemberSubjectListDto> subject_list = memberService.findMemberSubjectList(httpSession.getAttribute("member_id").toString(),pageable);
            model.addAttribute("subject_list",subject_list);
            model.addAttribute("page_type", "9.1");
            result = "menu/mypage/subject_list";
        }

        return result;
    }

    //내 정보 9.2
    @GetMapping("/member/mypage/info")
    public String my_info(Model model, HttpSession httpSession){
        String result = "";
        if(httpSession.getAttribute("member_id") == null || httpSession.getAttribute("member_type") == null){
            result = "redirect:/member/login";
        }else{
            MemberDetailDto md = memberService.findMemberDetail2(httpSession.getAttribute("member_id").toString());
            model.addAttribute("info", md);
            model.addAttribute("page_type","9.2");
            result = "menu/mypage/info";
        }

        return result;
    }

    /**
     * JoinMember -> JoinMemberDto
     */
    //정보변경 페이지9.2
    @GetMapping("/member/mypage/modify")
    public String my_info_modify(Model model, HttpSession httpSession){
        String result = "";
        if(httpSession.getAttribute("member_id") == null || httpSession.getAttribute("member_type") == null){
            result = "redirect:/member/login";
        }else{
            MemberDetailDto md = memberService.findMemberDetail2(httpSession.getAttribute("member_id").toString());
            JoinMemberDto jm = new JoinMemberDto();
            model.addAttribute("info", md);
            model.addAttribute("member", jm);
            result =  "menu/mypage/modify";
        }

        return result;
    }
    /**
     * JoinMember -> JoinMemberDto
     */
    //정보변경
    @RequestMapping(value = "/member/mypage/modify/pro", method = RequestMethod.POST)
    @ResponseBody
    public String my_info_modify_pro(JoinMemberDto member, HttpSession httpSession){
        String result;
        if(httpSession.getAttribute("member_id") == null || httpSession.getAttribute("member_type") == null){
            result = "<script>alert('로그인이 필요한 서비스 입니다.');window.location.href='/';</script>";

        }else{
            boolean updateResult = memberService.updateMember(httpSession.getAttribute("member_id").toString(), member.getPwd(), member.getPhone_number(), member.getAddress(), member.getAddress2());

            if(updateResult){
//            result = "<script>alert('회원정보가 변경되었습니다..');window.location.href='http://calvin.or.kr/member/mypage/info'</script>";//서버
//            result = "<script>alert('회원정보가 변경되었습니다.');window.location.href='http://localhost:8080/member/mypage/info'</script>";//
                result = "<script>alert('회원정보가 변경되었습니다. 변경 내용은 새로고침 후 적용됩니다.');history.go(-2);</script>";
            }else{
//            result = "<script>alert('회원정보 변경에 실패하였습니다.');window.location.href='http://calvin.or.kr/member/mypage/info'</script>";//서버
//            result = "<script>alert('회원정보 변경에 실패하였습니다.');window.location.href='http://localhost:8080/member/mypage/info'</script>";//
                result = "<script>alert('회원정보 변경에 실패하였습니다.');history.go(-2);</script>";

            }
        }

        return result;
    }



    //비밀번호 인증 페이지
    @GetMapping("/member/mypage/auth")
    public String AuthPage(Model model){
        model.addAttribute("page_type","9.2");
        return "menu/mypage/auth";
    }

    //비밀번호 인증
    @RequestMapping(value = "/member/mypage/auth/pro", method = RequestMethod.POST)
    @ResponseBody
    public String AuthPro(HttpSession httpSession, @RequestParam(value = "o_pwd") String pwd){
        String result = "";
        if(httpSession.getAttribute("member_id") == null || httpSession.getAttribute("member_type") == null){
            result = "<script>alert('로그인이 필요한 서비스 입니다.');window.location.href='/member/login'</script>";//서버
        }else{
            if(memberService.login(httpSession.getAttribute("member_id").toString(), pwd).isPresent()){
                result = "<script>window.location.href='/member/mypage/modify'</script>";//서버
//            result = "window.location.href='http://localhost:8080/member/mypage/modify'</script>";//
            }else{
                result = "<script>alert('비밀번호가 일치하지 않습니다.');window.location.href='/member/mypage/auth'</script>";//서버
//            result = "<script>alert('비밀번호가 일치하지 않습니다.');window.location.href='http://localhost:8080/member/mypage/auth'</script>";//
            }
        }

        return result;
    }
}