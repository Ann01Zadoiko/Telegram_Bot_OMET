package com.example.handler;

import com.example.bot.TelegramBot;
import com.example.constance.Button;
import com.example.constance.info.GeneralInfo;
import com.example.constance.info.Study;
import com.example.constance.info.vacancy.VacancyText;
import com.example.constance.museum.Registration;
import com.example.constance.rent.Rent;
import com.example.feature.museum.Museum;
import com.example.feature.museum.MuseumService;
import com.example.feature.stop.Stop;
import com.example.feature.stop.StopService;
import com.example.feature.transport.Transport;
import com.example.feature.transport.TransportService;
import com.example.feature.vacancy.VacancyService;
import com.example.handler.button.*;
import com.example.constance.museum.MuseumEnum;
import com.example.constance.museum.MuseumInfo;
import com.example.printer.TrackPrinter;
import com.example.registration.MuseumRegistration;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class HandlerCallback {

    private final MuseumService museumService;
    private final MuseumRegistration museumRegistration;
    private final VacancyService vacancyService;
    private final TransportService transportService;
    private final StopService stopService;


    @SneakyThrows
    public void handlerOfMuseum(Update update, TelegramBot bot){
        String data = update.getCallbackQuery().getData();
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        long messageId = update.getCallbackQuery().getMessage().getMessageId();

        if (MessageChecker.isEnumValueStatus(data)){
            switch (MuseumEnum.valueOf(data)){
                case INFO ->
                    bot.sendMessage(chatId, MuseumInfo.TEXT_1, messageId, update.getCallbackQuery());

                case SOCIAL_MEDIA_MUSEUM ->{
                    String text = "Соціальні мережі:\nFacebook\nhttps://www.facebook.com/museumoget/";

                    bot.sendMiniApp(chatId, LinkButtons.createLinksMuseumButtons(), text, messageId, update.getCallbackQuery());

                }

                case REGISTRATION ->{
                    // DateTimeFormatter instance to be provided to the API
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM");

                    Museum museum = museumService.getById(1L);
                    String textClose = Registration.STEP_9.getText() + "\n" + Registration.STEP_10.getText();
                    String textOpen =  Registration.STEP_1.getText() + museum.getDate().format(formatter) + " на 12:00?";

                    if (museum.isClose()){
                        bot.executeEditMessage(textClose, chatId, messageId, BackButton.getButtons("BACK_MUSEUM"));
                    } else {
                        bot.executeEditMessage(textOpen, chatId, messageId, MuseumButtons.getButtonsYesOrNo("YES_MUSEUM", "NO_MUSEUM"));
                    }
                }
            }
        }
    }

    @SneakyThrows
    public void handlerOfGeneralInfo(Update update, TelegramBot bot){
        String data = update.getCallbackQuery().getData();
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        long messageId = update.getCallbackQuery().getMessage().getMessageId();

        if(MessageChecker.isEnumValueGeneralInfo(data)){
            switch (GeneralInfo.valueOf(data)){
                case STUDY ->
                    bot.sendMessage(chatId, Study.TEXT, messageId, update.getCallbackQuery());

                case SOCIAL_MEDIA ->{
                    String text = "Соціальні мережі:\nFacebook\nhttps://www.facebook.com/kp.oget/";

                    bot.sendMiniApp(chatId, LinkButtons.createLinksGeneralInfoButtons(), text, messageId, update.getCallbackQuery());
                }

                case VACANCY ->
                    bot.sendMiniApp(chatId, VacancyButtons.getButtonsSpecification("WITH_EXPERIENCE", "WITHOUT_EXPERIENCE"), "Вакансії", messageId, update.getCallbackQuery());

                case RENT ->{
                    for (Rent rent: Rent.values()){
                        bot.sendMessage(chatId, rent.getDescription());
                        bot.sendPhoto(chatId, rent.getPhoto());
                    }
                }

            }
        }
    }

    @SneakyThrows
    public void handlerOfVacancy(Update update, TelegramBot bot){
        String data = update.getCallbackQuery().getData();
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        long messageId = update.getCallbackQuery().getMessage().getMessageId();

        if (data.equals("WITH_EXPERIENCE")){
            String text = "";
            com.example.feature.vacancy.Vacancy withEx = vacancyService.getBySpecification("З досвідом роботи");
            String [] with = withEx.getName().split("\n");

            for (int i = 0; i < with.length; i++) {
                text += "- " + with[i] + "\n";
            }

            bot.executeEditMessage(text + VacancyText.VACANCY_TEXT, chatId , messageId, BackButton.getButtons("BACK_VACANCY"));
        }

        if (data.equals("WITHOUT_EXPERIENCE")){
            String text = "";
            com.example.feature.vacancy.Vacancy withput = vacancyService.getBySpecification("Без досвіду роботи");
            String [] with = withput.getName().split("\n");

            for (int i = 0; i < with.length; i++) {
                text += "- " + with[i] + "\n";
            }

            bot.executeEditMessage(text + VacancyText.VACANCY_TEXT, chatId , messageId, BackButton.getButtons("BACK_VACANCY"));
        }

        if (data.equals("Трамвай")){

            List<String> numbers = transportService.getNumbersByType("трамвай");
            List<String> numbCall = new ArrayList<>();
            for (String number: numbers){
                numbCall.add(number + " trambus");
            }

            bot.sendMiniApp(chatId, new GeneralButton().getButtons(numbers, numbCall), "Оберіть номер маршрут:", messageId, update.getCallbackQuery());
        }

        if (data.equals("Тролейбус")){
            List<String> trolls = transportService.getNumbersByType("тролейбус");
            List<String> numbCall = new ArrayList<>();
            for (String number: trolls){
                numbCall.add(number + " trollbus");
            }

            bot.sendMiniApp(chatId, new GeneralButton().getButtons(trolls, numbCall), "Оберіть номер маршрут:", messageId, update.getCallbackQuery());
        }
    }

    public void handlerOfBack(Update update, TelegramBot bot){
        String data = update.getCallbackQuery().getData();
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        long messageId = update.getCallbackQuery().getMessage().getMessageId();

        if (data.equals("BACK_TRACKS")){
            bot.executeEditMessage("Маршрут", chatId, messageId, TracksButtons.getButtonsTracks());
        }

        if (data.equals("BACK_VACANCY")){
            bot.executeEditMessage("Вакансії", chatId,  messageId, VacancyButtons.getButtonsSpecification("WITH_EXPERIENCE", "WITHOUT_EXPERIENCE"));
        }

        if (data.equals("BACK_MUSEUM")){
            bot.executeEditMessage(Button.MUSEUM.getFullName(), chatId, messageId, MuseumButtons.getButtons());
        }
    }

    public void handlerOfYesOrNoMuseum(Update update, TelegramBot bot){
        String data = update.getCallbackQuery().getData();
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        long messageId = update.getCallbackQuery().getMessage().getMessageId();

        if (data.equals("YES_MUSEUM")){
            Museum museum = new Museum();
            museum.setDate(museumService.showDate());
            museum.setChatId(chatId);
            museumService.save(museum);
            museumRegistration.startRegistration(chatId, bot);
        }

        if (data.equals("NO_MUSEUM")){
            bot.executeEditMessage("Музей", chatId, messageId, MuseumButtons.getButtons());
        }
    }


    public void handlerOfTracks(Update update, TelegramBot bot){
        String data = update.getCallbackQuery().getData();
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        long messageId = update.getCallbackQuery().getMessage().getMessageId();

        if (data.contains("trollbus")){
            String[] s = data.split(" ");
            if (s[1].equals("trollbus")){
                for (String number: transportService.getNumbersByType("тролейбус")){
                    if (s[0].equals(number)){
                        Transport byTypeAndNumber = transportService.getByTypeAndNumber("тролейбус", s[0]).get();
                        bot.sendMiniApp(chatId, TrackButton.getButton(byTypeAndNumber), TrackPrinter.print(byTypeAndNumber), messageId,
                                update.getCallbackQuery());
                    }
                }
            }

        }

        if (data.contains("trambus")){
            String[] s = data.split(" ");
            if (s[1].equals("trambus")){
                for (String number: transportService.getNumbersByType("трамвай")){
                    if (s[0].equals(number)){
                        Transport byTypeAndNumber = transportService.getByTypeAndNumber("трамвай", s[0]).get();
                        bot.sendMiniApp(chatId, TrackButton.getButton(byTypeAndNumber), TrackPrinter.print(byTypeAndNumber), messageId,
                                update.getCallbackQuery());
                    }
                }
            }

        }
    }

    public void handlerForStops(Update update, TelegramBot bot){
        String data = update.getCallbackQuery().getData();
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        long messageId = update.getCallbackQuery().getMessage().getMessageId();

        if (data.startsWith("STOPS")){
            String[] s = data.split(" ");
            Transport byTypeAndNumber = transportService.getByTypeAndNumber(s[1], s[2]).get();
            Stop stop = stopService.getByTransport(byTypeAndNumber);

            if (stop == null){
                bot.sendMessage(chatId, "На даний час немає повного списку зупинок", messageId, update.getCallbackQuery());
            }

            bot.sendMessage(chatId, stop.getStopAcross(), messageId, update.getCallbackQuery());
            bot.sendMessage(chatId, stop.getStopRightBack(), messageId, update.getCallbackQuery());
        }
    }

}
