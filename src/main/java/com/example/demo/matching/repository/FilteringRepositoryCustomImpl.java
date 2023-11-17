package com.example.demo.matching.repository;

import com.example.demo.entity.Matching;
import com.example.demo.matching.dto.FilterRequestDto;
import com.example.demo.matching.dto.LocationDto;
import com.example.demo.matching.filter.Region;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.time.LocalDate;
import java.util.List;

import static com.example.demo.entity.QMatching.matching;

public class FilteringRepositoryCustomImpl implements FilteringRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public FilteringRepositoryCustomImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public Page<Matching> searchWithFilter(FilterRequestDto filterRequestDto, Pageable pageable) {
        List<Matching> matchingList;
        if(filterRequestDto.getLocation() == null){
            matchingList =  queryFactory.selectFrom(matching)
                    .where(
                            date(filterRequestDto),
                            region(filterRequestDto),
                            matchingType(filterRequestDto),
                            ageGroup(filterRequestDto),
                            ntrp(filterRequestDto)
                    )
                    .limit(pageable.getPageSize())
                    .fetch();
        } else {
            String haversineFormula = "ST_Distance_Sphere(point({0}, {1}), point("
                    + filterRequestDto.getLocation().getLon() + ", " + filterRequestDto.getLocation().getLat() + "))";

            matchingList = queryFactory.selectFrom(matching)
                    .where(
                            date(filterRequestDto),
                            region(filterRequestDto),
                            matchingType(filterRequestDto),
                            ageGroup(filterRequestDto),
                            ntrp(filterRequestDto)
                    )
                    .orderBy(Expressions.stringTemplate(haversineFormula, matching.lon, matching.lat).asc())
                    .limit(pageable.getPageSize())
                    .fetch();
        }

        JPQLQuery<Matching> total =  queryFactory.selectFrom(matching)
                .where(
                        date(filterRequestDto),
                        region(filterRequestDto),
                        matchingType(filterRequestDto),
                        ageGroup(filterRequestDto),
                        ntrp(filterRequestDto)
                );

        return PageableExecutionUtils.getPage(matchingList, pageable, total::fetchCount);
    }

    @Override
    public Page<Matching> searchWithin(LocationDto center, LocationDto northEastBound, LocationDto southWestBound, Pageable pageable) {
        String haversineFormula = "ST_Distance_Sphere(point({0}, {1}), point("
                + center.getLon() + ", " + center.getLat() + "))";

        List<Matching> matchingList =  queryFactory.selectFrom(matching)
                .where(
                        within(southWestBound.getLat(), northEastBound.getLat(), southWestBound.getLon(), northEastBound.getLon())
                )
                .orderBy(Expressions.stringTemplate(haversineFormula, matching.lon, matching.lat).asc())
                .fetch();

        JPQLQuery<Matching> total = queryFactory.selectFrom(matching)
                .where(
                        within(southWestBound.getLat(), northEastBound.getLat(), southWestBound.getLon(), northEastBound.getLon())
                );

        return PageableExecutionUtils.getPage(matchingList, pageable, total::fetchCount);
    }

    private BooleanExpression within(Double latLowerBound, Double latUpperBound, Double lonLeftBound, Double lonRightBound){
        return matching.lat.between(latLowerBound, latUpperBound)
                .and(matching.lon.between(lonLeftBound, lonRightBound));
    }

    private BooleanExpression date(FilterRequestDto filterRequestDto){
        if(filterRequestDto.getFilters() == null || filterRequestDto.getFilters().getDate() == null) {
            return null;
        }
        LocalDate dateOfFilter = LocalDate.parse(filterRequestDto.getFilters().getDate());
        return matching.date.eq(dateOfFilter);
    }

    private BooleanExpression ageGroup(FilterRequestDto filterRequestDto){
        if(filterRequestDto.getFilters() == null || filterRequestDto.getFilters().getAgeGroups() == null) {
            return null;
        }
        return matching.age.in(filterRequestDto.getFilters().getAgeGroups());
    }

    private BooleanExpression region(FilterRequestDto filterRequestDto){
        if(filterRequestDto.getFilters() == null || filterRequestDto.getFilters().getRegions() == null) {
            return null;
        }

        // 영어를 한국어로 - 매칭의 위치를 StringExpression로 - 지역이 포함되어있는지 boolean 배열로 - boolean 배열중에 하나라도 true 있으면 true
        List<String> regions = filterRequestDto.getFilters().getRegions().stream()
                .map(Region::getKorean).toList();
        StringExpression locationExpression = Expressions.asString(matching.location);

        BooleanExpression[] conditions = regions.stream()
                .map(region -> locationExpression.likeIgnoreCase("%" + region + "%"))
                .toArray(BooleanExpression[]::new);

        return Expressions.anyOf(conditions);
    }

    private BooleanExpression matchingType(FilterRequestDto filterRequestDto){
        if(filterRequestDto.getFilters() == null || filterRequestDto.getFilters().getMatchingTypes() == null) {
            return null;
        }
        return matching.matchingType.in(filterRequestDto.getFilters().getMatchingTypes());
    }

    private BooleanExpression ntrp(FilterRequestDto filterRequestDto){
        if(filterRequestDto.getFilters() == null || filterRequestDto.getFilters().getNtrps() == null) {
            return null;
        }
        return matching.ntrp.in(filterRequestDto.getFilters().getNtrps());
    }
}
