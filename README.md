# ChatSCI

Simple chat server working over TCP.

## How to use
To start server, simply execute start_server script
(Windows: `start_server.bat`, Linux: `start_server.sh`).
You can also open this project in any IDE with Gradle support
(tested in IntelliJ IDEA, Eclipse need plugin installation AFAIK)

## API

Communication over TCP, port 16384.
Packet types (_NICK_, _MSG_, etc.) are case-insensitive.
Nicks are case-sensitive.

### Packets from client to server
- `NICK;{nick}` - sets client's nick to {nick}.
Every client has to send this packet as the first thing.
Sending and receiving messages will not be possible until nick is set.
- `MSG;{recipient};{message}` - sends {message} to {recipient}

### Packets from server to client
- `JOIN;{nick}` - informs that {nick} has joined,
or had already been connected before receiver of this package joined 
- `LEAVE;{nick}` - informs that {nick} has left
- `MSG;{sender};{message}` - informs that {sender} sent you {message}
- `ERROR;{message}` - informs about wrong action,
{message} contains more details
