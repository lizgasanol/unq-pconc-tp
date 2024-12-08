import BufferedImageReader.BufferedImageReader;
import Resultados.AlmacenadorResultados;
import Buffer.Buffer;
import CSVReader.CSVReader;
import KNNHandler.KNNHandler;
import Resultados.Resultado;
import Task.MNISTask;
import Task.PoisonPill;
import Workers.ThreadPool;
import Workers.WorkerCounter;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        // Instancio Scanner para leer input del usuario
        Scanner scanner = new Scanner(System.in);

        // Variables modificables por el usuario
        System.out.println("Ingrese la cantidad de threads");
        int cantidadDeThreads = scanner.nextInt();
        System.out.println("Ingrese el tamaño del buffer");
        int tamano_buffer = scanner.nextInt();
        System.out.println("Ingrese el k deseado");
        int k = scanner.nextInt();
        System.out.println("Desea usar el modo imagen? (true o false, la imagen debe estar en la carpeta Data y tener nombre muestra.png)");
        boolean modoImagen = scanner.nextBoolean();

        // Cierro el Scanner
        scanner.close();

        // Inicializo clases necesarias
        Buffer buffer = new Buffer(tamano_buffer);
        WorkerCounter workerCounter = new WorkerCounter(0);
        ThreadPool threadPool = new ThreadPool(cantidadDeThreads, buffer, workerCounter);
        AlmacenadorResultados almacenadorResultados = new AlmacenadorResultados(workerCounter);
        KNNHandler knnHandler = new KNNHandler(k);

        // Leo el CSV de entrenamiento
        CSVReader csvReader = new CSVReader();
        List<List<String>> datos_entrenamiento = csvReader.read(60000, "mnist_train.csv");

        // Genero una lista de muestras para evitar codigo repetido entre modo imagen y modo csv
        List<List<String>> listaDeMuestras = new ArrayList<>();

        if(modoImagen) {
            // Leo la imagen cargada
            BufferedImageReader bufferedImageReader = new BufferedImageReader();
            List<String> muestra = bufferedImageReader.readImage("muestra.png");
            // Agrego una primera columna para consistencia con la columna del "numero" del csv de prueba
            muestra.addFirst("0");
            listaDeMuestras.add(muestra);
        } else {
            // Leo el csv de test
            listaDeMuestras = csvReader.read(500, "mnist_test.csv");
        }

        // Particiono las lineas de entrenamiento
        int tamano_particion = 10000;
        List<List<List<String>>> particiones = new ArrayList<>();
        for(int i = 0; i < datos_entrenamiento.size(); i += tamano_particion) {
            particiones.add(datos_entrenamiento.subList(i, Math.min(i + tamano_particion, datos_entrenamiento.size())));
        }

        long tiempoDeInicio = System.currentTimeMillis();

        List<Boolean> aciertos = new ArrayList<>();

        // Voy a iterar por cada muestra para obtener los resultados (Podria ser mas performante)
        for(List<String> muestra : listaDeMuestras) {
            // Inicializo los threads
            threadPool.start();

            // Creo las tasks segun cantidad de particiones
            for (List<List<String>> particion : particiones) {
                MNISTask mnisTask = new MNISTask(particion, muestra, almacenadorResultados, knnHandler);

                buffer.write(mnisTask);
            }

            // Creo una PoisonPill para cada thread
            for (int i = 0; i < cantidadDeThreads; i++) {
                PoisonPill poisonPill = new PoisonPill();

                buffer.write(poisonPill);
            }

            List<Resultado> resultados = knnHandler.getKNN(almacenadorResultados.getResultados());
            almacenadorResultados.vaciar();
            Map<Integer, Long> ocurrenciasMap = resultados.stream().collect(Collectors.groupingBy(Resultado::getValor, Collectors.counting()));

            int numero_con_mas_ocurrencias = ocurrenciasMap.entrySet().stream()
                    .max(Comparator.comparingLong(Map.Entry::getValue))
                    .map(Map.Entry::getKey)
                    .orElseThrow(() -> new NoSuchElementException("No se encontro resultado."));

            if (modoImagen) {
                System.out.println("Resultado obtenido: " + numero_con_mas_ocurrencias);
            } else {
                System.out.println("Numero de prueba: " + muestra.getFirst() + " / Resultado obtenido: " + numero_con_mas_ocurrencias);
                aciertos.add(Integer.parseInt(muestra.getFirst()) == numero_con_mas_ocurrencias);
                System.out.println("Muestras procesadas: " + (listaDeMuestras.indexOf(muestra) + 1) + " de " + listaDeMuestras.size());
            }
        }
        if(!modoImagen) {
            double porcentajeDeAciertos = (aciertos.stream().filter(b -> b).count() * 100.0) / aciertos.size();
            System.out.println("Porcentaje de aciertos: %" + porcentajeDeAciertos);
        }
        long tiempoDeFin = System.currentTimeMillis();
        System.out.println("Tiempo transcurrido: " + ((tiempoDeFin-tiempoDeInicio) / 1000) + " segundos.");
    }
}