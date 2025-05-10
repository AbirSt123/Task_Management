package ma.ensat.taskmanagement.dto;

public class AuthResponse {
    private String message;
    private String token;
    private String role;

    public AuthResponse(String message, String token, String role) {
        this.message = message;
        this.token = token;
        this.role = role;
    }

    public AuthResponse(String message) {
        this.message = message;
    }

    public AuthResponse(String message, String token) {
        this.message = message;
        this.token = token;
    }

    public String getToken() {
        return token;
    }


    public String getMessage() {
        return message;
    }


    public String getRole() {
        return role;
    }

}
