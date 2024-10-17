package com.sistema.examenes.sistema_examenes_backend.controladores;

import com.sistema.examenes.sistema_examenes_backend.DTO.RecursoDTO;
import com.sistema.examenes.sistema_examenes_backend.entidades.Negocio;
import com.sistema.examenes.sistema_examenes_backend.servicios.NegocioService;
import com.sistema.examenes.sistema_examenes_backend.servicios.RecursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recurso")
@CrossOrigin("*")
public class RecursoController {

    @Autowired
    private RecursoService recursoService;

    @Autowired
    private NegocioService negocioService;



}
