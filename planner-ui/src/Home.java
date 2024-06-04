import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.jdatepicker.JDatePicker;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import utils.DateLabelFormatter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;

import static java.util.Locale.*;

public class Home extends JFrame{

    Long startTime;
    Long endTime;

    JsonObject budgetObj;
    JLabel lblResult = new JLabel();

    public Home() {
        setTitle("Budget Planner");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setVisible(true);

        UtilDateModel model = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());

        // Add action listener to the JDatePicker
        datePicker.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // This method is called when a date is picked
                Date selectedDate = (Date) datePicker.getModel().getValue();
                System.out.println("Date picked: " + selectedDate);

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(selectedDate);

                // Set hours, minutes, and seconds
                calendar.set(Calendar.HOUR_OF_DAY, 0); // 24-hour format
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);

                // Update the Date object with the modified time
                selectedDate = calendar.getTime();

                // Print the modified Date object
                System.out.println("Modified Date: " + selectedDate);

                long milliseconds = selectedDate.getTime();
                startTime = milliseconds;
                // Print the milliseconds
                System.out.println("StartTime: " + milliseconds);
            }
        });

        // Create a panel with GridBagLayout
        JPanel panel = new JPanel(new GridBagLayout());


        // Set the GridBagConstraints for center alignment
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.anchor = GridBagConstraints.CENTER;

        // Add the JDatePicker to the panel with constraints
        datePicker.setVisible(true);
        panel.add(datePicker, constraints);
        //panel.setVisible(true);

        UtilDateModel model2 = new UtilDateModel();
        Properties p2 = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanel2 = new JDatePanelImpl(model2, p2);
        JDatePickerImpl datePicker2 = new JDatePickerImpl(datePanel2, new DateLabelFormatter());

        datePicker2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // This method is called when a date is picked
                Date selectedDate = (Date) datePicker2.getModel().getValue();
                System.out.println("Date picked: " + selectedDate);

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(selectedDate);

                // Set hours, minutes, and seconds
                calendar.set(Calendar.HOUR_OF_DAY, 23); // 24-hour format
                calendar.set(Calendar.MINUTE, 59);
                calendar.set(Calendar.SECOND, 59);

                // Update the Date object with the modified time
                selectedDate = calendar.getTime();

                // Print the modified Date object
                System.out.println("Modified Date: " + selectedDate);

                long milliseconds = selectedDate.getTime();
                endTime = milliseconds;
                // Print the milliseconds
                System.out.println("EndTime: " + milliseconds);

                if(endTime < startTime) {
                    JOptionPane.showMessageDialog(null, "End date cannot be smaller from start date!", "Alert", JOptionPane.ERROR_MESSAGE);
                    datePicker2.getModel().setValue(null);
                }
            }
        });

        GridBagConstraints constraints2 = new GridBagConstraints();
        constraints2.gridx = 20;
        constraints2.gridy = 0;
        constraints2.insets = new Insets(10, 10, 10, 10);
        constraints2.anchor = GridBagConstraints.CENTER;

        panel.add(datePicker2, constraints2);

        JButton btnBudget = new JButton();
        btnBudget.setText("Get Budget");
        constraints.gridy = 10;
        constraints.gridx = 15;
        constraints.insets = new Insets(0,0,0,0);

        btnBudget.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(startTime == null || endTime == null) {
                    JOptionPane.showMessageDialog(null, "Both start date and end date are mandatory!", "Alert", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                // This method is called when a date is picked
                StringBuilder sb = new StringBuilder();
                sb.append("{");
                    sb.append("\"startTime\":"+startTime+",");
                    sb.append("\"endTime\":"+endTime+",");
                sb.append("\"currency\":"+"\"EUR\"");
                sb.append("}");
                String response = performPOSTRequest("http://localhost:8088/api/budget/get", sb.toString());
                JsonObject responseJson = parseJSON(response);
                if(responseJson != null) {
                    budgetObj = responseJson;

                    Double balance = budgetObj.get("relevantObject").getAsJsonObject().get("balance").getAsDouble();

                    NumberFormat euroFormat = NumberFormat.getCurrencyInstance(GERMANY);

                    String formattedAmount = euroFormat.format(balance);
                    // Print the formatted money string
                    System.out.println("Formatted Amount: " + formattedAmount);

                    lblResult.setText("Balance: "+formattedAmount);
                    GridBagConstraints constLbl = new GridBagConstraints();
                    constLbl.gridy = 30;
                    constLbl.gridx = 15;
                    constLbl.insets = new Insets(0,0,0,0);
                    constLbl.anchor = GridBagConstraints.CENTER;
                    lblResult.setVisible(true);
                    panel.add(lblResult, constLbl);
                    panel.setVisible(true);
                }
            }
        });

        panel.add(btnBudget, constraints);



        panel.setVisible(true);
        // Add the panel to the frame's content pane
        getContentPane().add(panel);

        // Center the frame on the screen
        setLocationRelativeTo(null);

    }

    private static String performPOSTRequest(String apiUrl, String jsonPayload) {
        StringBuilder response = new StringBuilder();
        HttpURLConnection connection = null;
        try {
            URL url = new URL(apiUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonPayload.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
            }
        } catch (ProtocolException e) {
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        System.out.println(response.toString());
        return response.toString();
    }

    private static JsonObject parseJSON(String jsonString) {
        try {
            Gson gson = new Gson();
            return gson.fromJson(jsonString, JsonObject.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
