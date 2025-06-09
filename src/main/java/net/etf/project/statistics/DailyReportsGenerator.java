package net.etf.project.statistics;

import net.etf.project.financial.Receipt;
import net.etf.project.gui.MainApplication;

import java.util.*;
import java.text.SimpleDateFormat;
import java.util.stream.Collectors;

/**
 * The {@code DailyReportsGenerator} class is responsible for generating daily financial reports
 * based on the receipts. It groups the receipts by date and creates a {@code DailyReport} for each day.
 *
 * @author Jovana Šikanjić
 * @version 1.0
 */
public class DailyReportsGenerator
{
    /**
     * Generates a list of {@code DailyReport} objects, each representing a report for a specific date.
     * The receipts are grouped by the date of the rental, and a {@code DailyReport} is created for each date.
     *
     * @return a list of {@code DailyReport} objects for all the dates with receipts
     */
    public static List<DailyReport> generateAllDailyReports()
    {
        Map<String, List<Receipt>> receiptsByDate = MainApplication.receipts.stream()
                .collect(Collectors.groupingBy(r -> new SimpleDateFormat("dd.MM.yyyy").format(r.getRental().getRentalDateTime())));
        return receiptsByDate.entrySet().stream()
                .map(entry -> new DailyReport(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }
}
