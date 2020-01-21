import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.Serializable;

public class Contact implements IContact, Serializable {
	private static final long serialVersionUID = 1L;

	public final int ATTRIBUTES = 4;
	public static final  int ID_SIZE = 3;
	public static final  int TXT_SIZE = 21;

	public static final int RECORD_SIZE = (ID_SIZE + TXT_SIZE * 3) * 2;

	private int id;
	private String firstName;
	private String lastName;
	private String phoneNumber;

	public Contact(int id, String firstName, String lastName, String phoneNumber) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;

	}

	@Override
	public void writeObject(RandomAccessFile randomAccessFile) throws IOException {

		FixedLengthStringIO.writeFixedLengthString("" + getId(), ID_SIZE, randomAccessFile);
		FixedLengthStringIO.writeFixedLengthString("" + getFirstName(), TXT_SIZE, randomAccessFile);
		FixedLengthStringIO.writeFixedLengthString("" + getLastName(), TXT_SIZE, randomAccessFile);
		FixedLengthStringIO.writeFixedLengthString("" + getPhoneNumber(), TXT_SIZE, randomAccessFile);

	}

	@Override
	public void export(String format, File file) throws IOException {
		// txt
		if (format.equals("txt")) {
			PrintWriter pw = new PrintWriter(file);
			pw.write(getId() + "\n");
			pw.write(getFirstName() + "\n");
			pw.write(getLastName() + "\n");
			pw.write(getPhoneNumber());
			pw.close();
		}
		// obj.dat
		else if (format.equals("obj.dat")) {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
			oos.writeObject(this);
			oos.close();

		}
		// byte.dat
		else {
			DataOutputStream dos = new DataOutputStream(new FileOutputStream(file));
			dos.writeInt(getId());
			dos.writeUTF(getFirstName());
			dos.writeUTF(getLastName());
			dos.writeUTF(getPhoneNumber());
			dos.close();
		}

	}

	@Override
	public String[] getUiData() {
		String[] str = new String[ATTRIBUTES];
		str[0] = "" + getId();
		str[1] = getFirstName();
		str[2] = getLastName();
		str[3] = getPhoneNumber();
		return str;
	}

	@Override
	public int getObjectSize() {
		return (ID_SIZE + (TXT_SIZE * 3)) * 2;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

}
