package com.TaskMicro.Security;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.text.ParseException;
@Component
@RequiredArgsConstructor
public class DecodeJwt {

    private String secret;

    public DecodeJwt(String secret) {
        this.secret = secret;
    }

    public JWTClaimsSet decode(String token) throws ParseException, JOSEException {
        // Parse the token
        SignedJWT signedJWT = SignedJWT.parse(token);

        // Create a verifier using the secret key
        byte[] secretKey = secret.getBytes();
        JWSVerifier verifier = new MACVerifier(secretKey);

        // Verify the token
        if (!signedJWT.verify(verifier)) {
            throw new JOSEException("JWT verification failed");
        }

        // Retrieve the JWT claims
        return signedJWT.getJWTClaimsSet();
    }
}
