package com.bot;

import com.bot.service.TelegramBotService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramBotAdapter;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.model.request.ForceReply;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.GetUpdatesResponse;
import com.pengrad.telegrambot.response.SendResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class OvvaBotApplication {

	public final static String TOKEN = "373991576:AAFxsLAOZyPqkBErL91UayRZ5T_s1Mxa-8g";

	private static TelegramBot bot = TelegramBotAdapter.build(TOKEN);
	@Autowired
	private TelegramBotService telegramBotService;

	public static void main(String[] args) {
		SpringApplication.run(OvvaBotApplication.class, args);

		GetUpdatesResponse updatesResponse;
		int j = 0;
		while (true) {
			try {
				updatesResponse = bot.execute(new GetUpdates().offset(j).limit(100).timeout(20));
				List<Update> updates = updatesResponse.updates();
				for (int z = 0; z < updates.size(); z++) {
					j = updates.get(z).updateId() + 1;
					Message message = updates.get(z).message();
					//
					Chat chat = message.chat();
					User user = message.from();
					String mes = message.text();

					processText(user.id(), mes);

					// Base64.getEncoder().encodeToString(s.getBytes(StandardCharsets.UTF_8));
					if (message.text() != null) {
						System.out.println("New message: " + message.text() + " id: " +
								message.messageId() + " from " + chat);
					}

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private static void processText(Integer id, String mes) {
		String response = "hi";



		sendResponse(id, response);
	}

	private static void sendResponse(Integer id, String response) {
		SendMessage request = new SendMessage(id, "hi")
				.parseMode(ParseMode.HTML)
				.disableWebPagePreview(false)
				.disableNotification(false)
				.replyMarkup(new InlineKeyboardMarkup()
					//todo:
				)
				;

		SendResponse sendResponse = bot.execute(request);
	}

}
