package kr.java.thymeleaf.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "MENTOR") // physical-strategy: org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
@Getter @Setter @NoArgsConstructor
public class Mentor extends BaseEntity {
    private String name;
    private String speciality;
//    @Column(unique = true)
    private String email; // 유저라고 가정할 시 가입-탈퇴 -> 1년 이상 보존.
    // 실제 DB 상에서 지우지는 않고 남겨놓는 것 -> Soft Delete -> 보존 가능한 기간까지 두고... -> delete
    // email 등 개인정보 입력 데이터는 별도로 테이블을 관리하고, '계정'이라는 것 자체를 의미하는 엔터티(테이블)을 따로 두기도 함
    // -> 이 사람이 작성한 글, 댓글, 여러 통계 지표들을 (개인정보 삭제에 따른) 손실 없이 관리할 수 있다

    // setter로 하나씩 넣는게 귀찮아서(?) 넣는 생성자
    public Mentor(String name, String speciality, String email) {
        this.name = name;
        this.speciality = speciality;
        this.email = email;
    }

    // 일대다 관계
    @OneToMany(mappedBy = "mentor", fetch = FetchType.LAZY)
    private List<Mentee> mentees = new ArrayList<>();
}
