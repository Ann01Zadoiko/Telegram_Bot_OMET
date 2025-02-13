package com.example.handler;

import com.example.bot.TelegramBot;
import com.example.constance.complaint.Complain;
import com.example.constance.info.GeneralInfo;
import com.example.constance.info.Study;
import com.example.constance.museum.Registration;
import com.example.email.EmailSender;
import com.example.feature.complaint.Complaint;
import com.example.feature.complaint.ComplaintService;
import com.example.feature.museum.Museum;
import com.example.feature.museum.MuseumService;
import com.example.handler.button.*;
import com.example.constance.museum.MuseumEnum;
import com.example.constance.museum.MuseumInfo;
import com.example.constance.info.tracks.TracksTrams;
import com.example.constance.info.tracks.TracksTrolls;
import com.example.constance.info.vacancy.Specification;
import com.example.constance.info.vacancy.VacancyWithExperience;
import com.example.constance.info.vacancy.VacancyWithoutExperience;
import com.example.registration.ComplaintRegistration;
import com.example.registration.MuseumRegistration;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@RequiredArgsConstructor
@Service
public class HandlerCallback {

    private final MuseumService museumService;
    private final MuseumRegistration museumRegistration;
    private final ComplaintRegistration complaintRegistration;
    private final ComplaintService complaintService;

    @SneakyThrows
    public void handlerOfMuseum(Update update, TelegramBot bot){
        String data = update.getCallbackQuery().getData();
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        long messageId = update.getCallbackQuery().getMessage().getMessageId();

        if (MessageChecker.isEnumValueStatus(data)){
            switch (MuseumEnum.valueOf(data)){
                case INFO ->
                    bot.sendMessage(chatId, MuseumInfo.TEXT_1, messageId, update.getCallbackQuery());

                case SOCIAL_MEDIA_MUSEUM ->
                    bot.sendMiniApp(chatId, LinkButtons.createLinksMuseumButtons(), "Соціальні мережі", messageId, update.getCallbackQuery());

                case REGISTRATION ->{
                    Museum museum = museumService.getById(1L);
                    String textClose = Registration.STEP_9.getText() + "\n" + Registration.STEP_6.getText() + "\n" + Registration.STEP_7.getText();
                    String textOpen =  Registration.STEP_1.getText() + museum.getDate() + " на 12:00?";

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

                case SOCIAL_MEDIA ->
                    bot.sendMiniApp(chatId, LinkButtons.createLinksGeneralInfoButtons(), "Соціальні мережі", messageId, update.getCallbackQuery());

                case VACANCY ->
                    bot.sendMiniApp(chatId, VacancyButtons.getButtonsSpecification(), "Вакансії", messageId, update.getCallbackQuery());

                case TRACKS ->
                    bot.sendMiniApp(chatId, TracksButtons.getButtonsTracks(), "Маршрут", messageId, update.getCallbackQuery());

            }
        }
    }

    @SneakyThrows
    public void handlerOfVacancy(Update update, TelegramBot bot){
        String data = update.getCallbackQuery().getData();
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        long messageId = update.getCallbackQuery().getMessage().getMessageId();

        if (MessageChecker.isEnumValueSpecification(data)){
            switch (Specification.valueOf(data)){
                case WITH_EXPERIENCE -> {
                    String text = "";
                    for (VacancyWithExperience vacancy: VacancyWithExperience.values()){
                       text += "- " + vacancy.getName() + "\n";
                    }
                    bot.executeEditMessage(text, chatId , messageId, BackButton.getButtons("BACK_VACANCY"));
                }
                case WITHOUT_EXPERIENCE -> {
                    String text = "";
                    for (VacancyWithoutExperience vacancy: VacancyWithoutExperience.values()){
                        text += "- " + vacancy.getName() + "\n";
                    }
                    bot.executeEditMessage(text, chatId , messageId, BackButton.getButtons("BACK_VACANCY"));
                }
            }
        }

        if (data.equals("Трамвай")){
            String text = "";
            for (TracksTrams vacancy: TracksTrams.values()){
                text += vacancy.getNumber() + ": " + vacancy.getStartEnd()  + "\n";
            }
            bot.executeEditMessage(text, chatId , messageId, BackButton.getButtons("BACK_TRACKS"));
        }

        if (data.equals("Тролейбус")){
            String text = "";
            for (TracksTrolls vacancy: TracksTrolls.values()){
                text += vacancy.getNumber() + ": " + vacancy.getStartEnd() + "\n";
            }
            bot.executeEditMessage(text, chatId , messageId, BackButton.getButtons("BACK_TRACKS"));
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
            bot.executeEditMessage("Вакансії", chatId,  messageId, VacancyButtons.getButtonsSpecification());
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

    public void handlerOfYesOrNoComplaint(Update update, TelegramBot bot){
        String data = update.getCallbackQuery().getData();
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        long messageId = update.getCallbackQuery().getMessage().getMessageId();

        if (data.equals("YES_COMPLAINT")){
            Complaint complaint = new Complaint();
            complaint.setChatId(chatId);

            complaintService.save(complaint);

            complaintRegistration.startRegistration(chatId, bot);
        }

//        if (data.equals("NO_COMPLAINT")){
//        }
    }

    @SneakyThrows
    public void handlerOfSkip(Update update, TelegramBot bot){
        String data = update.getCallbackQuery().getData();
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        long messageId = update.getCallbackQuery().getMessage().getMessageId();

        if (data.equals("SKIP_PHOTO")){
            List<Complaint> byChatId = complaintService.findByChatId(chatId);
            Complaint complaint = byChatId.get(byChatId.size() - 1);

            EmailSender.sendEmailWithAttachment("info@oget.od.ua",
                    "Скарга",
                    complaint.getFullName() + "\n" + complaint.getPhoneNumber() + "\n" + complaint.getText());

            bot.sendMessage(chatId,  Complain.STEP_7.getText(), messageId, update.getCallbackQuery());
        }
    }

}
