package com.chappi3.QRGen.model;

import com.chappi3.QRGen.payload.response.QrResponse;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "qr_codes")
@NoArgsConstructor
@RequiredArgsConstructor
public class Qr {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;

    @NotBlank
    @Size(max = 20)
    @Getter
    @Setter
    @NonNull
    private String name;

    @NotBlank
    @Getter
    @NonNull
    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @Getter
    @NonNull
    private User user;

    public QrResponse toQrResponse() {
        return new QrResponse(this.getId(), this.getName(), this.getText());
    }
}
