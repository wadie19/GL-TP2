package theatricalplays;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;

public class StatementPrinter {

  public StringBuffer print(Invoice invoice, Map<String, Play> plays) {
    
    float totalAmount = 0;
    int volumeCredits = 0;

    StringBuffer result = new StringBuffer("Statement for " + invoice.customer + "\n");

    for (Performance perf : invoice.performances) {
      Play play = plays.get(perf.playID);
      float thisAmount = calculAmount(perf, play);

      volumeCredits += calculCredits(perf, play.type);
      // print line for this order
      result = result.append("  " + play.name + ": " + formatCurrency(thisAmount) + " (" + perf.audience + " seats)\n");
      totalAmount += thisAmount;
    }

    result = result.append("Amount owed is " + formatCurrency(totalAmount) + "\n");
    result = result.append("You earned " + volumeCredits + " credits\n");
    return result;
  }

  //fonction qui calcul total amount dépend de type
  public float calculAmount(Performance perf, Play play){
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

    return thisAmount;
  }

  //fonction qui calcul le volume de crédits
  private int calculCredits(Performance perf, String type){
    int result = 0;
    result += Math.max(perf.audience - 30, 0);

    // add extra credit for every ten comedy attendees
    if (Play.COMEDY.equals(type)) 
      result += Math.floor(perf.audience / 5);
    return result;
  }

  //fonction qui precise le format de currency
  private String formatCurrency(float totalAmount) {
    return NumberFormat.getCurrencyInstance(Locale.US).format(totalAmount);
  }

}
