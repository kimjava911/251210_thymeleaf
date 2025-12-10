package kr.java.thymeleaf.controller;

import kr.java.thymeleaf.model.entity.Mentor;
import kr.java.thymeleaf.service.MentorService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

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

    // http://localhost:8080/mentor/list
    @GetMapping("/mentor/list")
    public String mentorList(Model model) {
        model.addAttribute("mentors", mentorService.findAllWithMentees());
        model.addAttribute("pageTitle", "멘토 관리 시스템");
        return "mentor/list";
    }

    // http://localhost:8080/mentor/1
    @GetMapping("/mentor/{id}")
//    @ResponseBody // -> JSON Return
    public String mentorDetail(Model model, @PathVariable Long id) {
//    public Mentor mentorDetail(Model model, @PathVariable Long id) {
//        return mentorService.findByIdWithMentees(id);
        model.addAttribute("mentor", mentorService.findByIdWithMentees(id));
        return "mentor/detail";
    }
}
