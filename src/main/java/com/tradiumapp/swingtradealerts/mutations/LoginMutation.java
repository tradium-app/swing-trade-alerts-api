package com.tradiumapp.swingtradealerts.mutations;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.tradiumapp.swingtradealerts.models.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

@Component
public class LoginMutation implements GraphQLMutationResolver {
    Logger logger = LoggerFactory.getLogger(LoginMutation.class);

    @Value("${SERVICE_ACCOUNT_KEY}")
    private String serviceAccountKey;

    @PostConstruct
    public void init() throws IOException {
        byte[] decodedBytes = Base64.getDecoder().decode(serviceAccountKey);
        InputStream serviceStream =new ByteArrayInputStream(decodedBytes);
        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(new BufferedInputStream(serviceStream)))
                .build();

        FirebaseApp.initializeApp(options);
    }

    public Response loginUser(final String accessToken) {
        logger.info("inside loginUser");

        return new Response(true, "test message");
    }
}
