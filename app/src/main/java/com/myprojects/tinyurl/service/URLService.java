package com.myprojects.tinyurl.service;

import com.myprojects.tinyurl.repositiory.LongURLRepository;
import com.myprojects.tinyurl.domain.LongURL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class URLService {
    // Algorithm is based on post on SegmentFault:) https://segmentfault.com/a/1190000006140476

    @Autowired
    private LongURLRepository longURLRepository;

    private final String elements = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ" ;

    public String encode(String longURL) {
        LongURL newLongURL = longURLRepository.findByLongURL(longURL);
        if (newLongURL == null) {
            newLongURL = new LongURL(longURL);
            longURLRepository.saveAndFlush(newLongURL);
        }
        String shortURL = base10ToBase62(newLongURL.getId().intValue());
        return "http://zhuang.zhuang/" + shortURL;
    }

    public String decode(String shortURL) {
        shortURL = shortURL.substring("http://zhuang.zhuang/".length());
        int id = base62ToBase10(shortURL);
        LongURL longURL = longURLRepository.getOne((long) id);
        return longURL.getLongURL();
    }

    private int base62ToBase10(String s) {
        int n = 0;
        for (int i = 0; i < s.length(); i++) {
            n = n * 62 + convert(s.charAt(i));
        }
        return n;
    }

    private int convert(char c) {
        if (c >= '0' && c <= '9')
            return c - '0';
        if (c >= 'a' && c <= 'z') {
            return c - 'a' + 10;
        }
        if (c >= 'A' && c <= 'Z') {
            return c - 'A' + 36;
        }
        return -1;
    }

    private String base10ToBase62(int n) {
        StringBuilder sb = new StringBuilder();
        while (n != 0) {
            sb.insert(0, elements.charAt(n % 62));
            n /= 62;
        }
        while (sb.length() != 6) {
            sb.insert(0, '0');
        }
        return sb.toString();
    }
}
