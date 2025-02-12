# user-session-encryption
Test javax.crypto packages to encrypt a user session and stored it in an xml file

In this program, you can.
1. Generate a key and print it to the console, the user can then use the key to encrypt/decrypt his messages, the user can
generate as many keys as he/she want, however, the encryption is symmetric, the same key used to encrypt the data, must be used
to decrypt the data.

2. Encrypt/decrypt a message (using the user key), the user enters the message and key to encrypt/decrypt his/her message.

3. The user can use the saved key in the program to encrypt/decrypt his/her messages, here the user is only required to enter
The messages, the key will stay the same every time the program runs.

4. The user can create a user session (which consists of phone number and password), and store that session in a encrypted format
in a XML file.

5. The user can read the saved session (if it exists), the session will be read from the file, decrypted and printed the user.
The session file is deleted after the work is done with the file, so subsequent command to print the session will print "No saved session" message.

Used Technologies:
1. javax.crypto built-in library to encrypt/decrypt the data.
2. jakarta.xml.bind API to marshal and unmarshal data stored in the XML file.
3. com.sun.xml.bind API impl to provide the implementation for the jakarta API.
