from pbkdf2 import PBKDF2
import libnacl.secret
from base64 import b64encode
from base64 import b64decode

class PasswordEncryptor(object):
    def __init__(self, password):
        # Generate a symmetric key from the password
        salt = 'known salt'
        self.key = PBKDF2(password, salt, iterations=2000).read(16).encode('hex')

    def encrypt(self, plaintext_message, nonce=None):
        ## Encryption ##

        # Encrypt the message using the symmetric key
        box = libnacl.secret.SecretBox(self.key)
        encrypted_message = box.encrypt(plaintext_message, nonce=nonce)

        ## Encoding ##

        # # Encode the message in base64
        encoded_message = b64encode(encrypted_message)

        # Make the base64 encoding suitable for passing as a URL parameter
        encoded_message = encoded_message.replace('+', '-')
        encoded_message = encoded_message.replace('/', '_')

        return encoded_message

class PasswordDecryptor(object):
    def __init__(self, password):
        salt = 'known salt'
        self.key = PBKDF2(password, salt, iterations=2000).read(16).encode('hex')

    def decrypt(self, encoded_message):
        # Undo making the base64 encoding URL safe
        encrypted_message = encoded_message.replace('-', '+')
        encrypted_message = encrypted_message.replace('_', '/')

        # Decode the message from base64
        encrypted_message = b64decode(encrypted_message)

        # Decrypt the message
        box = libnacl.secret.SecretBox(self.key)
        decrypted_message = box.decrypt(encrypted_message)

        return decrypted_message


import libnacl.public

class PublicKeyEncryptor(object):
    def __init__(self, recipient_public_key_string):
        # This hardcoded private key is used so that the recipient of the messages *knows* we sent it (it is used to create a digital signature)
        self.my_private_key = libnacl.public.SecretKey('9de45a6041cd2ba834e6920ebe4178c02f34c7503e93ee859d66686bbed4966a'.decode('hex'))
        # This public key is used to encrypt the message
        self.recipient_public_key = libnacl.public.PublicKey(recipient_public_key_string.decode('hex'))

    def encrypt(self, plaintext_message, nonce=None):
        ## Encryption ##

        # Encrypt the message using the recipient's public key
        box = libnacl.public.Box(self.my_private_key, self.recipient_public_key)
        encrypted_message = box.encrypt(plaintext_message, nonce=nonce)

        ## Encoding ##

        # # Encode the message in base64
        encoded_message = b64encode(encrypted_message)

        return encoded_message

class PrivateKeyDecryptor(object):
    def __init__(self, private_key_string):
        # This private key is used to decrypt the message
        self.my_private_key = libnacl.public.SecretKey(private_key_string.decode('hex'))
        # This hardcoded public key matches the hardcoded private key above (it verifies the digital signature)
        self.sender_public_key = libnacl.public.PublicKey('dec6e11f0e7832d1c5b62535a25d9e89a383145af957a6a9c648a47276e0af08'.decode('hex'))

    def decrypt(self, encoded_message):
        # Undo making the base64 encoding URL safe
        encrypted_message = encoded_message.replace('-', '+')
        encrypted_message = encrypted_message.replace('_', '/')

        # Decode the message from base64
        encrypted_message = b64decode(encoded_message)

        # Decrypt the message with my private key
        box = libnacl.public.Box(self.my_private_key, self.sender_public_key)
        decrypted_message = box.decrypt(encrypted_message)

        return decrypted_message
