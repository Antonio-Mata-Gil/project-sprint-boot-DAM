package backen_examen.demo.dto;

/**
 * Middleware básico para verificar permisos de usuario
 * CLIENTE: Solo GET
 * ADMINISTRADOR: Todos los métodos (GET, POST, PUT, DELETE)
 */
public class AuthResponseDTO {
    private String rol; // CLIENTE o ADMINISTRADOR
    
    public AuthResponseDTO() {
    }
    
    public AuthResponseDTO(String rol) {
        this.rol = rol;
    }
    
    public String getRol() {
        return rol;
    }
    
    public void setRol(String rol) {
        this.rol = rol;
    }
    
    // Middleware: verificar si es ADMINISTRADOR
    public boolean esAdministrador() {
        return rol != null && rol.equals("ADMINISTRADOR");
    }
    
    // Middleware: verificar si es CLIENTE
    public boolean esCliente() {
        return rol != null && rol.equals("USUARIO");
    }
    
    // Middleware: verificar si usuario está autenticado
    public boolean estaAutenticado() {
        return rol != null;
    }
}
