import java.math.BigDecimal;
import java.math.RoundingMode;
public class Assignment {
    private static final int PRECISION = 25;
    
    private static final int SCALE = 5;

    public static BigDecimal roundToPrecision(BigDecimal value) throws ArithmeticException {
        BigDecimal scaledValue = value.setScale(SCALE, RoundingMode.HALF_UP);

        System.out.println("Scaled Value: " + scaledValue.toPlainString());

        if(scaledValue.precision() > PRECISION) {
            throw new ArithmeticException("Value exceeds maximum precision of " + PRECISION + " digits.");
        }

        return scaledValue;
    }

    public static void testRounding(String inputString, String expectedString){
        System.out.println("Input: " + inputString);

        try {
            BigDecimal input = new BigDecimal(inputString);
            BigDecimal expected = new BigDecimal(expectedString);

            BigDecimal rounded = roundToPrecision(input);

            System.out.println("Expected: " + expected.toPlainString());

            if(rounded.compareTo(expected) == 0) {
                System.out.println("Test Passed");
            } else {
                System.out.println("Test Failed");
            }
        } 
        catch(ArithmeticException e) {
            System.out.println("ArithmeticException: " + e.getMessage());
        }
        catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        testRounding("12345.12345467", "12345.12345");
        testRounding("12345.123456", "12345.12346");
        testRounding("999999999999999999999999.99999", "999999999999999999999999.99999"); // exceeds precision
        testRounding("1.999999", "2.00000");
    }    
}
