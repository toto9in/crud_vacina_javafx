package javafxvacina.models.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Alert;
import javafxvacina.models.Vacina;

public class VacinaDAO {

    private final Connection conexao;

    public VacinaDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public void gravar(Vacina vacina) throws SQLException {

        
        String sql = "INSERT INTO vacina(nome, descricao) VALUES (?, ?);";
        PreparedStatement pstmt = conexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

    
        pstmt.setString(1, vacina.getNome());
        pstmt.setString(2, vacina.getDescricao());

        if (pstmt.executeUpdate() == 1) {
            System.out.println("Insercao da vacina feita com sucesso");
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                vacina.setCodigo(rs.getLong(1));
            } else {
                System.out.println("Erro ao obter o codigo gerado pelo BD para a vacina");
            }
            rs.close();
        } else {
            System.out.println("Erro ao inserir a vacina");
        }
        pstmt.close();

    }

    public List<Vacina> buscar(String codigo, String nome, String descricao) throws SQLException {
        Vacina v = null;
        List<Vacina> vacinas = new ArrayList<>();
        int cont = 1;
        String sql = "SELECT * FROM vacina WHERE";

        if (codigo != "") {
            sql += " codigo = ? AND";
        }
        if (nome != "") {
            sql += " LOWER(nome) LIKE ? AND";
        }
        if (descricao != "") {
            sql += " LOWER(descricao) LIKE ? AND";
        }

        sql += " situacao = 'ATIVO';";
        
        PreparedStatement pstmt = conexao.prepareStatement(sql);

        if (codigo != "") {
            pstmt.setLong(cont, Long.parseLong(codigo));
            cont++;
        }
        if (nome != "") {
            pstmt.setString(cont,  "%" + nome.toLowerCase() + "%");
            cont++;
        }
        if (descricao != "") {
            pstmt.setString(cont, "%" + descricao.toLowerCase() + "%");
            cont++;
        }



        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
           
            v = new Vacina(rs.getLong(1), rs.getString(2), rs.getString(3));
            System.out.println(v + "EITA CUSAO");
            vacinas.add(v);
        }
        if (vacinas.size() == 0) {  
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Nao foi encontrada vacina/as");
            alert.showAndWait();
        }
        rs.close();
        pstmt.close();

        return vacinas;
    }

    public boolean remover(Vacina vacina) throws SQLException {

        boolean retorno = false;

        String sqlUpdate = "UPDATE vacina SET situacao = 'INATIVO' WHERE codigo = ? AND nome = ? AND descricao = ?;";
        PreparedStatement pstmtUpd = conexao.prepareStatement(sqlUpdate);

        pstmtUpd.setLong(1, vacina.getCodigo());
        pstmtUpd.setString(2, vacina.getNome());
        pstmtUpd.setString(3, vacina.getDescricao());

        int resultado = pstmtUpd.executeUpdate();

        if (resultado == 1) {
            System.out.println("Remocao da vacina executada com sucesso");
            retorno = true;
        } else {
            System.out.println("Erro removendo a vacina com codigo: " + vacina.getCodigo());
        }

        pstmtUpd.close();

        return retorno;
    }

    public boolean atualizar(Vacina vacina) throws SQLException {

        boolean retorno = false;

        String sqlUpdate = "UPDATE vacina SET nome = ?, descricao = ? WHERE codigo = ?;";
        PreparedStatement pstmtUpd = conexao.prepareStatement(sqlUpdate);
        pstmtUpd.setLong(3, vacina.getCodigo());
        pstmtUpd.setString(1, vacina.getNome());
        pstmtUpd.setString(2, vacina.getDescricao());

        int resultado = pstmtUpd.executeUpdate();
        if (resultado == 1) {
            System.out.println("Alteracao da vacina executada com sucesso");
            retorno = true;
        } else {
            System.out.println("Erro alterando a vacina com codigo: " + vacina.getCodigo());
        }
        pstmtUpd.close();

        return retorno;
    }

    public List<Vacina> buscarTodas() throws SQLException {
        Vacina v;
        List<Vacina> vacinas = new ArrayList<>();

        String sql = "SELECT * FROM vacina WHERE situacao = 'ATIVO';";
        Statement stmt = conexao.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            v = new Vacina(rs.getLong(1), rs.getString(2), rs.getString(3));
            vacinas.add(v);
        }
        rs.close();
        stmt.close();

        return vacinas;
    }
}
