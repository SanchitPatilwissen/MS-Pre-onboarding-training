import java.math.BigDecimal;
import java.time.LocalDate;

public class Employee{
	private String firstName;
	private String lastName;
	private String id;
	private LocalDate dateOfBirth;
	private BigDecimal salary;
	private String dept;

	public Employee(String firstName, String lastName, String id, LocalDate dateOfBirth, BigDecimal salary, String dept){
		this.firstName = firstName;
		this.lastName = lastName;
		this.id = id;
		this.dateOfBirth = dateOfBirth;
		this.salary = salary;
		this.dept = dept;
	}	

	public BigDecimal getSalary(){
		return this.salary;
	}

	public LocalDate getDateOfBirth(){
		return this.dateOfBirth;
	}

	public String getDept(){
		return this.dept;
	}

	public String getId(){
		return this.id;
	}

	public String getFirstName(){
		return this.firstName;
	}

	public String getLastName(){
		return this.lastName;
	}

	public void incrementSalary(int percent){
		this.salary = this.salary.multiply(BigDecimal.valueOf(1+(percent/100.0)));
	}

	public String toString() {
    		return "The employee name is " + firstName + " " + lastName + " with an id of " + id + ", date of birth " + dateOfBirth + ", salary of Rs. " + salary + " and working in " + dept + " department!";
	}
}