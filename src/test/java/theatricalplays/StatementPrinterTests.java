package theatricalplays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.approvaltests.Approvals.verify;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StatementPrinterTests {

    @Test
    void exampleStatement() {
        Map<String, Play> plays = Map.of(
                "hamlet",  new Play("Hamlet", "tragedy"),
                "as-like", new Play("As You Like It", "comedy"),
                "othello", new Play("Othello", "tragedy"));

        Invoice invoice = new Invoice("BigCo", List.of(
                new Performance("hamlet", 55),
                new Performance("as-like", 35),
                new Performance("othello", 40)));

        StatementPrinter statementPrinter = new StatementPrinter();
        var result = statementPrinter.print(invoice, plays);

        verify(result);
    }

    @Test
    void statementWithNewPlayTypes() {
        Map<String, Play> plays = Map.of(
                "henry-v",  new Play("Henry V", "history"),
                "as-like", new Play("As You Like It", "pastoral"));

        Invoice invoice = new Invoice("BigCo", List.of(
                new Performance("henry-v", 53),
                new Performance("as-like", 55)));

        StatementPrinter statementPrinter = new StatementPrinter();
        Assertions.assertThrows(Error.class, () -> {
        statementPrinter.print(invoice, plays);
        });
    }

    @Test
    void testAudience30ForTragedy() {
    HashMap<String, Play> plays = new HashMap<>();
    plays.put("playID", new Play("Play", "tragedy"));

    Invoice invoice = new Invoice("Customer", List.of(new Performance("playID", 30)));

    StatementPrinter statementPrinter = new StatementPrinter();
    var result = statementPrinter.print(invoice, plays);

    String expectedStatement = "Statement for Customer\n" +
            "  Play: $400.00 (30 seats)\n" +
            "Amount owed is $400.00\n" +
            "You earned 0 credits\n";

    assertEquals(expectedStatement, result);
    }

    @Test
    void testAudience20ForComedy() {
    HashMap<String, Play> plays = new HashMap<>();
    plays.put("playID", new Play("Play", "comedy"));

    Invoice invoice = new Invoice("Customer", List.of(new Performance("playID", 20)));

    StatementPrinter statementPrinter = new StatementPrinter();
    var result = statementPrinter.print(invoice, plays);

    String expectedStatement = "Statement for Customer\n" +
            "  Play: $360.00 (20 seats)\n" +
            "Amount owed is $360.00\n" +
            "You earned 4 credits\n"; 

    assertEquals(expectedStatement, result);
    }
}
