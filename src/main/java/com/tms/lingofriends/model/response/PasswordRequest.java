package com.tms.lingofriends.model.response;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordRequest {
    private int id;
    @Size(min = 10, max = 100)
    private String oldPassword;
    @Size(min = 10, max = 100)
    private String newPassword;
}