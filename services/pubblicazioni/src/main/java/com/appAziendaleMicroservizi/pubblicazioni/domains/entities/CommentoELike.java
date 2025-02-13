package com.appAziendaleMicroservizi.pubblicazioni.domains.entities;

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
@Table(name = "commento_e_like")
@EntityListeners(AuditingEntityListener.class)
public class CommentoELike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name="mi_piace")
    private Boolean miPiace;

    @Column
    private String commento;

    @JoinColumn(name = "news_id")
    @ManyToOne(optional = false)
    private News newsId;

    private Long creatorId;

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
