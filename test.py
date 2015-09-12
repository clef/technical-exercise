import unittest

from encryptor import (
    PasswordEncryptor,
    PasswordDecryptor,
    PublicKeyEncryptor,
    PrivateKeyDecryptor
)

KNOWN_NONCE = 'c0cf6b267bfe6f90b94bc870226d3adaf0a93a403c0db549'.decode('hex')

class TestPasswordEncryption(unittest.TestCase):
    def test_password_encryption(self):
        """ Verify that encryption matches a known encrypted string """

        PLAINTEXT_MESSAGE =  'This is a really cool encrypted message'
        ENCRYPTED_MESSAGE =  'wM9rJnv-b5C5S8hwIm062vCpOkA8DbVJhqOejlyY7gaR53ROM-QxfG7hxgu65wVawQLysRdFHvhPh9qValXdGr9OsCHJFO35rHHMhBhHWg=='

        password_encryptor = PasswordEncryptor('password')

        encrypted_message = password_encryptor.encrypt(PLAINTEXT_MESSAGE, nonce=KNOWN_NONCE)

        self.assertEqual(encrypted_message, ENCRYPTED_MESSAGE)

    def test_password_decryption(self):
        """ Verify that decryption matches a known plaintext string """

        ENCRYPTED_MESSAGE = 'wM9rJnv-b5C5S8hwIm062vCpOkA8DbVJ3VtFVo8pBaDYyQKsSAAepJ6N0PBTMeWHLR2ErY1UUOrpiqMaPzaAbrvvi7592JXftV3ZMU7wow=='
        PLAINTEXT_MESSAGE = 'This is a really cool encrypted message'

        password_decryptor = PasswordDecryptor('test password')
        decrypted_message = password_decryptor.decrypt(ENCRYPTED_MESSAGE)

        self.assertEqual(decrypted_message, PLAINTEXT_MESSAGE)

    def test_password_encryption_and_decryption(self):
        """ Verify that encryption with a given password matches decryption with the same password """

        PLAINTEXT_MESSAGE =  'This is a really cool encrypted message'

        password_encryptor = PasswordEncryptor('password')
        encrypted_message = password_encryptor.encrypt(PLAINTEXT_MESSAGE)

        password_decryptor = PasswordDecryptor('password')
        decrypted_message = password_decryptor.decrypt(encrypted_message)

        self.assertEqual(PLAINTEXT_MESSAGE, decrypted_message)

RECIPIENT_PUBLIC_KEY = '6ccb208e0bb4d875580ea004884a49e813455d22d7415ff71d6521335458780a'
RECIPIENT_PRIVATE_KEY = '9e6ece687d02d8ae5185f75b8f3a941abc67aa8b40a65954941ad425dbf7cb64'

class TestPublicKeyEncryption(unittest.TestCase):
    def test_public_key_encryption(self):
        """ Verify that encryption matches a known encrypted string """

        PLAINTEXT_MESSAGE = 'This is a secret message ~ for your eyes only'
        ENCRYPTED_MESSAGE = 'wM9rJnv-b5C5S8hwIm062vCpOkA8DbVJ90g-pVWMCufEZfRsrr9yub83Mxx-lO8SkyuX3F5gtqn9DWonypSNHNXyBl9_j2hDci1SRpE4jm4KLX_LLw=='

        public_key_encryptor = PublicKeyEncryptor(RECIPIENT_PUBLIC_KEY)

        encrypted_message = public_key_encryptor.encrypt(PLAINTEXT_MESSAGE, nonce=KNOWN_NONCE)

        self.assertEqual(encrypted_message, ENCRYPTED_MESSAGE)

    def test_public_key_decryption(self):
        """ Verify that decryption matches a known plaintext string """

        ENCRYPTED_MESSAGE = 'wM9rJnv-b5C5S8hwIm062vCpOkA8DbVJ6JJWEScFHnMqSPG63QAcCL83Mxx-lO8SkyuS3E9r866tBWw92JnKFJD_VVh3mA=='
        PLAINTEXT_MESSAGE = 'This is a very special message'

        private_key_decryptor = PrivateKeyDecryptor(RECIPIENT_PRIVATE_KEY)
        decrypted_message = private_key_decryptor.decrypt(ENCRYPTED_MESSAGE)

        self.assertEqual(PLAINTEXT_MESSAGE, decrypted_message)

    def test_public_key_encryption_and_decryption(self):
        """ Verify that encryption with a given password matches decryption with the same password """

        PLAINTEXT_MESSAGE = 'This is another cool message'

        public_key_encryptor = PublicKeyEncryptor(RECIPIENT_PUBLIC_KEY)

        encrypted_message = public_key_encryptor.encrypt(PLAINTEXT_MESSAGE)

        private_key_decryptor = PrivateKeyDecryptor(RECIPIENT_PRIVATE_KEY)
        decrypted_message = private_key_decryptor.decrypt(encrypted_message)

        self.assertEqual(PLAINTEXT_MESSAGE, decrypted_message)

if __name__ == '__main__':
    suite = unittest.TestLoader().loadTestsFromName(__name__)
    unittest.TextTestRunner(verbosity=2).run(suit)
