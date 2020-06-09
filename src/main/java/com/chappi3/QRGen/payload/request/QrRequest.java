package com.chappi3.QRGen.payload.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class QrRequest {

    @NotBlank
    @Size(max = 20)
    @Getter
    @Setter
    private String name;

    @NotBlank
    @Getter
    @Setter
    private String text;
}
