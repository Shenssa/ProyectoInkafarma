package com.logistica.proyecto.control;
 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.logistica.proyecto.entidad.Proveedor;
import com.logistica.proyecto.interfaces.InterfacesSimple;
import com.logistica.proyecto.servicio.ProveedorServicioImp;
 

@Controller
@RequestMapping("/Proveedor")
public class ProveedorControl {
	String carpeta = "Proveedor";
	@Autowired
	private InterfacesSimple<Proveedor> Service;

	@Autowired
	private  ProveedorServicioImp servicioIMP;
	
	
	@RequestMapping({"/"})
	public String inicio(Model model) {
		model.addAttribute("titulo", carpeta);
		model.addAttribute("RutaNuevo", "/" + carpeta + "/crear/"); // Crear
		model.addAttribute("RutaEliminar", "/" + carpeta + "/eliminar/"); // Eliminar
		model.addAttribute("RutaEditar", "/" + carpeta + "/editar/"); // Editar
		model.addAttribute("lista", Service.obtenerLista());
	  
		return "/" + carpeta + "/listado";
	}

	
	@RequestMapping({"/buscar/"})
	public String inicioB(Model model,@RequestParam(name="texto") String texto) {
	  model.addAttribute("lista1",texto);
	  model.addAttribute("lista",servicioIMP.BuscarPorEmpresa(texto));
	  return "/" + carpeta + "/buscar";
	}
	
	@GetMapping("/crear/")
	public String crear(Model model) {
		// instanciar entidad Nuevo
		Proveedor obj = new Proveedor();
		model.addAttribute("titulo", "Registrar " + carpeta);// titulo
		model.addAttribute("RutaListado", "/" + carpeta + "/"); // RutaListado
		model.addAttribute("RutaGrabar", "/" + carpeta + "/grabar"); // RutaGrabar
		model.addAttribute("entidad", obj);// entidad
		return "/" + carpeta + "/formulario";
	}

	@GetMapping("/editar/{id}")
	public String editar(@PathVariable("id") Integer id, Model model) {
		Proveedor obj = null;
		if (Service.existe(id) == true) {
			obj = Service.BuscarPorIdEntidad(id);
			model.addAttribute("titulo", "Modificar " + carpeta);
			model.addAttribute("RutaListado", "/" + carpeta + "/"); // RutaListado
			model.addAttribute("RutaGrabar", "/" + carpeta + "/grabar"); // RutaGrabar
			model.addAttribute("entidad", obj);// modificar

			return "/" + carpeta + "/formulario";
		} else {
			return "/" + carpeta + "/error";
		}

	}

	@RequestMapping("/grabar")
	public String guardar(Model model, Proveedor obj) {
		Service.guardar(obj);
		return "redirect:";
	}

	@RequestMapping("/eliminar/{id}")
	public String eliminar(@PathVariable(value = "id") Integer id) {
		Service.eliminar(id);
		return "redirect:../";
	}

}
