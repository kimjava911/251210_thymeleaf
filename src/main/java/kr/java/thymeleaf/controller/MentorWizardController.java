package kr.java.thymeleaf.controller;

import kr.java.thymeleaf.model.dto.MentorForm;
import kr.java.thymeleaf.service.MentorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@RequiredArgsConstructor
@SessionAttributes("mentorForm") // 세션에 담김
public class MentorWizardController {
    // 1단계
    @GetMapping("/mentor/wizard/step1")
    public String step1(Model model) {
        model.addAttribute("mentorForm", new MentorForm());
        return "mentor/wizard/step1"; // -> model로 담지 않으면 소멸
    }

    // 2단계
    @PostMapping("/mentor/wizard/step2")
    public String step2(@ModelAttribute MentorForm form) {
        // form -> session에서 가져온 객체
        return "mentor/wizard/step2"; // post인데 view를 리턴 (GET방식으로 step2 접근을 막기 위해)
    }

    private final MentorService mentorService;

    // 세션 정리
    @PostMapping("/mentor/wizard/complete")
    public String complete(@ModelAttribute MentorForm form,
                           SessionStatus sessionStatus) {
        mentorService.save(form.toEntity());

        sessionStatus.setComplete();
        return "redirect:/mentor/list";
    }
}
