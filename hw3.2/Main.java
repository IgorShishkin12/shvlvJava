
import java.util.Random;
import java.util.concurrent.*;

class Task {
    private final int duration;

    public Task(int duration) {
        this.duration = duration;
    }

    public void execute() {
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

class Producer implements Runnable {
    private final BlockingQueue<Task> queue;

    public Producer(BlockingQueue<Task> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        Random random = new Random();
        long startTime = System.currentTimeMillis();

        try {
            while (System.currentTimeMillis() - startTime < 30000) {
                int randomNumber = 100 + random.nextInt(2901); // Генерация числа от 100 до 3000
                Task task = new Task(randomNumber);
                queue.put(task);
                Thread.sleep(1000); // Пауза перед генерацией следующего числа
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            // После истечения 30 секунд отправляем сигнал завершения
            System.out.println("Производитель завершает работу.");
        }
    }
}

class Worker implements Runnable {
    private final BlockingQueue<Task> queue;

    public Worker(BlockingQueue<Task> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Task task = queue.poll(1, TimeUnit.SECONDS); // Получение задания из очереди
                if (task == null) {
                    break; // Прерываем выполнение, если очередь пуста
                }
                task.execute(); // Выполнение задания
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

public class Main {
    public static void main(String[] args) {
        BlockingQueue<Task> queue = new LinkedBlockingQueue<>();
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        Producer producer = new Producer(queue);
        executorService.submit(producer);

        Worker worker1 = new Worker(queue);
        Worker worker2 = new Worker(queue);

        executorService.submit(worker1);
        executorService.submit(worker2);

        try {
            Thread.sleep(30000); // Пауза для работы Producer
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            executorService.shutdown();
            try {
                executorService.awaitTermination(10, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
