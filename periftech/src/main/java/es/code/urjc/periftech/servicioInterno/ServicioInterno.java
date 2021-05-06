package es.code.urjc.periftech.servicioInterno;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ServicioInterno {
    public void sendMail(String string) {	
        HttpEntity<String> httpEntity = new HttpEntity<>(string);
        
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity res = restTemplate.postForEntity("http://servicio-interno:8080/enviarCorreo", httpEntity, ResponseEntity.class);
	}
}
