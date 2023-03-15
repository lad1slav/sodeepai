import { Message, MessageRequest } from "../interfaces/Message";

export class MessageService {
  async getMessages(): Promise<Message[]> {
    // Logic to retrieve messages from the server
	const array: Message[] = [];
	return array;
  }

  async sendMessage(message: MessageRequest): Promise<Message> {
    // Logic to send a message to the server
	const response: any = await fetch("http://34.116.157.226:8080/chat/saveMessage", {
		method: "POST",
		headers: {
			"Content-Type": "application/json",
		},
		body: JSON.stringify(message),
	});
	
	return response;
  }
}