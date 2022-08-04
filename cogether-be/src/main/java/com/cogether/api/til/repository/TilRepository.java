package com.cogether.api.til.repository;

import com.cogether.api.til.domain.Til;
import com.cogether.api.user.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TilRepository extends JpaRepository<Til, Integer> {
    List<Til> findAllByContentContainingIgnoreCaseOrTitleContainingIgnoreCaseOrUser_NicknameContainingIgnoreCaseOrderByCreatedAtDesc(String keyword, String keyword2, String keyword3);
    List<Til> findAllByUserOrderByCreatedAtDesc(User user);
    List<Til> findAllByTitleContainingIgnoreCaseAndUserOrderByCreatedAtDesc(String keyword, User user);
}
