package br.com.thaynna.forumhub.domain.dto;

import jakarta.validation.constraints.NotBlank;

public record AtualizacaoTopicoDTO(
        @NotBlank String titulo,
        @NotBlank String mensagem,
        @NotBlank String estado
) {}