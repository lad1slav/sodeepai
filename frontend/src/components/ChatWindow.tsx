import React, { FC, useEffect, useState } from "react";
import { ChatMessage } from "./ChatMessage";
import { ChatInput } from "./ChatInput";
import { Message, MessageRequest } from "../interfaces/Message";
import { MessageService } from "../services/MessageService";

export const ChatWindow: FC = () => {
  const [messages, setMessages] = useState<Message[]>([]);

  useEffect(() => {
    const messageService = new MessageService();
    messageService.getMessages().then((data) => setMessages(data));
  }, []);

  const handleSendMessage = (message: MessageRequest) => {
    const messageService = new MessageService();
    messageService
      .sendMessage(message)
      .then((data) => setMessages([...messages, data]));
  };

  return (
    <div className="chat-window">
      <h1 className="chat-header">Перший український AI чат</h1>
      {messages.map((message) => (
        <ChatMessage key={message.id} message={message} />
      ))}
      <ChatInput onSendMessage={handleSendMessage} />
    </div>
  );
};
