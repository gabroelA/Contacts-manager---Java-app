import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ListIterator;

public class FileListIterator<T extends IContact> implements ListIterator<T> {

	private T contact;
	private RandomAccessFile raf;
	private int currentPosition;

	private boolean nextSelected;

	public FileListIterator(File file) {
		try {
			// Open or create a random access file
			raf = new RandomAccessFile(file, "rw");
			currentPosition = 0;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// simple approach
	@SuppressWarnings({ "unchecked" })
	private T initContactWorkaround(int id, String firstName, String lastName, String phoneNumber) {
		return (T) new Contact(id, firstName, lastName, phoneNumber);
	}

	@Override
	public void add(T t) {
		try {
			String[] data = t.getUiData();
			contact = initContactWorkaround(Integer.parseInt(data[0]), data[1], data[2], data[3]);

			raf.seek(raf.length());

			contact.writeObject(raf);

			raf.seek(currentPosition * Contact.RECORD_SIZE);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean hasNext() {
		try {

			if (currentPosition * Contact.RECORD_SIZE < raf.length())
				return true;

			else
				return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public boolean hasPrevious() {

		if (currentPosition * Contact.RECORD_SIZE - Contact.RECORD_SIZE >= 0)
			return true;
		else
			return false;

	}

	@Override
	public T next() {
		String id = null;
		String firstName = null;
		String lastName = null;
		String phoneNumber = null;

		try {

			raf.seek(currentPosition * Contact.RECORD_SIZE);

			id = FixedLengthStringIO.readFixedLengthString(Contact.ID_SIZE, raf).trim();
			firstName = FixedLengthStringIO.readFixedLengthString(Contact.TXT_SIZE, raf);
			lastName = FixedLengthStringIO.readFixedLengthString(Contact.TXT_SIZE, raf);
			phoneNumber = FixedLengthStringIO.readFixedLengthString(Contact.TXT_SIZE, raf);
			currentPosition++;
			nextSelected = true;

		} catch (NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		contact = initContactWorkaround(Integer.parseInt(id), firstName, lastName, phoneNumber);

		return contact;
	}

	@Override
	public int nextIndex() {
		int index = -1;
		try {
			if (raf.getFilePointer() <= raf.length() - Contact.RECORD_SIZE)
				return (int) (raf.getFilePointer() / contact.getObjectSize());

		} catch (IOException e) {
			System.out.println("Error");
		}
		return index;
	}

	@Override
	public T previous() {
		int id = 0;
		String firstName = null;
		String lastName = null;
		String phoneNumber = null;

		try {
			currentPosition--;
			
			if(currentPosition == -1)
				currentPosition = 0;
			
			raf.seek(currentPosition * Contact.RECORD_SIZE);
			id = Integer.parseInt(FixedLengthStringIO.readFixedLengthString(Contact.ID_SIZE, raf).trim());
			firstName = FixedLengthStringIO.readFixedLengthString(Contact.TXT_SIZE, raf);
			lastName = FixedLengthStringIO.readFixedLengthString(Contact.TXT_SIZE, raf);
			phoneNumber = FixedLengthStringIO.readFixedLengthString(Contact.TXT_SIZE, raf);
			nextSelected = false;

		} catch (NumberFormatException | IOException e) {
			System.out.println("Cant move to Previous");
		}

		contact = initContactWorkaround(id, firstName, lastName, phoneNumber);

		return contact;

	}

	@Override
	public int previousIndex() {
		int index = -1;
		try {
			if (raf.getFilePointer() - Contact.RECORD_SIZE > 0)
				return (int) (raf.getFilePointer() / contact.getObjectSize() - 1);
			else if (raf.getFilePointer() - Contact.RECORD_SIZE == 0)
				return 0;

		} catch (IOException e) {
			System.out.println("Error");
		}
		return index;
	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub

	}

	@Override
	public void set(T t) {

		String[] data = t.getUiData();
		contact = initContactWorkaround(Integer.parseInt(data[0]), data[1], data[2], data[3]);
		try {
			if (nextSelected)
				currentPosition--;

			raf.seek(currentPosition * contact.getObjectSize());
			contact.writeObject(raf);

			if (nextSelected)
				currentPosition++;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void clear(){
		try {
			raf.setLength(0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
