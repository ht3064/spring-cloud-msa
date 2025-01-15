package com.example.catalogservice.application;

import com.example.catalogservice.domain.Catalog;

public interface CatalogService {
    Iterable<Catalog> getAllCatalogs();
}
