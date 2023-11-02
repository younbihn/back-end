package com.example.demo.matching.repository;

import com.example.demo.entity.Apply;
import com.example.demo.entity.Matching;
import com.example.demo.entity.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchingRepository extends JpaRepository<Matching, Long> {

    boolean existsByIdAndSiteUser(Long id, SiteUser siteUser);
}
