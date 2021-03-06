package com.qveo.qveoweb.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;

import com.qveo.qveoweb.model.Pais;
import com.qveo.qveoweb.service.PaisService;
import com.qveo.qveoweb.validation.PaisValidator;

@Controller
@RequestMapping("/admin")
public class PaisController {

	@Autowired
	PaisService paisService;
	
	@Autowired
	PaisValidator validator;
	
	@InitBinder
	public void InitBinder(WebDataBinder binder) {
		binder.setValidator(validator);
	}

	@RequestMapping(value = "/paises/listar", method = RequestMethod.GET)
	public String listado(Model mod) {

		mod.addAttribute("titulo", "Listado de directores");
		mod.addAttribute("paises", paisService.getAllPais());

		return "paises/listar";
	}

	@RequestMapping(value = "/paises/{id}", method = RequestMethod.GET)
	public String crear(Model mod) {


		mod.addAttribute("paisNuevo", new Pais());
		mod.addAttribute("Titulo", "Registro del pais");

		return "paises/registro";

	}

	@RequestMapping(value = "/paises/edit/{id}", method = RequestMethod.GET)
	public String editar(Model mod, @PathVariable(value = "id") Integer id) {
		Pais pais = null;

		if (id > 0) {
			pais = paisService.getPais(id);
			if (pais == null) {
				return "redirect:/admin/paises/listar";
			}
		} else {
			return "redirect:/admin/paises/listar";
		}

		mod.addAttribute("editar", true);
		mod.addAttribute("Title", "Editar pais");
		mod.addAttribute("paisNuevo", pais);

		return "paises/registro";
	}

	@RequestMapping(value = "/paises/form/add", method = RequestMethod.POST)
	public String guardar(@Valid @ModelAttribute("paisNuevo") Pais pais, BindingResult br, Model mod,
			SessionStatus status) {
		if (br.hasErrors()) {

			mod.addAttribute("paisNuevo", pais);
			mod.addAttribute("titulo", "Formulario de pais");
			return "paises/registro";

		}

		paisService.save(pais);
		status.setComplete();
		

		return "redirect:/admin/paises/listar";

	}
	
	@RequestMapping(value = "/paises/delete/{id}")
	public String eliminar(@PathVariable(value = "id") Integer id) {
		if (id > 0) {
			paisService.delete(id);
		}
		return "redirect:/admin/paises/listar";
	}

}
