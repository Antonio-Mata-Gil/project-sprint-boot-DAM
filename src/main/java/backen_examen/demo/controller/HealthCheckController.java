package backen_examen.demo.controller;

import backen_examen.demo.dto.AuthResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/healthcheck")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8080", "http://127.0.0.1:3000", "http://127.0.0.1:8080"}, allowCredentials = "true")
public class HealthCheckController {
    
    @GetMapping
    public ResponseEntity<?> healthCheck(@RequestHeader(value = "Rol", required = false) String rol) {
        AuthResponseDTO auth = new AuthResponseDTO(rol);
        
        // Solo ADMINISTRADOR puede acceder a healthcheck
        if (!auth.esAdministrador()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Solo ADMINISTRADOR puede acceder a healthcheck");
        }
        
        return ResponseEntity.ok("En funcionamiento");
    }
}
