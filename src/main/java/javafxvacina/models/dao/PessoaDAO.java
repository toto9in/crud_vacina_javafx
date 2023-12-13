package javafxvacina.models.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Alert;
import javafxvacina.models.Pessoa;


public class PessoaDAO {

    private final Connection conexao;

    public PessoaDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public List<Pessoa> buscar(String codigo, String nome, String cpf, String dataNascimento, String ateData)
            throws SQLException {
        Pessoa pessoa = null;
        List<Pessoa> pessoas = new ArrayList<>();
        int cont = 1;
        String sql = "SELECT * FROM pessoa WHERE";
        Date dataNascimentoDate = null;
        Date ateDataDate = null;

    

        if (codigo != "") {
            sql += " codigopessoa = ? AND";
        }
        if (nome != "") {
            sql += " LOWER(nomepessoa) LIKE ? AND";
        }
        if (cpf != "") {
            sql += " cpf LIKE ? AND";
        }
        if (dataNascimento != "" && ateData != "") {
            sql = "SELECT * FROM pessoa WHERE datanascimento BETWEEN ? AND ? AND";
            dataNascimentoDate = Date.valueOf(dataNascimento);
            ateDataDate = Date.valueOf(ateData);
        } else {
            if (dataNascimento != "") {
                sql += " datanascimento >= ? AND";
                dataNascimentoDate = Date.valueOf(dataNascimento);
            }
            if (ateData != "") {
                sql += " datanascimento <= ? AND";
                ateDataDate = Date.valueOf(ateData);
            }
        }

        sql += " situacao = 'ATIVO';";

        PreparedStatement pstmt = conexao.prepareStatement(sql);

        if (codigo != "") {
            pstmt.setLong(cont, Long.parseLong(codigo));
            cont++;
        }
        if (nome != "") {
            pstmt.setString(cont, "%" + nome.toLowerCase() + "%");
            cont++;
        }
        if (cpf != "") {
            pstmt.setString(cont, "%" + cpf + "%");
            cont++;
        }
        if (dataNascimento != "" && ateData != "") {
            pstmt.setDate(cont, dataNascimentoDate);
            cont++;
            pstmt.setDate(cont, ateDataDate);
            cont++;
        } else {
            if (dataNascimento != "") {
                pstmt.setDate(cont, dataNascimentoDate);
                cont++;
            }
            if (ateData != "") {
                pstmt.setDate(cont, ateDataDate);
                cont++;
            }
        }

        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            pessoa = new Pessoa(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getString(4));
            pessoas.add(pessoa);
        }

        if (pessoas.size() == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Nao foi encontrada essa/essas pessoa/as");
            alert.showAndWait();
        }

        rs.close();
        pstmt.close();

        return pessoas;
    }

    public List<Pessoa> buscarTodas() throws SQLException {
        Pessoa pessoa;
        List<Pessoa> pessoas = new ArrayList<>();

        String sql = "SELECT * FROM pessoa WHERE situacao = 'ATIVO';";
        Statement stmt = conexao.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            pessoa = new Pessoa(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getString(4));
            pessoas.add(pessoa);
        }
        rs.close();
        stmt.close();

        return pessoas;
    }
}
