package com.example.restapi.client;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.example.restapi.model.EspacioIndividual;
import com.example.restapi.model.Libro;
import com.example.restapi.model.Ordenador;
import com.example.restapi.model.Reserva;
import com.example.restapi.model.SalaGrupal;
import com.example.restapi.model.Usuario;
import com.example.restapi.service.ReservaService;
import com.example.restapi.service.ServicioEspacioIndividual;
import com.example.restapi.service.ServicioLibros;
import com.example.restapi.service.ServicioOrdenadores;
import com.example.restapi.service.ServicioSalaGrupo;

@Controller

public class ClienteWebController {
    private final String apiBaseUrl = "http://localhost:8083";
    private final RestTemplate restTemplate;

    public ClienteWebController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Autowired
    private ReservaService reservaService;

    @Autowired
    private ServicioLibros serivicioLibros;

    @Autowired
    private ServicioEspacioIndividual serivicioEspacioIndividual;

    @Autowired
    private ServicioOrdenadores servicioOrdenadores;

    @Autowired
    private ServicioSalaGrupo servicioSalaGrupo;

    // REDIRECCIONAMIENTO
    @GetMapping("/")
    public String redirectToLogin() {
        return "index";
    }

    @GetMapping("/adminHome")
    public String cargarAdminHomeConUsuarios(Model model) {
        try {
            // Cargar usuarios
            String urlUsuarios = apiBaseUrl + "/api/usuarios/all";
            ResponseEntity<Usuario[]> responseUsuarios = restTemplate.getForEntity(urlUsuarios, Usuario[].class);

            if (responseUsuarios.getStatusCode() == HttpStatus.OK) {
                Usuario[] usuarios = responseUsuarios.getBody();
                model.addAttribute("usuarios", usuarios);
            } else {
                model.addAttribute("error", "No se pudieron obtener los usuarios");
            }

            // Cargar reservas
            String urlReservas = apiBaseUrl + "/api/reservas/all";
            ResponseEntity<Reserva[]> responseReservas = restTemplate.getForEntity(urlReservas, Reserva[].class);

            if (responseReservas.getStatusCode() == HttpStatus.OK) {
                Reserva[] reservas = responseReservas.getBody();
                model.addAttribute("reservas", reservas);
            } else {
                model.addAttribute("error", "No se pudieron obtener las reservas");
            }

            // Cargar ordenadores
            String urlOrdenadores = apiBaseUrl + "/api/ordenadores/all";
            ResponseEntity<Ordenador[]> responseOrdenadores = restTemplate.getForEntity(urlOrdenadores,
                    Ordenador[].class);
            if (responseOrdenadores.getStatusCode() == HttpStatus.OK) {
                model.addAttribute("ordenadores", responseOrdenadores.getBody());
            } else {
                model.addAttribute("error", "No se pudieron obtener los ordenadores");
            }

            // Cargar libros
            String urlLibros = apiBaseUrl + "/api/libros/all";
            ResponseEntity<Libro[]> responseLibros = restTemplate.getForEntity(urlLibros, Libro[].class);
            if (responseLibros.getStatusCode() == HttpStatus.OK) {
                model.addAttribute("libros", responseLibros.getBody());
            } else {
                model.addAttribute("error", "No se pudieron obtener los libros");
            }

            // Cargar salas grupales
            String urlSalasGrupales = apiBaseUrl + "/api/sala-grupal/all";
            ResponseEntity<SalaGrupal[]> responseSalasGrupales = restTemplate.getForEntity(urlSalasGrupales,
                    SalaGrupal[].class);
            if (responseSalasGrupales.getStatusCode() == HttpStatus.OK) {
                model.addAttribute("salasGrupales", responseSalasGrupales.getBody());
            } else {
                model.addAttribute("error", "No se pudieron obtener las salas grupales");
            }

            // Cargar espacios individuales
            String urlEspaciosIndividuales = apiBaseUrl + "/api/Espacios-Individuales/all";
            ResponseEntity<EspacioIndividual[]> responseEspaciosIndividuales = restTemplate
                    .getForEntity(urlEspaciosIndividuales, EspacioIndividual[].class);
            if (responseEspaciosIndividuales.getStatusCode() == HttpStatus.OK) {
                model.addAttribute("espaciosIndividuales", responseEspaciosIndividuales.getBody());
            } else {
                model.addAttribute("error", "No se pudieron obtener los espacios individuales");
            }

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Error al conectar con la API");
        }

        return "adminHome"; // Vista que ya tienes
    }

