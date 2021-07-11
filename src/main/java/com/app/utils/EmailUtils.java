package com.app.utils;

import java.io.BufferedReader;
import java.io.FileReader;

import javax.annotation.PostConstruct;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailUtils {

	private static JavaMailSender mailSnder;

	@PostConstruct
	private void init() {
		mailSnder = this.mailSender;
	}

	@Autowired
	private JavaMailSender mailSender;

	public static boolean sendEmail(String to, String subject, String body) {
		boolean isSent = false;
		MimeMessage mimeMessage = mailSnder.createMimeMessage();
		try {
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
			mimeMessageHelper.setSubject(subject);
			mimeMessageHelper.setTo(to);
			mimeMessageHelper.setText(body, true);
			mailSnder.send(mimeMessageHelper.getMimeMessage());
			isSent = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isSent;
	}

	public static String readFile(String fileName) {
		StringBuilder sb = new StringBuilder(500);
		String mailBody = "";
		try (FileReader fr = new FileReader(fileName); BufferedReader br = new BufferedReader(fr)) {
			String line = br.readLine();
			while (line != null) {
				sb.append(line);
				line = br.readLine();
			}
			mailBody = sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mailBody;
	}
}
