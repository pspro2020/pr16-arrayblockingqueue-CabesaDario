import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Fregador implements Runnable{
    private final Tray nastyTray;
    private final Tray cleanTray;
    private int plateNumber;
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    public Fregador(Tray cleanTray, Tray nastyTray) {
        this.cleanTray = cleanTray;
        this.nastyTray = nastyTray;
    }

    @Override
    public void run() {
        Plate plate;
        while (!Thread.currentThread().isInterrupted()) {
            try {
                plate = nastyTray.extractFromTray();

            } catch (InterruptedException e) {
                return;
            }
            try {
                fregarPlato(plate);
            } catch (InterruptedException e) {

                return;
            }
            try {
                cleanTray.addToTray(plate);
            } catch (InterruptedException e) {
                return;
            }
        }
    }

    private void fregarPlato(Plate plate) throws InterruptedException{
        DateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");

        System.out.printf("El Fregador ha comenzado a fregar el plato #%d a las %s\n", plate.number, LocalTime.now().format(dateTimeFormatter));
        TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(4,9));

    }
}
