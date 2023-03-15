import React, { FC } from "react";
import { Message } from "../interfaces/Message";

type ChatMessageProps = {
  message: Message;
};

export const ChatMessage: FC<ChatMessageProps> = ({ message }) => {
  return (
    <div className="chat-message">
      <div className="message-sender">{message.senderName}</div>
      <div className="message-content">{message.context}</div>
      <div className="message-timestamp">{message.time}</div>
    </div>
  );
};
