import { Button, TextField } from "@mui/material";
import React, { FC, useState } from "react";
import { Message, MessageRequest } from "../interfaces/Message";

type ChatInputProps = {
  onSendMessage: (message: MessageRequest) => void;
};

export const ChatInput: FC<ChatInputProps> = ({ onSendMessage }) => {
  const [message, setMessage] = useState<string>("");

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setMessage(e.target.value);
  };

  const handleClick = () => {
    const messageData: MessageRequest = {
      senderName: "First Sender Name",
      prompt: message,
      time: Date.now(),
      type: "MESSAGE",
    };
    onSendMessage(messageData);
  };

  return (
    <div>
      <TextField
        id="outlined-multiline-flexible"
        className="chat-input"
        label="Повідомлення..."
        fullWidth={true}
        multiline
        maxRows={4}
        variant="filled"
        onChange={handleChange}
      />
      <Button onClick={handleClick} variant="contained">
        Надіслати
      </Button>
    </div>
  );
};
