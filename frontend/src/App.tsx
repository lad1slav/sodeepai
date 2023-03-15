import React, { FC } from "react";
import { ChatWindow } from "./components/ChatWindow";
import "./App.css";

const App: FC = () => {
  return (
    <div className="app">
      <ChatWindow />
    </div>
  );
};

export default App;