    @GetMapping("/userHome")
    public String userHome(Model model) {
        return "userHome"; // Redirige a la plantilla userHome.html
    }

    // LOGIN
    @PostMapping("/login-admin")
    public String loginAdmin(@RequestParam("email") String email, @RequestParam("password") String password,
            Model model) {
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
    public String loginUser(@RequestParam("email") String email, @RequestParam("password") String password,
            Model model) {
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

    // USUARIOS ADMIN
    @PostMapping("/add-user")
    public String addUser(@RequestParam("nombre") String nombre, @RequestParam("correo") String correo,
            @RequestParam("password") String password, Model model) {
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

        // Llamar al método cargarAdminHomeConUsuarios para recargar la lista de
        // usuarios
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
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, requestEntity,
                    String.class);

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

        // Llamar al método cargarAdminHomeConUsuarios para recargar la lista de
        // usuarios
        return cargarAdminHomeConUsuarios(model);
    }

    // RESERVAS ADMIN
    @PostMapping("/add-reserva")
    public String addReserva(
            @RequestParam("nombreCliente") String nombreCliente,
            @RequestParam("emailCliente") String emailCliente,
            @RequestParam("fechaReserva") String fechaReservaStr, // <- como String
            @RequestParam("horaReserva") String horaReserva,
            @RequestParam("numPersonas") int numPersonas,
            @RequestParam(required = false) Long libroId,
            @RequestParam(required = false) Long ordenadorId,
            @RequestParam(required = false) Long salaGrupalId,
            @RequestParam(required = false) Long espacioIndividualId,
            Model model) {

        String url = apiBaseUrl + "/api/reservas/add";
        try {
            // Convertir la fecha manualmente
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaReserva = formatter.parse(fechaReservaStr);

            Reserva reserva = new Reserva();
            reserva.setNombreCliente(nombreCliente);
            reserva.setEmailCliente(emailCliente);
            reserva.setFechaReserva(fechaReserva);
            reserva.setHoraReserva(horaReserva);
            reserva.setNumPersonas(numPersonas);
            reserva.setLibroId(libroId);
            reserva.setOrdenadorId(ordenadorId);
            reserva.setSalaGrupalId(salaGrupalId);
            reserva.setEspacioIndividualId(espacioIndividualId);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Reserva> requestEntity = new HttpEntity<>(reserva, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);

            if (response.getStatusCode() == HttpStatus.CREATED) {
                model.addAttribute("success", "Reserva añadida correctamente.");
            } else {
                model.addAttribute("error", "Error al añadir reserva: " + response.getBody());
            }

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Error al procesar la reserva.");
        }

        return "redirect:/adminHome"; // mejor que recargar con método
    }

    @PostMapping("/delete-reserva")
    public String deleteReserva(@RequestParam("id") Long id, Model model) {
        String url = apiBaseUrl + "/api/reservas/" + id;
        try {
            HttpEntity<Void> requestEntity = new HttpEntity<>(null);
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, requestEntity,
                    String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                model.addAttribute("success", "Reserva eliminada correctamente.");
            } else {
                model.addAttribute("error", "Error al eliminar la reserva: " + response.getBody());
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Error de conexión con el servidor.");
        }

        return "redirect:/adminHome";
    }

    // ORDENADORES ADMIN
    @PostMapping("/add-ordenador")
    public String addOrdenador(
            @RequestParam("marca") String marca,
            @RequestParam("modelo") String modelo,
            @RequestParam("numeroSerie") String numeroSerie,
            @RequestParam(value = "disponible", required = false) String disponibleParam,
            Model model) {

        boolean disponible = disponibleParam != null;

        String url = apiBaseUrl + "/api/ordenadores/add";

        try {
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("marca", marca);
            params.add("modelo", modelo);
            params.add("numeroSerie", numeroSerie);
            if (disponible) {
                params.add("disponible", "true"); // checkbox marcado
            }
            // Si no está marcado, no añadimos el parámetro (como en el API)

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);

            if (response.getStatusCode() == HttpStatus.CREATED) {
                model.addAttribute("success", "Ordenador agregado correctamente.");
            } else {
                model.addAttribute("error", "Error al agregar el ordenador: " + response.getBody());
            }

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Error de conexión con la API.");
        }

        return "redirect:/adminHome";
    }

