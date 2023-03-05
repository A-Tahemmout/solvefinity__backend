package ma.emsi.solvefinity;

import lombok.AllArgsConstructor;
import ma.emsi.solvefinity.mock.MockData;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@AllArgsConstructor
public class SolvefinityAppApplication implements CommandLineRunner {
    private final MockData mockData;

    public static void main(String[] args) {
        SpringApplication.run(SolvefinityAppApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // mockData.destroy();
        // mockData.init();
    }
}
