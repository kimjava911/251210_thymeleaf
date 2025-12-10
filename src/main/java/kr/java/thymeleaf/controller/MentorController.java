package kr.java.thymeleaf.controller;

import kr.java.thymeleaf.model.entity.Mentor;
import kr.java.thymeleaf.service.MentorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class MentorController {
    private final MentorService mentorService;

    // http://localhost:8080/syntax/basic
    @GetMapping("/syntax/basic")
    public String basicSyntax(Model model) {
        Mentor mentor = mentorService.findById(1L);

        model.addAttribute("mentor", mentor);
        model.addAttribute("greeting", "안녕하세요!");
        model.addAttribute("currentTime", LocalDateTime.now());

        return "syntax/basic"; // (resources)/templates/syntax/basic.html
    }
}
