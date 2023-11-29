package javafxvacina.models.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafxvacina.models.Aplicacao;
import javafxvacina.models.Pessoa;
import javafxvacina.models.Vacina;

public class AplicacaoDAO {
    private final Connection conexao;

    public AplicacaoDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public boolean gravar(Aplicacao aplicacao) throws SQLException {

        //MUDAR NO BANCO PARA CODIGO SER INCREMENTADO AUTOMATICAMENTE
        Pessoa pessoa = aplicacao.getPessoa();
        Vacina vacina = aplicacao.getVacina();
        Date dataAplicacao = Date.valueOf(aplicacao.getDataAplicacao());

        //CODIGOS PARA COLOCAR COMO CHAVE NO BANCO JA QUE MODELEI PRA TABELA APLICACAO TER CHAVE ESTRANGEIRA
        Long codigoPessoa = pessoa.getCodigoPessoa();
        Long codigoVacina = vacina.getCodigo();


        
        String sql = "INSERT INTO aplicacao(dataaplicacao, codigopessoa, codigovacina) VALUES (?, ?, ?);";
        PreparedStatement pstmt = conexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

        pstmt.setDate(1, dataAplicacao);
        pstmt.setLong(2, codigoPessoa);
        pstmt.setLong(3, codigoVacina);

        if (pstmt.executeUpdate() == 1) {
            System.out.println("Insercao da aplicacao com sucesso");
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                aplicacao.setCodigoAplicacao(rs.getLong(1));  
            } else {
                System.out.println("Erro ao obter o codigo gerado pelo BD para a aplicacao");
            }
            rs.close();
            return true;
        } else {
            System.out.println("Erro ao inserir a aplicacao");
            
        }
        pstmt.close();
        return false;
    }

    
}
