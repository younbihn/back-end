package com.example.demo.matching.repository;

import com.example.demo.entity.Matching;
import com.example.demo.matching.dto.FilterRequestDto;
import com.example.demo.matching.dto.MatchingPreviewDto;
import com.example.demo.matching.filter.Region;
import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.QMap;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.demo.entity.QMatching.matching;

public class FilteringRepositoryImpl implements FilteringRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    public FilteringRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public Page<Matching> searchWithFilter(FilterRequestDto filterRequestDto, Pageable pageable) {
        List<Matching> matchingList =  queryFactory.selectFrom(matching)
                .where(
                        startDate(filterRequestDto),
                        endDate(filterRequestDto),
                        region(filterRequestDto),
                        matchingType(filterRequestDto),
                        ageGroup(filterRequestDto),
                        ntrp(filterRequestDto)
                        )
                .limit(pageable.getPageSize())
                .fetch();

        JPQLQuery<Matching> total =  queryFactory.selectFrom(matching)
                .where(
                        startDate(filterRequestDto),
                        endDate(filterRequestDto),
                        region(filterRequestDto),
                        matchingType(filterRequestDto),
                        ageGroup(filterRequestDto),
                        ntrp(filterRequestDto)
                );

        return PageableExecutionUtils.getPage(matchingList, pageable, total::fetchCount);
    }

    private BooleanExpression startDate(FilterRequestDto filterRequestDto){
        if(filterRequestDto.getFilters().getStartDate() == null) {
            return null;
        }
        LocalDate dateOfFilter = LocalDate.parse(filterRequestDto.getFilters().getStartDate());
        return matching.date.goe(dateOfFilter);
    }

    private BooleanExpression endDate(FilterRequestDto filterRequestDto){
        if(filterRequestDto.getFilters().getEndDate() == null) {
            return null;
        }
        LocalDate dateOtFilter = LocalDate.parse(filterRequestDto.getFilters().getEndDate());
        return matching.date.loe(dateOtFilter);
    }

    private BooleanExpression ageGroup(FilterRequestDto filterRequestDto){
        if(filterRequestDto.getFilters().getAgeGroups() == null) {
            return null;
        }
        return matching.age.in(filterRequestDto.getFilters().getAgeGroups());
    }

    private BooleanExpression region(FilterRequestDto filterRequestDto){
        if(filterRequestDto.getFilters().getRegions() == null) {
            return null;
        }

        List<String> regions = filterRequestDto.getFilters().getRegions().stream()
                .map(Region::getKorean).toList();
        StringExpression locationExpression = Expressions.asString(matching.location);

        BooleanExpression[] conditions = regions.stream()
                .map(region -> locationExpression.likeIgnoreCase("%" + region + "%"))
                .toArray(BooleanExpression[]::new);

        return Expressions.anyOf(conditions);
    }

    private BooleanExpression matchingType(FilterRequestDto filterRequestDto){
        if(filterRequestDto.getFilters().getMatchingTypes() == null) {
            return null;
        }
        return matching.matchingType.in(filterRequestDto.getFilters().getMatchingTypes());
    }

    private BooleanExpression ntrp(FilterRequestDto filterRequestDto){
        if(filterRequestDto.getFilters().getNtrps() == null) {
            return null;
        }
        return matching.ntrp.in(filterRequestDto.getFilters().getNtrps());
    }
}
