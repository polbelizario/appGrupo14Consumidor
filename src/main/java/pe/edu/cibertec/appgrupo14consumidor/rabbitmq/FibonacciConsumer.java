package pe.edu.cibertec.appgrupo14consumidor.rabbitmq;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import pe.edu.cibertec.appgrupo14consumidor.config.RabbitMqConfig;
import pe.edu.cibertec.appgrupo14consumidor.service.FibonacciService;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class FibonacciConsumer {

    private final FibonacciService fibonacciService;

    @RabbitListener(queues = RabbitMqConfig.QUEUE)
    public void recibirMensaje(String mensaje) throws InterruptedException {

        System.out.println("Mensaje recibido: " + mensaje);

        Integer[] numeros = Arrays.stream(mensaje.split(";"))
                .map(String::trim)
                .map(Integer::valueOf)
                .toArray(Integer[]::new);

        System.out.println("Esperando 20 segundos...");

        Thread.sleep(20000);

        System.out.println("Resultados Fibonacci:");

        for (Integer numero : numeros) {
            long resultado = fibonacciService.calcularFibonacci(numero);

            System.out.println(
                    "Fibonacci(" + numero + ") = " + resultado
            );
        }
    }
}