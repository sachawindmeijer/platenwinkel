package com.example.platenwinkel.controllers;

import com.example.platenwinkel.dtos.input.LpProductInputDto;
import com.example.platenwinkel.dtos.output.LpProductOutputDto;
import com.example.platenwinkel.exceptions.InvalidInputException;
import com.example.platenwinkel.helper.BindingResultHelper;
import com.example.platenwinkel.service.LpProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/lpproducts")
public class LpProductController {


    private final LpProductService lpProductService;

    public LpProductController(LpProductService lpProductService) {
        this.lpProductService = lpProductService;
    }


    @PostMapping
    public ResponseEntity<LpProductOutputDto> addLpProduct(@Valid @RequestBody LpProductInputDto lpProductInputDto, BindingResult bindingResult) {
        //convert this part to a helper class
        if (bindingResult.hasErrors()) {
            throw new InvalidInputException("Somthing went wrong, please check the following fields. " + BindingResultHelper.getErrorMessage(bindingResult));
        }
        LpProductOutputDto lpProduct = lpProductService.addLpProduct(lpProductInputDto);

        URI uri = URI.create(
                ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/" + lpProduct.getId()).toUriString());


        return ResponseEntity.created(uri).body(lpProduct);
    }
    @PutMapping("/{id}")
    public ResponseEntity<LpProductOutputDto> updateLpProduct(@PathVariable Long id, @Valid @RequestBody LpProductInputDto lpProductInputDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidInputException("Something went wrong, please check the following fields: " + BindingResultHelper.getErrorMessage(bindingResult));
        }

        LpProductOutputDto updatedLpProduct = lpProductService.updateLpProduct(id, lpProductInputDto);
        return ResponseEntity.ok(updatedLpProduct);
    }

    @GetMapping
    public ResponseEntity<List<LpProductOutputDto>> getAllLps(@RequestParam(value = "artist", required = false) Optional<String> artist) {
        List<LpProductOutputDto> dtos;

        if (artist.isEmpty()) {
            dtos = lpProductService.getAllLps();
        } else {
            dtos = lpProductService.getAllLpProductsByArtist(artist.get());
        }
        return ResponseEntity.ok().body(dtos);
    }

    @GetMapping("/{artist}")
    public ResponseEntity<List<LpProductOutputDto>>getLpProductByArtist(@PathVariable String artist){

        List<LpProductOutputDto> dtos;

        try {
            if (artist == null || artist.isEmpty()) {
                dtos = lpProductService.getAllLps();
            } else {
                dtos = lpProductService.getAllLpProductsByArtist(artist);
            }
            return ResponseEntity.ok().body(dtos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteLpProduct(@PathVariable Long id) {
        System.out.println("Received request to delete LP product with ID: " + id);


        try {
            lpProductService.deletelpproduct(id);
            System.out.println("LP product deleted successfully");
        } catch (Exception e) {
            System.out.println("Error occurred: " + e.getMessage());
            throw e;
        }
        return ResponseEntity.noContent().build();
    }
}
