package com.backend.store.interfacelayer.dto.objectDTO;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressDTO {
    @NotNull(message = "street is required")
    private String street;
    @NotNull(message = "city is required")
    private String city;
    @NotNull(message = "state is required")
    private String state;
}
