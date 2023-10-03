import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/*
 Program4 reads a CSV file containing car sales data, processes the records, and calculates broken deals.
 @author Jere Perisic
 @version October 2, 2023
 */
public class program4 {

  /*
   The main method of the program.
   @param args Command-line arguments (not used in this program).
   */
  public static void main(String[] args) {
    String file = "car_sales_data.csv";

    try {
      long startReadTime = System.currentTimeMillis();
      List<SaleRecord> saleRecords = read(file);
      long endReadTime = System.currentTimeMillis();
      long startTime = System.currentTimeMillis();
      int brokenDeals = calculateBreaks(saleRecords, 5);
      long endTime = System.currentTimeMillis();

      System.out.println(brokenDeals + " deals were broken");
      System.out.println((double) (endTime - startTime) / 1000 + " seconds to simulate the process");
      System.out.println((double) (endReadTime - startReadTime) / 1000 + " seconds to read the file");
    } catch (IOException | ParseException e) {
      e.printStackTrace();
    }
  }

  /*
Reads the CSV file and parses its contents into a list of SaleRecords.
@param filePath The path to the CSV file.
@return A list of SaleRecord objects.
@throws IOException    If an error occurs while reading the file.
@throws ParseException If an error occurs while parsing dates in the file.
   */
  public static List<SaleRecord> read(String filePath) throws IOException, ParseException {
    List<SaleRecord> saleRecords = new ArrayList<>();

    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
      String line;
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

      String header = reader.readLine();

      while ((line = reader.readLine()) != null) {
        String[] data = line.split(",");
        if (data.length != 9) {
          System.err.println("Invalid CSV line: " + line);
          continue;
        }

        Date date = dateFormat.parse(data[0]);
        String salesperson = data[1];
        String customerName = data[2];
        String carMake = data[3];
        String carModel = data[4];
        int carYear = Integer.parseInt(data[5]);
        double salePrice = Double.parseDouble(data[6]);
        double commissionRate = Double.parseDouble(data[7]);
        double commissionEarned = Double.parseDouble(data[8]);

        SaleRecord saleRecord = new SaleRecord(date, salesperson, customerName, carMake, carModel, carYear, salePrice, commissionRate, commissionEarned);
        saleRecords.add(saleRecord);
      }
    }

    Collections.sort(saleRecords, new Comparator<SaleRecord>() {
      @Override
      public int compare(SaleRecord o1, SaleRecord o2) {
        return o1.getDate().compareTo(o2.getDate());
      }
    });

    return saleRecords;
  }

  /*
   Processes the sale records and calculates the number of broken deals.
   @param saleRecords  A list of SaleRecord objects.
   @param nCounters  The number of counters available for processing.
   @return The number of broken deals.
   */
  public static int calculateBreaks(List<SaleRecord> saleRecords, int nCounters) {
    Collections.sort(saleRecords, new Comparator<SaleRecord>() {
      @Override
      public int compare(SaleRecord o1, SaleRecord o2) {
        return o1.getDate().compareTo(o2.getDate());
      }
    });

    int workTimeCounters = 1440;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date currentDate = null;
    int processingTimeRecords = 0;
    Queue<SaleRecord> queue = new LinkedList<>();
    int brokenDeals = 0;

    for (SaleRecord saleRecord : saleRecords) {
      Date dateSale = saleRecord.getDate();
      if (currentDate == null || !dateFormat.format(currentDate).equals(dateFormat.format(dateSale))) {
        currentDate = dateSale;
        processingTimeRecords = 0;
      }

      int maxProcessingTime = nCounters * workTimeCounters;

      if (processingTimeRecords + saleRecord.getProcessingTime() <= maxProcessingTime) {
        queue.add(saleRecord);
        processingTimeRecords += saleRecord.getProcessingTime();
      } else {
        brokenDeals++;
      }
    }

    while (!queue.isEmpty()) {
      int counters = Math.min(nCounters, queue.size());
      for (int i = 0; i < counters; i++) {
        SaleRecord record = queue.poll();
        processingTimeRecords -= record.getProcessingTime();
      }
      if (processingTimeRecords > 0) {
        processingTimeRecords -= workTimeCounters;
      }
    }

    return brokenDeals;
  }
}
