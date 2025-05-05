import java.io.*;
import java.util.Scanner;

class ExpressionCalculator {
    private double x;
    private double y;

    public void calculate(double x) {
        this.x = x;
        this.y = x - Math.sin(x);
    }

    public void saveToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("x = " + x);
            writer.newLine();
            writer.write("y = " + y);
            writer.newLine();
            System.out.println("Состояние сохранено в файл: " + filename);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void loadFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("x = ")) {
                    this.x = Double.parseDouble(line.substring(4));
                } else if (line.startsWith("y = ")) {
                    this.y = Double.parseDouble(line.substring(4));
                }
            }
            System.out.println("Состояние загружено из файла: " + filename);
        } catch (IOException | NumberFormatException ex) {
            ex.printStackTrace();
        }
    }

    public void displayState() {
        System.out.printf("Текущее состояние: x = %.4f, y = %.4f\n", x, y);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ExpressionCalculator calculator = new ExpressionCalculator();

        while (true) {
            System.out.println("Введите число x, 'save', 'upload' или 'exit':");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("exit")) {
                break;
            } else if (input.equalsIgnoreCase("save")) {
                calculator.saveToFile("calculator_state.txt");
            } else if (input.equalsIgnoreCase("upload")) {
                calculator.loadFromFile("calculator_state.txt");
                calculator.displayState();
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
