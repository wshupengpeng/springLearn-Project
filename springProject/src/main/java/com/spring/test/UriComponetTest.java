package com.spring.test;

import org.junit.Test;
import org.springframework.web.util.UriComponentsBuilder;

public class UriComponetTest {

    @Test
    public void componetTest(){
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl("http://localhost");
        uriComponentsBuilder.queryParam("test",null);
        System.out.println(uriComponentsBuilder.toUriString());
    }
}
