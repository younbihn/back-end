package com.example.demo.siteuser.dto;

import com.example.demo.entity.ReportUser;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ViewReportsDto {
    private Long id;
    private Long siteUser;
    private String title;
    private String content;
    private String createTime;

    public static ViewReportsDto fromEntity(ReportUser reportUser) {
        return ViewReportsDto.builder()
                .id(reportUser.getId())
                .siteUser(reportUser.getSiteUser().getId())
                .title(reportUser.getTitle())
                .content(reportUser.getContent())
                .createTime(reportUser.getCreateTime().toString())
                .build();
    }
}
