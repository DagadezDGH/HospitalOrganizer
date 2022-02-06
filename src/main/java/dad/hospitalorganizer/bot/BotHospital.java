package dad.hospitalorganizer.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.request.SendMessage;

public class BotHospital {
	TelegramBot bot = new TelegramBot("5142094328:AAFtwPNKH4twrywpDjbYP5TxiHvI2vpgI9g");
	
	public BotHospital() {
	bot.setUpdatesListener(updates -> {
		
		updates.stream().forEach(u -> {
			
			if (u.message().text().equals("Comando1")) {
				
				bot.execute(new SendMessage(u.message().chat().id(), "Resultado Query1"));
			}
		});

		
		
		return UpdatesListener.CONFIRMED_UPDATES_ALL;
	});
	
	}
}
