import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ContactsManagerFrame extends JFrame implements IContactRegistrable, IContactView {
	public static final int TXT_SIZE = 21;
	private static final long serialVersionUID = 1L;

	private ArrayList<IContactListener> listeners = new ArrayList<IContactListener>();

	// north panel
	JPanel panel1 = new JPanel(new GridLayout(4, 2, 30, 30));

	private JLabel firstNameLabel = new JLabel("First name: ");
	private JTextField firstNameText = new JTextField(TXT_SIZE);

	private JLabel lastNameLabel = new JLabel("Last name: ");
	private JTextField lastNameText = new JTextField(TXT_SIZE);

	private JLabel phoneNumberLabel = new JLabel("Phone number ");
	private JTextField phoneNumberText = new JTextField(TXT_SIZE);

	private JButton createButton = new JButton("Create");
	private JButton updateButton = new JButton("Update");

	// west panel
	private JPanel panel2 = new JPanel(new GridLayout(2, 1, 15, 15));
	private JButton leftButton = new JButton("<");
	private JButton firstButton = new JButton("First");

	// center panel
	private JPanel panel3 = new JPanel(new GridLayout(4, 2, 40, 40));
	private JLabel firstNameLabel2 = new JLabel("First name: ");
	private JLabel emptyLabel1 = new JLabel();
	private JLabel lastNameLabel2 = new JLabel("Last name: ");
	private JLabel emptyLabel2 = new JLabel();
	private JLabel phoneNumberLabel2 = new JLabel("Phone number ");
	private JLabel emptyLabel3 = new JLabel();
	private JButton editButton = new JButton("Edit contact");

	// east panel
	private JPanel panel4 = new JPanel(new GridLayout(2, 1, 15, 15));
	private JButton rightButton = new JButton(">");
	private JButton lastButton = new JButton("Last");

	// south panel
	private JPanel panel5 = new JPanel(new GridLayout(1, 2, 30, 34));
	private JPanel leftPanel = new JPanel(new GridLayout(1, 2));
	// left
	private JPanel comboPanel = new JPanel(new FlowLayout(0, 5, 65));
	private JPanel exportPanel = new JPanel(new GridLayout(1, 1, 50, 5));
	private JComboBox<String> comboBox = new JComboBox<>(new String[] { "txt", "obj.dat", "byte.dat" });

	private JButton exportButton = new JButton("Export");
	// right
	private JPanel rightPanel = new JPanel(new GridLayout(3, 1, 50, 45));
	private JLabel filePath = new JLabel("file path: ");
	private JTextField loadFileText = new JTextField(TXT_SIZE);
	private JButton loadButton = new JButton("load file");

	// lower panel
	private JPanel lowerPanel = new JPanel(new BorderLayout(5, 5));

	private JPanel panel6 = new JPanel(new GridLayout(2, 1, 10, 10));
	private JPanel inerTopPanel = new JPanel(new GridLayout(1, 4, 40, 40));
	private JComboBox<String> comboBox2 = new JComboBox<>(new String[] { "sort-field", "sort-count", "reverse" });
	private JComboBox<String> comboBox3 = new JComboBox<>(
			new String[] { "FIRST_NAME_FIELD", "LAST_NAME_FIELD", "PHONE_NUMBER_FIELD" });
	private JComboBox<String> comboBox4 = new JComboBox<>(new String[] { "asc", "desc" });
	private JButton sortButton = new JButton("SORT");

	private JPanel inerDownPanel = new JPanel(new GridLayout(1, 2, 40, 40));
	private JComboBox<String> comboBox5 = new JComboBox<>(new String[] { "asc", "desc" });
	private JButton ShowButton = new JButton("SHOW");

	public ContactsManagerFrame() {
		super("Contacts manager");
		setLayout(new BorderLayout(5, 5));

		// Adding action listener
		createButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				modify(e, firstNameText.getText(), lastNameText.getText(), phoneNumberText.getText());
			}
		});

		rightButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				move(e);

			}
		});

		leftButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				move(e);

			}
		});

		lastButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				move(e);
			}
		});

		firstButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				move(e);
			}
		});

		editButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				modify(e, emptyLabel1.getText(), emptyLabel2.getText(), emptyLabel3.getText());

			}
		});

		updateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				modify(e, firstNameText.getText(), lastNameText.getText(), phoneNumberText.getText());
			}
		});

		exportButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String format = (String) comboBox.getSelectedItem();
				export(format, emptyLabel1.getText(), emptyLabel2.getText(), emptyLabel3.getText());
			}
		});

		loadButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String format = (String) comboBox.getSelectedItem();
				String fileName = loadFileText.getText().trim();
				loadContact(format, fileName);
			}
		});

		sortButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String box1 = (String) comboBox2.getSelectedItem();
				String box2 = (String) comboBox3.getSelectedItem();
				String box3 = (String) comboBox4.getSelectedItem();

				sort(box1, box2, box3);

			}
		});

		ShowButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String order = (String) comboBox5.getSelectedItem();
				show(order);
			}

		});

		// Adding to panel1
		panel1.add(firstNameLabel);
		panel1.add(firstNameText);
		panel1.add(lastNameLabel);
		panel1.add(lastNameText);
		panel1.add(phoneNumberLabel);
		panel1.add(phoneNumberText);
		panel1.add(createButton);
		panel1.add(updateButton);
		updateButton.setEnabled(false);

		// Adding to panel2
		panel2.add(leftButton);
		panel2.add(firstButton);

		// Adding to panel3
		panel3.add(firstNameLabel2);
		panel3.add(emptyLabel1);
		panel3.add(lastNameLabel2);
		panel3.add(emptyLabel2);
		panel3.add(phoneNumberLabel2);
		panel3.add(emptyLabel3);
		panel3.add(editButton);

		// Adding to panel4
		panel4.add(rightButton);
		panel4.add(lastButton);

		// Adding to panel5
		comboPanel.add(comboBox);
		exportPanel.add(exportButton);

		rightPanel.add(filePath);
		rightPanel.add(loadFileText);
		rightPanel.add(loadButton);

		leftPanel.add(comboPanel);
		leftPanel.add(exportPanel);

		panel5.add(leftPanel);
		panel5.add(rightPanel);

		inerTopPanel.add(comboBox2);
		inerTopPanel.add(comboBox3);
		inerTopPanel.add(comboBox4);
		inerTopPanel.add(sortButton);
		panel6.add(inerTopPanel);

		inerDownPanel.add(comboBox5);
		inerDownPanel.add(ShowButton);
		panel6.add(inerDownPanel);

		lowerPanel.add(panel5);
		lowerPanel.add(panel6, BorderLayout.SOUTH);

		add(panel1, BorderLayout.NORTH);
		add(panel2, BorderLayout.WEST);
		add(panel3, BorderLayout.CENTER);
		add(panel4, BorderLayout.EAST);
		add(lowerPanel, BorderLayout.SOUTH);

		generalSettings();

		// init
		setVisible(true);

	}

	public void init() {

	}

	public void setText(String firstName, String lastName, String phoneNumber, javafx.scene.paint.Color color) {
		emptyLabel1.setText(firstName);
		emptyLabel2.setText(lastName);
		emptyLabel3.setText(phoneNumber);
	}

	public void setLabels(IContact c, javafx.scene.paint.Color color) {
		if (c != null) {
			String[] data = c.getUiData();
			emptyLabel1.setText(data[1]);
			emptyLabel2.setText(data[2]);
			emptyLabel3.setText(data[3]);
		}
	}

	// Creating the frame
	public void generalSettings() {
		setSize(510, 670);
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);

	}

	@Override
	public void registerListener(IContactListener iContactListener) {
		listeners.add(iContactListener);

	}

	private void move(ActionEvent e) {

		for (IContactListener listener : listeners) {
			if (e.getSource() == rightButton)
				listener.goNext();
			else if (e.getSource() == leftButton)
				listener.goPrevious();
			else if (e.getSource() == lastButton)
				listener.goLast();
			else if (e.getSource() == firstButton)
				listener.goFirst();

		}
	}

	private void modify(ActionEvent e, String firstName, String lastName, String phoneNumber) {
		for (IContactListener listener : listeners) {
			if (e.getSource() == createButton)
				listener.create(firstName, lastName, phoneNumber);
			else if (e.getSource() == updateButton)
				listener.update(firstName, lastName, phoneNumber);
			else if (e.getSource() == editButton)
				listener.edit(firstName, lastName, phoneNumber);
		}
	}

	private void export(String format, String firstName, String lastName, String phoneNumber) {
		for (IContactListener listener : listeners) {
			listener.export(format, firstName, lastName, phoneNumber);
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

	private void loadContact(String format, String fileName) {
		for (IContactListener listener : listeners) {
			listener.load(format, fileName);
		}
	}

	@Override
	public void setTextField(String firstName, String lastName, String phoneNumber) {
		firstNameText.setText(firstName);
		lastNameText.setText(lastName);
		phoneNumberText.setText(phoneNumber);

	}

	@Override
	public void enableUpdate() {
		updateButton.setEnabled(true);
	}

	@Override
	public void disableUpdate() {
		updateButton.setEnabled(false);
	}

}
