import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Calculator {
    private static Map<Character, Integer> romanNumerals;

    static {
        romanNumerals = new HashMap<>();
        romanNumerals.put('I', 1);
        romanNumerals.put('V', 5);
        romanNumerals.put('X', 10);
        romanNumerals.put('L', 50);
        romanNumerals.put('C', 100);
        romanNumerals.put('D', 500);
        romanNumerals.put('M', 1000);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        try {
            System.out.println(calculate(input));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static String calculate(String input) throws Exception {
        String[] parts = input.split(" ");
        if (parts.length != 3) {
            throw new Exception("Неверный формат ввода");
        }

        String operand1 = parts[0];
        String operator = parts[1];
        String operand2 = parts[2];

        int num1 = romanToArabic(operand1);
        int num2 = romanToArabic(operand2);

        if (num1 < 1 || num1 > 3999 || num2 < 1 || num2 > 3999) {
            throw new Exception("Числа должны быть в диапазоне от 1 до 3999");
        }

        int result;
        switch (operator) {
            case "+":
                result = num1 + num2;
                break;
            case "-":
                result = num1 - num2;
                break;
            case "*":
                result = num1 * num2;
                break;
            case "/":
                if (num2 == 0) {
                    throw new Exception("Деление на ноль невозможно");
                }
                result = num1 / num2;
                break;
            default:
                throw new Exception("Неверный оператор");
        }

        return arabicToRoman(result);
    }

    private static int romanToArabic(String roman) {
        int result = 0;
        for (int i = 0; i < roman.length(); i++) {
            if (i > 0 && romanNumerals.get(roman.charAt(i)) > romanNumerals.get(roman.charAt(i - 1))) {
                result += romanNumerals.get(roman.charAt(i)) - 2 * romanNumerals.get(roman.charAt(i - 1));
            } else {
                result += romanNumerals.get(roman.charAt(i));
            }
        }
        return result;
    }

    private static String arabicToRoman(int number) {
        String[] romanValues = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        int[] arabicValues = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < arabicValues.length; i++) {
            while (number >= arabicValues[i]) {
                number -= arabicValues[i];
                result.append(romanValues[i]);
            }
        }
        return result.toString();
    }
}