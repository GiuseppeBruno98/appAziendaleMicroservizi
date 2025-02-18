package com.appAziendaleMicroservizi.timbri.domains.dto.requests;

import jakarta.validation.constraints.Email;

public record MailRequest(
        @Email
        String email
) {
}
