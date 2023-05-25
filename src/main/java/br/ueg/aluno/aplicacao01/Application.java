package br.ueg.aluno.aplicacao01;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

@SpringBootApplication(
		scanBasePackages = {
				"br.ueg.aluno.aplicacao01.*",
				//Para funcionamento da Arquitetura
				"br.ueg.prog.webi.*"}
)
@EntityScan(basePackageClasses = { Jsr310JpaConverters.class },
		basePackages = {
		"br.ueg.aluno.aplicacao01.*",
		//Para funcionamento da Arquitetura
		"br.ueg.prog.webi.api.*"}
)
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
