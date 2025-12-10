package kr.java.thymeleaf.service;

import kr.java.thymeleaf.model.entity.Mentor;
import kr.java.thymeleaf.model.repository.MentorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service // Component Scan
@RequiredArgsConstructor // 의존성 주입
// springframework
@Transactional(readOnly = true)
public class MentorService {
    private final MentorRepository mentorRepository;

    // 모든 멘토 조회 (멘티 포함)
    public List<Mentor> findAllWithMentees() {
        return mentorRepository.findAllWithMentees();
    }

    // ID로 멘토 조회
    public Mentor findById(Long id) {
        return mentorRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("멘토를 찾을 수 없습니다: " + id)
        );
    }

    // ID로 멘토 조회 (멘티 포함)
    public Mentor findByIdWithMentees(Long id) {
        return mentorRepository.findByIdWithMentees(id).orElseThrow(
                () -> new IllegalArgumentException("멘토를 찾을 수 없습니다: " + id)
        );
    }

    // 멘토 저장
    @Transactional
    public Mentor save(Mentor mentor) {
        return mentorRepository.save(mentor); // <- ID, createdAt (비워서 보냄) -> 찬 게 나옴
    }

    // 멘토 삭제
    @Transactional
    public void delete(Long id) {
        mentorRepository.deleteById(id);
    }
}
