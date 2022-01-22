# distributedATM
A Java TCP Socket implementation of a distributed 3-layer ATM service with a client interface, a multi-threaded server and a MySQL database.
#Client
The client has a simplistic, menu-style interface and a protocol for Login, Balance check, Withdrawal and Depositing.
There are 3 demo card objects on /cards who contain a pin and an ID. The client has to choose one and insert the correct ID to log in. This takes place locally.
The rest are TCP messages to the server who contain the ID and the ammount that was inserted by the user, if needed.
#Server
The server has a dispatcher main thread that creates a server thread for each incoming connection.
The server thread reads the message and calls the coresponding protocol method from the server protocol class.

The code is entirely untested but the logic and message outlines should be obvious.
