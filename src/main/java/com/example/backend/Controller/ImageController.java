package com.example.backend.Controller;


import com.example.backend.Entity.Image;
import com.example.backend.Entity.Plat;
import com.example.backend.Entity.User;
import com.example.backend.Repository.ImageRepository;
import com.example.backend.Repository.PlatRepository;
import com.example.backend.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@RestController
@RequestMapping("/Image")

public class ImageController {
    @Autowired
    ImageRepository imageRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PlatRepository platRepository;

    @PostMapping("/upload/{idUser}")
    public ResponseEntity<String> uploadImage(@RequestParam("imageFile") MultipartFile file, @PathVariable Long idUser) {
        try {
            Optional<User> userOptional = userRepository.findById(idUser);

            if (userOptional.isPresent()) {
                User userEntity = userOptional.get();
                if (userOptional.get().getImage() != null) {
                    return ResponseEntity.badRequest().body("User already has an image");
                }
                Image img = new Image();
                img.setName(file.getOriginalFilename());
                img.setPicByte(compressBytes(file.getBytes()));
                img.setUser(userOptional.get());
                imageRepository.save(img);
                return ResponseEntity.ok("image ( " + img.getName() + " ) added to user with ID:" + img.getUser().getIdUser()); // Use an appropriate endpoint or identifier
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/getImage/{idUser}")
    public ResponseEntity<Image> getImageByidUser(@PathVariable Long idUser) {
        Optional<User> userOptional = userRepository.findById(idUser);

        if (userOptional.isPresent()) {
            User userEntity = userOptional.get();
            if (userEntity.getImage() != null) {
                Image img = userEntity.getImage();

                img.setPicByte(decompressBytes(img.getPicByte()));
                return ResponseEntity.ok(img);
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // compress the image bytes before storing it in the database
    public static byte[] compressBytes(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        try {
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);

        return outputStream.toByteArray();
    }

    // uncompress the image bytes before returning it to the angular application
    public static byte[] decompressBytes(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
        } catch (IOException | DataFormatException e) {
            e.printStackTrace();
        }
        return outputStream.toByteArray();
    }


    /////// image pour plat /////


    @PostMapping("/uploadPlatImage/{idPlat}")
    public ResponseEntity<String> uploadPlatImage(@RequestParam("imageFile") MultipartFile file, @PathVariable Long idPlat)
    {
        try {
            Optional<Plat> platOptional = platRepository.findById(idPlat);

            if (platOptional.isPresent()) {
                Plat plat = platOptional.get();

                if (plat.getImagePlat() != null) {
                    return ResponseEntity.badRequest().body("Le plat a déjà une image.");
                }

                // Crée l'objet Image sans compresser l'image
                Image img = new Image();
                img.setName(file.getOriginalFilename());
                img.setPicByte(file.getBytes()); // Ici, tu ne compresse pas l'image
                img.setPlat(plat);

                imageRepository.save(img);
                plat.setImagePlat(img);
                platRepository.save(plat);

                return ResponseEntity.ok("Image ajoutée au plat avec ID: " + plat.getIdPlat());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors du traitement de l'image.");
        }
    }

    @GetMapping("/getPlatImage/{idPlat}")
    public ResponseEntity<Image> getPlatImage(@PathVariable Long idPlat) {
        Optional<Plat> platOptional = platRepository.findById(idPlat);

        if (platOptional.isPresent()) {
            Plat plat = platOptional.get();
            if (plat.getImagePlat() != null) {
                Image img = plat.getImagePlat();
                // Pas besoin de décompresser l'image ici
                return ResponseEntity.ok(img);
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}

