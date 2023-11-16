package com.example.demo.matching.repository;

import com.example.demo.entity.Matching;
import com.example.demo.matching.dto.FilterRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilteringRepositoryCustom {
    Page<Matching> searchWithFilter(FilterRequestDto filterRequestDto, Pageable pageable);
}