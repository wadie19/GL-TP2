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

  public String toHTML(Invoice invoice, Map<String, Play> plays) {
    StringBuffer result = new StringBuffer("<!DOCTYPE html> \n"+"<html> \n"
    + "\t<head>\n"
    + "\t\t<style>\n" + "\t\t\ttable, th, td {border: 1px solid black; }\n"
    + "\t\t\tth, td { padding: 5px; }\n" 
    + "\t\t</style>\n" + "\t\t</head>\n" 
    + "\t<body> \n"
    + "\t\t<h1>Invoice</h1> \n"+"\t\t<ul><li><p><b>Client :</b> " 
    + invoice.customer+ "</ul></li></h2> \n"
    + "\t\t<table style=\"width: 400px; border: 1px solid black\"> \n"
    + "\t\t\t<tr> \n"+"\t\t\t\t <th>Piece</th> \n"+"\t\t\t\t <th>Seats sold</th> \n"
    + "\t\t\t\t <th>Price</th> \n"+"\t\t\t</tr> \n");

    for (Performance perf : invoice.performances) {
      result.append("\t\t\t<tr> \n"
        +"\t\t\t\t <td>" + perfPlay(perf, plays).name + "</td> \n"
        +"\t\t\t\t <td>" + perf.audience + "</td> \n"
        +"\t\t\t\t <td>" + frmt.format(perfPlay(perf, plays).getPrice(perf.audience)) + "</td> \n"
        +"\t\t\t</tr> \n");
    }

    result.append("\t\t\t<tr> \n"
    +"\t\t\t\t <td colspan=\"2\" style=\"text-align: right;\"><b>Total owed:<b></td> \n"
    +"\t\t\t\t <td>" + frmt.format(calculAmount(invoice, plays))+ "</td> \n"
    +"\t\t\t\t</tr> \n");

    result.append("\t\t\t<tr> \n"
    + "\t\t\t\t <td colspan=\"2\" style=\"text-align: right;\"><b>Fidelity points earned:<b></td> \n"
    + "\t\t\t\t <td>" + calculCredits(invoice, plays) + "</td> \n"
    + "\t\t\t</tr> \n");

    result.append("\t\t</table> \n"
    + "\t\t<p style=\"font-style: italic;\">Payment is required under 30 days. We can break your knees if you don't do so.</p> \n"
    + "\t</body> \n"
    + "</html> \n");

    return result.toString();
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
