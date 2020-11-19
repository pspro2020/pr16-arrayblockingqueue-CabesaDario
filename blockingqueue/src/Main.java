import java.util.concurrent.TimeUnit;

public class Main {




    public static void main(String[] args) throws InterruptedException {
        Tray nastyTray = new Tray("sucios",50);

        Tray cleanTray = new Tray("limpios",10);

        Tray dryTray = new Tray("secos",10);

        Plate plate;

        int number = 0;

        for (int i =0; i<50; i++){
            plate = new Plate(++number);

            nastyTray.addToTray(plate);


        }

        ThreadGroup colegas = new ThreadGroup("los colegas");


        Thread fregadorHilo = new Thread(colegas, new Fregador(cleanTray, nastyTray), "Fregador");
        Thread secadorHilo = new Thread(colegas, new Secador(cleanTray, dryTray), "Secador");
        Thread organizadorHilo = new Thread(colegas, new Organizador(dryTray), "Organizador");

        fregadorHilo.start();
        secadorHilo.start();
        organizadorHilo.start();


        TimeUnit.SECONDS.sleep(60);


        colegas.interrupt();

        TimeUnit.SECONDS.sleep(1);

        System.out.println("Cumpleaños feliz");



    }


}
