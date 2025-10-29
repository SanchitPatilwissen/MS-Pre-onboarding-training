import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

// Using strategy design pattern which is behavioural pattern helping us keep all Sum strategy algos together.

interface SumStrategy{
    public List<Integer> filter(List<Integer> numbers);
}

class AllSumStrategy implements SumStrategy{
    @Override
    public List<Integer> filter(List<Integer> numbers){
        return numbers;
    }
}

class OddSumStrategy implements SumStrategy{
    @Override
    public List<Integer> filter(List<Integer> numbers) {
        return numbers.stream().filter(n -> n%2 == 1).collect(Collectors.toList());
    }
    
}

class EvenSumStrategy implements SumStrategy{
    @Override
    public List<Integer> filter(List<Integer> numbers){
        return numbers.stream().filter(num -> num%2 == 0).collect(Collectors.toList());
    }
}

class SumCalculator{
    private final SumStrategy sumStrategy;

    public SumCalculator(SumStrategy sumStrategy){
        this.sumStrategy = sumStrategy;
    }

    public int calculateSum(List<Integer> numbers){
        return sumStrategy.filter(numbers).stream().mapToInt(Integer::intValue).sum();
    }
}

// implementing prime numbers filter as well just by extending the strategy

class PrimeNumberStrategy implements SumStrategy{
    @Override
    public List<Integer> filter(List<Integer> numbers) {
        return numbers.stream()
                      .filter(this::isPrime)
                      .collect(Collectors.toList());
    }

    private boolean isPrime(int n) {
        if (n <= 1) return false;
        if (n <= 3) return true;
        if (n % 2 == 0 || n % 3 == 0) return false;

        for (int i = 5; i * i <= n; i += 6) {
            if (n % i == 0 || n % (i + 2) == 0) return false;
        }
        return true;
    }
}

// Using factory pattern to create the object
class SumStrategyFactory {
    public static SumStrategy getStrategy(int option) {
        return switch (option) {
            case 1 -> new AllSumStrategy();
            case 2 -> new OddSumStrategy();
            case 3 -> new EvenSumStrategy();
            case 4 -> new PrimeNumberStrategy();
            default -> throw new IllegalArgumentException("Invalid option selected");
        };
    }
}


public class Main{
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose option:\n1. All\n2. Odd\n3. Even\n4. Prime");
        int option = scanner.nextInt();

        SumStrategy strategy = SumStrategyFactory.getStrategy(option);
        SumCalculator calculator = new SumCalculator(strategy);
        int result = calculator.calculateSum(numbers);

        System.out.println("Sum: " + result);
    }
}