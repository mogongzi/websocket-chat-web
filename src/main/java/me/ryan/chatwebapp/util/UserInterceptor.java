package me.ryan.chatwebapp.util;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.messaging.support.NativeMessageHeaderAccessor;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class UserInterceptor implements ChannelInterceptor {

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (StompCommand.CONNECT.equals(Objects.requireNonNull(accessor).getCommand())) {
            Object raw = message.getHeaders().get(NativeMessageHeaderAccessor.NATIVE_HEADERS);

            if (raw instanceof Map) {
                Object name = ((Map<?, ?>) raw).get("username");

                if (name instanceof ArrayList) {
                    accessor.setUser(new User(((ArrayList<?>) name).get(0).toString()));
                }
            }
        }

        return message;
    }
}
