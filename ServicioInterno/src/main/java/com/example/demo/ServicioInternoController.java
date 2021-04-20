package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServicioInternoController {

	@Autowired
	private Mailer mailer;

	@RequestMapping(method = RequestMethod.POST, value = "/enviarCorreo")
	public void envioCorreo(@RequestBody String datosCorreo) {
		System.out.println(datosCorreo);
		mailer.sendMail(datosCorreo);
	}
}