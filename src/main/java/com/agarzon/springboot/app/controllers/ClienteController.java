package com.agarzon.springboot.app.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.agarzon.springboot.app.models.entities.Cliente;
import com.agarzon.springboot.app.models.services.IClienteService;

import jakarta.validation.Valid;

@Controller
@SessionAttributes("cliente")
public class ClienteController {
	
	@Autowired
	private IClienteService clienteService;
	
	@GetMapping("/listar")
	public String listar(Model model) {
		model.addAttribute("titulo", "Listado de Clientes");
		model.addAttribute("clientes", clienteService.findAll());
		return "listar";
	}
	
	@GetMapping("/form")
	public String crear(Map<String, Object> model) {
		Cliente cliente = new Cliente(); 
		model.put("titulo", "Formulario de Cliente");
		model.put("cliente", cliente);
		return "form";
	}
	
	@PostMapping("/form")
	public String guardar(@Valid Cliente cliente, BindingResult result, Model model, RedirectAttributes flash ,SessionStatus status) {
		if (result.hasErrors()) {
			model.addAttribute("titulo", "Formulario de Cliente");
			return "form";
		}
		
		String mensaje = (cliente.getId() != null) ? "Cliente editado con exito!" : "Cliente creado con exito!";
				
		clienteService.save(cliente);
		status.setComplete();
		flash.addFlashAttribute("success", mensaje);
		return "redirect:listar";
	}
	
	@GetMapping("/form/{id}")
	public String editar(@PathVariable(value = "id") Long id, RedirectAttributes flash, Map<String, Object> model) {
		Cliente cliente = null;		
		if (id>0) {
			cliente = clienteService.findById(id);
			if (cliente == null) {
				flash.addFlashAttribute("error", "El Id del Cliente no existe en la Base de Datos!");
				return "redirect:/listar";
			}
		}else {
			flash.addFlashAttribute("error", "El Id del Cliente no puede ser cero!");
			return "redirect:/listar";
		}

		model.put("titulo", "Formulario de Cliente");
		model.put("cliente", cliente);
		return "form";
		
	}
	
	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {	
		if (id>0) {
			clienteService.delete(id);
			flash.addFlashAttribute("success", "Cliente eliminado con exito");
		}
			return "redirect:/listar";		
	}
	
}
