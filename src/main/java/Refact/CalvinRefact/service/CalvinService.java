package Refact.CalvinRefact.service;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CalvinService {
    public boolean validationMethod(String target, Pattern pattern){
        boolean result = false;

        Matcher matcher = pattern.matcher(target);

        if(matcher.matches()){
            result = true;
        }

        return result;
    }
}
