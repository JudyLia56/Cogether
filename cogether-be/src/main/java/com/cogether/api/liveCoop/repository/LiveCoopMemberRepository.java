package com.cogether.api.liveCoop.repository;

import com.cogether.api.liveCoop.domain.LiveCoop;
import com.cogether.api.liveCoop.domain.LiveCoopMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LiveCoopMemberRepository extends JpaRepository<LiveCoopMember, Integer> {
    List<LiveCoopMember> findAllByLiveCoop(LiveCoop liveCoop);
}