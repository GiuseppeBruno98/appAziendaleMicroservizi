package com.appAziendaleMicroservizi.domains.dto.responses;

import lombok.Builder;

@Builder
public record DipartimentoResponse (Long id,
                                    String nome,
                                    String descrizione
){
}
