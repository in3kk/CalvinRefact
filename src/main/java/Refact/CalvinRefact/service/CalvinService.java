package Refact.CalvinRefact.service;

import Refact.CalvinRefact.entity.entityEnum.Subject_Field;
import Refact.CalvinRefact.entity.entityEnum.Subject_Type;
import Refact.CalvinRefact.repository.SubjectRepository;
import Refact.CalvinRefact.repository.dto.subject.SubjectListDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CalvinService {

    //regex 패턴을 이용한 유효성 검사 메소드
    public boolean validationMethod(String target, Pattern pattern){
        boolean result = false;

        Matcher matcher = pattern.matcher(target);

        if(matcher.matches()){
            result = true;
        }
        return result;
    }

    public String searchWordFilter(String target) {
        Pattern p1 = Pattern.compile("/[^(A-Za-z가-힣0-9\\s.,)]/");
        Matcher m1 = p1.matcher(target);
        target = m1.replaceAll(" ");
        String [] word_data = {"select", "insert", "delete", "update", "create", "drop", "exec", "union", "fetch", "declare", "truncate"};
        for(String data : word_data){
            target = target.replaceAll(data,"");
        }
        Pattern p2 = Pattern.compile("(%|#|-|>|<|=|'|\")");
        Matcher m2 = p2.matcher(target);
        target = m2.replaceAll("");
        return target;
    }


}
