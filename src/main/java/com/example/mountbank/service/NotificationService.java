package com.example.mountbank.service;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Service
public class NotificationService extends TextWebSocketHandler {

    // 存储 WebSocket 会话
    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    // 存储会话的过期时间
    private final Map<String, Long> sessionExpiryTimes = new ConcurrentHashMap<>();

    // 当 WebSocket 连接建立时，从 WebSocket 会话中提取 JWT token 并存储会话
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("WebSocket 连接已建立");

        String jwtToken = (String) session.getAttributes().get("jwtToken");
        String phoneNumber = (String) session.getAttributes().get("phoneNumber");

        if (jwtToken != null && phoneNumber != null) {
            // 存储会话状态
            sessions.put(phoneNumber, session);
            // 设置该会话的过期时间为 5 分钟后（你可以根据需求调整过期时间）
            sessionExpiryTimes.put(phoneNumber, System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(5));
            System.out.println("已存储 WebSocket 会话，phoneNumber: " + phoneNumber);
        } else {
            System.out.println("无法从 WebSocket 会话中提取有效的 JWT 或 phoneNumber");
        }

        super.afterConnectionEstablished(session);
    }

    // 处理接收到的消息
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        System.out.println("接收到消息: " + message.getPayload());

        // 处理心跳消息
        if ("{\"type\": \"heartbeat\"}".equals(message.getPayload())) {
            System.out.println("收到心跳包，保持连接");
            session.sendMessage(new TextMessage("{\"status\": \"alive\"}"));  // 发送心跳响应
            return;
        }

        // 处理其他类型的消息
        // 这里可以解析消息并进行其他处理
    }

    // 用于向特定用户发送通知
    public void sendNotification(String phoneNumber, String message, String senderPhone) {
        System.out.println("尝试向用户发送通知，phoneNumber: " + phoneNumber + "，消息: " + message);

        WebSocketSession session = sessions.get(phoneNumber);
        if (session != null) {
            System.out.println("找到的 WebSocket 会话: " + session);
            System.out.println("WebSocket 会话是否已打开: " + session.isOpen());

            if (session.isOpen()) {
                try {
                    String jsonMessage = String.format("{\"sender\": \"%s\", \"message\": \"%s\"}", senderPhone, message);
                    session.sendMessage(new TextMessage(jsonMessage));
                    System.out.println("消息已发送给用户: " + phoneNumber);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("会话已找到，但会话未打开");
            }
        } else {
            System.out.println("无法找到有效的 WebSocket 会话");
        }
    }

    // 当有新评论时，发送通知给帖子的拥有者
    public void onCommentAdded(String content, String commenterPhone, String postOwnerPhone) {
        String message = String.format("User %s commented: %s", commenterPhone, content);
        System.out.println("触发新评论通知，消息: " + message);
        sendNotification(postOwnerPhone, message, commenterPhone);
    }

    // 处理 WebSocket 会话关闭时的逻辑
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);

        String phoneNumber = (String) session.getAttributes().get("phoneNumber");

        if (phoneNumber != null) {
            System.out.println("WebSocket 会话已关闭，phoneNumber: " + phoneNumber + ", 状态: " + status);

            // 检查是否需要延迟移除会话
            long currentTime = System.currentTimeMillis();
            long expiryTime = sessionExpiryTimes.getOrDefault(phoneNumber, 0L);

            if (currentTime < expiryTime) {
                // 会话在过期时间内，保持会话
                sessions.put(phoneNumber, session);  // 保留会话状态
                System.out.println("保持 WebSocket 会话，phoneNumber: " + phoneNumber);
            } else {
                // 会话已过期，删除会话
                sessions.remove(phoneNumber);
                sessionExpiryTimes.remove(phoneNumber);
                System.out.println("会话已过期，移除 WebSocket 会话，phoneNumber: " + phoneNumber);
            }
        } else {
            System.out.println("WebSocket 会话已关闭，但没有找到对应的 phoneNumber，状态: " + status);
        }
    }
}
