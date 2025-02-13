package com.appAziendaleMicroservizi.pubblicazioni.domains.dto.responses;

import lombok.Builder;

@Builder
public record CommentoELikeResponse(
        Long id,
        String commento,
        Boolean miPiace,
        Long userId,
        Long newsId
) {

}
