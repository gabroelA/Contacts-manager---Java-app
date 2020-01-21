import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Map.Entry;

public class ContactsManager implements IContactRegistrable {

	private ArrayList<IContactListener> listeners = new ArrayList<IContactListener>();

	private Contact c;

	private FileListIterator<IContact> iterator;

	private boolean nextSelected;

	public ContactsManager(String fileName) throws IOException, NullPointerException {
		iterator = new FileListIterator<IContact>(new File(fileName));

	}

	/** Sorting the list */
	public void sort(String box1, String box2, String box3) {

		// Sort by field
		if (box1.equals("sort-field")) {
			sortByField(box2, box3);
		}
		// sort by count
		else if (box1.equals("sort-count")) {
			sortByCount(box2, box3);
		}
		// Sort by reverse
		else {
			reverse();
		}
	}

	public void sortByCount(String field, String order) {
		ArrayList<Contact> list = readContacts();
		int ord;
		if (order.equals("asc"))
			ord = 1;
		else
			ord = -1;

		// Sort comparator
		Comparator<Contact> comp = new Comparator<Contact>() {

			@Override
			public int compare(Contact c1, Contact c2) {

				if (field.equals("FIRST_NAME_FIELD"))
					return c1.getFirstName().compareTo(c2.getFirstName());
				else if (field.equals("LAST_NAME_FIELD"))
					return c1.getLastName().compareTo(c2.getLastName());
				// By phone number
				else
					return c1.getPhoneNumber().compareTo(c2.getPhoneNumber());
			}
		};

		// Sort value
		Comparator<Entry<Contact, Integer>> comp2 = new Comparator<Entry<Contact, Integer>>() {

			@Override
			public int compare(Entry<Contact, Integer> o1, Entry<Contact, Integer> o2) {
				int diff = o1.getValue() - o2.getValue();

				if (diff == 0)
					return 1 * ord;
				else
					return diff * ord;

			}
		};

		Map<Contact, Integer> map = new TreeMap<Contact, Integer>(comp);

		for (Contact contact : list) {

			Integer count = map.get(contact);
			if (count == null) {
				map.put(contact, 1);
			} else {
				map.put(contact, count + 1);
			}
		}
		Set<Entry<Contact, Integer>> ts = new TreeSet<Entry<Contact, Integer>>(comp2);
		for (Entry<Contact, Integer> entry : map.entrySet()) {
			ts.add(entry);
		}
		// Clear the file
		iterator.clear();
		for (Entry<Contact, Integer> entry : ts) {
			iterator.add(entry.getKey());
		}
		// Back to start
		while (iterator.hasPrevious()) {
			iterator.previous();
		}
		nextSelected = false;

	}

	public ArrayList<Contact> readContacts() {
		ArrayList<Contact> list = new ArrayList<>();
		while (iterator.hasPrevious()) {
			iterator.previous();
		}

		while (iterator.hasNext()) {
			list.add((Contact) iterator.next());
		}
		while (iterator.hasPrevious()) {
			iterator.previous();
		}

		nextSelected = false;
		return list;
	}

	public void sortByField(String field, String order) {
		Set<Contact> treeSet = new TreeSet<Contact>(new FieldComperator(field, order));

		// Move to start
		while (iterator.hasPrevious()) {
			iterator.previous();
		}

		// Read all contacts
		while (iterator.hasNext()) {
			treeSet.add((Contact) iterator.next());
		}

		while (iterator.hasPrevious()) {
			iterator.previous();
		}

		iterator.clear();
		for (Contact contact : treeSet) {
			iterator.add(contact);
		}

		while (iterator.hasPrevious()) {
			iterator.previous();
		}

		nextSelected = false;

	}

	public void reverse() {
		Stack<Contact> stack = new Stack<>();

		// Move to start
		while (iterator.hasPrevious()) {
			iterator.previous();
		}

		// Read all contacts
		while (iterator.hasNext()) {
			stack.push((Contact) iterator.next());
		}

		while (iterator.hasPrevious()) {
			iterator.previous();
		}

		while (iterator.hasNext()) {
			iterator.next();
			iterator.set(stack.pop());

		}

		while (iterator.hasPrevious()) {
			iterator.previous();
		}

		nextSelected = false;

	}

	/** get current contact */
	public IContact getCurrentContact() throws NumberFormatException, IOException {
		if (iterator.hasNext()) {
			nextSelected = true;
			return iterator.next();
		} else
			return null;

	}

