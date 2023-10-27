package com.example.demo.apply.repository;

import com.example.demo.entity.Apply;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplyRepository extends JpaRepository<Apply, Long> {
    boolean existsBySiteUser_IdAndMatching_Id(long userId, long matchId);

    Optional<Apply> findBySiteUser_IdAndMatching_Id(long userId, long matchId);
}
