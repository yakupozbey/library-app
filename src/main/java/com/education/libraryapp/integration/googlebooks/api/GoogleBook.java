package com.education.libraryapp.integration.googlebooks.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GoogleBook(List<Item> items) {

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Item(VolumeInfo volumeInfo, SaleInfo saleInfo) {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record VolumeInfo(
            String title,
            List<String> authors,
            String publisher,
            List<IndustryIdentifier> industryIdentifiers
    ) {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record IndustryIdentifier(String type, String identifier) {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record SaleInfo(ListPrice listPrice) {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record ListPrice(Double amount, String currencyCode) {
    }
}
