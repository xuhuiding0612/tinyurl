package com.myprojects.tinyurl.service;

import com.myprojects.tinyurl.repositiory.LongURLRepository;
import com.myprojects.tinyurl.domain.LongURL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class URLServiceAdvance {
    // Algorithm is based on StackOverFlow:) https://stackoverflow.com/a/17592413

    @Autowired
    private LongURLRepository longURLRepository;

    public String encode(String longURL) {
        LongURL newLongURL = longURLRepository.findByLongURL(longURL);
        if (newLongURL == null) {
            newLongURL = new LongURL(longURL);
            longURLRepository.saveAndFlush(newLongURL);
        }
        String shortURL = base10ToBase62(permuteId(newLongURL.getId().intValue()));
        return "http://zhuang.zhuang/" + shortURL;
    }

    public String decode(String shortURL) {
        shortURL = shortURL.substring("http://zhuang.zhuang/".length());
        int id = permuteId(base62ToBase10(shortURL));
        LongURL longURL = longURLRepository.getOne((long) id);
        return longURL.getLongURL();
    }

    private char Base62Digit(int d) {
        if (d < 26) {
            return (char)('a' + d);
        } else if (d < 52) {
            return (char)('A' + d - 26);
        } else if (d < 62) {
            return (char)('0' + d - 52);
        } else {
            throw new IllegalArgumentException(Integer.toString(d));
        }
    }

    private String base10ToBase62(int n) {
        StringBuilder sb = new StringBuilder();
        while (n != 0) {
            sb.insert(0, Base62Digit(n % 62));
            n /= 62;
        }
        return sb.toString();
    }

    private int base62Decode(char c) {
        if (c >= '0' && c <= '9') {
            return 52 + c - '0';
        } else if (c >= 'A' && c <= 'Z') {
            return 26 + c - 'A';
        } else if (c >= 'a' && c <= 'z') {
            return c - 'a';
        } else {
            throw new IllegalArgumentException("c");
        }
    }

    private int base62ToBase10(String s) {

//        return s.Aggregate(0, (current, c) => current*62 + Base62Decode(c));
        int current = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            current = current*62 + base62Decode(c);
        }
        return current;
    }

    private int permuteId(int id) {
        int l1 = (id >> 16) & 65535;
        int r1 = id & 65535;
        int l2, r2;
        for (int i = 0; i < 3; i++)
        {
            l2 = r1;
            r2 = l1 ^ (int)(roundFunction(r1) * 65535);
            l1 = l2;
            r1 = r2;
        }
        return ((r1 << 16) + l1);
    }

    private double roundFunction(int input) {
      // Must be a function in the mathematical sense (x=y implies f(x)=f(y))
      // but it doesn't have to be reversible.
      // Must return a value between 0 and 1
      return ((1369 * input + 150889) % 714025) / 714025.0;
    }
}
