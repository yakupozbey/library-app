package com.education.libraryapp.integration.googlebooks.impl;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
        name = "GoogleBooks",
        url = "https://www.googleapis.com/books/v1",
        configuration = DefaultFeignConfig.class)
public interface GoogleBooksFeignClient extends GoogleBooksCallableApi{
}
