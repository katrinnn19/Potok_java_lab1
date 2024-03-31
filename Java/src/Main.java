import java.util.Scanner;

public class Main {
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in); // Создание объекта scanner для считывания ввода с клавиатуры
        System.out.print("Крок: ");
        int krok = scanner.nextInt(); // Считывание значения шага с клавиатуры
        System.out.print("Кiлькiсть потокiв: ");
        int potoki = scanner.nextInt(); // Считывание значения количества потоков с клавиатуры
        int permissionInterval = 10000; // Инициализация интервала разрешения

        SummingThread[] potok = new SummingThread[potoki]; // Создание массива потоков

        for (int i = 0; i < potoki; i++) { // Цикл создания и запуска потоков
            potok[i] = new SummingThread(i, krok); // Создание объекта SummingThread
            potok[i].start(); // Запуск потока
        }

        try { // Обработка исключения InterruptedException
            Thread.sleep(permissionInterval); // Приостановка выполнения текущего потока на указанное количество миллисекунд
        } catch (InterruptedException e) { // Обработка исключения InterruptedException
            e.printStackTrace(); // Вывод стека вызовов исключения на стандартный вывод ошибок
        }

        for (int i = 0; i < potoki; i++) { // Цикл остановки всех потоков
            potok[i].setRunning(false); // Установка флага running в false для остановки потока
        }
    }

    private static class SummingThread extends Thread { // Внутренний класс SummingThread, наследующийся от Thread
        private final int id; // Поле для идентификатора потока
        private final int step; // Поле для шага
        private volatile boolean running = true; // Волатильное булево поле running для определения состояния выполнения потока

        public SummingThread(int id, int step) { // Конструктор класса SummingThread
            this.id = id; // Присвоение значения идентификатора потока
            this.step = step; // Присвоение значения шага
        }

        public void run() { // Переопределение метода run
            double sum = 0; // Инициализация переменной для суммы
            double count = 0; // Инициализация переменной для подсчета количества добавленных значений
            double current = 0; // Инициализация переменной для текущего значения

            while (running) { // Цикл, выполняющийся, пока флаг running равен true
                sum += current; // Увеличение суммы
                count++; // Увеличение счетчика
                current += step; // Увеличение текущего значения на шаг
            }

            System.out.printf("Потiк %d: сума = %f кiлькiсть доданкiв = %f2\n", id + 1, sum, count); // Вывод результатов работы потока
        }

        public void setRunning(boolean running) { // Метод для установки значения флага running
            this.running = running; // Присвоение нового значения флагу running
        }
    }
}
