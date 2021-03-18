package com.cpay.fileUpload.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
@Slf4j
@Component
public class ListAggregationStrategy implements AggregationStrategy {


    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        List rows = null;

        if (oldExchange == null) {
            // First row ->
            rows = new LinkedList();
            rows.add(newExchange.getMessage().getBody());
            newExchange.getMessage().setBody(rows);
            return newExchange;
        }

        rows = oldExchange.getIn().getBody(List.class);
        Map newRow = newExchange.getIn().getBody(Map.class);

        log.debug("Current rows count: {} ", rows.size());
        log.debug("Adding new row: {}", newRow);

        rows.add(newRow);
        oldExchange.getIn().setBody(rows);

        return oldExchange;
    }
}

