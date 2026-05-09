package backen_examen.demo.controller;

import backen_examen.demo.model.Usuario;
import backen_examen.demo.service.UsuarioService;
import backen_examen.demo.dto.AuthResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8080", "http://127.0.0.1:3000", "http://127.0.0.1:8080"}, allowCredentials = "true")
public class UsuarioController {
    
    @Autowired
    private UsuarioService usuarioService;
    
    // CLIENTE: Solo puede hacer GET
    // ADMINISTRADOR: Puede hacer todas las operaciones
    
    @GetMapping
    public ResponseEntity<?> obtenerTodos(@RequestHeader(value = "Rol", required = false) String rol) {
        if (rol == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Se requiere header Rol");
        }
        
        return ResponseEntity.ok(usuarioService.obtenerTodos());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id, @RequestHeader(value = "Rol", required = false) String rol) {
        if (rol == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Se requiere header Rol");
        }
        
        Usuario usuario = usuarioService.obtenerPorId(id);
        return usuario != null ? ResponseEntity.ok(usuario) : ResponseEntity.notFound().build();
    }
    
    @PostMapping
    public ResponseEntity<?> crearUsuario(@RequestBody Usuario usuario, @RequestHeader(value = "Rol", required = false) String rol) {
        AuthResponseDTO auth = new AuthResponseDTO(rol);
        
        if (!auth.esAdministrador()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Solo ADMINISTRADOR puede crear usuarios");
        }
        
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.crearUsuario(usuario));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuario, @RequestHeader(value = "Rol", required = false) String rol) {
        AuthResponseDTO auth = new AuthResponseDTO(rol);
        
        if (!auth.esAdministrador()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Solo ADMINISTRADOR puede actualizar usuarios");
        }
        
        Usuario actualizado = usuarioService.actualizarUsuario(id, usuario);
        return actualizado != null ? ResponseEntity.ok(actualizado) : ResponseEntity.notFound().build();
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Long id, @RequestHeader(value = "Rol", required = false) String rol) {
        AuthResponseDTO auth = new AuthResponseDTO(rol);
        
        if (!auth.esAdministrador()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Solo ADMINISTRADOR puede eliminar usuarios");
        }
        
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
