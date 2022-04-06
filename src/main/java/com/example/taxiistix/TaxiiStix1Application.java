package com.example.taxiistix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class TaxiiStix1Application  {

	public static void main(String[] args) {
		SpringApplication.run(TaxiiStix1Application.class, args);

		try {
			staticBlock();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

	}


	static void staticBlock() throws IOException {
		ServiceHelper.getJson(null);
	}

}
