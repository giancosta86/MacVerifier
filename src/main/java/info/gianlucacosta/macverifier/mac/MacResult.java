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

import java.io.Serializable;
import java.util.Arrays;

/**
 * Result of a MAC computation, consisting in:
 * <ul>
 * <li>a random <i>salt</i></li>
 * <li>the MAC value itself</li>
 * </ul>
 */
public class MacResult implements Serializable {

    private static final long serialVersionUID = 1L;

    private final byte[] salt;
    private final byte[] macValue;

    public MacResult(byte[] salt, byte[] macValue) {
        this.salt = salt;
        this.macValue = macValue;
    }

    public byte[] getSalt() {
        return salt;
    }

    public byte[] getMacValue() {
        return macValue;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof MacResult)) {
            return false;
        }

        MacResult other = (MacResult) obj;

        return Arrays.equals(salt, other.salt)
                && Arrays.equals(macValue, other.macValue);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(this.macValue);
    }

}
