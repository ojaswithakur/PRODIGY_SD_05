import com.opencsv.CSVWriter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.io.IOException;

public class EcommerceScraper {
    public static void main(String[] args) {
        // URL of the e-commerce website to scrape (Example: Amazon or Flipkart)
        String url = "https://www.flipkart.com/search?q=laptops";

        // Output CSV file path
        String csvFile = "products.csv";

        try {
            // Connect to the website and parse HTML
            Document doc = Jsoup.connect(url).get();

            // Select product elements (Update selectors based on the website structure)
            Elements products = doc.select("div._1AtVbE");

            // Prepare CSVWriter
            CSVWriter writer = new CSVWriter(new FileWriter(csvFile));

            // Write CSV header
            String[] header = {"Product Name", "Price", "Rating"};
            writer.writeNext(header);

            // Loop through the products and extract data
            for (Element product : products) {
                String name = product.select("div._4rR01T").text(); // Product name
                String price = product.select("div._30jeq3").text(); // Price
                String rating = product.select("div._3LWZlK").text(); // Rating

                // Skip if essential information is missing
                if (name.isEmpty() || price.isEmpty()) {
                    continue;
                }

                // Write data to CSV
                String[] data = {name, price, rating.isEmpty() ? "N/A" : rating};
                writer.writeNext(data);
            }

            // Close writer
            writer.close();

            System.out.println("Data scraped and saved to " + csvFile);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

