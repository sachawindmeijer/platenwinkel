package com.example.platenwinkel.service;

import com.example.platenwinkel.dtos.input.LpProductInputDto;
import com.example.platenwinkel.dtos.mapper.LpProductMapper;
import com.example.platenwinkel.dtos.output.LpProductOutputDto;
import com.example.platenwinkel.exceptions.RecordNotFoundException;
import com.example.platenwinkel.models.LpProduct;
import com.example.platenwinkel.repositories.LpProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LpProductService {
    private final LpProductRepository lpProductRepository;//depandancies


    public LpProductService(LpProductRepository lpProductRepository) {
        this.lpProductRepository = lpProductRepository;
    }

    //--------------------------------------------------------------------------
    // Vanuit de repository kunnen we een lijst van Televisions krijgen, maar de communicatie container tussen Service en
    // Controller is de Dto. We moeten de Televisions dus vertalen naar TelevisionDtos. Dit moet een voor een, omdat
    // de translateToDto() methode geen lijst accepteert als argument, dus gebruiken we een for-loop.
    public List<LpProductOutputDto> getAllLps() {
        List<LpProduct> lpProductlist = lpProductRepository.findAll();
        List<LpProductOutputDto> lpDtoList = new ArrayList<>();

        for (LpProduct lp : lpProductlist) {
            LpProductOutputDto dto = LpProductMapper.fromModelToOutputDto(lp);
            lpDtoList.add(dto);
        }
        return lpDtoList;
    }


    public List<LpProductOutputDto> getAllLpProductsByArtist(String artist) {
        List<LpProduct> lpProductsByArtist = lpProductRepository.findAllLpProductsByArtistEqualsIgnoreCase(artist);
        List<LpProductOutputDto> lpDtoList = new ArrayList<>();

        for (LpProduct lp : lpProductsByArtist) {
            LpProductOutputDto dto = LpProductMapper.fromModelToOutputDto(lp);
            lpDtoList.add(dto);
        }
        return lpDtoList;
    }


    // In deze methode moeten we twee keer een vertaal methode toepassen.
    // De eerste keer van dto naar televsion, omdat de parameter een dto is.
    // De tweede keer van television naar dto, omdat de return waarde een dto is.
    public LpProductOutputDto addLpProduct(LpProductInputDto lpProductInputDto) {
        LpProduct lpProduct = LpProductMapper.fromInputDtoToModel(lpProductInputDto);
        LpProduct savedProduct = lpProductRepository.save(lpProduct);
        return LpProductMapper.fromModelToOutputDto(savedProduct);
    }
    public LpProductOutputDto updateLpProduct(Long id, LpProductInputDto lpProductInputDto) {
        // Retrieve the existing LP product
        Optional<LpProduct> lpProductOptional = lpProductRepository.findById(id);

        // Check if the LP product exists
        if (lpProductOptional.isPresent()) {
            LpProduct existingLpProduct = lpProductOptional.get();

            // Update the fields of the existing LP product with new data from input DTO
            existingLpProduct.setArtist(lpProductInputDto.getArtist());
            existingLpProduct.setAlbum(lpProductInputDto.getAlbum());
            existingLpProduct.setDescription(lpProductInputDto.getDescription());
            existingLpProduct.setGenre(lpProductInputDto.getGenre());
            existingLpProduct.setInStock(lpProductInputDto.getInStock());
            existingLpProduct.setPriceInclVat(lpProductInputDto.getPriceInclVat());
            existingLpProduct.setPriceEclVat(lpProductInputDto.getPriceEclVat());

            // Save the updated LP product back to the repository
            LpProduct updatedProduct = lpProductRepository.save(existingLpProduct);

            // Map the updated LP product to the output DTO and return
            return LpProductMapper.fromModelToOutputDto(updatedProduct);
        } else {
            // Throw an exception if the LP product was not found
            throw new RecordNotFoundException("Geen LP product gevonden met ID: " + id);
        }
    }

    // Dit is de vertaal methode van Television naar TelevisionDto
    // Deze methode is inhoudelijk neit veranderd. Het is alleen verplaatst naar de Service laag.
    public void deletelpproduct(@RequestBody Long id) {

        lpProductRepository.deleteById(id);
    }
    // ---------------Dit sit in de mapper fromInputDtoToModel
//    public LpProduct transferToLpProduct(LpProductInputDto dto) {
//        var lpProduct = new LpProduct();
////        dto.setid
//        lpProduct.setArtist(dto.getArtist());
//        lpProduct.setTitel(dto.getTitel());
//        lpProduct.setDescription(dto.getDescription());
//        lpProduct.setOriginalStock(lpProduct.getOriginalStock());
//        lpProduct.setPrice(lpProduct.getPrice());
////        dto.setsold
//
//        return lpProduct;
//    }

// -----------------Dit sit in de mapper fromModelToOutputDto
//    public LpProductOutputDto transferToDto(LpProduct lpProduct) {
//        LpProductOutputDto dto = new LpProductOutputDto();
////        dto.setid
//        dto.setArtist(lpProduct.getArtist());
//        dto.setTitel(lpProduct.getTitel());
//        dto.setDescription(lpProduct.getDescription());
//        dto.setOriginalStock(lpProduct.getOriginalStock());
//
////        dto.setsold
//
//        return dto;
//    }
}
