package com.appAziendaleMicroservizi.curriculums.domains.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "curriculum")
@EntityListeners(AuditingEntityListener.class)
public class Curriculum {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String path;

    @Column(nullable = false, unique = true)
    private Long idUtente;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @CreatedBy
    @Column(name = "created_by")
    private Long createdBy;
    @LastModifiedDate
    @Column(name = "last_modified_at")
    private LocalDateTime lastModifiedAt;
    @LastModifiedBy
    @Column(name = "last_modified_by")
    private Long lastModifiedBy;

}
