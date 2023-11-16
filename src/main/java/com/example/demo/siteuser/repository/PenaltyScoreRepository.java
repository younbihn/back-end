package com.example.demo.siteuser.repository;

import com.example.demo.entity.PenaltyScore;
import com.example.demo.entity.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PenaltyScoreRepository extends JpaRepository<PenaltyScore, Long> {
}
