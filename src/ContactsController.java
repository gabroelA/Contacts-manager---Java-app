import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.Timer;

import javafx.scene.paint.Color;

public class ContactsController implements IContactListener {

	private ArrayList<IContactView> views = new ArrayList<IContactView>();
	private ContactsManager cm;

	private String order;
	private Timer timer = new Timer(400, new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			Color color;
			try {
				if (order.equals("asc")) {
					IContact c = cm.moveNext();
					color = myColors.FORWARD.getMyColor();
					for (IContactView view : views) {
						view.setLabels(c, color);
					}
					if (!cm.hasNext()) {
						timer.stop();
						cm.movePrevious();
					}

				} else {
					IContact c = cm.movePrevious();
					color = myColors.BACWARD.getMyColor();
					for (IContactView view : views) {
						view.setLabels(c, color);
					}
					if (!cm.hasPrevious()) {
						timer.stop();
						cm.moveNext();
					}
				}
			} catch (NumberFormatException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();

			}

		}
	});

	public ContactsController(ContactsManager contactsManager) {
		this.cm = contactsManager;
		registerIfPossible(this.cm);

	}

	public void registerIfPossible(Object o) {
		if (o instanceof IContactRegistrable) {
			IContactRegistrable registrable = (IContactRegistrable) o;
			registrable.registerListener(this);
		}

	}

	public void addView(IContactView view) {
		this.views.add(view);
		registerIfPossible(view);

		// At the beginning: Display the first record if exists
		try {
			Color color = myColors.DEFAULT.getMyColor();
			view.setLabels(cm.getCurrentContact(), color);
			if (cm.canMovePrevious())
				cm.movePrevious();
		} catch (NumberFormatException | IOException e) {
			System.out.println("Error");
		}

	}

	@Override
	public void create(String firstName, String lastName, String phoneNumber) {
		try {
			Color color = myColors.NEW.getMyColor();
			for (IContactView view : views) {
				view.setText(firstName, lastName, phoneNumber, color);
				view.disableUpdate();
			}
			cm.createContact(firstName, lastName, phoneNumber);
		} catch (IOException e) {
			System.out.println("Error");
		}

	}

	@Override
	public void goNext() {
		if (cm.canMoveNext()) {
			IContact c = cm.moveNext();
			Color color = myColors.FORWARD.getMyColor();
			for (IContactView view : views) {
				view.setLabels(c, color);
				view.disableUpdate();
			}
		} else if (cm.isEmpty())
			System.out.println("There are no contacts");
		else
			System.out.println("This is the last contact");

	}

	@Override
	public void goPrevious() {
		if (cm.canMovePrevious()) {
			IContact c = cm.movePrevious();
			Color color = myColors.BACWARD.getMyColor();
			for (IContactView view : views) {
				view.setLabels(c, color);
				view.disableUpdate();
			}
		} else if (cm.isEmpty())
			System.out.println("There are no contacts");
		else
			System.out.println("This is the First contact");

	}

	@Override
	public void goLast() {
		if (cm.canMoveLast()) {
			IContact c = cm.moveLast();
			Color color = myColors.FORWARD.getMyColor();
			for (IContactView view : views) {
				view.setLabels(c, color);
				view.disableUpdate();
			}
		} else if (cm.isEmpty())
			System.out.println("There are no contacts");
		else
			System.out.println("This is the last contact");

	}

	@Override
	public void goFirst() {
		if (cm.canMoveFirst()) {
			IContact c = cm.moveFirst();
			Color color = myColors.BACWARD.getMyColor();
			for (IContactView view : views) {
				view.setLabels(c, color);
				view.disableUpdate();
			}
		} else if (cm.isEmpty())
			System.out.println("There are no contacts");
		else
			System.out.println("This is the first contact");

	}

	@Override
	public void update(String firstName, String lastName, String phoneNumber) {
		try {
			cm.updateContact(firstName, lastName, phoneNumber);
			Color color = myColors.NEW.getMyColor();
			for (IContactView view : views) {
				view.setText(firstName, lastName, phoneNumber, color);
				view.disableUpdate();
			}

		} catch (IOException ex) {
			System.out.println("Error");

		}

	}

	@Override
	public void export(String format, String firstName, String lastName, String phoneNumber) {
		try {
			cm.preExport(format, firstName, lastName, phoneNumber);
		} catch (IOException ex) {
			System.out.println("Error");
		}

	}

	@Override
	public void sort(String option1, String option2, String option3) {
		cm.sort(option1, option2, option3);
		setCurrentContact();
	}

	@Override
	public void setCurrentContact() {
		try {
			IContact c = cm.getCurrentContact();
			Color color = myColors.DEFAULT.getMyColor();
			for (IContactView view : views) {
				view.setLabels(c, color);
				view.disableUpdate();
			}
		} catch (NumberFormatException | IOException e) {
			System.out.println("Error");
		}

	}

	@Override
	public void show(String order) {
		for (IContactView view : views) {
			view.disableUpdate();
		}
		this.order = order;
		if (!cm.isEmpty()) {
			timer.start();
			cm.show(order);
		} else
			System.out.println("Cant show");

	}

	@Override
	public void load(String format, String fileName) {
		String firstName;
		String lastName;
		String phoneNumber;

		try {
			// txt
			if (format.equals("txt")) {
				String path = fileName + "." + format;
				Scanner input = new Scanner(new File(path));

				// skipping id
				input.nextLine();

				firstName = input.nextLine().trim();
				lastName = input.nextLine().trim();
				phoneNumber = input.nextLine().trim();

				for (IContactView view : views) {
					view.setTextField(firstName, lastName, phoneNumber);
				}

				input.close();
			}
			// obj.dat
			else if (format.equals("obj.dat")) {
				String path = fileName + "." + format;
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path));
				String[] details = ((IContact) ois.readObject()).getUiData();
				firstName = details[1];
				lastName = details[2];
				phoneNumber = details[3];
				for (IContactView view : views) {
					view.setTextField(firstName, lastName, phoneNumber);
				}

				ois.close();
			}
			// byte.dat
			else {
				String path = fileName + "." + format;
				DataInputStream dis = new DataInputStream(new FileInputStream(path));

				// skipping id
				dis.readInt();

				firstName = dis.readUTF().trim();
				lastName = dis.readUTF().trim();
				phoneNumber = dis.readUTF().trim();
				for (IContactView view : views) {
					view.setTextField(firstName, lastName, phoneNumber);
				}

				dis.close();
			}
		} catch (FileNotFoundException fnfe) {
			System.out.println("File not found");
		} catch (IOException ioe) {
			System.out.println("Error");
		} catch (ClassNotFoundException cnfe) {
			System.out.println("Error");
		}

	}

	@Override
	public void edit(String firstName, String lastName, String phoneNumber) {
		if (!cm.isEmpty()) {
			for (IContactView view : views) {
				view.enableUpdate();
				view.setTextField(firstName, lastName, phoneNumber);
			}
		} else
			System.out.println("There are no contact to update");

	}
	
	public enum myColors {
		
		DEFAULT(Color.BLACK),
		NEW(Color.BLUE),
		FORWARD(Color.GREEN),
		BACWARD(Color.RED);
		
		private Color color;
		
		private myColors(Color color) {
			this.color = color;
		}
		
		public Color getMyColor(){
			return color;
		}
		
	}
}


