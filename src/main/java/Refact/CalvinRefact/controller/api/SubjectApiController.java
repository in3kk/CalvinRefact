package Refact.CalvinRefact.controller.api;

import Refact.CalvinRefact.entity.entityEnum.Subject_Field;
import Refact.CalvinRefact.entity.entityEnum.Subject_Type;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class SubjectApiController {

    @GetMapping("/subjectFieldList")
    @ResponseBody
    public ResponseEntity<List<String>> subjectFiledList(@RequestParam("subject_type") String subjectType) {
        List<String> subjectFieldList = new ArrayList<>();
        if (subjectType.equals(Subject_Type.학점은행제.toString())) {
            subjectFieldList.add("사회복지학과");
            subjectFieldList.add("상담");
            subjectFieldList.add("신학");
            subjectFieldList.add("아동");
            subjectFieldList.add("실용음악과");
            subjectFieldList.add("교양");
            subjectFieldList.add("사회복지현장실습");
        } else if (subjectType.equals(Subject_Type.특별교육과정.toString())) {
            subjectFieldList.add("용인");
            subjectFieldList.add("서현정치경제");
            subjectFieldList.add("경기교육");
            subjectFieldList.add("레이번스축구아카데미");
            subjectFieldList.add("연예");
            subjectFieldList.add("미디어");
        } else if (subjectType.equals(Subject_Type.일반교양.toString())) {
            subjectFieldList.add("논술강좌");
            subjectFieldList.add("자기계발");
            subjectFieldList.add("생활건강");
            subjectFieldList.add("생활교양");
            subjectFieldList.add("생활예술");
        } else if (subjectType.equals(Subject_Type.자격증취창업.toString())) {
            subjectFieldList.add("전문자격증");
            subjectFieldList.add("민간자격증");
            subjectFieldList.add("기술자격증");
            subjectFieldList.add("취창업");
        } else if (subjectType.equals(Subject_Type.언어.toString())) {
            subjectFieldList.add("성경고전어");
            subjectFieldList.add("제2외국어");
            subjectFieldList.add("한국어");
        } else if (subjectType.equals(Subject_Type.목회.toString())) {
            subjectFieldList.add("사모포럼");
            subjectFieldList.add("성경주해");
            subjectFieldList.add("성경강해");
            subjectFieldList.add("목회트렌드");
        }
        System.out.print("실행 : ");
        for (String s : subjectFieldList) {
            System.out.print(s+", ");
        }
        System.out.println();
        return ResponseEntity.ok(subjectFieldList);
    }
}