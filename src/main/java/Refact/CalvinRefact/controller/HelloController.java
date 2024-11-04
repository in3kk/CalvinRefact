package Refact.CalvinRefact.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @GetMapping("/sessionAttributeTest")
    public String sessionTest(HttpSession httpSession){
        String result = httpSession.getAttribute("member_type").toString();
        return result;
    }

}
