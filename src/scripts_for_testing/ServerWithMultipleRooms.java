package scripts_for_testing;

import chatapp.ChatRoom;
import chatapp.Message;
import chatapp.Server;

/**
 * Created by Yitzi on 2/25/2016.
 */
public class ServerWithMultipleRooms {

    public static void main(String[] args) {
        Server server = new Server();


        ChatRoom room1 = new ChatRoom(ChatRoom.getNextId(), "Basketball", "Sports");
        ChatRoom room2 = new ChatRoom(ChatRoom.getNextId(), "Baseball", "Sports");
        ChatRoom room3 = new ChatRoom(ChatRoom.getNextId(), "Soccer", "Sports");
        ChatRoom room4 = new ChatRoom(ChatRoom.getNextId(), "Rugby", "Sports");
        ChatRoom room5 = new ChatRoom(ChatRoom.getNextId(), "Hockey", "Sports");
        ChatRoom room6 = new ChatRoom(ChatRoom.getNextId(), "Algebra", "Math");
        ChatRoom room7 = new ChatRoom(ChatRoom.getNextId(), "Calculus", "Math");
        ChatRoom room8 = new ChatRoom(ChatRoom.getNextId(), "Java", "Programming");
        ChatRoom room9 = new ChatRoom(ChatRoom.getNextId(), "C#", "Programming");
        ChatRoom room10 = new ChatRoom(ChatRoom.getNextId(), "Agile Methodology", "Programming");
        ChatRoom room11 = new ChatRoom(ChatRoom.getNextId(), "Unit Testing", "Programming");
        ChatRoom room12 = new ChatRoom(ChatRoom.getNextId(), "Movies", "Entertainment");
        ChatRoom room13 = new ChatRoom(ChatRoom.getNextId(), "TV Shows", "Entertainment");
        ChatRoom room14 = new ChatRoom(ChatRoom.getNextId(), "Sports", "Entertainment");
        ChatRoom room15 = new ChatRoom(ChatRoom.getNextId(), "Celebrities", "Entertainment");

        server.processNewChatRoom(room1);
        server.processNewChatRoom(room2);
        server.processNewChatRoom(room3);
        server.processNewChatRoom(room4);
        server.processNewChatRoom(room5);
        server.processNewChatRoom(room6);
        server.processNewChatRoom(room7);
        server.processNewChatRoom(room8);
        server.processNewChatRoom(room9);
        server.processNewChatRoom(room10);
        server.processNewChatRoom(room11);
        server.processNewChatRoom(room12);
        server.processNewChatRoom(room13);
        server.processNewChatRoom(room14);
        server.processNewChatRoom(room15);

        Message m1 = new Message();
        m1.setText("Hello");
        Message m2 = new Message();
        m2.setText("How are you?");
        Message m3 = new Message();
        m3.setText("I like Baseball");
        Message m4 = new Message();
        m4.setText("Me too!");
        room1.addMessage(m1);
        room1.addMessage(m2);
        room1.addMessage(m3);
        room1.addMessage(m4);

        Message m11 = new Message();
        m11.setText("Math is fun!");
        Message m12 = new Message();
        m12.setText("Are you insane??");
        Message m13 = new Message();
        m13.setText("yes");
        Message m14 = new Message();
        m14.setText("Me too!");
        room7.addMessage(m11);
        room7.addMessage(m12);
        room7.addMessage(m13);
        room7.addMessage(m14);

    }

}
