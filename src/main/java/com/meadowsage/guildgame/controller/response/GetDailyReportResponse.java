package com.meadowsage.guildgame.controller.response;

import com.meadowsage.guildgame.usecase.GetDailyReportUseCase;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class GetDailyReportResponse {
    List<ResponseReportItem> items = new ArrayList<>();

    public GetDailyReportResponse(GetDailyReportUseCase.GetDailyReportUseCaseResult result) {
        items.addAll(result.getReportItems().stream().map(reportItem -> new ResponseReportItem(
                reportItem.getDescription(),
                reportItem.getIncome(),
                reportItem.getOutcome(),
                reportItem.getBalance()
        )).collect(Collectors.toList()));
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class ResponseReportItem {
        private final String description;
        private final Integer income;
        private final Integer outcome;
        private final Integer balance;
    }
}
