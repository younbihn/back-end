package com.example.demo.matching.repository;

import com.example.demo.entity.Matching;
import com.example.demo.entity.SiteUser;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MatchingRepository extends JpaRepository<Matching, Long> {
    List<Matching> findBySiteUser_Id(Long userId);
    boolean existsByIdAndSiteUser(Long id, SiteUser siteUser);
    Optional<List<Matching>> findByRecruitDueDateTime(LocalDateTime now);
}
