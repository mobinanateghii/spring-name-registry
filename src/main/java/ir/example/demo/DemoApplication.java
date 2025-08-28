package ir.example.demo;

import ir.example.demo.service.CustomerService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {
    private static CustomerService service;

    public DemoApplication(CustomerService service) {
        this.service = service;
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);

        service.test();
    }

}
