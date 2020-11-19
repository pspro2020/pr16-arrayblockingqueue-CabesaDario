import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Organizador implements Runnable{
    private final Tray dryTray;
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");



    public Organizador(Tray dryTray) {
        this.dryTray = dryTray;
    }

    @Override
    public void run() {
        Plate plate;

        while (!Thread.currentThread().isInterrupted()) {
            try {
                plate = dryTray.extractFromTray();
            } catch (InterruptedException e) {
                return;
            }
            try {
                organize(plate);
            } catch (InterruptedException e) {
                return;
            }


        }



    }

    private void organize(Plate plate) throws InterruptedException{
        DateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");

        System.out.printf("El Organizador ha comenzado a poner en la alacena el plato #%d a las %s\n", plate.number, LocalTime.now().format(dateTimeFormatter));

        TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(1,3));



    }


}
