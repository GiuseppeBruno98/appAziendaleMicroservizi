package com.appAziendaleMicroservizi.api_gateway.domains.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "token_bl")
@EntityListeners(AuditingEntityListener.class)
public class TokenBlackList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false, name= "id_utente")
    private Long idUtente;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

}
