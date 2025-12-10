package kr.java.thymeleaf.model.repository;

import kr.java.thymeleaf.model.entity.Mentor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

// save, findAll, ... -> 기본 메서드가 내장
public interface MentorRepository extends JpaRepository<Mentor, Long> {
    @Query("SELECT DISTINCT m FROM Mentor m LEFT JOIN FETCH m.mentees") // N+1
    List<Mentor> findAllWithMentees();

    @Query("SELECT m FROM Mentor m LEFT JOIN FETCH m.mentees WHERE m.id = :id")
    Optional<Mentor> findByIdWithMentees(@Param("id") Long id);
}
