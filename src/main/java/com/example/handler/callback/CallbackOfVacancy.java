package com.example.handler.callback;

import com.example.constance.info.vacancy.Specification;
import com.example.constance.info.vacancy.VacancyText;
import com.example.feature.transport.TransportService;
import com.example.feature.vacancy.VacancyService;
import com.example.handler.BotSenderService;
import com.example.handler.button.GeneralButton;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CallbackOfVacancy implements ICallback{

    private final VacancyService vacancyService;
    private final TransportService transportService;

    @Override
    @SneakyThrows
    public void handlerOfCallback(Update update, BotSenderService botSenderService){
        String data = update.getCallbackQuery().getData();
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        long messageId = update.getCallbackQuery().getMessage().getMessageId();

        if (data.equals(Specification.WITH_EXPERIENCE.getName())){
            StringBuilder text = new StringBuilder();
            com.example.feature.vacancy.Vacancy withEx = vacancyService.getBySpecification("З досвідом роботи");
            String [] with = withEx.getName().split("\n");

            for (String s : with) {
                text.append("- ").append(s).append("\n");
            }

            botSenderService.executeEditMessage(text + VacancyText.VACANCY_TEXT, chatId , messageId, new GeneralButton().getButtons(List.of("Назад"), List.of("BACK_VACANCY")));
        }

        if (data.equals(Specification.WITHOUT_EXPERIENCE.getName())){
            StringBuilder text = new StringBuilder();
            com.example.feature.vacancy.Vacancy withput = vacancyService.getBySpecification("Без досвіду роботи");
            String [] with = withput.getName().split("\n");

            for (String s : with) {
                text.append("- ").append(s).append("\n");
            }

            botSenderService.executeEditMessage(text + VacancyText.VACANCY_TEXT, chatId , messageId, new GeneralButton().getButtons(List.of("Назад"), List.of("BACK_VACANCY")));
        }

        if (data.equals("Трамвай")){

            List<String> numbers = transportService.getNumbersByType("трамвай");
            List<String> numbCall = new ArrayList<>();
            for (String number: numbers){
                numbCall.add(number + " trambus");
            }

            botSenderService.sendMiniApp(chatId, new GeneralButton().getButtons(numbers, numbCall), "Оберіть номер маршруту:", messageId, update.getCallbackQuery());
        }

        if (data.equals("Тролейбус")){
            List<String> trolls = transportService.getNumbersByType("тролейбус");
            List<String> numbCall = new ArrayList<>();
            for (String number: trolls){
                numbCall.add(number + " trollbus");
            }

            botSenderService.sendMiniApp(chatId, new GeneralButton().getButtons(trolls, numbCall), "Оберіть номер маршруту:", messageId, update.getCallbackQuery());
        }
    }
}
