package iv.synapseuser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SynapseUserApplication {

	public static void main(String[] args) {
		SpringApplication.run(SynapseUserApplication.class, args);
	}

}
