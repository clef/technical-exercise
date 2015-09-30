require 'rbnacl'
require 'pbkdf2'
require 'base64'

def hex_to_binary(str)
  [str].pack('H*')
end

class PasswordEncryptor
  def initialize(password)
    # Generate a symmetric key from the password
    salt = 'known salt'
    @key = PBKDF2.new(:password => password, :salt => salt, :iterations => 2000, :key_length => 16).hex_string
  end

  def encrypt(plaintext_message, opts={})
    ## Encryption ##

    # Encrypt the message using the symmetric key
    box = RbNaCl::SecretBox.new(@key)
    encrypted_message = box.encrypt(opts[:nonce], plaintext_message)

    ## Encoding ##

    # # Encode the message in base64
    encoded_message = Base64.encode64(encrypted_message)

    # Make the base64 encoding suitable for passing as a URL parameter
    encoded_message = encoded_message.sub('+', '-')
    encoded_message = encoded_message.sub('/', '_')

    encoded_message
  end
end

class PasswordDecryptor
  def initialize(password)
    salt = 'known salt'
    @key = PBKDF2.new(:password => password, :salt => salt, :iterations => 2000, :key_length => 16).hex_string
  end

  def decrypt(encoded_message, opts={})
    # Undo making the base64 encoding URL safe
    encrypted_message = encoded_message.sub('-', '+')
    encrypted_message = encrypted_message.sub('_', '/')

    # Decode the message from base64
    encrypted_message = Base64.decode64(encrypted_message)

    # Decrypt the message
    box = RbNaCl::SecretBox.new(@key)
    decrypted_message = box.decrypt(opts[:nonce], encrypted_message)

    decrypted_message
  end
end

class PublicKeyEncryptor
  def initialize(recipient_public_key_string)
    # This hardcoded private key is used so that the recipient of the messages *knows* we sent it (it is used to create a digital signature)
    @my_private_key = RbNaCl::PrivateKey.new(hex_to_binary('9de45a6041cd2ba834e6920ebe4178c02f34c7503e93ee859d66686bbed4966a'))
    # This public key is used to encrypt the message
    @recipient_public_key = RbNaCl::PublicKey.new(hex_to_binary(recipient_public_key_string))
  end

  def encrypt(plaintext_message, opts={})
    ## Encryption ##

    # Encrypt the message using the recipient's public key
    box = RbNaCl::Box.new(@recipient_public_key, @my_private_key)
    encrypted_message = box.encrypt(opts[:nonce], plaintext_message)

    ## Encoding ##

    # # Encode the message in base64
    encoded_message = Base64.encode64(encrypted_message)

    return encoded_message
  end
end

class PrivateKeyDecryptor
  def initialize(private_key_string)
    # This private key is used to decrypt the message
    @my_private_key = RbNaCl::PrivateKey.new(hex_to_binary(private_key_string))
    # This hardcoded public key matches the hardcoded private key above (it verifies the digital signature)
    @sender_public_key = RbNaCl::PublicKey.new(hex_to_binary('dec6e11f0e7832d1c5b62535a25d9e89a383145af957a6a9c648a47276e0af08'))
  end

  def decrypt(encoded_message, opts={})
    # Undo making the base64 encoding URL safe
    encrypted_message = encoded_message.sub('-', '+')
    encrypted_message = encrypted_message.sub('_', '/')

    # Decode the message from base64
    encrypted_message = Base64.decode64(encoded_message)

    # Decrypt the message with my private key
    box = RbNaCl::Box.new(@sender_public_key, @my_private_key)
    decrypted_message = box.decrypt(opts[:nonce], encrypted_message)

    decrypted_message
  end
end
