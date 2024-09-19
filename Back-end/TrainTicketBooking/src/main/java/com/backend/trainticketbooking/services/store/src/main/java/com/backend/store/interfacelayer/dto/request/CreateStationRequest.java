package com.backend.store.interfacelayer.dto.request;

import com.backend.store.interfacelayer.dto.objectDTO.AddressDTO;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateStationRequest {
    @NotNull(message = "station name is required")
    private String name;
    @NotNull(message = "address is required ")
    private AddressDTO address;
}
