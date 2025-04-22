package com.example.restapi.client;

import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import org.springframework.http.HttpHeaders;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

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
    public String redirectToAdminHome() {
        return "adminHome";
    }

    public String loginUser(String email, String password){
        String url = apiBaseUrl + "/api/usuarios/login"; // Endpoint correcto
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
                return "redirect:/index";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/index"; 
        }
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
}
