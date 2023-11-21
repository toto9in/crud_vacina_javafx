package javafxvacina.models;

public class Vacina {
    
    private Long codigo;
    private String nome;
    private String descricao;
    
    public Vacina() {
        codigo = -1L;
        nome = "sem nome";
        descricao = "sem descrição";
    }

    public Vacina(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }

    public Vacina(Long codigo, String nome, String descricao) {
        this.codigo = codigo;
        this.nome = nome;
        this.descricao = descricao;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }


    @Override
    public String toString() {
        return "Vacina [codigo=" + codigo + ", nome=" + nome + ", descricao=" + descricao +"]";
    }
        
}
