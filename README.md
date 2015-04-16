# MacVerifier

*Extensible MAC-based Java app, checking file integrity and authenticity*


## Introduction

MacVerifier is a simple - but effective and extensible - command-line tool for checking integrity and authenticity of files using [MAC](http://en.wikipedia.org/wiki/Message_authentication_code), a family of algorithms belonging to the field of *symmetric cryptography*.


## How it works

There are two logical phases, executed by two users, that we'll call *Alice* and *Bob*:

1. Alice and Bob share a password, which they have previously chosen *in a secure way*

2. Alice wants to send Bob a file, for example via e-mail. Before doing that, Alice runs: `MacVerifier <file path>`. The program requests a password, then outputs a new file, having the same path as the original file, but with an additional **.mac** extension.

3. Alice sends both files to Bob

4. Bob downloads the files into a directory on his PC, then invokes the program just like Alice did: `MacVerifier <file path>`; this time, however, MacVerifier will find the **.mac** file and, after asking for the password, will compare the results, showing an error message (and returning an error code) in case of discrepancies.

The integrity and authenticity of the original file is ensured by the MAC algorithm.

It is also interesting to note that the program can also be employed by the very same person in two different moments.


## Technical details

MacVerifier uses the following cryptographic algorithms:

* **HMAC with SHA-256** for MAC computation
* standard JCA **SHA1 PRNG** for salt generation
* **PBKDF2** based on **HMAC with SHA-1** for key generation

For further details, please refer to the [JCA Reference Guide](https://docs.oracle.com/javase/8/docs/technotes/guides/security/crypto/CryptoSpec.html).


## Extending the program

MacVerifier comes with a default implementation of the MAC algorithm; however, it is possible to create and test a custom MAC solution by following a few basic steps:

1. Add [MacVerifier](https://bintray.com/giancosta86/Hephaestus/MacVerifier/) to your Gradle/Maven dependencies

2. Implement the **MacService** interface provided by MacVerifier. 

3. Run `java -cp <MacVerifier's jar and your own jar, separated by ':' on Unix and ';' on Windows> info.gianlucacosta.macverifier.App <file to check> <fully-qualified-name of your class implementing MacService>`