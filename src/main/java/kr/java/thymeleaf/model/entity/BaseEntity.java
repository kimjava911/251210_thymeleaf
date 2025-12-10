package kr.java.thymeleaf.model.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.Instant;
import java.time.LocalDateTime;

@MappedSuperclass // 상속 (extends) <- Table을 만들지 X
@Getter // Setter가 필요 없는 속성들
public class BaseEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //    private Instant createdAt; // UTC 기준으로
    private LocalDateTime createdAt; // 서버 시간 기준으로

    @PrePersist
    public void prePersist() // 신규 save 되면 (INSERT로 실제 반영되기 직전)
    {
        createdAt = LocalDateTime.now();
    }
}
