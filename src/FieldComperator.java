import java.util.Comparator;

public class FieldComperator implements Comparator<Contact>{
	
	private String field;
	private String order;
	
	public FieldComperator(String field, String order) {
		this.field = field;
		this.order = order;
	}
	
	@Override
	public int compare(Contact c1, Contact c2) {
		int o;
		if(order.equals("asc"))
			o = 1;
		else
			o = -1;
		
		if(field.equals("FIRST_NAME_FIELD")){
			String firstName1 = c1.getFirstName().toLowerCase();
			String firstName2 = c2.getFirstName().toLowerCase();
			
			if(firstName1.compareTo(firstName2) < 0){
				return -1*o;
			}
			else if(firstName1.compareTo(firstName2) == 0){
				return 0;
			}
			else{
				return 1*o;
			}
		}
		
		else if(field.equals("LAST_NAME_FIELD")){
			String lastName1 = c1.getLastName().toLowerCase();
			String lastName2 = c2.getLastName().toLowerCase();
			
			if(lastName1.compareTo(lastName2) < 0){
				return -1*o;
			}
			else if(lastName1.compareTo(lastName2) == 0){
				return 0;
			}
			else{
				return 1*o;
			}
		
		}
		
		// By phone number
		else{
			String phoneNumber1 = c1.getPhoneNumber().toLowerCase();
			String phoneNumber2 = c2.getPhoneNumber().toLowerCase();
			
			if(phoneNumber1.compareTo(phoneNumber2) < 0){
				return -1*o;
			}
			else if(phoneNumber1.compareTo(phoneNumber2) == 0){
				return 0;
			}
			else{
				return 1*o;
			}
		}
		
	}

	
}
