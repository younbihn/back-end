package com.example.demo.apply.dto;

import com.example.demo.entity.Apply;
import com.example.demo.entity.Matching;
import com.example.demo.entity.SiteUser;
import com.example.demo.type.ApplyStatus;
import java.sql.Timestamp;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApplyDto {

    private Matching matching;
    private SiteUser siteUser;
    private Timestamp createTime;
    private ApplyStatus applyStatus;


    public static ApplyDto fromEntity(Apply entity) {
        return ApplyDto.builder()
                .matching(entity.getMatching())
                .siteUser(entity.getSiteUser())
                .createTime(entity.getCreateTime())
                .applyStatus(entity.getStatus())
                .build();
    }
}
