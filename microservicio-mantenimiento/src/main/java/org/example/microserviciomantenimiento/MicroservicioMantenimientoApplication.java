package org.example.microserviciomantenimiento;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MicroservicioMantenimientoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroservicioMantenimientoApplication.class, args);
    }

}
