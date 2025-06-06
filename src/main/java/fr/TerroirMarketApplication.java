package fr;

import fr.ecommerce.services.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class TerroirMarketApplication {

	public static void main(String[] args) {
		SpringApplication.run(TerroirMarketApplication.class, args);
	}

}
