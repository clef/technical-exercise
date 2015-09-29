require_relative 'encryptor'
require 'test/unit'

KNOWN_NONCE = hex_to_binary('8c54420e9b666e05613cc0f7d6bc10c7410f446b5d734dde')

class TestPasswordEncryption < Test::Unit::TestCase
  def test_password_encryption
    # Verify that encryption matches a known encrypted string

    plaintext_message =  'This is a really cool encrypted message'
    check_encrypted_message =  "McddBivjIJp7O9Yq1T1Vomi-ozQVcOxIGjfpYAQtK4TG5nbd_Rz8AYo8Exj1\n47SxIVO7CfyHzA==\n"

    password_encryptor = PasswordEncryptor.new('password')
    encrypted_message = password_encryptor.encrypt(plaintext_message, :nonce => KNOWN_NONCE)

    assert_equal(check_encrypted_message, encrypted_message)
  end

  def test_password_decryption
    # Verify that decryption matches a known plaintext string
    encrypted_message = "QvMeHBSdFBJLUccynH6ssWi-ozQVcOxIGnn0cQ0kNd2U4Hje_UW5DIYhBkjk\n6LPjNUa8H/nAxNk3S8SL/A==\n"
    plaintext_message = 'This is another really cool encrypted message'

    password_decryptor = PasswordDecryptor.new('password')
    decrypted_message = password_decryptor.decrypt(encrypted_message, :nonce => KNOWN_NONCE)

    assert_equal(decrypted_message, plaintext_message )
  end

  def test_password_encryption_and_decryption
    # Verify that encryption with a given password matches decryption with the same password
    plaintext_message =  'This is a really cool encrypted message'

    password_encryptor = PasswordEncryptor.new('password')
    encrypted_message = password_encryptor.encrypt(plaintext_message, :nonce => KNOWN_NONCE)

    password_decryptor = PasswordDecryptor.new('password')
    decrypted_message = password_decryptor.decrypt(encrypted_message, :nonce => KNOWN_NONCE)

    assert_equal(plaintext_message, decrypted_message)
  end
end

RECIPIENT_PUBLIC_KEY = '6ccb208e0bb4d875580ea004884a49e813455d22d7415ff71d6521335458780a'
RECIPIENT_PRIVATE_KEY = '9e6ece687d02d8ae5185f75b8f3a941abc67aa8b40a65954941ad425dbf7cb64'

class TestPublicKeyEncryption < Test::Unit::TestCase
  def test_public_key_encryption
    # Verify that encryption matches a known encrypted string

    plaintext_message = 'This is a secret message ~ for your eyes only'
    check_encrypted_message = "CaiUAHniGMf0ABj3Xqn7BjhNZqqYTFxyVtnwmloZUVkBsogo0BgBrt5jOZXk\narzpAUBsP21uGrTH28fQtQ==\n"

    public_key_encryptor = PublicKeyEncryptor.new(RECIPIENT_PUBLIC_KEY)

    encrypted_message = public_key_encryptor.encrypt(plaintext_message, :nonce => KNOWN_NONCE)

    assert_equal(encrypted_message, check_encrypted_message
  end

  def test_public_key_decryption
    # Verify that decryption matches a known plaintext string

    encrypted_message = "Qj0dTNAUjhgzV2koLjg-mThNZqqYTFxyVtn1mksSFF5Ruo4ywhVGpptuapLs\nfQ==\n"
    plaintext_message = 'This is a very special message'

    private_key_decryptor = PrivateKeyDecryptor.new(RECIPIENT_PRIVATE_KEY)
    decrypted_message = private_key_decryptor.decrypt(encrypted_message, :nonce => KNOWN_NONCE)

    assert_equal(plaintext_message, decrypted_message)
  end

  def test_public_key_encryption_and_decryption
    # Verify that encryption with a given password matches decryption with the same password

    plaintext_message = 'This is another cool message'

    public_key_encryptor = PublicKeyEncryptor.new(RECIPIENT_PUBLIC_KEY)
    encrypted_message = public_key_encryptor.encrypt(plaintext_message, :nonce => KNOWN_NONCE)

    private_key_decryptor = PrivateKeyDecryptor.new(RECIPIENT_PRIVATE_KEY)
    decrypted_message = private_key_decryptor.decrypt(encrypted_message, :nonce => KNOWN_NONCE)

    assert_equal(plaintext_message, decrypted_message)
  end
end