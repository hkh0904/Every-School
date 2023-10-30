package com.everyschool.chatservice.config;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;

public class StompHandler implements ChannelInterceptor {

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        handleMessage(accessor.getCommand(), accessor);
        if(StompCommand.CONNECT == accessor.getCommand()){
            
        } else if (StompCommand.SUBSCRIBE==accessor.getCommand()) {

        }
        return ChannelInterceptor.super.preSend(message, channel);
    }

    private void handleMessage(StompCommand command, StompHeaderAccessor accessor) {
        switch (command){
            case CONNECT:
                connectToChatRoom(accessor);
        }
    }

    private void connectToChatRoom(StompHeaderAccessor accessor) {

    }
}
