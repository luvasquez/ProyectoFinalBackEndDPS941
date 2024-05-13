package sv.com.consultorio.apiconsultorio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication
public class ApiConsultorioApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiConsultorioApplication.class, args);
    }

}
