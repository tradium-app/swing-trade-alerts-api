package com.tradiumapp.swingtradealerts.scheduledtasks.helpers;

import com.tradiumapp.swingtradealerts.models.Alert;
import com.tradiumapp.swingtradealerts.models.Condition;
import com.tradiumapp.swingtradealerts.models.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class AlertEmailSender {
    @Autowired
    SendGridEmailSender sendGridEmailSender;

    public void sendEmail(User user, List<Alert> alerts) throws IOException {
        String buys = alerts.stream().filter(a -> a.signal == Alert.AlertSignal.Buy).limit(4)
                .map(a -> a.symbol).collect(Collectors.joining(","));
        String sells = alerts.stream().filter(a -> a.signal == Alert.AlertSignal.Sell).limit(4)
                .map(a -> a.symbol).collect(Collectors.joining(","));

        String subject = "Buy " + buys + "..  and Sell " + sells + "..";

        alerts.sort(Comparator.comparing((Alert a) -> a.signal));

        String message = "";
        for (int i = 0; i < alerts.size(); i++) {
            Alert alert = alerts.get(i);
            message += (i + 1) + ") " + alert.signal + " " + alert.symbol + ": " + alerts.get(i).title + " <br/> ";

            for (Condition condition : alerts.get(i).conditions) {
                message += "&nbsp;&nbsp;" + StringUtils.capitalize(condition.timeframe) + " " + condition.indicator.toString().toUpperCase()
                        + (condition.operator == Condition.Operator.Not ? " ≠ " : " = ")
                        + " '" + condition.valueText + "'. <br/> ";
            }
            message += "<br/>";
        }

        try {
            sendGridEmailSender.sendEmail(user, subject, message);
        } catch (Exception ignored) {
        }
    }
}
