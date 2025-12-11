package kr.java.thymeleaf.controller;

import jakarta.validation.Valid;
import kr.java.thymeleaf.model.dto.MenteeForm;
import kr.java.thymeleaf.model.entity.Mentee;
import kr.java.thymeleaf.service.MenteeService;
import kr.java.thymeleaf.service.MentorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class MenteeController {
    // service
    private final MenteeService menteeService;
    private final MentorService mentorService;

    // form - create.
    @GetMapping("/mentee/new")
    public String newMenteeForm(Model model) {
        model.addAttribute("menteeForm", new MenteeForm());
        model.addAttribute("mentors", mentorService.findAllWithMentees());
        return "mentee/form";
    }

    @PostMapping("/mentee/new")
    public String createMentee(
            @Valid @ModelAttribute MenteeForm form,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("mentors", mentorService.findAllWithMentees());
            return "mentee/form";
        }
        Mentee mentee = form.toEntity(); // Entity에서 DB를 호출하는 건 부적절
        menteeService.save(mentee, form.getMentorId());
        redirectAttributes.addFlashAttribute("message", "멘티가 등록되었습니다");
        return "redirect:/mentor/list";
    }
}
