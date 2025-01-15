package com.example.catalogservice.api;

import com.example.catalogservice.domain.Catalog;
import com.example.catalogservice.dto.ResponseCatalogDto;
import com.example.catalogservice.application.CatalogService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/catalog-service")
@RequiredArgsConstructor
public class CatalogController {
    private final CatalogService catalogService;
    private final Environment env;

    @GetMapping("/health_check")
    public String status() {
        return String.format("It's Working in Catalog Service on PORT %S", env.getProperty("local.server.port"));
    }

    @GetMapping("/catalogs")
    public ResponseEntity<List<ResponseCatalogDto>> getMembers() {
        Iterable<Catalog> catalogList = catalogService.getAllCatalogs();

        List<ResponseCatalogDto> result = new ArrayList<>();
        catalogList.forEach(v -> {
            result.add(new ModelMapper().map(v, ResponseCatalogDto.class));
        });

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
