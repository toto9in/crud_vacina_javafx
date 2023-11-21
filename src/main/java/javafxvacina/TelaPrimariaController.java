package javafxvacina;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.stage.Stage;
import javafxvacina.models.Vacina;
import javafxvacina.models.dao.DAOFactory;
import javafxvacina.models.dao.VacinaDAO;


public class TelaPrimariaController implements Initializable{

    @FXML
    private TableColumn<Vacina, Long> codigoColuna;

    @FXML
    private TextField codigoTextField;

    @FXML
    private TableColumn<Vacina, String> descricaoColuna;

    @FXML
    private TextField descricaoTextField;

    @FXML
    private Button editarButton;

    @FXML
    private TableColumn<Vacina, String> nomeColuna;

    @FXML
    private TextField nomeTextField;

    @FXML
    private Button novaButton;

    @FXML
    private Button pesquisarButton;

    @FXML
    private Button removerButton;

    @FXML
    private TableView<Vacina> vacinaTableView;

    private Stage stageTelaSecundaria;
    private TelaSecundariaController controllerTelaSecundaria;
    private Vacina vacina;

    List<Vacina> vacinas = null;
    


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("telasecundaria.fxml"));
        Parent root;
        try {
            
            root = loader.load();
            Scene scene = new Scene(root);
            stageTelaSecundaria = new Stage();
            stageTelaSecundaria.setScene(scene);
            stageTelaSecundaria.setTitle("tela 2");
            controllerTelaSecundaria = loader.getController();
            
            // Chama a função buscarTodasVacinas e preenche a tabela com os valores das vacinas dessa lista
            //configura as celulas da tabela
            vacinas = buscarTodasVacinas();
            codigoColuna.setCellValueFactory(new PropertyValueFactory<Vacina, Long>("codigo"));
            nomeColuna.setCellValueFactory(new PropertyValueFactory<Vacina, String>("nome"));
            descricaoColuna.setCellValueFactory(new PropertyValueFactory<Vacina, String>("descricao"));
            vacinaTableView.getItems().addAll(vacinas);
            vacinaTableView.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && (!vacinaTableView.getSelectionModel().isEmpty())) {
                    Vacina selectedVacina = vacinaTableView.getSelectionModel().getSelectedItem();
                    controllerTelaSecundaria.setVacina(selectedVacina);
                    vacina = selectedVacina;
                }
            });
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void abrirTelaCriacao(ActionEvent event) {
        controllerTelaSecundaria.setEditar(false);
        stageTelaSecundaria.showAndWait();
        pesquisarVacina(event);
    }

    @FXML
    void abrirTelaEdicao(ActionEvent event) {
        controllerTelaSecundaria.setEditar(true);
        stageTelaSecundaria.showAndWait();
        pesquisarVacina(event);
    }

    private Boolean validarCampos() {
        return !codigoTextField.getText().isEmpty() &&
        !nomeTextField.getText().isEmpty() &&
        !descricaoTextField.getText().isEmpty();
    }

    @FXML
    void pesquisarVacina(ActionEvent event) {
        if(validarCampos()) {
            DAOFactory daoFactory = new DAOFactory();

            try {
            
                daoFactory.abrirConexao();
                VacinaDAO vacinaDAO = daoFactory.criarVacinaDAO();
                
                Vacina vacina = vacinaDAO.buscar(Long.parseLong(codigoTextField.getText()), nomeTextField.getText(), descricaoTextField.getText());
                System.out.println(vacina);
                if (vacina != null) {
                    codigoColuna.setCellValueFactory(new PropertyValueFactory<Vacina, Long>("codigo"));
                    nomeColuna.setCellValueFactory(new PropertyValueFactory<Vacina, String>("nome"));
                    descricaoColuna.setCellValueFactory(new PropertyValueFactory<Vacina, String>("descricao"));
                    vacinaTableView.getItems().clear(); // clear the table before adding new items
                    List<Vacina> vacinas = new ArrayList<>();
                    vacinas.add(vacina);
                    vacinaTableView.getItems().addAll(vacinas);
                }

            } catch (SQLException e) {
                DAOFactory.mostrarSQLException(e);
            } finally {
                daoFactory.fecharConexao();
            }
        } else {
            vacinaTableView.getItems().clear();
            List<Vacina>vacinas = buscarTodasVacinas();
            codigoColuna.setCellValueFactory(new PropertyValueFactory<Vacina, Long>("codigo"));
            nomeColuna.setCellValueFactory(new PropertyValueFactory<Vacina, String>("nome"));
            descricaoColuna.setCellValueFactory(new PropertyValueFactory<Vacina, String>("descricao"));
            vacinaTableView.getItems().addAll(vacinas);
        }
    }

    @FXML
    void removerVacina(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Remover Vacina");
        alert.setHeaderText("Remover Vacina");
        alert.setContentText("Tem certeza que deseja remover a vacina?");
        alert.showAndWait();
        if (alert.getResult().getText().equals("OK")) {
            DAOFactory daoFactory = new DAOFactory();
            try {
                daoFactory.abrirConexao();
                VacinaDAO vacinaDAO = daoFactory.criarVacinaDAO();
                vacinaDAO.remover(vacina);
                vacinaTableView.getItems().remove(vacina);
            } catch (SQLException e) {
                DAOFactory.mostrarSQLException(e);
            } finally {
                daoFactory.fecharConexao();
                
            }
        }
       
    }

    protected static List<Vacina> buscarTodasVacinas() {
        DAOFactory daoFactory = new DAOFactory();
        try {
            daoFactory.abrirConexao();
            VacinaDAO vacinaDAO = daoFactory.criarVacinaDAO();
            List<Vacina> vacinas = vacinaDAO.buscarTodas();

            for (Vacina vacina : vacinas) {
                System.out.println(vacina);
            }
            return vacinas;
        } catch (SQLException e) {
            DAOFactory.mostrarSQLException(e);
        } finally {
            daoFactory.fecharConexao();
        }
        return null;

    }

}
