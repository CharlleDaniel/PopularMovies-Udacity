package com.popularmovies;

/**
 * Created by Charlle Daniel on 11/04/2016.
 */
public class Movie {
    private String titulo;
    private String descricao;
    private String urlImage;
    private String urlBackGround;
    private String dataLancamento;
    private double popularidade;
    private double votosQuantidade;
    private double votosPositivos;

    public Movie(String titulo, String descricao, String urlImage, String urlBackGround, String dataLancamento, double popularidade, double votosQuantidade, double votosPositivos) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.urlImage = urlImage;
        this.urlBackGround = urlBackGround;
        this.dataLancamento = dataLancamento;
        this.popularidade = popularidade;
        this.votosQuantidade = votosQuantidade;
        this.votosPositivos = votosPositivos;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getUrlBackGround() {
        return urlBackGround;
    }

    public void setUrlBackGround(String urlBackGround) {
        this.urlBackGround = urlBackGround;
    }

    public String getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(String dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    public double getPopularidade() {
        return popularidade;
    }

    public void setPopularidade(double popularidade) {
        this.popularidade = popularidade;
    }

    public double getVotosQuantidade() {
        return votosQuantidade;
    }

    public void setVotosQuantidade(double votosQuantidade) {
        this.votosQuantidade = votosQuantidade;
    }

    public double getVotosPositivos() {
        return votosPositivos;
    }

    public void setVotosPositivos(double votosPositivos) {
        this.votosPositivos = votosPositivos;
    }

    public String getUrlImage() {
        return urlImage;
    }

}
