package com.dirapp.java_dasar_csv;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;

public class CSVTest {
  // untuk membuat CSV, menggunakan class CSVPrinter(Appendable, format), dimana Appendable bisa berupa Writer atau OutputStream
  // printRecord() digunakan untuk menambah data ke CSV
  // flush() digunakan untuk memasukkan data ke Writer meski CSVPrinter belum di close()

  // untuk membaca CSV, menggunakan CSVParser(Reader, format), dimana CSVParser merupakan turunan dari iterable, sehingga untuk mengambil datanya bisa dilakukan perulangan foreach
  // tiap perulangan, bisa ambil datanya dalam bentuk CSVRecord, yang merupakan representasi dari baris table
  // record.get(kolomKe) digunakan untuk mengambil value dari setiap kolom (dimulai dari 0)
  // record.get(namaKolom) digunakan untuk mengambil value sesuai dengan nama kolom, jika telah melakukan setHeader() pada formatnya


  @Test
  void createCSV() throws IOException {
    StringWriter writer = new StringWriter();
    // Path path = Path.of("contoh.csv");
    // Writer writer = Files.newBufferedWriter(path);

    CSVPrinter printer = new CSVPrinter(writer, CSVFormat.DEFAULT);
    printer.printRecord("Dira", "Sanjaya", 100);
    printer.printRecord("Andi", "Pratama", 95);
    printer.flush();

    String csv = writer.getBuffer().toString();
    System.out.println(csv);

    printer.close();
  }


  @Test
  void readCSV() throws IOException {
    Path path = Path.of("sample.csv");
    Reader reader = Files.newBufferedReader(path);

    CSVParser parser = new CSVParser(reader, CSVFormat.DEFAULT);
    for (CSVRecord record : parser) {
      System.out.println(record.get(0));
      System.out.println(record.get(1));
      System.out.println(record.get(2));
    }

    parser.close();
  }


  @Test
  void readCSVWithHeader() throws IOException {
    Path path = Path.of("sample.csv");
    Reader reader = Files.newBufferedReader(path);

    // memberi tahu baris pertama sebagai header
    CSVFormat format = CSVFormat.DEFAULT.builder().setHeader().build();
    CSVParser parser = new CSVParser(reader, format);

    for (CSVRecord record : parser) {
      System.out.println("First Name : " + record.get("First Name"));
      System.out.println("Last Name : " + record.get("Last Name"));
      System.out.println("Value : " + record.get("Value"));
    }

    parser.close();
  }


  @Test
  void createCSVWithHeader() throws IOException {
    StringWriter writer = new StringWriter();

    // membuat header ketika membuat CSV
    CSVFormat format = CSVFormat.DEFAULT.builder()
        .setHeader("First Name", "Last Name", "Value")
        .build();
    CSVPrinter printer = new CSVPrinter(writer, format);

    printer.printRecord("Dira", "Sanjaya", 100);
    printer.printRecord("Andi", "Pratama", 95);
    printer.flush();

    String csv = writer.getBuffer().toString();
    System.out.println(csv);

    printer.close();
  }


  @Test
  void createCSVWithTab() throws IOException {
    StringWriter writer = new StringWriter();

    // format untuk membuat CSV dengan pemisah tab, bukan comma
    CSVFormat format = CSVFormat.TDF.builder()
        .setHeader("First Name", "Last Name", "Value")
        .build();
    CSVPrinter printer = new CSVPrinter(writer, format);

    printer.printRecord("Dira", "Sanjaya", 100);
    printer.printRecord("Andi", "Pratama", 95);
    printer.flush();

    String csv = writer.getBuffer().toString();
    System.out.println(csv);

    printer.close();
  }
}