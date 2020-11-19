import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Secador implements Runnable{
    private final Tray cleanTray;
    private final Tray dryTray;
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");


    public Secador(Tray cleanTray, Tray dryTray) {
        this.cleanTray = cleanTray;
        this.dryTray = dryTray;
    }

    @Override
    public void run() {
        Plate plate;

            while (!Thread.currentThread().isInterrupted()) {
                try {
                    plate = cleanTray.extractFromTray();
                } catch (InterruptedException e) {
                    return;
                }
                try {
                    dry(plate);
                } catch (InterruptedException e) {
                    return;
                }

                try {
                    dryTray.addToTray(plate);
                } catch (InterruptedException e) {
                    return;
                }
            }


    }

    private void dry(Plate plate) throws InterruptedException{
        DateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");
        System.out.printf("El Secador ha comenzado a secar el plato #%d a las %s\n", plate.number, LocalTime.now().format(dateTimeFormatter));
        TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(1,4));


    }
}
