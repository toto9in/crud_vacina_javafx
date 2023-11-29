package javafxvacina.models;

import java.time.LocalDate;

public class Aplicacao {
    
    private Long codigoAplicacao;
    private LocalDate dataAplicacao;
    private Pessoa pessoa;
    private Vacina vacina;
    private Situacao situacao;

    
    public Aplicacao(Long codigoAplicacao, LocalDate dataAplicacao, Pessoa pessoa, Vacina vacina, Situacao situacao) {
        this.codigoAplicacao = codigoAplicacao;
        this.dataAplicacao = dataAplicacao;
        this.pessoa = pessoa;
        this.vacina = vacina;
        this.situacao = situacao;
    }


    public Aplicacao(LocalDate dataAplicacao, Pessoa pessoa, Vacina vacina) {
        this.dataAplicacao = dataAplicacao;
        this.pessoa = pessoa;
        this.vacina = vacina;
    }



    public Long getCodigoAplicacao() {
        return codigoAplicacao;
    }
    public void setCodigoAplicacao(Long codigoAplicacao) {
        this.codigoAplicacao = codigoAplicacao;
    }
    public LocalDate getDataAplicacao() {
        return dataAplicacao;
    }
    public void setDataAplicacao(LocalDate dataAplicacao) {
        this.dataAplicacao = dataAplicacao;
    }
    public Pessoa getPessoa() {
        return pessoa;
    }
    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }
    public Vacina getVacina() {
        return vacina;
    }
    public void setVacina(Vacina vacina) {
        this.vacina = vacina;
    }
    public Situacao getSituacao() {
        return situacao;
    }
    public void setSituacao(Situacao situacao) {
        this.situacao = situacao;
    }


    @Override
    public String toString() {
        return "Aplicacao [codigoAplicacao=" + codigoAplicacao + ", dataAplicacao=" + dataAplicacao + ", pessoa="
                + pessoa + ", vacina=" + vacina + ", situacao=" + situacao + "]";
    }

    
    
}
