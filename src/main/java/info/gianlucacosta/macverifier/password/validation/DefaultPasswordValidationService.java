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

package info.gianlucacosta.macverifier.password.validation;

/**
 * Default implementation of PasswordValidationService.
 */
public class DefaultPasswordValidationService implements PasswordValidationService {

    @Override
    public void validatePassword(String password) throws InvalidPasswordException {
        //Further checks might be performed - for example, at least one letter, 
        //at least one digit and at least one special character.
        //But, for now, I prefer to leave the choice to the user.
        if (password.length() < 8) {
            throw new InvalidPasswordException("The password must be at least 8 characters long");
        }
    }
}
