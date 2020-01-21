import java.util.ArrayList;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class ContactsManagerJFX implements IContactRegistrable, IContactView {

	private ArrayList<IContactListener> listeners = new ArrayList<IContactListener>();

	// Main pane
	private GridPane mainPane = new GridPane();

	// First pane
	private GridPane pane1 = new GridPane();
	private Label firstNameLabel = new Label("first name: ");
	private TextField firstNameText = new TextField();
	private Label lastNameLabel = new Label("last name: ");
	private TextField lastNameText = new TextField();
	private Label phoneNumberLabel = new Label("phone number: ");
	private TextField phoneNumberText = new TextField();

	// Second pane
	private GridPane pane2 = new GridPane();
	private Button createButton = new Button("create");
	private Button updateButton = new Button("update");

	// Third pane
	private GridPane pane3 = new GridPane();
	// Left pane
	private GridPane leftPane = new GridPane();
	private Button previousButton = new Button("<");
	private Button firstButton = new Button("first");
	// Center pane
	private GridPane centerPane = new GridPane();
	private Label firstNameLabel2 = new Label("first name: ");
	private Label emptyLabel1 = new Label();
	private Label lastNameLabel2 = new Label("last name: ");
	private Label emptyLabel2 = new Label();
	private Label phoneNumberLabel2 = new Label("phone number: ");
	private Label emptyLabel3 = new Label();

	// Right pane
	private GridPane rightPane = new GridPane();
	private Button nextButton = new Button(">");
	private Button lastButton = new Button("last");

	// Fourth pane
	private GridPane pane4 = new GridPane();
	private Button editButton = new Button("EDIT");

	// Fifth pane
	private GridPane pane5 = new GridPane();
	private Label filePathLabel = new Label("file path: ");

	// Sixth pane
	private GridPane pane6 = new GridPane();
	private ComboBox<String> formatCombo = new ComboBox<>();
	private Button exportButton = new Button("export");
	private TextField filePathText = new TextField();

	// Seventh pane
	private GridPane pane7 = new GridPane();
	private Button loadButton = new Button("load");

	// Eighth pane
	private GridPane pane8 = new GridPane();
	private ComboBox<String> sortCombo = new ComboBox<>();
	private ComboBox<String> fieldCombo = new ComboBox<>();
	private ComboBox<String> orderCombo = new ComboBox<>();
	private Button sortButton = new Button("sort");

	// Ninth pane
	private GridPane pane9 = new GridPane();
	private ComboBox<String> orderCombo2 = new ComboBox<>();
	private Button showButton = new Button("show");

	public ContactsManagerJFX(Stage primaryStage) {

		// Setting action to buttons
		nextButton.setOnAction(e -> moveNext());
		previousButton.setOnAction(e -> movePrevious());
		lastButton.setOnAction(e -> moveLast());
		firstButton.setOnAction(e -> moveFirst());
		createButton
				.setOnAction(e -> create(firstNameText.getText(), lastNameText.getText(), phoneNumberText.getText()));
		updateButton
				.setOnAction(e -> update(firstNameText.getText(), lastNameText.getText(), phoneNumberText.getText()));
		editButton.setOnAction(e -> edit(emptyLabel1.getText(), emptyLabel2.getText(), emptyLabel3.getText()));
		loadButton.setOnAction(e -> load(formatCombo.getSelectionModel().getSelectedItem(), filePathText.getText()));
		exportButton.setOnAction(e -> export(formatCombo.getSelectionModel().getSelectedItem(), emptyLabel1.getText(),
				emptyLabel2.getText(), emptyLabel3.getText()));
		sortButton.setOnAction(e -> sort(sortCombo.getSelectionModel().getSelectedItem(),
				fieldCombo.getSelectionModel().getSelectedItem(), orderCombo.getSelectionModel().getSelectedItem()));
		showButton.setOnAction(e -> show(orderCombo2.getSelectionModel().getSelectedItem()));

		// Adding to pane1
		pane1.setAlignment(Pos.TOP_CENTER);
		pane1.setPadding(new Insets(10, 10, 10, 10));
		pane1.setVgap(7);
		pane1.add(firstNameLabel, 0, 0);
		pane1.add(firstNameText, 1, 0);
		pane1.add(lastNameLabel, 0, 1);
		pane1.add(lastNameText, 1, 1);
		pane1.add(phoneNumberLabel, 0, 2);
		pane1.add(phoneNumberText, 1, 2);

		// Adding to pane2
		pane2.setPadding(new Insets(0, 0, 0, 136));
		pane2.setHgap(130);
		pane2.setVgap(15);
		pane2.add(createButton, 0, 0);
		pane2.add(updateButton, 1, 0);
		updateButton.setDisable(true);

		// Adding to pane3
		pane3.setPadding(new Insets(30, 10, 10, 10));
		pane3.setHgap(10);
		// Left
		pane3.add(leftPane, 0, 0);
		leftPane.add(previousButton, 0, 0);
		leftPane.add(firstButton, 0, 1);
		// Center
		emptyLabel1.setFont(Font.font("Ariel", FontWeight.BOLD, 15));
		emptyLabel2.setFont(Font.font("Ariel", FontWeight.BOLD, 15));
		emptyLabel3.setFont(Font.font("Ariel", FontWeight.BOLD, 15));
		pane3.add(centerPane, 1, 0);
		centerPane.setHgap(100);
		centerPane.setPrefSize(380, 380);
		centerPane.add(firstNameLabel2, 0, 0);
		centerPane.add(emptyLabel1, 1, 0);
		centerPane.add(lastNameLabel2, 0, 1);
		centerPane.add(emptyLabel2, 1, 1);
		centerPane.add(phoneNumberLabel2, 0, 2);
		centerPane.add(emptyLabel3, 1, 2);
		// Right
		pane3.add(rightPane, 2, 0);
		rightPane.add(nextButton, 0, 0);
		rightPane.add(lastButton, 0, 1);

		// Adding to pane4
		pane4.setPadding(new Insets(10, 10, 10, 60));
		pane4.add(editButton, 0, 0);

		// Adding to pane5
		pane5.setPadding(new Insets(10, 10, 10, 335));
		pane5.add(filePathLabel, 0, 0);

		// Adding to pane6
		pane6.add(formatCombo, 0, 0);
		pane6.add(exportButton, 1, 0);
		pane6.add(filePathText, 13, 0);
		pane6.setHgap(15);
		formatCombo.getItems().addAll("txt", "obj.dat", "byte.dat");
		formatCombo.setValue("txt");
		filePathText.setPromptText("Enter file name. ");

		// Adding to pane7
		pane7.setPadding(new Insets(10, 10, 10, 10));
		pane7.setHgap(61.5);
		pane7.add(loadButton, 7, 0);

		// Adding to pane8
		pane8.setPadding(new Insets(20, 10, 10, 0));
		pane8.add(sortCombo, 0, 0);
		pane8.add(fieldCombo, 2, 0);
		pane8.add(orderCombo, 4, 0);
		pane8.add(sortButton, 6, 0);
		pane8.setHgap(20);
		sortCombo.getItems().addAll("sort-field", "sort-count", "reverse");
		sortCombo.setValue("sort-field");
		fieldCombo.getItems().addAll("FIRST_NAME_FIELD", "LAST_NAME_FIELD", "PHONE_NUMBER_FIELD");
		fieldCombo.setValue("FIRST_NAME_FIELD");
		orderCombo.getItems().addAll("asc", "desc");
		orderCombo.setValue("asc");

		// Adding to pane9
		pane9.setPadding(new Insets(40.5, 10, 10, 0));
		pane9.add(orderCombo2, 0, 0);
		pane9.add(showButton, 4, 0);
		pane9.setHgap(35);
		orderCombo2.getItems().addAll("asc", "desc");
		orderCombo2.setValue("asc");

		// Adding to mainPane
		mainPane.setAlignment(Pos.TOP_CENTER);
		mainPane.add(pane1, 0, 0);
		mainPane.add(pane2, 0, 1);
		mainPane.add(pane3, 0, 2);
		mainPane.add(pane4, 0, 3);
		mainPane.add(pane5, 0, 4);
		mainPane.add(pane6, 0, 5);
		mainPane.add(pane7, 0, 6);
		mainPane.add(pane8, 0, 7);
		mainPane.add(pane9, 0, 8);

		Scene scene = new Scene(mainPane, 500, 500);
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.setTitle("Contacts manager jfx");
		primaryStage.show();
		primaryStage.setAlwaysOnTop(true);

	}

	private void moveNext() {
		for (IContactListener listener : listeners) {
			listener.goNext();
		}
	}

	private void movePrevious() {
		for (IContactListener listener : listeners) {
			listener.goPrevious();
		}
	}

	private void moveLast() {
		for (IContactListener listener : listeners) {
			listener.goLast();
		}
	}

	private void moveFirst() {
		for (IContactListener listener : listeners) {
			listener.goFirst();
		}
	}

	private void create(String firstName, String lastName, String phoneNumber) {
		for (IContactListener listener : listeners) {
			listener.create(firstName, lastName, phoneNumber);
		}
	}

	private void load(String format, String fileName) {
		for (IContactListener listener : listeners) {
			listener.load(format, fileName);
		}
	}

	private void export(String format, String firstName, String lastName, String phoneNumber) {
		for (IContactListener listener : listeners) {
			listener.export(format, firstName, lastName, phoneNumber);
		}
	}

	private void update(String firstName, String lastName, String phoneNumber) {
		for (IContactListener listener : listeners) {
			listener.update(firstName, lastName, phoneNumber);
		}
	}

	private void edit(String firstName, String lastName, String phoneNumber) {
		for (IContactListener listener : listeners) {
			listener.edit(firstName, lastName, phoneNumber);
		}
	}

	private void sort(String option1, String option2, String option3) {
		for (IContactListener listener : listeners) {
			listener.sort(option1, option2, option3);
		}
	}

	private void show(String order) {
		for (IContactListener listener : listeners) {
			listener.show(order);
		}
	}

	@Override
	public void setLabels(IContact c, Color color) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				if (c != null) {
					String[] data = c.getUiData();
					emptyLabel1.setTextFill(color);
					emptyLabel2.setTextFill(color);
					emptyLabel3.setTextFill(color);
					
					emptyLabel1.setText(data[1]);
					emptyLabel2.setText(data[2]);
					emptyLabel3.setText(data[3]);
				}
			}
		});
	}

	@Override
	public void setText(String firstName, String lastName, String phoneNumber, Color color) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				emptyLabel1.setTextFill(color);
				emptyLabel2.setTextFill(color);
				emptyLabel3.setTextFill(color);
				
				emptyLabel1.setText(firstName);
				emptyLabel2.setText(lastName);
				emptyLabel3.setText(phoneNumber);
			}
		});

	}

	@Override
	public void registerListener(IContactListener iContactListener) {
		listeners.add(iContactListener);
	}

	@Override
	public void setTextField(String firstName, String lastName, String phoneNumber) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				firstNameText.setText(firstName);
				lastNameText.setText(lastName);
				phoneNumberText.setText(phoneNumber);
			}
		});

	}

	@Override
	public void enableUpdate() {
		updateButton.setDisable(false);
	}

	@Override
	public void disableUpdate() {
		updateButton.setDisable(true);

	}

}
