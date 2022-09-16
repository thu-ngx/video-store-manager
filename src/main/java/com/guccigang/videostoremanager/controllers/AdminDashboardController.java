package com.guccigang.videostoremanager.controllers;

import com.guccigang.videostoremanager.auth.Account;
import com.guccigang.videostoremanager.core.ApplicationCore;
import com.guccigang.videostoremanager.items.Item;
import com.guccigang.videostoremanager.transactions.Transaction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class AdminDashboardController implements Initializable {

    @FXML
    private TextField accountMngSearchBar;

    @FXML
    private AnchorPane background;

    @FXML
    private Button btnAccount;

    @FXML
    private Button btnAccountMngSearch;

    @FXML
    private Button btnItemSearch;

    @FXML
    private Button btnItems;

    @FXML
    private Button btnLogOut;

    @FXML
    private Button btnSetting;

    @FXML
    private Button btnTransactionSearch;

    @FXML
    private Button btnTransactions;

    @FXML
    private ComboBox<String> cbAccountMng;

    @FXML
    private ComboBox<String> cbItem;

    @FXML
    private ComboBox<String> cbTransactions;

    @FXML
    private TextField itemSearchBar;

    @FXML
    private Label lbiStatus;

    @FXML
    private Label lbiStatusMini;

    @FXML
    private GridPane pnlAccount;

    @FXML
    private GridPane pnlItems;

    @FXML
    private GridPane pnlSetting;

    @FXML
    private Pane pnlStatus;

    @FXML
    private GridPane pnlTransactions;

    @FXML
    private VBox settingBackground;

    @FXML
    private HBox settingBackground2;

    @FXML
    private TextField transactionSearchBar;

    @FXML
    private TableView<Item> tableItems = new TableView<>();

    @FXML
    private TableView<Transaction> tableTransactions;

    @FXML
    private TableView<Account> accountsTable = new TableView<>();

    @FXML
    private final TableColumn<Account, String> accountId = new TableColumn<>("ID");

    @FXML
    private final TableColumn<Account, String> accountAddress = new TableColumn<>("Address");

    @FXML
    private final TableColumn<Account, String> accountUsername = new TableColumn<>("Username");

    @FXML
    private final TableColumn<Account, String> accountPassword = new TableColumn<>("Password");

    @FXML
    private final TableColumn<Account, String> accountPhone = new TableColumn<>("Phone");

    @FXML
    private final TableColumn<Account, Integer> accountPoints = new TableColumn<>("Points");

    @FXML
    private final TableColumn<Account, String> accountRole = new TableColumn<>("Role");

    @FXML
    private TableColumn<?, ?> itemGenre;

    @FXML
    private TableColumn<Item, String> itemID = new TableColumn<>("ID");;

    @FXML
    private TableColumn<?, ?> itemLoanType;

    @FXML
    private TableColumn<?, ?> itemRentalFees;

    @FXML
    private TableColumn<?, ?> itemRentalType;

    @FXML
    private TableColumn<?, ?> itemStockStatus;

    @FXML
    private TableColumn<Item, String> itemTitle= new TableColumn<>("Title");

    // Transactions
    @FXML
    private TableColumn<?, ?> transAccID;

    @FXML
    private TableColumn<?, ?> transAccName;

    @FXML
    private TableColumn<?, ?> transItemID;

    @FXML
    private TableColumn<?, ?> transItemName;

    @FXML
    private TableColumn<?, ?> transStatus;

    @FXML
    void search(ActionEvent event) {

    }

    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        cbItem.setItems(FXCollections.observableArrayList("Titles", "IDs", "Display All", "Display Out Of Stock"));
        cbAccountMng.setItems(FXCollections.observableArrayList("All Customers", "Guest", "Regular", "VIP"));

        // Set cell value factories
        accountId.setCellValueFactory(new PropertyValueFactory<>("id"));
        accountAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        accountUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        accountPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        accountPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        accountPoints.setCellValueFactory(new PropertyValueFactory<>("points"));
        accountRole.setCellValueFactory(new PropertyValueFactory<>("role"));

        itemTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        itemID.setCellValueFactory(new PropertyValueFactory<>("id"));

        // Display all accounts
        accountsTable.setItems(getAccounts());

        tableItems.setItems(getItems());

        // Add columns to table
        accountsTable.getColumns().addAll(accountId, accountAddress, accountUsername, accountPassword, accountPhone, accountPoints, accountRole);
        tableItems.getColumns().addAll(itemTitle,itemID);
    }

    private ObservableList<Account> getAccounts() {
        var appCore = ApplicationCore.getInstance();
        var accountManager = appCore.getAccountManager();
        var accounts = accountManager.getAll();

        return FXCollections.observableArrayList(accounts);
    }

    private ObservableList<Item> getItems() {
        var appCore = ApplicationCore.getInstance();
        var itemManager = appCore.getItemManager();
        var items = itemManager.getAll();

        return FXCollections.observableArrayList(items);
    }

    @FXML
    private void handleClick(ActionEvent event) {
        if (event.getSource() == btnAccount) {
            lbiStatusMini.setText("/Menu/Account");
            lbiStatus.setText("Account Management");
            pnlStatus.setBackground(new Background(new BackgroundFill(Color.rgb(201, 198, 193), CornerRadii.EMPTY, Insets.EMPTY)));
            pnlAccount.toFront();
        } else if (event.getSource() == btnItems) {
            lbiStatusMini.setText("/Menu/Items");
            lbiStatus.setText("Items Menu");
            pnlStatus.setBackground(new Background(new BackgroundFill(Color.rgb(201, 198, 193), CornerRadii.EMPTY, Insets.EMPTY)));
            pnlItems.toFront();
        } else if (event.getSource() == btnTransactions) {
            lbiStatusMini.setText("/Menu/Transactions");
            lbiStatus.setText("Transactions");
            pnlStatus.setBackground(new Background(new BackgroundFill(Color.rgb(201, 198, 193), CornerRadii.EMPTY, Insets.EMPTY)));
            pnlTransactions.toFront();
        } else if (event.getSource() == btnLogOut) {
            if (AccountController.showLogoutConfirmation()) {
                var app = ApplicationCore.getInstance();
                var auth = app.getAuthManager();

                // Log the user out
                auth.logout();

                // Show the auth screen
                var manager = app.getSceneManager();
                manager.showScene("auth");
            }
        }
    }
}

