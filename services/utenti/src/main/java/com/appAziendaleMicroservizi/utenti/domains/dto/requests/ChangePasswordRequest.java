package com.appAziendaleMicroservizi.utenti.domains.dto.requests;

import lombok.Builder;

@Builder
public record ChangePasswordRequest(
        String oldPassword,
        String newPassword
) {
}
