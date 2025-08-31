package com.bulish.dto;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UserOperationEvent implements Serializable {
    private UserOperation userOperation;
    private String email;
}