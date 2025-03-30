package com.utd.ti.soa.ebs_service.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.utd.ti.soa.ebs_service.models.User;
import com.utd.ti.soa.ebs_service.models.Login;
import com.utd.ti.soa.ebs_service.utils.Auth;


@RestController
@RequestMapping("/api/v1/esb")
public class ESBcontroller {
    private final WebClient webClient = WebClient.create();
    private final Auth auth = new Auth();

    @PostMapping("/user")
    public ResponseEntity<?> createUser(@RequestBody User user,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        System.out.println("Request Body: " + user);
        System.out.println("Token recibido: " + token);

        // Validar token
        if (!auth.validToken(token)) {
            return ResponseEntity.status(401)
                    .body("Token invalido o expirado");
        }

        // Enviar solicitud a servicio externo
        String response = webClient.post()
                .uri("https://users-production-bfce.up.railway.app/api/users/create")
                .bodyValue(user)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return ResponseEntity.ok().body(response);
    }

    @PatchMapping("/user/{id}")
    public ResponseEntity<String> updateUser(@PathVariable String id,
            @RequestBody User user,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        System.out.println("Request Body: " + user);
        System.out.println("Token recibido: " + token);

        // Validar token
        if (!auth.validToken(token)) {
            return ResponseEntity.status(401)
                    .body("Token invalido o expirado");
        }

        System.out.println("Updating user with ID: " + id + " Data: " + user);

        String response = webClient.patch()
                .uri("https://users-production-bfce.up.railway.app/api/users/" + id)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(BodyInserters.fromValue(user))
                .retrieve()
                .bodyToMono(String.class)
                .doOnError(e -> System.out.println("Error: " + e.getMessage()))
                .block();

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/user")
    public ResponseEntity<String> recolectUsers(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {

        System.out.println("Token recibido: " + token);
    
        // Validar token
        if (!auth.validToken(token)) {
            return ResponseEntity.status(401)
                    .body("Token invalido o expirado");
        }
        String response = webClient.get()
                .uri("https://users-production-bfce.up.railway.app/api/users/all")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)  // Asegúrate de que el token esté bien formado
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable String id, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {

        System.out.println("Token recibido: " + token);
    
        // Validar token
        if (!auth.validToken(token)) {
            return ResponseEntity.status(401)
                    .body("Token invalido o expirado");
        }
        String response = webClient.delete()
                .uri("https://users-production-bfce.up.railway.app/api/users/" + id)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Login loginRequest, String token) {
        System.out.println("Request Body: " + loginRequest);
        System.out.println("Token recibido: " + token);

        String response = webClient.post()
                .uri("https://users-production-bfce.up.railway.app/api/users/login")  // Aquí es donde debe apuntar
                .bodyValue(loginRequest)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return ResponseEntity.ok().body(response);
}


}
