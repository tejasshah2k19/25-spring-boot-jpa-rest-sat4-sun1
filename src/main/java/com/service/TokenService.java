package com.service;

import java.util.Random;

import org.springframework.stereotype.Service;

@Service
public class TokenService {
	private static final String ALPHANUMERIC = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

	public String generateToken(int length) {

		Random random = new Random();
		StringBuilder sb = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			int index = random.nextInt(ALPHANUMERIC.length());
			sb.append(ALPHANUMERIC.charAt(index));
		}
		return sb.toString();
	}
}
