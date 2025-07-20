package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;

import com.event.OrderPlacedEvent;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class NotificateServiceAplication {
	public static void main(String[] args) {
		SpringApplication.run(NotificateServiceAplication.class, args);
	}
	
	@KafkaListener(topics="notificationTopic")
	public void handleNotification(OrderPlacedEvent orderPlacedEvent) {
		// send out an email message
		log.info("Notification for order = {}", orderPlacedEvent.getOrderNumber());
	}
}
