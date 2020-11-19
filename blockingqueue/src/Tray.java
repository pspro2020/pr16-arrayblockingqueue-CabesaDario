import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Tray {
    private final ArrayList<Plate> tray = new ArrayList<>();
    private final ReentrantLock lock = new ReentrantLock(true);
    private final Condition isNotFull = lock.newCondition();
    private final Condition isNotEmpty = lock.newCondition();
    private final int size;
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    private final String tryName;

    public Tray(String tryName, int size) {
        this.tryName = tryName;
        this.size = size;
    }



    public void addToTray(Plate plate) throws InterruptedException {

        lock.lock();

        try{
            while(tray.size() >= size){
                System.out.printf("%s está esperando a que haya hueco en la bandeja de los platos %s\n",Thread.currentThread().getName(), tryName );
                isNotFull.await();
            }

            tray.add(plate);

            System.out.printf("%s ha añadío el plato %d a la bandeja de los platos %s a las %s\n",
                    Thread.currentThread().getName(), plate.number, tryName, LocalTime.now().format(dateTimeFormatter));

            isNotEmpty.signal();

        }finally {
            lock.unlock();

        }








    }

    public Plate extractFromTray() throws InterruptedException {
        Plate plate;

        lock.lock();
        try{
            while (tray.isEmpty()) {
                System.out.printf("%s está esperando a que la bandeja de los platos %s tenga un plato\n",Thread.currentThread().getName(), tryName);
                isNotEmpty.await();
            }
            plate = tray.remove(0);

            System.out.printf("%s ha sacao el plato %d de la bandeja de los platos %s a las %s\n",
                    Thread.currentThread().getName(), plate.number, tryName, LocalTime.now().format(dateTimeFormatter));

            isNotFull.signal();


            return plate;

        }finally {
            lock.unlock();
        }





    }
}