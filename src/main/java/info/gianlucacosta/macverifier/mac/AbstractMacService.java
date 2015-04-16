/*§
  ===========================================================================
  MacVerifier
  ===========================================================================
  Copyright (C) 2015 Gianluca Costa
  ===========================================================================
  Permission is hereby granted, free of charge, to any person obtaining a copy
  of this software and associated documentation files (the "Software"), to deal
  in the Software without restriction, including without limitation the rights
  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
  copies of the Software, and to permit persons to whom the Software is
  furnished to do so, subject to the following conditions:

  The above copyright notice and this permission notice shall be included in
  all copies or substantial portions of the Software.

  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
  THE SOFTWARE.
  ===========================================================================
*/

package info.gianlucacosta.macverifier.mac;

import javax.crypto.Mac;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

/**
 * Basic implementation of MacService, providing a ready-made infrastructure.
 *
 * @see info.gianlucacosta.macverifier.mac.HMacSha256Service
 */
public abstract class AbstractMacService implements MacService {

    private final int bufferSize;

    private SecureRandom secureRandom;

    public AbstractMacService(int bufferSize) {
        this.bufferSize = bufferSize;
    }

    private SecureRandom getSecureRandom() {
        if (secureRandom == null) {
            try {
                secureRandom = SecureRandom.getInstance("SHA1PRNG");
            } catch (NoSuchAlgorithmException ex) {
                throw new RuntimeException(ex);
            }
        }

        return secureRandom;
    }

    protected byte[] createSalt(int size) {
        byte[] salt = new byte[size];

        getSecureRandom().nextBytes(salt);

        return salt;
    }

    protected Key createMacKey(String password, byte[] salt) {
        try {
            int macKeySizeInBits = getKeySizeInBits();
            KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, 65536, macKeySizeInBits);

            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            return secretKeyFactory.generateSecret(keySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            throw new RuntimeException(ex);
        }
    }

    protected abstract int getKeySizeInBits();


    private Mac createMac() {
        try {
            return Mac.getInstance(getMacAlgorithm());
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        }
    }

    protected abstract String getMacAlgorithm();


    @Override
    public MacResult computeMac(String password, byte[] salt, InputStream sourceStream) throws IOException {
        Mac mac = createMac();

        Key macKey = createMacKey(password, salt);
        try {
            mac.init(macKey);
        } catch (InvalidKeyException ex) {
            throw new RuntimeException(ex);
        }

        byte[] buffer = new byte[bufferSize];

        while (true) {
            int readBytesCount = sourceStream.read(buffer);

            if (readBytesCount == -1) {
                break;
            }

            mac.update(buffer, 0, readBytesCount);
        }

        byte[] macValue = mac.doFinal();

        return new MacResult(salt, macValue);
    }
}
