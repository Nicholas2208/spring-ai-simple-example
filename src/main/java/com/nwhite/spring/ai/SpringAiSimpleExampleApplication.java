package com.nwhite.spring.ai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class SpringAiSimpleExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringAiSimpleExampleApplication.class, args);
	}

	@Bean
	public CommandLineRunner runner(ChatClient.Builder builder) {
		return (args)-> {
			ChatClient chatClient = builder.build();
			Scanner scanner = new Scanner(System.in);

			List<Message> chatHistory = new ArrayList<>();

			System.out.println("Чат с ИИ запущен! (введите 'выход' для выхода)");
			System.out.print("=".repeat(50));

			while (true) {
				System.out.println("\nЧел: ");
				String userInput = scanner.nextLine().trim();

				if (userInput.equalsIgnoreCase("выход")) {
					System.out.println("Пока!");
					scanner.close();
					System.exit(0);
				}

				if (userInput.isEmpty()) continue;

				try {
					System.out.print("Giga: ");
					chatHistory.add(new UserMessage(userInput));

					String response = chatClient.prompt()
							.messages(chatHistory)
							.call()
							.content();

					// Если без истории
					// String response = chatClient.prompt(userInput).call().content();
					// chatHistory.clear(); // Очищаем память, контекст теряется

					System.out.println(response);
					// Добавляем ответ ИИ в историю
					chatHistory.add(new AssistantMessage(response));
				} catch (Exception e) {
					System.out.println("Ошибка: " + e.getMessage());
				}
			}
		};
	}

}
