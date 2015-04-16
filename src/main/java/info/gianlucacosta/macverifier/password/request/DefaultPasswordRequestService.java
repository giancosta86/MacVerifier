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

package info.gianlucacosta.macverifier.password.request;

import info.gianlucacosta.macverifier.password.validation.InvalidPasswordException;
import info.gianlucacosta.macverifier.password.validation.PasswordValidationService;
import info.gianlucacosta.macverifier.ui.UserInterface;

/**
 * Default implementation of PasswordRequestService; requests password retyping for confirmation.
 */
public class DefaultPasswordRequestService implements PasswordRequestService {

    private final UserInterface userInterface;
    private final PasswordValidationService passwordValidationService;
    private final boolean requestConfirmation;

    public DefaultPasswordRequestService(UserInterface userInterface, PasswordValidationService passwordValidationService, boolean requestConfirmation) {
        this.userInterface = userInterface;
        this.passwordValidationService = passwordValidationService;
        this.requestConfirmation = requestConfirmation;
    }

    @Override
    public String requestPassword(String prompt) {
        String password;

        while (true) {
            while (true) {
                userInterface.print(prompt);
                password = userInterface.askForPassword();

                if (password == null) {
                    return null;
                }

                try {
                    passwordValidationService.validatePassword(password);
                    break;
                } catch (InvalidPasswordException ex) {
                    userInterface.printlnErr(ex.getMessage());
                }
            }

            if (!requestConfirmation) {
                break;
            }

            userInterface.print("Confirm password: ");
            String confirmationPassword = userInterface.askForPassword();

            if (confirmationPassword == null) {
                return null;
            }

            if (confirmationPassword.equals(password)) {
                break;
            } else {
                userInterface.printlnErr("The confirmation password does not match. Please, retry");
            }

        }

        return password;
    }
}
