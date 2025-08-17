package br.com.thaynna.forumhub.domain.dto;

import java.time.LocalDateTime;

public class TopicoResponseDTO {

    private String titulo;
    private String mensagem;
    private LocalDateTime dataCriacao;
    private String estado;
    private String autor;
    private String curso;

    public TopicoResponseDTO(String titulo, String mensagem, LocalDateTime dataCriacao,
                            String estado, String autor, String curso) {
        this.titulo = titulo;
        this.mensagem = mensagem;
        this.dataCriacao = dataCriacao;
        this.estado = estado;
        this.autor = autor;
        this.curso = curso;
    }

    // Getters
    public String getTitulo() { return titulo; }
    public String getMensagem() { return mensagem; }
    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public String getEstado() { return estado; }
    public String getAutor() { return autor; }
    public String getCurso() { return curso; }
}

