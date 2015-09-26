# Welcome to the ~~Clef~~ AcmeCorp take-home exercise! 

You should spend **max 2 hours** on this exercise.  Understanding your thought process is *more important* than whether or not you finished, so when you send the exercise back to us, we'd love it if you also gave us a few paragraphs about: 

1. Your process for debugging the issues and fixing them
2. The reasons you chose to refactor code a particular way

This code relates to encryption and decryption -- we **don't** expect you to know anything about cryptography other than the fact that encryption uses a key to scramble messages and decryption unscrambles those messages. We also know that this can be a little scary: try to stick with the exercise, it's about identifying bugs and ways to write better code, not understanding cryptography.

**Other than getting the tests passing, there is no "answer" to this exercise — wherever you are when time is up is where you should stop!**

If you have any questions about the exercise, please don't hesitate to email [jobs@getclef.com](mailto:jobs@getclef.com).

## Getting started

To get started, clone this repo and then run:

```
$ git clone git@github.com:clef/technical-exercise.git
$ cd technical-exercise
$ brew install libsodium
$ pip install -r requirements.txt
```

If you can't use Homebrew to install libsodium, please follow the instructions for downloading and installing it [here](https://download.libsodium.org/doc/).

You should then be able to run tests with: 

```
$ python test.py
```

Good luck! 

## The Big Project

Our AcmeCorp engineers have been hard at work extending some of our existing code for The Big Project, but ran into some snags. 

Before The Big Project, our existing clients required us to encrypt their Widget messages with a **password**. In other words, using a *shared password* known by both them and us, we generated an *secret key* to encrypt and decrypt messages that we passed back and forth. This is called symmetric key cryptography, and it's very similar to how a password manager encrypts your website account passwords using a master password.

To do this, we created two classes, `PasswordEncryptor` and `PasswordDecryptor`.

Our new client wants us to encrypt their Widget messages using a **asymmetric keys**. In other words, we'll encrypt messages we want to send them using *their public key*, which is known by everyone. They'll then decrypt the message using *their private key*, which they don't share with anyone. Private and public key pairs are mathematically related such that a public key can encrypt things, but can't decrypt them -- that's what the private key does.

To do this, we created two classes `PublicKeyEncryptor` and `PrivateKeyDecryptor`. 

We implemented the encryption and decryption functionality and wrote tests that match our existing cases for password-based encryption, but the *tests are failing*, and our engineers can't figure out why. 

That's where you come in. 

We're 100% certain that the underlying algorithms we're using are correct, since we use a well-tested cryptography library called NaCl to do the heavy lifting. We're also certain that the public and private keys, and any hardcoded values, are correct. Finally, we're also certain that the test cases are written correctly, and do not need to be modified for them to pass.

However, our engineers can't even get them to run! 

We'd first like you to **fix the bugs that are causing the tests to fail**. 

After running tests so that each of them pass, we'd like you to **suggest and implement a refactoring that will prevent these kinds of bugs in the future**. 

From all of us at AcmeCorp, we really appreciate your time and help with this one! 

