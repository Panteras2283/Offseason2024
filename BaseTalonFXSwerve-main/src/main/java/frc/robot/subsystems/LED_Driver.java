// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;



public class LED_Driver extends SubsystemBase {
  /** Creates a new LED_Driver. */
  public LED_Driver() {
     
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void setPreset1(){

    try {
            // The URL you want to request
            String targetUrl = "http://10.22.83.100/win&PL=1";

            // Create a URL object
            URL url = new URL(targetUrl);

            // Open a connection to the URL
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set request method to GET
            connection.setRequestMethod("GET");

            // Optional: Set request headers (if needed)
            // connection.setRequestProperty("User-Agent", "Mozilla/5.0");

            // Get the response code (HTTP status)
            int responseCode = connection.getResponseCode();
            //System.out.println("Response Code: " + responseCode);

            // Read the response content
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder responseContent = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                responseContent.append(line);
            }
            reader.close();

            // Print the response content
            //System.out.println("Response Content:\n" + responseContent.toString());

            // Close the connection
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

  }

  public void setPreset2(){

    try {
            // The URL you want to request
            String targetUrl = "http://10.22.83.100/win&PL=2";

            // Create a URL object
            URL url = new URL(targetUrl);

            // Open a connection to the URL
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set request method to GET
            connection.setRequestMethod("GET");

            // Optional: Set request headers (if needed)
            // connection.setRequestProperty("User-Agent", "Mozilla/5.0");

            // Get the response code (HTTP status)
            int responseCode = connection.getResponseCode();
            //System.out.println("Response Code: " + responseCode);

            // Read the response content
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder responseContent = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                responseContent.append(line);
            }
            reader.close();

            // Print the response content
            //System.out.println("Response Content:\n" + responseContent.toString());

            // Close the connection
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

  }

  public void setPreset3(){

    try {
      // The URL you want to request
      String targetUrl = "http://10.22.83.100/win&PL=3";

      // Create a URL object
      URL url = new URL(targetUrl);

      // Open a connection to the URL
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();

      // Set request method to GET
      connection.setRequestMethod("GET");

      // Optional: Set request headers (if needed)
      // connection.setRequestProperty("User-Agent", "Mozilla/5.0");

      // Get the response code (HTTP status)
      int responseCode = connection.getResponseCode();
      //System.out.println("Response Code: " + responseCode);

      // Read the response content
      BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
      String line;
      StringBuilder responseContent = new StringBuilder();
      while ((line = reader.readLine()) != null) {
          responseContent.append(line);
      }
      reader.close();

      // Print the response content
      //System.out.println("Response Content:\n" + responseContent.toString());

      // Close the connection
      connection.disconnect();
  } catch (Exception e) {
      e.printStackTrace();
  }

  }

  public void setPreset4(){

    try {
      // The URL you want to request
      String targetUrl = "http://10.22.83.100/win&PL=4";

      // Create a URL object
      URL url = new URL(targetUrl);

      // Open a connection to the URL
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();

      // Set request method to GET
      connection.setRequestMethod("GET");

      // Optional: Set request headers (if needed)
      // connection.setRequestProperty("User-Agent", "Mozilla/5.0");

      // Get the response code (HTTP status)
      int responseCode = connection.getResponseCode();
      //System.out.println("Response Code: " + responseCode);

      // Read the response content
      BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
      String line;
      StringBuilder responseContent = new StringBuilder();
      while ((line = reader.readLine()) != null) {
          responseContent.append(line);
      }
      reader.close();

      // Print the response content
      //System.out.println("Response Content:\n" + responseContent.toString());

      // Close the connection
      connection.disconnect();
  } catch (Exception e) {
      e.printStackTrace();
  }
  }

  
}