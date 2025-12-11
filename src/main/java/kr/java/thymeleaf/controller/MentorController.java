package kr.java.thymeleaf.controller;

import jakarta.validation.Valid;
import kr.java.thymeleaf.model.dto.MentorForm;
import kr.java.thymeleaf.model.entity.Mentor;
import kr.java.thymeleaf.service.MentorService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MentorController {
    private final MentorService mentorService;

    // 등록 폼
    @GetMapping("/mentor/new")
    public String newMentorForm(Model model) {
        // th:object <- form
        model.addAttribute("mentorForm", new MentorForm());
        model.addAttribute("specialties",
                List.of("백엔드", "프론트엔드", "풀스택", "데이터"));
        return "mentor/form"; // templates/mentor/form.html
    }

    // 등록 처리 (PRG)
    @PostMapping("/mentor/new")
    public String createMentor(
            @Valid @ModelAttribute MentorForm form,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {
        // 유효성 검증 실패 시 form
        if (bindingResult.hasErrors()) {
            model.addAttribute("specialties",
                    List.of("백엔드", "프론트엔드", "풀스택", "데이터"));
            // request(Model) -> mentorForm(th:object) -> forward
            return "mentor/form";
        }
        // 저장
        Mentor mentor = form.toEntity();
        mentorService.save(mentor);

        // 성공 메시지 -> (Redirect -> addFlashAttribute)
        redirectAttributes.addFlashAttribute("message",
                "멘토가 등록되었습니다.");
        redirectAttributes.addAttribute("messageType", "success");

        return "redirect:/mentor/list";
    }

    // 수정 폼 페이지
    @GetMapping("/mentor/{id}/edit")
    public String editMentorForm(@PathVariable Long id, Model model) {
        Mentor mentor = mentorService.findById(id);
//        model.addAttribute("mentorForm", new MentorForm(mentor));
        model.addAttribute("mentorForm", MentorForm.from(mentor));
        model.addAttribute("specialties",
                List.of("백엔드", "프론트엔드", "풀스택", "데이터"));
        // -> @Value, DB의 값.
        return "mentor/form";
    }

    @PostMapping("/mentor/{id}/edit")
    public String updateMentor(
            @PathVariable Long id,
            @Valid @ModelAttribute MentorForm form,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("specialties",
                    List.of("백엔드", "프론트엔드", "풀스택", "데이터"));
            // bindingResult <- 별도의 작업이 추가로 필요 없음
            return "mentor/form";
        }
        Mentor mentor = mentorService.findById(id);
        mentor.setName(form.getName());
        mentor.setSpecialty(form.getSpecialty());
        mentor.setEmail(form.getEmail());
        mentorService.save(mentor); // Transaction -> Service.

        redirectAttributes.addFlashAttribute("message", "멘토 정보가 수정되었습니다");

//        return "redirect:/mentor/" + id;
        return "redirect:/mentor/%d".formatted(id);
    }

    // 삭제 처리
    @GetMapping("/mentor/{id}/delete")
    public String deleteMentor(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        mentorService.delete(id);
        redirectAttributes.addFlashAttribute("message", "멘토가 삭제되었습니다");
        return "redirect:/mentor/list";
    }

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