    @PostMapping("/delete-ordenador")
    public String deleteOrdenador(@RequestParam("numeroSerie") String numeroSerie, Model model) {
        String url = apiBaseUrl + "/api/ordenadores/delete-ordenador";

        try {
            // Crear los parámetros del formulario
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("numeroSerie", numeroSerie);

            // Configurar headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            // Crear la entidad con headers y parámetros
            HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);

            // Enviar la petición POST
            ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                model.addAttribute("success", "Ordenador eliminado correctamente.");
            } else {
                model.addAttribute("error", "Error al eliminar el ordenador: " + response.getBody());
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Error de conexión con la API de ordenadores.");
        }

        // Recargar la vista con la lista actualizada de ordenadores y usuarios
        return cargarAdminHomeConUsuarios(model);
    }

    @PostMapping("/update-ordenador")
    public String updateOrdenador(
            @RequestParam("id") Long id,
            @RequestParam("marca") String marca,
            @RequestParam("modelo") String modelo,
            @RequestParam("numeroSerie") String numeroSerie,
            @RequestParam(value = "disponible", required = false) String disponibleParam,
            Model model) {

        boolean disponible = disponibleParam != null; // checkbox o similar

        Ordenador ordenador = new Ordenador(marca, modelo, numeroSerie, disponible);
        try {
            servicioOrdenadores.updateOrdenador(id, ordenador);
            return "redirect:/adminHome?success=Actualizado+correctamente";
        } catch (Exception e) {
            model.addAttribute("error", "Error al actualizar: " + e.getMessage());
            return "adminHome"; // o la vista que uses
        }
    }

    // SALAS GRUPALES ADMIN
    @PostMapping("add-sala-grupal")
    public String addSalaGrupal(
            @RequestParam("piso") int piso,
            @RequestParam("numeroSala") int numeroSala,
            @RequestParam("numeroPersonas") int numeroPersonas,
            Model model) {

        String url = apiBaseUrl + "/api/sala-grupal/add";

        try {
            // Crear parámetros del formulario
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("piso", String.valueOf(piso));
            params.add("numeroSala", String.valueOf(numeroSala));
            params.add("numeroPersonas", String.valueOf(numeroPersonas));

            // Encabezados como formulario
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            // Crear la entidad de la petición
            HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);

            // Hacer la solicitud
            ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);

            if (response.getStatusCode() == HttpStatus.CREATED) {
                model.addAttribute("success", "Sala grupal añadida correctamente.");
            } else {
                model.addAttribute("error", "Error al añadir la sala grupal: " + response.getBody());
            }

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Error de conexión con el servidor.");
        }

        // Recargar datos del panel de administrador
        return "redirect:/adminHome";
    }

    @PostMapping("/edit-sala-grupal")
    public String editSalaGrupal(
            @RequestParam("id") Long id,
            @RequestParam("piso") int piso,
            @RequestParam("numeroSala") int numeroSala,
            @RequestParam("numeroPersonas") int numeroPersonas,
            Model model) {

        String url = apiBaseUrl + "/api/sala-grupal/update/" + id;

        SalaGrupal sala = new SalaGrupal();
        sala.setId(id);
        sala.setPiso(piso);
        sala.setNumeroSala(numeroSala);
        sala.setNumeroPersonas(numeroPersonas);

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");
            HttpEntity<SalaGrupal> requestEntity = new HttpEntity<>(sala, headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    url, HttpMethod.PUT, requestEntity, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                model.addAttribute("success", "Sala grupal editada correctamente.");
            } else {
                model.addAttribute("error", "Error al editar la sala: " + response.getBody());
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Error de conexión con el servidor.");
        }

        return "redirect:/adminHome";
    }

    @PostMapping("/delete-sala-grupal")
    public String deleteSalaGrupal(
            @RequestParam("piso") int piso,
            @RequestParam("numeroSala") int numeroSala,
            Model model) {
        try {
            String url = apiBaseUrl + "/api/sala-grupal/" + piso + "/" + numeroSala;

            HttpEntity<Void> requestEntity = new HttpEntity<>(null);
            ResponseEntity<String> response = restTemplate.exchange(
                    url, HttpMethod.DELETE, requestEntity, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                model.addAttribute("success", "Sala grupal eliminada correctamente.");
            } else {
                model.addAttribute("error", "Error al eliminar la sala grupal: " + response.getBody());
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Error de conexión con el servidor: " + e.getMessage());
        }

        return "redirect:/adminHome";
    }

    // LIBROS ADMIN
    @PostMapping("/add-libro")
    public String addLibro(
            @RequestParam("titulo") String titulo,
            @RequestParam("autor") String autor,
            @RequestParam("isbn") String isbn,
            Model model) {

        String url = apiBaseUrl + "/api/libros/add";
        try {
            // Crear libro
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("titulo", titulo);
            params.add("autor", autor);
            params.add("isbn", isbn);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED); // Porque el controller usa @RequestParam

            HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);

            if (response.getStatusCode() == HttpStatus.CREATED) {
                model.addAttribute("success", "Libro añadido correctamente.");
            } else {
                model.addAttribute("error", "Error al añadir libro: " + response.getBody());
            }

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Error al procesar el libro.");
        }

        return "redirect:/adminHome";
    }

    @PostMapping("/delete-libro")
    public String deleteLibro(@RequestParam("id") Long id, Model model) {
        String url = apiBaseUrl + "/api/libros/delete/" + id;
        try {
            HttpEntity<Void> requestEntity = new HttpEntity<>(null);
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, requestEntity,
                    String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                model.addAttribute("success", "Libro eliminado correctamente.");
            } else {
                model.addAttribute("error", "Error al eliminar el libro: " + response.getBody());
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Error de conexión con el servidor.");
        }

        return "redirect:/adminHome";
    }

    @PostMapping("/update-libro")
    public String updateLibro(
            @RequestParam("id") Long id,
            @RequestParam("titulo") String titulo,
            @RequestParam("autor") String autor,
            @RequestParam("isbn") String isbn,
            Model model) {

        String url = apiBaseUrl + "/api/libros/update/" + id;
        try {
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("titulo", titulo);
            params.add("autor", autor);
            params.add("isbn", isbn);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);

            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                model.addAttribute("success", "Libro actualizado correctamente.");
            } else {
                model.addAttribute("error", "Error al actualizar libro: " + response.getBody());
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Error de conexión con el servidor.");
        }

        return "redirect:/adminHome";
    }

    // ESPACIOS INDIVIDUALES ADMIN
    @PostMapping("/add-espacio-individual")
    public String addEspacioIndividual(
            @RequestParam("piso") int piso,
            @RequestParam("numeroAsiento") int numeroAsiento,
            Model model) {

        String url = apiBaseUrl + "/api/Espacios-Individuales/add";

        try {
            // Enviar como formulario URL encoded (porque el controlador espera
            // @RequestParam)
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("piso", String.valueOf(piso));
            params.add("numeroAsiento", String.valueOf(numeroAsiento));

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);

            if (response.getStatusCode() == HttpStatus.CREATED) {
                model.addAttribute("success", "Espacio individual añadido correctamente.");
            } else {
                model.addAttribute("error", "Error al añadir el espacio individual: " + response.getBody());
            }

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Error de conexión con el servidor.");
        }

        // Puedes redirigir o recargar modelo según lógica similar a "addLibro"
        return "redirect:/adminHome";
    }

    @PostMapping("/delete-espacio-individual")
    public String deleteEspacioIndividual(@RequestParam("id") Long id, Model model) {
        try {
            String url = apiBaseUrl + "/api/Espacios-Individuales/delete/" + id;

            HttpHeaders headers = new HttpHeaders();
            HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

            restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, String.class);

            model.addAttribute("success", "Espacio eliminado correctamente.");
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Error al eliminar el espacio.");
        }

        return "redirect:/adminHome";
    }

    @PostMapping("/update-espacio-individual")
    public String updateEspacioIndividual(
            @RequestParam("id") Long id,
            @RequestParam("piso") int piso,
            @RequestParam("numeroAsiento") int numeroAsiento,
            Model model) {

        String url = apiBaseUrl + "/api/Espacios-Individuales/update/" + id;

        try {
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("piso", String.valueOf(piso));
            params.add("numeroAsiento", String.valueOf(numeroAsiento));

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.PUT,
                    requestEntity,
                    String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                model.addAttribute("success", "Espacio individual actualizado correctamente.");
            } else {
                model.addAttribute("error", "Error al actualizar el espacio: " + response.getBody());
            }

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Error de conexión con el servidor.");
        }

        return "redirect:/adminHome";
    }

    // RESERVAS RECURSOS ADMIN
    @GetMapping("/api/recursos")
    @ResponseBody
    public ResponseEntity<?> getRecursosPorTipo(@RequestParam String tipo) {
        switch (tipo.toLowerCase()) {
            case "libro":
                return ResponseEntity.ok(serivicioLibros.findAll());
            case "ordenador":
                return ResponseEntity.ok(servicioOrdenadores.findAll());
            case "sala":
                return ResponseEntity.ok(servicioSalaGrupo.findAll());
            case "espacio":
                return ResponseEntity.ok(serivicioEspacioIndividual.findAll());
            default:
                return ResponseEntity.badRequest().body("Tipo de recurso no válido");
        }
    }

    @PostMapping("/reservar")
    public String reservarRecurso(
            @RequestParam("nombreCliente") String nombreCliente,
            @RequestParam("emailCliente") String emailCliente,
            @RequestParam("fechaReserva") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaReserva,
            @RequestParam("horaReserva") String horaReserva,
            @RequestParam("numPersonas") int numPersonas,
            @RequestParam(required = false) Long libroId,
            @RequestParam(required = false) Long ordenadorId,
            @RequestParam(required = false) Long salaGrupalId,
            @RequestParam(required = false) Long espacioIndividualId,
            Model model) {

        try {
            // Guardar la reserva
            Reserva reserva = new Reserva();
            reserva.setNombreCliente(nombreCliente);
            reserva.setEmailCliente(emailCliente);
            reserva.setFechaReserva(fechaReserva);
            reserva.setHoraReserva(horaReserva);
            reserva.setNumPersonas(numPersonas);
            reserva.setLibroId(libroId);
            reserva.setOrdenadorId(ordenadorId);
            reserva.setSalaGrupalId(salaGrupalId);
            reserva.setEspacioIndividualId(espacioIndividualId);

            reservaService.crearReserva(reserva); // Guardar en la BD

            model.addAttribute("success", "Reserva guardada correctamente.");
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Error al guardar la reserva.");
        }

        return "userHome"; // Redirige a la página de inicio del usuario
    }
}
