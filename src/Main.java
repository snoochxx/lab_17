import java.io.*;
import java.util.Scanner;

class ExpressionCalculator implements Serializable {
    private static final long serialVersionUID = 1L;

    private double x;
    private double y;

    public void calculate(double x) {
        this.x = x;
        this.y = x - Math.sin(x);
    }

    public void saveToFile(String filename) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(this);
            System.out.println("Состояние сериализовано в файл: " + filename);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static ExpressionCalculator loadFromFile(String filename) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            ExpressionCalculator obj = (ExpressionCalculator) in.readObject();
            System.out.println("Состояние загружено из файла: " + filename);
            return obj;
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public void displayState() {
        System.out.printf("Текущее состояние: x = %.4f, y = %.4f%n", x, y);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ExpressionCalculator calculator = new ExpressionCalculator();
        String fileName = "calculator_state.txt";

        while (true) {
            System.out.println("Введите число x, 'save', 'upload' или 'exit':");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("exit")) {
                break;
            } else if (input.equalsIgnoreCase("save")) {
                calculator.saveToFile(fileName);
            } else if (input.equalsIgnoreCase("upload")) {
                ExpressionCalculator loaded = ExpressionCalculator.loadFromFile(fileName);
                if (loaded != null) {
                    calculator = loaded;
                    calculator.displayState();
                }
            } else {
                try {
                    double xValue = Double.parseDouble(input);
                    calculator.calculate(xValue);
                    calculator.displayState();
                } catch (NumberFormatException e) {
                    System.out.println("Неверный ввод. Попробуйте снова.");
                }
            }
        }
    }
}
