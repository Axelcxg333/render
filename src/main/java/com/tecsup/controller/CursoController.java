package com.tecsup.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.tecsup.model.entities.Curso;
import com.tecsup.services.CursoService;

import jakarta.validation.Valid;

@Controller
@SessionAttributes("curso")

public class CursoController {

    @Autowired
    private CursoService servicio;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "redirect:/listar";
    }

    @RequestMapping(value = "/listar", method = RequestMethod.GET)
    public String listar(Model model) {
        model.addAttribute("titulo","Listado de cursos");
        model.addAttribute("cursos",servicio.FindAllCourse());
        return "listView";
    }

    @RequestMapping(value = "/form")
    public String create(Map<String, Object> model) {
        Curso curso = new Curso();
        model.put("curso", curso);
        model.put("titulo", "Formulario de Curso");
        return "formView";
    }

    @RequestMapping(value = "/form", method = RequestMethod.POST)
    public String save(@Valid Curso curso, BindingResult result, Model model, SessionStatus status) {
        if (result.hasErrors()) {
            model.addAttribute("titulo", "Formulario de Curso");
            return "formView";
        }
        
        servicio.SaveCourse(curso);
        status.setComplete();
        return "redirect:listar";
    }



}
