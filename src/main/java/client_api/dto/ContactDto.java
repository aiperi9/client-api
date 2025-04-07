package client_api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ContactDto {
    @NotBlank(message = "Номер телефона обязателен")
    private String phone;

    @Email(message = "Невалидный email")
    @NotBlank(message = "Email не должен быть пустым")
    private String email;
}

