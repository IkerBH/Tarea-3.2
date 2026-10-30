package org.iesvdm.ventas;

import org.iesvdm.dao.ClienteDAO;
import org.iesvdm.dao.ComercialDAO;
import org.iesvdm.modelo.Comercial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@RestController 
@Slf4j 
public class VentasApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(VentasApplication.class, args);
	}

	@Autowired 
	private ClienteDAO clienteDAO;

	@Autowired
	private ComercialDAO comercialDAO;

	@Override 
	public void run(String... args) throws Exception {
		log.info("*********************************");
		log.info("**Prueba de arranque ClienteDAO**");
		log.info("*********************************");

		clienteDAO.getAll().forEach(cliente -> log.info("cliente: {}", cliente));

		log.info("***********************************");
		log.info("**Prueba de arranque ComercialDAO**");
		log.info("***********************************");
		comercialDAO.getAll().forEach(comercial -> log.info("comercial: {}", comercial));

		Comercial comercial = new Comercial(0, "Prueba", "DAO", "Temporal", 0.10f);
		comercialDAO.create(comercial);
		if (comercialDAO.find(comercial.getId()).isEmpty()) {
			throw new IllegalStateException("No se pudo consultar el comercial insertado.");
		}

		comercial.setNombre("Prueba actualizada");
		comercial.setComision(0.20f);
		comercialDAO.update(comercial);
		Comercial comercialActualizado = comercialDAO.find(comercial.getId())
				.orElseThrow(() -> new IllegalStateException("No se encontró el comercial actualizado."));
		if (!"Prueba actualizada".equals(comercialActualizado.getNombre())
				|| Float.compare(0.20f, comercialActualizado.getComision()) != 0) {
			throw new IllegalStateException("La actualización del comercial no se guardó correctamente.");
		}

		comercialDAO.delete(comercial.getId());
		if (comercialDAO.find(comercial.getId()).isPresent()) {
			throw new IllegalStateException("No se pudo borrar el comercial de prueba.");
		}

	}

}
