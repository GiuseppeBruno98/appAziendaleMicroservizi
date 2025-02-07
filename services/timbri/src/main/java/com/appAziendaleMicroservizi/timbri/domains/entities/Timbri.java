package com.appAziendaleMicroservizi.timbri.domains.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "timbri")
@EntityListeners(AuditingEntityListener.class)
public class Timbri {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*@JoinColumn(name = "utente_id")
    @ManyToOne(optional = false)
    private Utente utenteId;*/

    @Column(nullable = false)
    private LocalDateTime oraInizio;

    @Column
    private LocalTime oraFine;

    @Column
    private LocalTime inizioPausa;

    @Column
    private LocalTime  finePausa;

    @CreatedBy
    @Column(name = "created_by")
    private Long createdBy;
    @LastModifiedBy
    @Column(name = "last_modified_by")
    private Long lastModifiedBy;
}
