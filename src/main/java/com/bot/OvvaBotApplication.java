package com.bot;

import com.bot.model.ovvatv.Channel;
import com.bot.model.ovvatv.Language;
import com.bot.model.ovvatv.VideoCard;
import com.bot.service.MessageBuilderService;
import com.bot.service.OvvaService;
import com.bot.service.TelegramBotService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramBotAdapter;
import com.pengrad.telegrambot.model.*;
import com.pengrad.telegrambot.model.request.*;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import com.pengrad.telegrambot.response.GetUpdatesResponse;
import com.pengrad.telegrambot.response.SendResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SpringBootApplication
public class OvvaBotApplication {

	public final static String TOKEN = "373991576:AAFxsLAOZyPqkBErL91UayRZ5T_s1Mxa-8g";
	private static final String TELEPROGRAMMA = "Телепрограмма на сегодня";
	private static final String LAST_VIDEO = "Последнее видео по проекту";
	static MessageBuilderService service = new MessageBuilderService();
	static OvvaService ovvaService = new OvvaService();

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
                    if ((message.entities() != null && message.entities()[0] != null &&
							message.entities()[0].type().equals(MessageEntity.Type.bot_command))) {
                        processCommand(user, mes);
                        continue;
                    }


                    processText(user, mes);

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

	private static void processCommand(User user, String command) {
        if (command.equals("/start")) {
            SendMessage request = new SendMessage(user.id(), "Чем могу помочь?")
                    .parseMode(ParseMode.HTML)
                    .disableWebPagePreview(false)
                    .disableNotification(false)
                    .replyMarkup(new ReplyKeyboardMarkup(
									new KeyboardButton[]{
											new KeyboardButton(TELEPROGRAMMA),//.callbackData("TELEPROG"),
											new KeyboardButton(LAST_VIDEO)//.callbackData("VIDEO")}
									}
							).oneTimeKeyboard(true).resizeKeyboard(true)
                    )
                    ;
            SendResponse sendResponse = bot.execute(request);
        }

    }

	private static void processText(User user, String mes) {
		if (mes.equals(TELEPROGRAMMA)) {
			List<KeyboardButton> channels = new ArrayList<>();
			for (Channel channel : Channel.values()) {
				channels.add(new KeyboardButton(channel.getName()));
			}
			KeyboardButton[] buttons = channels.toArray(new KeyboardButton[channels.size()]);

			SendMessage request = new SendMessage(user.id(), "Выберете канал:")
					.parseMode(ParseMode.HTML)
					.disableWebPagePreview(false)
					.disableNotification(false)
					.replyMarkup(new ReplyKeyboardMarkup(buttons).oneTimeKeyboard(true).resizeKeyboard(true)
					)
					;
			SendResponse sendResponse = bot.execute(request);
		} else if (mes.equals(LAST_VIDEO)) {
			SendMessage request = new SendMessage(user.id(), "Какой проект вас интересует?")
					.parseMode(ParseMode.HTML)
					.disableWebPagePreview(false)
					.disableNotification(false)
					;
			SendResponse sendResponse = bot.execute(request);
		} else {
			for (Channel channel : Channel.values()) {
				if (channel.getName().equals(mes)) {
					String program =
							service.buildTvProgram(ovvaService.getProgramsData(Language.RU, channel, LocalDate.now()));
					SendMessage request = new SendMessage(user.id(), program)
							.parseMode(ParseMode.HTML)
							.disableWebPagePreview(false)
							.disableNotification(false)
							.replyMarkup(new ReplyKeyboardMarkup(
											new KeyboardButton[]{
													new KeyboardButton(TELEPROGRAMMA),//.callbackData("TELEPROG"),
													new KeyboardButton(LAST_VIDEO)//.callbackData("VIDEO")}
											}
									).oneTimeKeyboard(true).resizeKeyboard(true)
							)
							;
					SendResponse sendResponse = bot.execute(request);
					return;
				}
			}
			VideoCard videoCard = ovvaService.getVideoCardByQuery(Language.RU, mes);
			String projectInfo = service.buildTelecastMessage(videoCard);
			SendMessage request = new SendMessage(user.id(), projectInfo)
					.parseMode(ParseMode.HTML)
					.disableWebPagePreview(false)
					.disableNotification(false)
					.replyMarkup(new ReplyKeyboardMarkup(
									new KeyboardButton[]{
											new KeyboardButton(TELEPROGRAMMA),//.callbackData("TELEPROG"),
											new KeyboardButton(LAST_VIDEO)//.callbackData("VIDEO")}
									}
							).oneTimeKeyboard(true).resizeKeyboard(true)
					)
					;
			SendResponse sendResponse = bot.execute(request);
			//bot.execute(new SendPhoto(user.id(), videoCard.getImage().getPreview()));
			return;
		}
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
