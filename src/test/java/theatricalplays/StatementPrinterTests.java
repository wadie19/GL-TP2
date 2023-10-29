package theatricalplays;

//import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.approvaltests.Approvals.verify;
import static org.approvaltests.Approvals.verifyHtml;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StatementPrinterTests {

    @Test
    void exampleStatement() {
        Map<String, Play> plays = Map.of(
                "hamlet",  new PlayTragedy("Hamlet"),
                "as-like", new PlayComedy("As You Like It"),
                "othello", new PlayTragedy("Othello"));

        Invoice invoice = new Invoice(new Customer("BigCo", "1", 103), List.of(
                new Performance("hamlet", 55),
                new Performance("as-like", 35),
                new Performance("othello", 40)));

        StatementPrinter statementPrinter = new StatementPrinter();
        var result = statementPrinter.print(invoice, plays);

        verify(result);
    } 

    @Test
    void exampleStatementCustomer() {
        Map<String, Play> plays = Map.of(
                "hamlet",  new PlayTragedy("Hamlet"),
                "as-like", new PlayComedy("As You Like It"),
                "othello", new PlayTragedy("Othello"));

        Invoice invoice = new Invoice(new Customer("BigCo", "1", 150), List.of(
                new Performance("hamlet", 55),
                new Performance("as-like", 35),
                new Performance("othello", 40)));
        
        StatementPrinter statementPrinter = new StatementPrinter();
        var result = statementPrinter.print(invoice, plays);

        verify(result);
    }

    @Test
    void exampleStatementHTML() {
        Map<String, Play> plays = Map.of(
                "hamlet",  new PlayTragedy("Hamlet"),
                "as-like", new PlayComedy("As You Like It"),
                "othello", new PlayTragedy("Othello"));

        Invoice invoice = new Invoice(new Customer("BigCo", "2", 0), List.of(
                new Performance("hamlet", 55),
                new Performance("as-like", 35),
                new Performance("othello", 40)));

        StatementPrinter statementPrinter = new StatementPrinter();
        var result = statementPrinter.toHTML(invoice, plays);

        verifyHtml(result);
    }

    /*@Test
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
    }*/

    @Test
    void testAudience30ForTragedy() {
    HashMap<String, Play> plays = new HashMap<>();
    plays.put("playID", new PlayTragedy("Play"));

    Invoice invoice = new Invoice(new Customer("Customer", "1", 0), List.of(new Performance("playID", 30)));

    StatementPrinter statementPrinter = new StatementPrinter();
    var result = statementPrinter.print(invoice, plays);

    StringBuffer expectedStatement = new StringBuffer();
    expectedStatement.append("Statement for Customer\n");
    expectedStatement.append("  Play: $400.00 (30 seats)\n");
    expectedStatement.append("Amount owed is $400.00\n");
    expectedStatement.append("You earned 0 credits\n");

    assertEquals(expectedStatement.toString(), result.toString());
}


    @Test
    void testAudience20ForComedy() {
    HashMap<String, Play> plays = new HashMap<>();
    plays.put("playID", new PlayComedy("Play"));

    Invoice invoice = new Invoice(new Customer("Customer", "1", 0), List.of(new Performance("playID", 20)));

    StatementPrinter statementPrinter = new StatementPrinter();
    var result = statementPrinter.print(invoice, plays);

    StringBuffer expectedStatement = new StringBuffer();
    expectedStatement.append("Statement for Customer\n");
    expectedStatement.append("  Play: $360.00 (20 seats)\n");
    expectedStatement.append("Amount owed is $360.00\n");
    expectedStatement.append("You earned 4 credits\n");

    assertEquals(expectedStatement.toString(), result.toString());
    }
}
