package com.chappi3.QRGen.payload.response;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@RequiredArgsConstructor
public class JwtResponse {

    @Getter
    @Setter
    @NonNull
    private String token;

    @Getter
    @Setter
    private String type = "Bearer";

    @Getter
    @Setter
    @NonNull
    private Long id;

    @Getter
    @Setter
    @NonNull
    private String username;

    @Getter
    @Setter
    @NonNull
    private String email;

    @Getter
    @NonNull
    private List<String> roles;

}
