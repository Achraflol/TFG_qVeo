package com.qveo.qveoweb.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.qveo.qveoweb.model.Actor;
import com.qveo.qveoweb.model.Director;
import com.qveo.qveoweb.model.Genero;
import com.qveo.qveoweb.model.Pais;
import com.qveo.qveoweb.model.Plataforma;
import com.qveo.qveoweb.model.Serie;
import com.qveo.qveoweb.service.ActorService;
import com.qveo.qveoweb.service.DirectorService;
import com.qveo.qveoweb.service.GeneroService;
import com.qveo.qveoweb.service.IUploadFileService;
import com.qveo.qveoweb.service.PaisService;
import com.qveo.qveoweb.service.PlataformaService;
import com.qveo.qveoweb.service.SerieService;
import com.qveo.qveoweb.validation.SerieValidador;

@Controller
public class SerieController {
	Boolean editar = false;

	@Autowired
	SerieService serieService;

	@Autowired
	GeneroService generoService;

	@Autowired
	PaisService paisService;

	@Autowired
	PlataformaService plataformaSerive;

	@Autowired
	DirectorService directorService;

	@Autowired
	IUploadFileService uploadFileService;

	@Autowired
	SerieValidador validador;

	@Autowired
	ActorService actorService;

	@InitBinder
	public void InitBinder(WebDataBinder binder) {
		binder.setValidator(validador);
	}

	@GetMapping("/serie/{id}")
	public String Serie(@PathVariable Integer id, Model model) {

		Serie series = serieService.getSerie(id);

		model.addAttribute("series", series);

		return "series/serie";
	}

	@GetMapping("/admin/serie/listar")
	public String listaSerie(Model model) {
		List<Serie> series = serieService.findAllSerie();
		model.addAttribute("series", series);
		return "series/listaSerie";
	}

	@GetMapping("/admin/serie/form")
	public String SerieFormulario(Model model) {
		editar = false;
		List<Genero> generos = generoService.getAllGenero();
		List<Pais> paises = paisService.getAllPais();
		List<Plataforma> plataformas = plataformaSerive.getAllPlataformas();
		List<Director> directores = directorService.getAllDirector();
		List<Actor> actores = actorService.getAllActor();

		model.addAttribute("serieNueva", new Serie());
		model.addAttribute("paises", paises);
		model.addAttribute("generos", generos);
		model.addAttribute("plataformas", plataformas);
		model.addAttribute("directores", directores);
		model.addAttribute("actores", actores);

		return "series/registroSerie";
	}

	@GetMapping("/admin/serie/edit/{id}")
	public String editarSerie(Model model, @PathVariable("id") Integer id) {
		editar = true;
		List<Genero> generos = generoService.getAllGenero();
		List<Pais> paises = paisService.getAllPais();
		List<Plataforma> plataformas = plataformaSerive.getAllPlataformas();
		List<Director> directores = directorService.getAllDirector();
		Serie serieEditar = serieService.getSerie(id);
		List<Actor> actores = actorService.getAllActor();

		model.addAttribute("editar", true);
		model.addAttribute("serieNueva", serieEditar);
		model.addAttribute("paises", paises);
		model.addAttribute("generos", generos);
		model.addAttribute("plataformas", plataformas);
		model.addAttribute("directores", directores);
		model.addAttribute("actores", actores);

		return "series/registroSerie";
	}
	
	@GetMapping("/serie/mostar")
	public String listado(Model model) {
		List<Serie> series = serieService.findAllSerie();
		model.addAttribute("serieMostrar", series);
		return "series/listaMostrar";
	}
	

	@PostMapping("/admin/serie/form")
	public String guardar(@Valid @ModelAttribute("serieNueva") Serie serieNueva, BindingResult br, Model model,
			@RequestParam("posters") MultipartFile file, RedirectAttributes redirectAttrs, SessionStatus status) {
		try {
			if (br.hasErrors()) {

				List<Genero> generos = generoService.getAllGenero();
				List<Pais> paises = paisService.getAllPais();
				List<Plataforma> plataformas = plataformaSerive.getAllPlataformas();
				List<Director> directores = directorService.getAllDirector();
				List<Actor> actores = actorService.getAllActor();

				model.addAttribute("editar", true);
				model.addAttribute("serieNueva", serieNueva);
				model.addAttribute("paises", paises);
				model.addAttribute("generos", generos);
				model.addAttribute("plataformas", plataformas);
				model.addAttribute("directores", directores);
				model.addAttribute("actores", actores);

				return "series/registroSerie";
			}
			
			serieService.save(serieNueva, file);
			status.setComplete();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "redirect:/admin/serie/listar";
	}

	@GetMapping("/admin/serie/delete/{id}")
	public String deleteSerie(@PathVariable("id") Integer id) {

		String rutaFoto = serieService.getSerie(id).getPoster();
		String ruta = rutaFoto.substring(rutaFoto.lastIndexOf('/') + 1);

		serieService.deleteSerie(id);
		uploadFileService.delete(ruta, 1);

		return "redirect:/admin/serie/listar";
	}

}
