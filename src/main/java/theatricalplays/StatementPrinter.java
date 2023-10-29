package theatricalplays;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;

public class StatementPrinter {

  public static final NumberFormat frmt = NumberFormat.getCurrencyInstance(Locale.US);

  public StringBuffer print(Invoice invoice, Map<String, Play> plays) {

    StringBuffer result = new StringBuffer("Statement for " + invoice.customer + "\n");

    for (Performance perf : invoice.performances) {

      // print line for this order
      result = result.append("  " + perfPlay(perf, plays).name + ": " + frmt.format(perfPlay(perf, plays).getPrice(perf.audience)) + " (" + perf.audience + " seats)\n");
    }

    result = result.append("Amount owed is " + frmt.format(calculAmount(invoice, plays)) + "\n");
    result = result.append("You earned " + calculCredits(invoice, plays) + " credits\n");
    
    return result;
  }

  //fonction qui calcul total amount dépend de type
  public float calculAmount(Invoice invoice, Map<String, Play> plays){
    float thisAmount = 0;

    for(Performance perf: invoice.performances){
      thisAmount += perfPlay(perf, plays).getPrice(perf.audience);
    }

    return thisAmount;
  }



  //fonction qui calcul le volume de crédits
  private int calculCredits(Invoice invoice, Map<String, Play> plays){
    int result = 0;
    for(Performance perf: invoice.performances){
      result += perfPlay(perf, plays).getCredits(perf.audience);
    }
    return result;
  }

  // cette fonction permet de récupèrer un objet Play associé à un Performance donné à partir d'une collection de plays
  private Play perfPlay(Performance perf, Map<String, Play> plays) {
    return plays.get(perf.playID);
  }

  //fonction qui precise le format de currency
  /*private String formatCurrency(float totalAmount) {
    return NumberFormat.getCurrencyInstance(Locale.US).format(totalAmount);
  }*/

}
