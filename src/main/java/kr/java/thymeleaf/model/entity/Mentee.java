package kr.java.thymeleaf.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "MENTEE")
@Getter @Setter @NoArgsConstructor
@ToString(exclude = "mentor")
public class Mentee extends BaseEntity {
    private String name;
    private String goal; // !
    private Integer age; // !

    public Mentee(String name, String goal, Integer age) { // !
        this.name = name;
        this.goal = goal;
        this.age = age;
    }

    // 다대일
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mentor_id")
    private Mentor mentor;
}
