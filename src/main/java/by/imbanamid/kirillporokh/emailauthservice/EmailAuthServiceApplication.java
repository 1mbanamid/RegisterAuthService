package by.imbanamid.kirillporokh.emailauthservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableKafka
public class EmailAuthServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(EmailAuthServiceApplication.class, args);
  }
}