	/** creating new contact */
	public void createContact(String firstName, String lastName, String phoneNumber) throws IOException {
		iterator.add(new Contact(getFinalId() + 1, firstName, lastName, phoneNumber));
		if (canMoveLast())
			moveLast();
	}

	/** Checks if can move to next contact */
	public boolean canMoveNext() {
		if (!nextSelected) {
			nextSelected = true;
			if (iterator.hasNext()) {
				iterator.next();
			}

			if (iterator.hasNext()) {
				return true;
			} else
				return false;
		} else {
			if (iterator.hasNext()) {
				return true;
			} else
				return false;
		}

	}

	/** Moves to next contact */
	public IContact moveNext() {
		return iterator.next();

	}

	/** Checks if can move to previous contact */
	public boolean canMovePrevious() {
		if (nextSelected) {
			nextSelected = false;
			if (iterator.hasPrevious()) {
				iterator.previous();

			}
			if (iterator.hasPrevious()) {

				return true;
			} else {

				return false;
			}
		} else {
			if (iterator.hasPrevious()) {
				return true;
			} else
				return false;
		}

	}

	/** Moves to previous contact */
	public IContact movePrevious() {
		return iterator.previous();
	}

	/** Checks if can move to last contact */
	public boolean canMoveLast() {
		if (!nextSelected) {
			if (iterator.hasNext()) {
				iterator.next();
				nextSelected = true;
				if (iterator.hasNext()) {
					return true;
				} else
					return false;
			} else
				return false;
		} else {
			if (iterator.hasNext()) {
				return true;
			} else
				return false;
		}
	}

	/** Moves to last contact */
	public IContact moveLast() {
		while (iterator.hasNext()) {
			iterator.next();
		}

		nextSelected = false;
		return iterator.previous();
	}

	/** Checks if can move to first contact */
	public boolean canMoveFirst() {
		if (nextSelected) {
			if (iterator.hasPrevious()) {
				iterator.previous();
				nextSelected = false;
				if (iterator.hasPrevious()) {
					return true;
				} else
					return false;
			} else
				return false;
		} else {
			if (iterator.hasPrevious()) {
				return true;
			} else {
				return false;
			}
		}
	}

	/** Moves to first contact */
	public IContact moveFirst() {
		while (iterator.hasPrevious()) {
			iterator.previous();
		}

		nextSelected = true;
		return iterator.next();

	}

	/** Getting final id of contact */
	public int getFinalId() {
		if (isEmpty()) {
			return 0;
		} else {
			int counter = 0;
			if (!nextSelected) {
				while (iterator.hasNext()) {
					iterator.next();
					counter++;
				}
				c = (Contact) iterator.previous();
				counter--;
				while (counter > 0) {
					iterator.previous();
					counter--;
				}

				return c.getId();
			} else {
				while (iterator.hasNext()) {
					iterator.next();
					counter++;
				}
				c = (Contact) iterator.previous();
				while (counter > 0) {
					iterator.previous();
					counter--;
				}

				iterator.next();
				nextSelected = true;

				return c.getId();
			}
		}
	}

	/** Getting current id of contact */
	public int getCurrentId() {
		if (!nextSelected) {
			c = (Contact) iterator.next();
			iterator.previous();
			return c.getId();
		} else {
			c = (Contact) iterator.previous();
			iterator.next();
			return c.getId();
		}
	}

	/** Updating current contact */
	public void updateContact(String firstName, String lastName, String phoneNumber) throws IOException {
		iterator.set(new Contact(getCurrentId(), firstName, lastName, phoneNumber));
	}

	/** Exporting current contact */
	public void preExport(String format, String firstName, String lastName, String phoneNumber) throws IOException {
		int id = getCurrentId();
		String fileName = id + "." + format;
		File file = new File(fileName);
		c = new Contact(id, firstName, lastName, phoneNumber);
		c.export(format, file);

	}

	/** Checks if the file empty */
	public boolean isEmpty() {
		if (iterator.hasNext() == false && iterator.hasPrevious() == false)
			return true;
		else
			return false;
	}

	public void show(String order) {
		if (order.equals("asc")) {
			moveFirst();
			movePrevious();

			nextSelected = false;
		} else {
			moveLast();
			moveNext();
			nextSelected = true;
		}
	}

	public boolean hasNext() {
		if (iterator.hasNext())
			return true;
		else
			return false;
	}

	public boolean hasPrevious() {
		if (iterator.hasPrevious())
			return true;
		else
			return false;
	}

	@Override
	public void registerListener(IContactListener iContactListener) {
		listeners.add(iContactListener);

	}

}
