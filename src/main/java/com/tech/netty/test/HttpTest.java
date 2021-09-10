package com.tech.netty.test;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;

/**
 * @author lw
 * @since 2021/9/3
 */
public class HttpTest {
    public static void main(String[] args) {
        String url="https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.pconline.com.cn%2Fimages%2Fupload%2Fupc%2Ftx%2Fphotoblog%2F1309%2F25%2Fc49%2F26316176_26316176_1380092693834_mthumb.jpg&refer=http%3A%2F%2Fimg.pconline.com.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1633486134&t=345c1e1e087c7ea62c075b398fb7d5d2";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.131 Safari/537.36");
        httpHeaders.add("Content-Type","image/jpeg");
        MultiValueMap<Object, Object> map = new LinkedMultiValueMap<>();
        HttpEntity<Object> httpEntity = new HttpEntity(httpHeaders,map);
        ResponseEntity<byte[]> entity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, byte[].class);
        byte[] body = entity.getBody();
        File file = new File("d://",System.nanoTime()+".jpg");
        try {
            FileCopyUtils.copy(body,file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(body.length);


    }
}
