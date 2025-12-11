package kr.java.thymeleaf.model.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import kr.java.thymeleaf.model.entity.Mentee;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MenteeForm {
    private Long id; // Form 직접 설정하지 X

    @NotBlank(message = "이름은 필수입니다")
    private String name;
    @NotBlank(message = "학습 목표를 입력해주세요")
    private String goal;

    @Min(value = 10, message = "나이는 10세 이상이어야 합니다")
    @Max(value = 100, message = "나이는 100세 이하이어야 합니다")
    private Integer age;

    private Long mentorId;

    // entity <-> dto(form)
    public static MenteeForm from(Mentee mentee) {
        MenteeForm form = new MenteeForm();
        form.setId(mentee.getId());
        form.setGoal(mentee.getGoal());
        form.setAge(mentee.getAge());
        if (mentee.getMentor() != null) {
            form.setMentorId(mentee.getMentor().getId());
        }
        return form;
    }

    // 생성용
    public Mentee toEntity() {
        return new Mentee(name, goal, age);
    }
}
