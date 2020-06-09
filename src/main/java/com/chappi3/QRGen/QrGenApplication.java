package com.chappi3.QRGen;

import com.chappi3.QRGen.service.StartupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class QrGenApplication implements CommandLineRunner {

	@Autowired
	StartupService startupService;

	public static void main(String[] args) { SpringApplication.run(QrGenApplication.class, args); }

	@Override
	public void run(String... args) throws Exception {
		startupService.populateDbIfNeeded();
	}
}
