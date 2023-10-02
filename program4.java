import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class program4 {
  public static void main(String[] args) {
    String file = "small_sample.csv";
    List<SaleRecord> saleRecords = read(file);
    processSaleRecords(saleRecords, 5, 24);


  }

  public static List<SaleRecord> read(String csvFilePath) {
    List<SaleRecord> saleRecords = new ArrayList<>();

    try (BufferedReader reader = new BufferedReader(new FileReader(csvFilePath))) {
      String line;
      String header = reader.readLine();

      while ((line = reader.readLine()) != null) {
        String[] data = line.split(",");
        if (data.length != 9) {
          System.err.println("Invalid data: " + line);
          continue;
        }

        String date = data[0].trim();
        String salesperson = data[1].trim();
        String customerName = data[2].trim();
        String carMake = data[3].trim();
        String carModel = data[4].trim();
        int carYear = Integer.parseInt(data[5].trim());
        double salePrice = Double.parseDouble(data[6].trim());
        double commissionRate = Double.parseDouble(data[7].trim());
        double commissionEarned = Double.parseDouble(data[8].trim());

        SaleRecord saleRecord = new SaleRecord(date, salesperson, customerName, carMake, carModel, carYear, salePrice, commissionRate, commissionEarned);
        saleRecords.add(saleRecord);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    Collections.sort(saleRecords, new Comparator<SaleRecord>() {
      @Override
      public int compare(SaleRecord record1, SaleRecord record2) {
        return record1.getDate().compareTo(record2.getDate());
      }
    });

    return saleRecords;
  }
  public static void processSaleRecords(List<SaleRecord> saleRecords, int numberOfCounters, int hoursPerDay) {
    int maxWaitDays = 10;
    int totalProcessingTimePerDay = numberOfCounters * hoursPerDay * 60; // minutes

    List<SaleRecord> queue = new ArrayList<>();
    int currentDay = 0;
    int brokenDealsCount = 0; // Counter for broken deals

    for (SaleRecord saleRecord : saleRecords) {
      int processingTime = 0;

      if (saleRecord.getCommissionRate() >= 0.09) {
        processingTime = 2; // 2 minutes
      } else if (saleRecord.getCommissionRate() >= 0.06) {
        processingTime = 3; // 3 minutes
      } else {
        processingTime = 1; // 1 minute
      }

      String dateStr = saleRecord.getDate(); // Assuming getDate() returns a string in "yyyy-MM-dd" format
      int saleDay = Integer.parseInt(dateStr.substring(8, 10)); // Extract day part and parse to an integer

      if (currentDay != saleDay) {
        // Move to the next day
        currentDay = saleDay;
        totalProcessingTimePerDay = numberOfCounters * hoursPerDay * 60; // Reset daily processing time
      }

      if (totalProcessingTimePerDay < processingTime || currentDay > maxWaitDays) {
        // If there's not enough time left in the day or the customer has waited too long, break the deal
        System.out.println("Deal for Sale Record with date " + saleRecord.getDate() + " is broken.");
        brokenDealsCount++; // Increment the counter for broken deals
      } else {
        // Process the sale record
        queue.add(saleRecord);
        totalProcessingTimePerDay -= processingTime;
        System.out.println("Sale Record with date " + saleRecord.getDate() + " is processed on Day " + currentDay);
      }
    }

    // Print the total number of broken deals after processing all sale records
    System.out.println("Total broken deals: " + brokenDealsCount);
  }


}
