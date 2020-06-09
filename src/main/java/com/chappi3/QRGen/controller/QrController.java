package com.chappi3.QRGen.controller;

import com.chappi3.QRGen.model.Qr;
import com.chappi3.QRGen.payload.request.QrRequest;
import com.chappi3.QRGen.payload.response.MessageResponse;
import com.chappi3.QRGen.repository.QrRepository;
import com.chappi3.QRGen.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/qr")
public class QrController {

    private QrRepository qrRepository;
    private UserRepository userRepository;

    @Autowired
    public QrController(QrRepository qrRepository, UserRepository userRepository) {
        this.qrRepository = qrRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getAllQrsByUserId(@PathVariable (value = "userId") Long userId)
    {
        Iterable<Qr> qrCodes = qrRepository.findByUserId(userId);
        return ResponseEntity.ok(StreamSupport.stream(qrCodes.spliterator(), false)
            .map(Qr::toQrResponse)
            .collect(Collectors.toList()));
    }

    @PostMapping("/user/{userId}/create")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> createQrData(@PathVariable (value = "userId") Long userId,
                                          @Valid @RequestBody QrRequest qrRequest) {
        return userRepository.findById(userId).map(user -> {
            Qr newQr = new Qr(qrRequest.getName(), qrRequest.getText(), user);
            qrRepository.save(newQr);
            return ResponseEntity.ok(new MessageResponse("Qr data created successfully!"));
        }).orElseThrow(() -> new RuntimeException("UserId " + userId + " not found."));
    }

    @PutMapping("/user/{userId}/edit/{qrId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> updateQr(@PathVariable (value = "userId") Long userId,
                                      @PathVariable (value = "qrId") Long qrId,
                                      @RequestBody String newName) {
        ObjectMapper objectMapper = new ObjectMapper();
        Qr tmpQr = new Qr();
        try {
            tmpQr = objectMapper.readValue(newName, Qr.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        Qr finalTmpQr = tmpQr;
        return qrRepository.findByIdAndUserId(qrId, userId).map(qr -> {
            qr.setName(finalTmpQr.getName());
            qrRepository.save(qr);
            return ResponseEntity.ok(new MessageResponse("Qr updated successfully!"));
        }).orElseThrow(() -> new RuntimeException("QrId " + qrId + " not found."));
    }

    @DeleteMapping("/user/{userId}/delete/{qrId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> deleteQr(@PathVariable (value = "userId") Long userId,
                                      @PathVariable (value = "qrId") Long qrId) {
        return qrRepository.findByIdAndUserId(qrId, userId).map(qr -> {
            qrRepository.delete(qr);
            return ResponseEntity.ok(new MessageResponse("Qr deleted successfully!"));
        }).orElseThrow(() -> new RuntimeException("QrId " + qrId + " not found."));
    }
}
