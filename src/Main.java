public class Main {
    public static void main(String[] args) {
        MathModel model = new MathModel();
        MathView view = new MathView();
        MathController controller = new MathController(model, view);

        controller.run();
    }
}