import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MathModel {
    // Операторы и их приоритеты
    private static final String OPERATORS = "+-*/^//";
    private static final int[] PRECEDENCE = {1, 1, 2, 2, 3, 2}; // Приоритеты операторов

    // Метод для вычисления выражения
    public double calculate(String expression) {
        try {
            // Преобразуем выражение в обратную польскую запись (ОПЗ)
            String postfix = infixToPostfix(expression);
            // Вычисляем значение выражения из ОПЗ
            return evaluatePostfix(postfix);
        } catch (Exception e) {
            throw new IllegalArgumentException("Ошибка при вычислении выражения: " + e.getMessage());
        }
    }

    // Преобразование инфиксной записи в постфиксную (ОПЗ)
    private String infixToPostfix(String expression) {
        StringBuilder output = new StringBuilder();
        Stack<Character> operators = new Stack<>();

        // Регулярное выражение для поиска чисел и операторов
        Pattern pattern = Pattern.compile("\\d+(\\.\\d+)?|[-+*/^//]");
        Matcher matcher = pattern.matcher(expression);

        while (matcher.find()) {
            String token = matcher.group();

            if (token.matches("\\d+(\\.\\d+)?")) {
                // Если токен — число, добавляем его в выходную строку
                output.append(token).append(" ");
            } else if (OPERATORS.contains(token)) {
                // Если токен — оператор, обрабатываем его с учетом приоритета
                while (!operators.isEmpty() && OPERATORS.contains(String.valueOf(operators.peek()))) {
                    int currentPrecedence = PRECEDENCE[OPERATORS.indexOf(token)];
                    int stackPrecedence = PRECEDENCE[OPERATORS.indexOf(operators.peek())];

                    if (stackPrecedence >= currentPrecedence) {
                        output.append(operators.pop()).append(" ");
                    } else {
                        break;
                    }
                }
                operators.push(token.charAt(0));
            }
        }

        // Добавляем оставшиеся операторы в выходную строку
        while (!operators.isEmpty()) {
            output.append(operators.pop()).append(" ");
        }

        return output.toString().trim();
    }

    // Вычисление значения из постфиксной записи (ОПЗ)
    private double evaluatePostfix(String postfix) {
        Stack<Double> stack = new Stack<>();
        String[] tokens = postfix.split(" ");

        for (String token : tokens) {
            if (token.matches("\\d+(\\.\\d+)?")) {
                // Если токен — число, добавляем его в стек
                stack.push(Double.parseDouble(token));
            } else if (OPERATORS.contains(token)) {
                // Если токен — оператор, выполняем операцию
                double operand2 = stack.pop();
                double operand1 = stack.pop();
                double result = applyOperation(token.charAt(0), operand1, operand2);
                stack.push(result);
            }
        }

        return stack.pop();
    }

    // Применение операции
    private double applyOperation(char operator, double operand1, double operand2) {
        switch (operator) {
            case '+':
                return operand1 + operand2;
            case '-':
                return operand1 - operand2;
            case '*':
                return operand1 * operand2;
            case '^':
                return Math.pow(operand1, operand2);
            case '/':
                return (int) (operand1 / operand2); // Деление без остатка
            default:
                throw new IllegalArgumentException("Неизвестный оператор: " + operator);
        }
    }
}