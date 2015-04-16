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

package info.gianlucacosta.macverifier.ui;

/**
 * Generic command-line user interface.
 */
public abstract class BasicCommandLineInterface implements UserInterface {

    @Override
    public void print(String message) {
        System.out.print(message);
    }

    @Override
    public void println(String line) {
        System.out.println(line);
    }

    @Override
    public void printlnErr(String line) {
        System.err.println(line);
    }


    @Override
    public void printFatal(String line) {
        printlnErr("");

        printlnErr(line);

        System.exit(1);
    }

    @Override
    public void printFatal(Exception exception) {
        printlnErr("");
        printlnErr(
                String.format("An exception occured:\n\t%s", exception.getMessage())
        );

        printlnErr("");
        printlnErr("");
        printlnErr("-----------");
        printlnErr("STACK TRACE");
        printlnErr("-----------");
        printlnErr("");
        exception.printStackTrace(System.err);

        System.exit(1);
    }
}
