package theatricalplays;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;

public class StatementPrinter {

  public StringBuffer print(Invoice invoice, Map<String, Play> plays) {
    
    float totalAmount = 0;
    int volumeCredits = 0;

    StringBuffer result = new StringBuffer("Statement for " + invoice.customer + "\n");
    NumberFormat frmt = NumberFormat.getCurrencyInstance(Locale.US);

    for (Performance perf : invoice.performances) {
      Play play = plays.get(perf.playID);
      float thisAmount = 0;

      switch (play.type) {
        case Play.TRAGEDY:
          thisAmount = 400;
          if (perf.audience > 30) {
            thisAmount += 10 * (perf.audience - 30);
          }
          break;
        case Play.COMEDY:
          thisAmount = 300;
          if (perf.audience > 20) {
            thisAmount += 100 + 5 * (perf.audience - 20);
          }
          thisAmount += 3 * perf.audience;
          break;
        default:
          throw new Error("unknown type: ${play.type}");
      }

      // add volume credits
      volumeCredits += Math.max(perf.audience - 30, 0);
      // add extra credit for every ten comedy attendees
      if ("comedy".equals(play.type)) volumeCredits += Math.floor(perf.audience / 5);

      // print line for this order
      result = result.append("  " + play.name + ": " + frmt.format(thisAmount) + " (" + perf.audience + " seats)\n");
      totalAmount += thisAmount;
    }
    result = result.append("Amount owed is " + frmt.format(totalAmount) + "\n");
    result = result.append("You earned " + volumeCredits + " credits\n");
    return result;
  }

}
