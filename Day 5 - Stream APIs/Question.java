import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.*;

public class Question {
    private static List<Employee> employees = new ArrayList<>();

    static {
        employees.add(new Employee("John", "Doe", "E001", LocalDate.of(1990, 1, 1), new BigDecimal("50000"), "IT"));
        employees.add(new Employee("Jane", "Smith", "E002", LocalDate.of(1985, 5, 15), new BigDecimal("60000"), "HR"));
        employees.add(new Employee("Alice", "Johnson", "E003", LocalDate.of(1992, 7, 20), new BigDecimal("55000"), "Finance"));
        employees.add(new Employee("Bob", "Brown", "E004", LocalDate.of(1988, 3, 30), new BigDecimal("70000"), "IT"));
        employees.add(new Employee("Charlie", "Davis", "E005", LocalDate.of(1995, 12, 10), new BigDecimal("45000"), "Marketing"));
        employees.add(new Employee("Eve", "Wilson", "E006", LocalDate.of(1991, 11, 25), new BigDecimal("52000"), "Finance"));
        employees.add(new Employee("Frank", "Miller", "E007", LocalDate.of(1983, 9, 5), new BigDecimal("75000"), "HR"));
        employees.add(new Employee("Grace", "Taylor", "E008", LocalDate.of(1994, 4, 18), new BigDecimal("48000"), "IT"));
        employees.add(new Employee("Hank", "Anderson", "E009", LocalDate.of(1987, 6, 22), new BigDecimal("68000"), "Marketing"));
        employees.add(new Employee("Ivy", "Thomas", "E010", LocalDate.of(1993, 2, 14), new BigDecimal("53000"), "Finance"));
    }

    public static void printFirstName(){
        System.out.println("Printing first names of all employees:");
        employees.forEach(e->System.out.println(e.getFirstName()));
    }

    public static void printDots(){
        System.out.println("\n........................................\n");
    }

    public static void printStats(){
        List<BigDecimal> salariesInITDept = employees.stream().filter(e-> e.getDept().equals("IT")).map(e->e.getSalary()).collect(Collectors.toList());
        System.out.print("The minimum salary of employees in IT department is : ");
        System.out.println(salariesInITDept.stream().min((a, b)->a.compareTo(b)).get());
        System.out.print("The maximum salary of employees in IT department is : ");
        System.out.println(salariesInITDept.stream().max((a, b)->a.compareTo(b)).get());
        System.out.print("The count of employees in IT department is : ");
        System.out.println(salariesInITDept.stream().count());
        System.out.print("The average salary of employee in IT department is : ");
        System.out.println(salariesInITDept.stream().collect(Collectors.averagingDouble(e->e.doubleValue())));
        System.out.print("The sum of salaries of employees in IT department is : ");
        System.out.println(salariesInITDept.stream().reduce((a, b)->a.add(b)).get());
    }

    public static void sortedList(){
        System.out.println("The sorted list of employees based on their first name of all department except HR is : ");
        employees.stream().filter(e->!e.getDept().equals("HR")).sorted((a, b)-> a.getFirstName().compareTo(b.getFirstName())).forEach(System.out::println);
    }

    public static void incrementSalary(){
        System.out.println("After Incrementing salary of all employees in Finance department by 10%, the new salaries are : ");
        employees.stream().filter(e->e.getDept().equals("Finance")).map(e->{ 
            e.incrementSalary(10); 
            return e;
        }).collect(Collectors.toList()).forEach(System.out::println);
    }

    public static void fiftyOddNumbersAfterHundred(){
        List<Integer> numbers = new ArrayList<>();
        for(int i=101;i<=200;i++) numbers.add(i);
        System.out.println("Fifty odd numbers after 100 are : ");
        numbers.stream().filter(e->e%2==1).limit(50).forEach(System.out::println);
    }

    public static void commaSeperatedList(){
        System.out.print("The comma separated list of first names of all employees order by DOB is : ");
        System.out.println(employees.stream().sorted((a, b)->a.getDateOfBirth().compareTo(b.getDateOfBirth())).map(e->e.getFirstName()).reduce("", (a, b)->a+b+", "));
    }

    public static void main(String[] args) {
        printFirstName();
        printDots();
        printStats();
        printDots();
        sortedList();
        printDots();
        incrementSalary();
        printDots();
        fiftyOddNumbersAfterHundred();
        printDots();
        commaSeperatedList();
        printDots();
    }
}
