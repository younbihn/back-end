package com.example.demo.matching.repository;

import com.example.demo.entity.Matching;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchingRepository extends JpaRepository<Matching, Long> {
}
