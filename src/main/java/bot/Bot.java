package bot;

import java.io.IOException;
import java.util.Date;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Bot extends TelegramLongPollingBot {

	@Override
	public void onUpdateReceived(Update update) {
		if (update.hasMessage() && update.getMessage().hasText()) {
			String text = update.getMessage().getText();
			long chatID = update.getMessage().getChatId();
			Date date = new Date(update.getMessage().getDate());
			String name = update.getMessage().getFrom().getFirstName();
			System.out.println(text);

			try {
				text = Weather.getWeather(text, date, name);
			} catch (IOException e1) {
				if (text.equalsIgnoreCase("/start")) {
					text = "Enter your city!";
				} else {
					text = "Try again!";
				}
				e1.printStackTrace();
			}

			SendMessage message = new SendMessage(String.valueOf(chatID), text);
			try {
				execute(message);
			} catch (TelegramApiException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public String getBotUsername() {
		return "wb5_weather_bot";
	}

	@Override
	public String getBotToken() {
		return "2076585906:AAFPdtgo6U1w0PxZE6KwJolI1N5D2kwAycg";
	}
}