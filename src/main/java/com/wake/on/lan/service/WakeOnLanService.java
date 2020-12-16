package com.wake.on.lan.service;

import com.wake.on.lan.model.WakeRequestBody;
import com.wake.on.lan.util.WakeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Base64;


@Service
public class WakeOnLanService {

    @Value("${authentication.key}")
    private String authenticationKey;

    @Value("${target.ip}")
    private String ip;

    @Value("${target.mac}")
    private String mac;

    @Value("${target.port}")
    private int port;

    private static final Logger logger = LoggerFactory.getLogger(WakeOnLanService.class);

    public ResponseEntity<String> pcWake(WakeRequestBody body) {

        if(!(isAuthenticated(body.getAuthentication())))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");

        try {
            sendWakeSignal();
        } catch (Exception e) {
            logger.error("Failed to send Wake-on-LAN packet: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send Wake-on-LAN packet");

        }

        return ResponseEntity.ok().body("Pc has been waked successfully");

    }


    private void sendWakeSignal() throws Exception {
            byte[] macBytes = WakeUtil.getMacBytes(mac);
            byte[] bytes = new byte[6 + 16 * macBytes.length];
            for (int i = 0; i < 6; i++) {
                bytes[i] = (byte) 0xff;
            }
            for (int i = 6; i < bytes.length; i += macBytes.length) {
                System.arraycopy(macBytes, 0, bytes, i, macBytes.length);
            }

            InetAddress address = InetAddress.getByName(ip);
            DatagramPacket packet = new DatagramPacket(bytes, bytes.length, address, port);
            DatagramSocket socket = new DatagramSocket();
            socket.send(packet);
            socket.close();

            logger.info("Wake-on-LAN packet sent.");
    }

    private boolean isAuthenticated(String authString){
        byte[] decodedBytes = Base64.getDecoder().decode(authString);
        String decodedAuthString = new String(decodedBytes);

        if(authenticationKey.equals(decodedAuthString)) return true;
        return false;
    }
}
