package com.example.demovaadintutorial;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.apache.commons.lang3.SystemUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import com.example.demovaadintutorial.model.Company;
import com.example.demovaadintutorial.model.Contact;
import com.example.demovaadintutorial.repo.ICompanyRepo;
import com.example.demovaadintutorial.repo.IContactRepo;
import com.github.javafaker.Faker;
import com.vaadin.flow.component.page.AppShellConfigurator;

@SpringBootApplication
public class DemoVaadinTutorialApplication implements AppShellConfigurator {

	public static void main(String[] args) {
		SpringApplication.run(DemoVaadinTutorialApplication.class, args);
	}

	@Bean
	public CommandLineRunner run(IContactRepo repoContact, ICompanyRepo repoCompany) {
		return args -> {
			final Faker faker = new Faker();

			repoCompany.saveAll(Stream
					.of("Path-Way Electronics", "E-Tech Management", "Path E-Tech Management")
					.map(s -> Company.builder().name(s).build()).collect(Collectors.toList()));

			var r = new Random(0);
			List<Company> companies = repoCompany.findAll();

			List<Contact> contacts = IntStream.range(1, 100).mapToObj(i -> {
				var _name = faker.name();
				var _fName = _name.firstName();
				var _lName = _name.lastName();
				var _company = companies.get(r.nextInt(companies.size()));
				return Contact.builder().firstName(_fName).lastName(_lName).company(_company)
						.email(buildEmail(_fName, _lName, _company))
						.status(Contact.Status.values()[r.nextInt(Contact.Status.values().length)])
						.build();
			}).toList();
			repoContact.saveAll(contacts);
		};
	}

	private String buildEmail(String firstName, String lastName, Company company) {
		return firstName.toLowerCase() + "." + lastName.toLowerCase() + "@"
				+ company.getName().replaceAll("[\\s-]", "").toLowerCase() + ".com";
	}


	@EventListener({ApplicationReadyEvent.class})
	void applicationReadyEvent() {
		System.out.println("Application started ... launching browser now");
		browse("http://localhost:8080");
		browse("http://localhost:8080/h2-console");
	}

	public static void browse(String url) {
		if (!SystemUtils.IS_OS_WINDOWS)
			return;
		try {
			Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + url);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
