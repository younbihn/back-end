package com.example.demo.entity;

import com.example.demo.apply.dto.ApplyDto;
import com.example.demo.type.ApplyStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@DynamicInsert
@EntityListeners(AuditingEntityListener.class)
public class Apply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MATCHING_ID", nullable = false)
    private Matching matching;

    @ManyToOne
    @JoinColumn(name = "SITE_USER_ID", nullable = false)
    private SiteUser siteUser;

    @CreatedDate
    @Column(name = "CREATE_TIME")
    private LocalDateTime createTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private ApplyStatus status; // default 값 지정됨(PENDING)

    public static Apply fromDto(ApplyDto applyDto) {
        return Apply.builder()
                .matching(applyDto.getMatching())
                .siteUser(applyDto.getSiteUser())
                .build();
    }

    public void changeApplyStatus(ApplyStatus applyStatus) {
        this.status = applyStatus;
    }
}