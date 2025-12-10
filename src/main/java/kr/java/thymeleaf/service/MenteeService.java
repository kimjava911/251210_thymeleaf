package kr.java.thymeleaf.service;

import kr.java.thymeleaf.model.entity.Mentee;
import kr.java.thymeleaf.model.entity.Mentor;
import kr.java.thymeleaf.model.repository.MenteeRepository;
import kr.java.thymeleaf.model.repository.MentorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor // 의존성 주입
@Transactional(readOnly = true)
public class MenteeService {
    private final MenteeRepository menteeRepository;
    private final MentorRepository mentorRepository;

    // 모든 멘티 조회
    public List<Mentee> findAll() {
        return menteeRepository.findAll();
    }

    // ID로 멘티 조회 (멘토 정보 포함)
    public Mentee findByIdWithMentor(Long id) {
        return menteeRepository.findByIdWithMentor(id).orElseThrow(
                () -> new IllegalArgumentException("멘티를 찾을 수 없습니다: " + id)
        );
    }

    // 멘티 저장 (멘토 지정)
    @Transactional
    public Mentee save(Mentee mentee, Long mentorId) {
        // 1. 멘토 찾기
        if (mentorId != null) {
            Mentor mentor = mentorRepository.findById(mentorId)
                    .orElseThrow(() -> new IllegalArgumentException("멘토를 찾을 수 없습니다"));
            // 2. 해당 멘토를 지정
            mentee.setMentor(mentor);
        }
        return menteeRepository.save(mentee);
    }

    // 멘티 삭제
    @Transactional
    public void delete(Long id) {
        menteeRepository.deleteById(id);
    }
}
