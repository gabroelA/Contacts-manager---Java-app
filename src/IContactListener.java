
public interface IContactListener {
	
	void create(String firstName, String lastName, String phoneNumber);
	
	void goNext();

	void goPrevious();

	void goLast();

	void goFirst();

	void update(String firstName, String lastName, String phoneNumber);

	void export(String format, String firstName, String lastName, String phoneNumber);

	void sort(String option1, String option2, String option3);

	void setCurrentContact();

	void show(String order);

	void load(String format, String fileName);

	void edit(String firstName, String lastName, String phoneNumber);


	
}
