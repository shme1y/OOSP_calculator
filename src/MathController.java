public class MathController {
    private MathModel model;
    private MathView view;

    public MathController(MathModel model, MathView view) {
        this.model = model;
        this.view = view;
    }

    // Метод для запуска программы
    public void run() {
        try {
            String expression = view.getExpression();
            double result = model.calculate(expression);
            view.displayResult(result);
        } catch (Exception e) {
            view.displayError(e.getMessage());
        }
    }
}