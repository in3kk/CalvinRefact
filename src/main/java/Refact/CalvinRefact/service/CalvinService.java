package Refact.CalvinRefact.service;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
}
