package org.iesvdm.controller;

import java.util.List;

import org.iesvdm.modelo.Cliente;
import org.iesvdm.servicios.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

@Controller 
public class ClienteController {
    
    @Autowired 
    private ClienteService clienteServicio;

    @GetMapping("/")
    public String listar(Model model){
        List<Cliente> clientes = clienteServicio.listAll();
        model.addAttribute("clientes", clientes);
        return "clientes";
    }
}
