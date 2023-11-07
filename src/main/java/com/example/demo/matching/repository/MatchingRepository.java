package com.example.demo.matching.repository;

import com.example.demo.entity.Matching;
import com.example.demo.entity.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MatchingRepository extends JpaRepository<Matching, Long> {
    List<Matching> findMatchingBySiteUser(SiteUser siteUser);
}