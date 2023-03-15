export interface Message {
  id: number;
  context: string;
  senderName: string;
  time: number;
}

export interface MessageRequest {
	prompt: string;
	senderName: string;
	time: number;
	type: string;
}
