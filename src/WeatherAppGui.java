import org.json.simple.JSONObject;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class WeatherAppGui extends JFrame {
    private JSONObject weatherData;
    private JLabel temperatureText;
    private JLabel weatherConditionDesc;
    private JLabel humidityText;
    private JLabel windspeedText;

    public WeatherAppGui(){
        super("Uleke's Weather App");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(450, 650);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);

        addGuiComponents();
    }

    private void addGuiComponents(){
        JTextField searchTextField = new JTextField();
        searchTextField.setBounds(15, 15, 351, 45);
        searchTextField.setFont(new Font("Dialog", Font.PLAIN, 24));

        add(searchTextField);

        JLabel weatherConditionImage = new JLabel(loadImage("src/cloudy.png"));
        weatherConditionImage.setBounds(0, 125, 450, 217);
        add(weatherConditionImage);
        JLabel temperatureText = new JLabel("__ C");
        temperatureText.setBounds(0, 350, 450, 54);
        temperatureText.setFont(new Font("Dialog", Font.BOLD, 48));
        temperatureText.setHorizontalAlignment(SwingConstants.CENTER);
        add(temperatureText);
        JLabel weatherConditionDesc = new JLabel("_____");
        weatherConditionDesc.setBounds(0, 405, 450, 36);
        weatherConditionDesc.setFont(new Font("Dialog", Font.PLAIN, 32));
        weatherConditionDesc.setHorizontalAlignment(SwingConstants.CENTER);
        add(weatherConditionDesc);


        JLabel humidityImage = new JLabel(loadImage("src/humidity.png"));
        humidityImage.setBounds(15, 500, 74, 66);
        add(humidityImage);


        JLabel humidityText = new JLabel("<html><b>Humidity</b> ___%</html>");
        humidityText.setBounds(90, 500, 85, 55);
        humidityText.setFont(new Font("Dialog", Font.PLAIN, 16));
        add(humidityText);


        JLabel windspeedImage = new JLabel(loadImage("src/windspeed.png"));
        windspeedImage.setBounds(220, 500, 74, 66);
        add(windspeedImage);


        JLabel windspeedText = new JLabel("<html><b>Windspeed</b> __km/h</html>");
        windspeedText.setBounds(310, 500, 85, 55);
        windspeedText.setFont(new Font("Dialog", Font.PLAIN, 16));
        add(windspeedText);


        JButton searchButton = new JButton(loadImage("src/search.png"));


        searchButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        searchButton.setBounds(375, 13, 47, 45);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String userInput = searchTextField.getText();


                if(userInput.replaceAll("\\s", "").length() <= 0){
                    return;
                }


                weatherData = WeatherApp.getWeatherData(userInput);


                String weatherCondition = (String) weatherData.get("weather_condition");

                switch(weatherCondition){
                    case "Clear":
                        weatherConditionImage.setIcon(loadImage("src/clear.png"));
                        getContentPane().setBackground(Color.YELLOW);
                        break;
                    case "Cloudy":
                        weatherConditionImage.setIcon(loadImage("src/cloudy.png"));
                        getContentPane().setBackground(Color.DARK_GRAY);
                       // setLabelColors(Color.WHITE);
                        break;
                    case "Rain":
                        weatherConditionImage.setIcon(loadImage("src/rain.png"));
                        getContentPane().setBackground(Color.BLUE);
                      //  setLabelColors(Color.WHITE);
                        break;
                    case "Snow":
                        weatherConditionImage.setIcon(loadImage("src/snow.png"));
                        getContentPane().setBackground(Color.WHITE);
                        break;
                }


                double temperature = (double) weatherData.get("temperature");
                temperatureText.setText(temperature + " C");


                weatherConditionDesc.setText(weatherCondition);


                long humidity = (long) weatherData.get("humidity");
                humidityText.setText("<html><b>Humidity</b> " + humidity + "%</html>");


                double windspeed = (double) weatherData.get("windspeed");
                windspeedText.setText("<html><b>Windspeed</b> " + windspeed + "km/h</html>");
            }
        });
        add(searchButton);
    }

    private ImageIcon loadImage(String resourcePath){
        try{

            BufferedImage image = ImageIO.read(new File(resourcePath));

            return new ImageIcon(image);
        }catch(IOException e){
            e.printStackTrace();
        }

        System.out.println("Could not find resource");
        return null;
    }

}




