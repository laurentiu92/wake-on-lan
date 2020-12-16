package com.wake.on.lan.service;

import com.wake.on.lan.model.WakeRequestBody;
import lombok.SneakyThrows;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.net.DatagramSocket;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class WakeOnLanServiceTest {

    @Autowired
    WakeOnLanService wakeOnLanService;

    @Test
    @SneakyThrows
    public void should_return_ok(){

        WakeRequestBody req = WakeRequestBody.builder()
                .authentication("dGVzdA==")
                .build();

        DatagramSocket socket = spy(new DatagramSocket());
        doNothing().when(socket).send(any());

        ResponseEntity<String> resp = wakeOnLanService.pcWake(req);

        assertThat(resp.getStatusCode(), is(HttpStatus.OK));
        assertThat(resp.getBody(), is("Pc has been waked successfully"));
    }

    @Test
    public void when_wrong_auth_expect_unauthorized(){
        WakeRequestBody req = WakeRequestBody.builder()
                .authentication("badAuth")
                .build();
        ResponseEntity<String> resp = wakeOnLanService.pcWake(req);

        assertThat(resp.getStatusCode(), is(HttpStatus.UNAUTHORIZED));
        assertThat(resp.getBody(), is("Unauthorized"));

    }

    @Test
    public void when_wrong_ip_format_expect_internal_server_error(){
        WakeRequestBody req = WakeRequestBody.builder()
                .authentication("dGVzdA==")
                .build();

        ReflectionTestUtils.setField(wakeOnLanService, "ip", "999.999.999.999");

        ResponseEntity<String> resp = wakeOnLanService.pcWake(req);

        assertThat(resp.getStatusCode(), is(HttpStatus.INTERNAL_SERVER_ERROR));
        assertThat(resp.getBody(), is("Failed to send Wake-on-LAN packet"));

    }

    @Test
    public void when_wrong_mac_format_expect_internal_server_error(){
        WakeRequestBody req = WakeRequestBody.builder()
                .authentication("dGVzdA==")
                .build();

        ReflectionTestUtils.setField(wakeOnLanService, "mac", "some:wrong:mac:format");

        ResponseEntity<String> resp = wakeOnLanService.pcWake(req);

        assertThat(resp.getStatusCode(), is(HttpStatus.INTERNAL_SERVER_ERROR));
        assertThat(resp.getBody(), is("Failed to send Wake-on-LAN packet"));

    }

}
