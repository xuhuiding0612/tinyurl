package com.myprojects.tinyurl.controller;

import com.myprojects.tinyurl.domain.LongURL;
import com.myprojects.tinyurl.domain.ResponseDTO;
import com.myprojects.tinyurl.service.URLService;
import com.myprojects.tinyurl.service.URLServiceAdvance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
public class URLController {

    @Autowired
    private URLService urlService;
    @Autowired
    private URLServiceAdvance urlServiceAdvance;

    // GET
//    @RequestMapping("/")
//    public ResponseEntity<ResponseDTO<String>> redirect(@RequestParam(value = "shortURL") String shortURL) {
//        String longURL = urlService.decode(shortURL);
//        ResponseDTO<String> responseDTO = new ResponseDTO<>();
//        responseDTO.setResult(longURL);
//        responseDTO.setSuccess(true);
//        return new ResponseEntity<>(responseDTO, HttpStatus.MOVED_PERMANENTLY);
//    }

    // following code will redirect to the web page directly.
    @RequestMapping("/")
    public ResponseEntity<ResponseDTO<String>> redirect(@RequestParam(value = "shortURL") String shortURL) throws URISyntaxException {
//        String longURL = urlService.decode(shortURL);
        String longURL = urlServiceAdvance.decode(shortURL);
        URI uri = new URI(longURL);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(uri);
        return new ResponseEntity<>(httpHeaders, HttpStatus.MOVED_PERMANENTLY);
    }


    // POST
    @RequestMapping("/shorten")
    public ResponseEntity<ResponseDTO<String>> shorten(@RequestBody LongURL longURL) {
//        String shortURL = urlService.encode(longURL.getLongURL());
        String shortURL = urlServiceAdvance.encode(longURL.getLongURL());
        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        responseDTO.setResult(shortURL);
        responseDTO.setSuccess(true);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}
