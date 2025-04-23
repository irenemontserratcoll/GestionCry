package com.example.restapi.client;

import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.example.restapi.model.Usuario;

import org.springframework.http.MediaType;

@Controller
public class ClienteWebController {
    private final String apiBaseUrl = "http://localhost:8083";
    private final RestTemplate restTemplate;

    public ClienteWebController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/")
    public String redirectToLogin() {
        return "index";
    }

    @GetMapping("/adminHome")
    public String cargarAdminHomeConUsuarios(Model model) {
        String url = apiBaseUrl + "/api/usuarios/all";
        try {
            ResponseEntity<Usuario[]> response = restTemplate.getForEntity(url, Usuario[].class);
    
            if (response.getStatusCode() == HttpStatus.OK) {
                Usuario[] usuarios = response.getBody();
                model.addAttribute("usuarios", usuarios);
            } else {
                model.addAttribute("error", "No se pudieron obtener los usuarios");
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Error al conectar con la API");
        }
    
        return "adminHome"; // Vista que ya tenías
    }
    


    @PostMapping("/login-admin")
    public String loginAdmin(@RequestParam("email") String email, @RequestParam("password") String password, Model model) {
        String url = apiBaseUrl + "/api/usuarios/login-admin";
        try {
            MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
            requestBody.add("Email", email);
            requestBody.add("Password", password);
    
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);
    
            ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);
    
            if (response.getStatusCode() == HttpStatus.OK) {
                return "redirect:/adminHome";
            } else {
                // Capturar el mensaje de error exacto del servidor
                model.addAttribute("error", response.getBody());
                return "index";
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Error de conexión con el servidor");
            return "index";
        }
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam("email") String email, @RequestParam("password") String password, Model model) {
        String url = apiBaseUrl + "/api/usuarios/login";
        try {
            MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
            requestBody.add("Email", email);
            requestBody.add("Password", password);
    
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);
    
            ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);
    
            if (response.getStatusCode() == HttpStatus.OK) {
                return "redirect:/userHome";
            } else {
                model.addAttribute("error", response.getBody());
                return "index";
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Error de conexión con el servidor");
            return "index";
        }
    }

    @PostMapping("/add-user")
    public String addUser(@RequestParam("nombre") String nombre, 
                        @RequestParam("correo") String correo, 
                        @RequestParam("password") String password, 
                        Model model) {
        String url = apiBaseUrl + "/api/usuarios/add"; // Endpoint en UsuarioController
        try {
            // Crear el cuerpo de la solicitud como parámetros de formulario
            MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
            requestBody.add("nombre", nombre);
            requestBody.add("correo", correo);
            requestBody.add("password", password);

            // Configurar los encabezados
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            // Crear la entidad HTTP
            HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

            // Enviar la solicitud POST al UsuarioController
            ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);

            // Manejar la respuesta
            if (response.getStatusCode() == HttpStatus.CREATED) {
                model.addAttribute("success", "Usuario agregado correctamente.");
            } else {
                model.addAttribute("error", "Error al agregar el usuario: " + response.getBody());
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Error de conexión con el servidor.");
        }
        return cargarAdminHomeConUsuarios(model);
    }

    @PostMapping("/delete-user")
    public String deleteUser(@RequestParam("correo") String correo, Model model) {
        String url = apiBaseUrl + "/api/usuarios/delete/" + correo; // Endpoint en UsuarioController
        try {
            // Crear la entidad HTTP (puede ser null para DELETE)
            @SuppressWarnings("null")
            HttpEntity<Void> requestEntity = new HttpEntity<>(null);
    
            // Enviar la solicitud DELETE al UsuarioController
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, String.class);
    
            // Manejar la respuesta
            if (response.getStatusCode() == HttpStatus.OK) {
                model.addAttribute("success", "Usuario eliminado correctamente.");
            } else {
                model.addAttribute("error", "Error al eliminar el usuario: " + response.getBody());
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Error de conexión con el servidor.");
        }
    
        // Llamar al método cargarAdminHomeConUsuarios para recargar la lista de usuarios
        return cargarAdminHomeConUsuarios(model);
    }
}
