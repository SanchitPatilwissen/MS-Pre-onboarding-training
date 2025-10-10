import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Supplier;

interface UserNameGenerator{
	String generateUserName(String firstName, String lastName, LocalDate dob, String id);
}

public class Question1{
	public static List<Employee> input() {
		List<Employee> employees = new ArrayList<Employee> ();
		
    		Scanner sc = new Scanner(System.in);

    		System.out.print("Enter the number of employees: ");
    		int n = sc.nextInt();
    		sc.nextLine(); // Consume leftover newline

    		for (int i = 0; i < n; i++) {
        		System.out.println("Enter details for employee #" + (i + 1));

        		System.out.print("First name: ");
        		String firstName = sc.nextLine();

        		System.out.print("Last name: ");
        		String lastName = sc.nextLine();

        		System.out.print("ID: ");
        		String id = sc.nextLine();

        		System.out.print("Date of birth (yyyy-mm-dd): ");
        		String dobInput = sc.nextLine();
        		LocalDate dateOfBirth = LocalDate.parse(dobInput); // Expects input like 1990-05-15

        		System.out.print("Salary: ");
        		String salaryStr = sc.nextLine();
        		BigDecimal salary = new BigDecimal(salaryStr);

        		System.out.print("Department: ");
        		String dept = sc.nextLine();

        		// Create and add employee
        		Employee emp = new Employee(firstName, lastName, id, dateOfBirth, salary, dept);
        		employees.add(emp);

        		System.out.println("Employee added.\n");
    		}

		System.out.println("---------------------------All Employees Added Successfully---------------------------\n");

		return employees;
	}

	public static void question1(List<Employee> employees){
		// Predicate<Employee> greaterThan2000 = emp -> emp.getSalary().compareTo(new BigDecimal("2000")) > 0;
		System.out.print("Enter the base amount of salary you want to see : ");
		String baseSalary = new Scanner(System.in).nextLine();
		BiPredicate<Employee, String> greaterThan = (emp, amt) -> emp.getSalary().compareTo(new BigDecimal(amt))>0;
		Consumer<Employee> printerFunction = (value) -> System.out.println(value);
		employees.forEach((employee) -> {
			if(greaterThan.test(employee, baseSalary)){
				printerFunction.accept(employee);
			}
		});
	}

	public static void question2(List<Employee> employees, List<User> users){
		Supplier<String> passwordGenerator = () -> 	PasswordGenerator.generatePassword(16);
		Consumer<User> printerFunction2 = (value) -> System.out.println(value);

		employees.forEach((employee)->{
			User u = employee.convertToUser();
			u.setPassword(passwordGenerator.get());
			printerFunction2.accept(u);
			users.add(u);
		});
	}

	public static void sortByDOB(List<Employee> employees){
		System.out.println("Sorting Employees based on Salary in descending order and Date of Birth in ascending order : \n");

		employees.sort((e1, e2) -> {
			if(e1.getDateOfBirth().getMonthValue() > e2.getDateOfBirth().getMonthValue()){
				return 1;
			} else
				return -1;
			
		});
	}

	public static void runMultithreadingDemo(List<Employee> employees, List<User> users){
		System.out.println("Multithreading using Lambda : \n");

		Thread employeeThread = new Thread(() -> {
			employees.forEach((employee) -> {
				System.out.println(employee);
			});
		});
		Thread userThread = new Thread(()->{
			users.forEach((user)->{
				System.out.println(user);
			});
		});
		
		employeeThread.start();
		userThread.start();

		System.out.println("\n---------------------------------Both Threads have finished execution--------------------------------------------------\n");
	} 

	public static void main(String[] args){

		// Question 1
				
		List<Employee> employees = input();
		List<User> users = new ArrayList<User>();

		question1(employees);

		System.out.println("\n------------------------------------------------------------------------------------------\n");

		// Question 2

		question2(employees, users);
		
		System.out.println("\n------------------------------------------------------------------------------------------\n");
		
		// Sort Logic Lambda - 1

		sortByDOB(employees);

		// Creating 2 threads to show users and employees respectively
		runMultithreadingDemo(employees, users);
	}
}