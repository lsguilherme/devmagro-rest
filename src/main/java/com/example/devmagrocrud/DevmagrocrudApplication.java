package com.example.devmagrocrud;

import com.example.devmagrocrud.cli.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
public class DevmagrocrudApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(DevmagrocrudApplication.class, args);
	}

	@Autowired
	private Menu menu;

	@Override
	public void run(String... args) {
		menu.showMenu();
	}

}
