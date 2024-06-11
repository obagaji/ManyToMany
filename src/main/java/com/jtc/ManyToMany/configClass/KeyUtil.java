package com.jtc.ManyToMany.configClass;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class KeyUtil
{

    private final Environment environment;

    @Value("access-private.key")
    private String accessTokenPrivateKeyPath;

    @Value("access-public.key")
    private String accessTokenPublicKeyPath;

    @Value("refresh-public.key")
    private String refreshTokenPublicKeyPath;

    @Value("refresh-private.key")
    private String refreshTokenPrivateKeyPath;

    private KeyPair accessTokenkeyPair;

    private KeyPair refreshTokenKeyPair;

    public KeyPair getAccessTokenkeyPair()  {

        if (Objects.isNull(accessTokenkeyPair))
        {
            accessTokenkeyPair = getkeyPair(accessTokenPublicKeyPath,accessTokenPrivateKeyPath);
        }

        return accessTokenkeyPair;
    }
    
    public KeyPair getRefreshTokenKeyPair()  {

        if (Objects.isNull(refreshTokenKeyPair))
        {
            refreshTokenKeyPair = getkeyPair(refreshTokenPublicKeyPath,refreshTokenPrivateKeyPath);
        }

        return refreshTokenKeyPair;
    }

    private KeyPair getkeyPair(String publicKeyPath, String privateKeyPath)  {

        KeyPair keyPair;

        File  filePublic = new File(publicKeyPath);

        File filePrivate = new File(privateKeyPath);

        if (filePrivate.exists() && filePublic.exists())
        {

            try {

                KeyFactory keyFactory = KeyFactory.getInstance("RSA");

                byte[] publicKeyByte = Files.readAllBytes(filePublic.toPath());

                EncodedKeySpec encodedKeySpec = new X509EncodedKeySpec(publicKeyByte);

                PublicKey publicKey = keyFactory.generatePublic(encodedKeySpec);



                byte[] privatekeyByte = Files.readAllBytes(filePrivate.toPath());

                PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(privatekeyByte);

                PrivateKey privateKey = keyFactory.generatePrivate(spec);

                keyPair = new KeyPair(publicKey, privateKey);

                return keyPair;

            }

            catch (InvalidKeySpecException| NoSuchAlgorithmException | IOException e)
            {
                throw new RuntimeException(e);
            }

        }

        else
        {
            if(Arrays.stream(environment.getActiveProfiles()).anyMatch(e-> e.equals("prod")))
            {
                throw new RuntimeException("public and private keys does not exist");
            }
        }

        File directory = new File("access-refresh-token-key");

        if (!directory.exists())
        {
            directory.mkdirs();
        }

        try
        {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");

            keyPairGenerator.initialize(2048);

            keyPair = keyPairGenerator.generateKeyPair();


            FileOutputStream fileOut = new FileOutputStream(publicKeyPath);

            X509EncodedKeySpec xSpec = new X509EncodedKeySpec(keyPair.getPublic().getEncoded());

            fileOut.write(xSpec.getEncoded());
        }

        catch (NoSuchAlgorithmException |IOException e)
        {
            throw new RuntimeException(e);
        }

        try
        {
            FileOutputStream fileOutWrite = new FileOutputStream(privateKeyPath);

            PKCS8EncodedKeySpec pk8 = new PKCS8EncodedKeySpec(keyPair.getPrivate().getEncoded());

            fileOutWrite.write(pk8.getEncoded());
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
        return keyPair;
    }

    public RSAPublicKey getAccessPublicKey()  {
        return (RSAPublicKey) getAccessTokenkeyPair().getPublic();
    }

    public RSAPrivateKey getAccessPrivateKey()
    {
        return (RSAPrivateKey) getAccessTokenkeyPair().getPrivate();
    }

    public RSAPublicKey getAccessRefreshPublicKey()  {
        return (RSAPublicKey) getRefreshTokenKeyPair().getPublic();
    }

    public RSAPrivateKey getAccessRefreshPrivateKey()
    {
        return (RSAPrivateKey) getRefreshTokenKeyPair().getPrivate();
    }


}
