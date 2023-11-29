package javafxvacina.models;

public class Pessoa {
    
    private Long codigoPessoa;
    private String nomePessoa;
    private String cpf;
    private String dataNascimento;
    private Situacao situacao;
    

    public Pessoa(Long codigoPessoa, String nomePessoa, String cpf, String dataNascimento) {
        this.codigoPessoa = codigoPessoa;
        this.nomePessoa = nomePessoa;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
    }
    public Long getCodigoPessoa() {
        return codigoPessoa;
    }
    public void setCodigoPessoa(Long codigoPessoa) {
        this.codigoPessoa = codigoPessoa;
    }
    public String getNomePessoa() {
        return nomePessoa;
    }
    public void setNomePessoa(String nomePessoa) {
        this.nomePessoa = nomePessoa;
    }
    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    public String getDataNascimento() {
        return dataNascimento;
    }
    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
    public Situacao getSituacao() {
        return situacao;
    }
    public void setSituacao(Situacao situacao) {
        this.situacao = situacao;
    }
    @Override
    public String toString() {
        return "Pessoa [codigoPessoa=" + codigoPessoa + ", nomePessoa=" + nomePessoa + ", cpf=" + cpf
                + ", dataNascimento=" + dataNascimento + ", situacao=" + situacao + "]";
    }

    
    
}
