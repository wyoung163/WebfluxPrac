package com.example.webflux.controller;

import com.example.webflux.entity.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class SearchController {
    @GetMapping("/look")
    public String lookAround(@RequestParam("block") boolean block,
                                @RequestParam("people") int people) {
        long start = System.currentTimeMillis(); // 시작 시간
        requestSearching(block, people);
        long end = System.currentTimeMillis(); // 완료 시간
        return (double) (end - start) / 1000 + "초";
    }

    private static void requestSearching(boolean block, int people){
        int n = 0;
        while(people-- > 0){
            Mono<String> mono = WebClient.create()
                    .get()
                    .uri("http://localhost:8080/books/look")
                    .retrieve()
                    .bodyToMono(String.class);
            if(block) {
                System.out.println(mono.block());
            } else {
                mono.subscribe(System.out::println);
            }
        }
    }

    @GetMapping("/getBooks")
    public List<Book> getBooks(@RequestParam("text") String text){
        Flux<Book> flux = WebClient
                .builder()
                .baseUrl("http://localhost:8080/books")
                .build()
                .get()
                .uri(uriBuilder -> uriBuilder.path("")
                        .queryParam("text", text)
                        .build()
                )
                .retrieve()
                .bodyToFlux(Book.class);

        List<Book> books = flux.toStream().collect(Collectors.toList());
        return books;
    }
}
