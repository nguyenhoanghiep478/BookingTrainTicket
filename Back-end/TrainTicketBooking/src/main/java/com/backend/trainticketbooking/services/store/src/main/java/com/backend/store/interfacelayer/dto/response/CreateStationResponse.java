package com.backend.store.interfacelayer.dto.response;

import com.backend.store.interfacelayer.dto.objectDTO.AddressDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateStationResponse {
    private String name;
    private AddressDTO address;
}
