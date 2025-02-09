package com.appAziendaleMicroservizi.utente.domains.entities;

import com.appAziendaleMicroservizi.utente.domains.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "utente")
@EntityListeners(AuditingEntityListener.class)
public class Utente{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String cognome;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "data_nascita", nullable = false)
    private LocalDate dataNascita;

    @Column(name = "luogo_nascita", nullable = false)
    private String luogoNascita;

    @Column(nullable = false, unique = true)
    private String telefono;

    @Column(nullable = false)
    private String indirizzo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role ruolo;

    @Column // nullable true non doverla inserire atm
    private String imgUtente;

    @ManyToOne(optional = false)
    @JoinColumn(name = "posizione_lavorativa_id")
    private PosizioneLavorativa idPosizioneLavorativa;

    @Column(name = "registration_token")
    private String registrationToken;

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

    @Column(name = "last_login")
    private LocalDateTime lastLogin;

}
