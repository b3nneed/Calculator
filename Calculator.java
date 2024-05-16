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

        boolean isRoman = isRomanNumeral(operand1) && isRomanNumeral(operand2);
        boolean isArabic = isArabicNumeral(operand1) && isArabicNumeral(operand2);

        if (!(isRoman || isArabic)) {
            throw new Exception("Используйте арабские или римские цифры одновременно");
        }

        int num1 = isRoman ? romanToArabic(operand1) : Integer.parseInt(operand1);
        int num2 = isRoman ? romanToArabic(operand2) : Integer.parseInt(operand2);

        if (num1 < 1 || num1 > 10 || num2 < 1 || num2 > 10) {
            throw new Exception("Числа должны быть в диапазоне от 1 до 10");
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

        if (isRoman) {
            if (result < 1) {
                throw new Exception("Результат не может быть меньше I");
            }
            return arabicToRoman(result);
        } else {
            return String.valueOf(result);
        }
    }

    private static boolean isRomanNumeral(String numeral) {
        return numeral.matches("^(M{0,3})(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})$");
    }

    private static boolean isArabicNumeral(String numeral) {
        return numeral.matches("^[1-9]|10$");
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
