package com.tradiumapp.swingtradealerts.auth.service.impl;

import com.tradiumapp.swingtradealerts.auth.firebase.FirebaseTokenHolder;
import com.tradiumapp.swingtradealerts.auth.service.FirebaseService;
import com.tradiumapp.swingtradealerts.auth.service.shared.FirebaseParser;
import org.springframework.stereotype.Service;
//import rs.pscode.pomodorofire.config.auth.firebase.FirebaseTokenHolder;
//import rs.pscode.pomodorofire.service.FirebaseService;
//import rs.pscode.pomodorofire.service.shared.FirebaseParser;
//import rs.pscode.pomodorofire.spring.conditionals.FirebaseCondition;

@Service
public class FirebaseServiceImpl implements FirebaseService {
	@Override
	public FirebaseTokenHolder parseToken(String firebaseToken) {
		return new FirebaseParser().parseToken(firebaseToken);
	}
}
