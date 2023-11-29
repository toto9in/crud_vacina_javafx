package javafxvacina;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafxvacina.models.Vacina;
import javafxvacina.models.dao.DAOFactory;
import javafxvacina.models.dao.VacinaDAO;


public class TelaSecundariaController {

    @FXML
    private Button cancelarButton;

    @FXML
    private TextField codigo2TextFIeld;

    @FXML
    private Button criarVacinaButton;

    @FXML
    private TextField descricao2TextField;

    @FXML
    private TextField nome2TextFIeld;

    @FXML
    void cancelarOperacao(ActionEvent event) {
        ((Button)event.getSource()).getScene().getWindow().hide();
    }


    private Vacina vacina;

    private Boolean editarVacina = null;


    public void setVacina(Vacina vacina) {
        this.vacina = vacina;
    }

    public Boolean setEditar(Boolean editar) {
        if(editar) {
            editarVacina = true;
            criarVacinaButton.setText("Editar");
            codigo2TextFIeld.setText(String.valueOf(vacina.getCodigo()));
            nome2TextFIeld.setText(vacina.getNome());
            descricao2TextField.setText(vacina.getDescricao());
        } else {
            editarVacina = false;
            criarVacinaButton.setText("Criar");
            codigo2TextFIeld.clear();
            nome2TextFIeld.clear();
            descricao2TextField.clear();
        }
        return editar;
    }

    private Boolean validarCampos() {
        return !
        !nome2TextFIeld.getText().isEmpty() &&
        !descricao2TextField.getText().isEmpty();
    }

    @FXML
    void criarVacina(ActionEvent event) {
        if (editarVacina != true) {
            if(validarCampos()) {
                vacina = new Vacina(nome2TextFIeld.getText(), descricao2TextField.getText());
    
                //nao precisa necessariamente criar o objeto vacina e salvar os dados nele
                //mas por boas praticas irei pegar os valores do objeto vacina
                DAOFactory daoFactory = new DAOFactory();
                try {
                    daoFactory.abrirConexao();
                    VacinaDAO vacinaDAO = daoFactory.criarVacinaDAO();
                    
                    //salvando a vacina
                    vacinaDAO.gravar(vacina);
                    ((Button)event.getSource()).getScene().getWindow().hide();
    
                } catch (SQLException e) {
                    DAOFactory.mostrarSQLException(e);
                } finally {
                    daoFactory.fecharConexao();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Um dos campos está vazio");
                alert.showAndWait();
            }
        } else {
            if(validarCampos()) {
                vacina = new Vacina(nome2TextFIeld.getText(), descricao2TextField.getText());
                System.out.println(vacina);
                //nao precisa necessariamente criar o objeto vacina e salvar os dados nele
                //mas por boas praticas irei pegar os valores do objeto vacina
                DAOFactory daoFactory = new DAOFactory();
                try {
                    daoFactory.abrirConexao();
                    VacinaDAO vacinaDAO = daoFactory.criarVacinaDAO();
                    
                    //salvando a vacina
                    vacinaDAO.atualizar(vacina);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("Vacina atualizada com sucesso");
                    alert.showAndWait();
                    ((Button)event.getSource()).getScene().getWindow().hide();
    
                } catch (SQLException e) {
                    DAOFactory.mostrarSQLException(e);
                } finally {
                    
                    daoFactory.fecharConexao();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Um dos campos está vazio");
                alert.showAndWait();
            } 
        }
    }

}
