package kr.java.thymeleaf.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

// Record에선 생성자 검증 형태 -> Setter가 없음.
public record ExampleForm(
        @NotNull @Size(min = 2, max = 20) String name
) {
}
