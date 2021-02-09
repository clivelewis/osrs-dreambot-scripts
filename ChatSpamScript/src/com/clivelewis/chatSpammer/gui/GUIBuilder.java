package com.clivelewis.chatSpammer.gui;

import com.clivelewis.chatSpammer.Main;
import com.clivelewis.chatSpammer.utils.Utils;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.map.Tile;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.Arrays;

/**
 * @author Clive on 10/3/2019
 */
public class GUIBuilder {
    private Main main;

    private Image icon;

    private JFrame mainFrame;
    private JLabel nameLabel;
    private JLabel authorLabel;
    private JLabel versionLabel;
    private JLabel accountListLabel;
    private JLabel settingsLabel;
    private JLabel locationLabel;
    private JLabel messageDelayLabel;
    private JLabel msLabel;
    private JLabel changeAccountTimeLabel;
    private JLabel minutesLabel;
    private JLabel tooltipLabel;
    private JLabel messagesLabel;
    private JLabel messagesTooltipLabel;
    private JLabel coordinatesLabel;
    private JLabel coordinatesTooltipLabel;
    private JScrollPane scrollPanel;
    private JTextArea accountsTextArea;
    private JComboBox locationComboBox;
    private JTextField coordinatesTextField;
    private JTextField messageDelayTextField;
    private JTextField changeAccountTimeTextField;
    private JScrollPane messagesScrollPane;
    private JTextArea messagesTextArea;
    private JButton startButton;


    public GUIBuilder(Main main){
        this.main = main;
    }


    public void initGUI(){
        getIcon();

        mainFrame = new JFrame();
        mainFrame.setTitle("OSRS Chat Spammer");
        mainFrame.setSize(new Dimension(470, 510));
        mainFrame.setBackground(Utils.GUI_BACKGROUND_COLOR);
        mainFrame.setPreferredSize(new Dimension(470,450));
        mainFrame.setResizable(false);
        mainFrame.setLocationRelativeTo(main.getClient().getInstance().getCanvas());
        mainFrame.getContentPane().setLayout(null);
        mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        if(icon != null) mainFrame.setIconImage(icon);

        initComponents();
        setLabelValues();
        setAccountListArea();
        setLocationChoiceBox();
        setCoordinatesField();
        setMessageDelayField();
        setAccountChangeIntervalField();
        setMessagesTextArea();

        setupStartButton();

        mainFrame.pack();
        mainFrame.setVisible(true);
    }


    private void setupStartButton() {
        startButton.setText("START");
        startButton.setBounds(290, 240, 100, 40);

        startButton.addActionListener(listener -> {
            main.accountChangeInterval = Long.parseLong(changeAccountTimeTextField.getText());
            main.messageDelay = Integer.parseInt(messageDelayTextField.getText());

            if(locationComboBox.getSelectedItem().equals("Grand Exchange")){
               main.currentSpamArea = Utils.GRAND_EXCHANGE;
            }else if(locationComboBox.getSelectedItem().equals("Lumbridge")){
                main.currentSpamArea = Utils.LUMBRIDGE;
            }else if(locationComboBox.getSelectedItem().equals("Custom")){
                int xCoord = Integer.parseInt(coordinatesTextField.getText().substring(0,coordinatesTextField.getText().indexOf(",")).trim());
                int yCoord = Integer.parseInt(coordinatesTextField.getText().substring(coordinatesTextField.getText().indexOf(",") + 1).trim());
                main.currentSpamArea = new Tile(xCoord, yCoord);
            }

            main.messageList.addAll(Arrays.asList(messagesTextArea.getText().trim().split("\\n")));
            main.accountList.addAll(Arrays.asList(accountsTextArea.getText().split("\\n")));
            main.currentAccount = accountsTextArea.getText().split("\\n")[0];

            main.setupInitialValues();
            main.isRunning = true;
            mainFrame.dispose();
        });

        mainFrame.add(startButton);
    }

    private void initComponents() {
        nameLabel = new JLabel();
        authorLabel = new JLabel();
        versionLabel = new JLabel();
        accountListLabel = new JLabel();
        settingsLabel = new JLabel();
        locationLabel = new JLabel();
        messageDelayLabel = new JLabel();
        msLabel = new JLabel();
        changeAccountTimeLabel = new JLabel();
        minutesLabel = new JLabel();
        tooltipLabel = new JLabel();
        coordinatesLabel = new JLabel();
        coordinatesTooltipLabel = new JLabel();
        scrollPanel = new JScrollPane();
        accountsTextArea = new JTextArea();
        locationComboBox = new JComboBox();
        coordinatesTextField = new JTextField();
        messageDelayTextField = new JTextField();
        changeAccountTimeTextField = new JTextField();
        startButton = new JButton();

        messagesScrollPane = new JScrollPane();
        messagesTextArea = new JTextArea();
        messagesLabel = new JLabel();
        messagesTooltipLabel = new JLabel();

    }

    private void setLabelValues(){
        //---- nameLabel ----
        nameLabel.setText("OSRS Chat Spammer");
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainFrame.add(nameLabel);
        nameLabel.setBounds(new Rectangle(new Point(160, 15), nameLabel.getPreferredSize()));

        //---- authorLabel ----
        authorLabel.setText("by CliveLewis");
        authorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainFrame.add(authorLabel);
        authorLabel.setBounds(new Rectangle(new Point(200, 35), authorLabel.getPreferredSize()));

        //---- versionLabel ----
        versionLabel.setText("v1.1");
        mainFrame.add(versionLabel);
        versionLabel.setBounds(new Rectangle(new Point(430, 5), versionLabel.getPreferredSize()));

        //---- accountListLabel ----
        accountListLabel.setText("Account List");
        accountListLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        mainFrame.add(accountListLabel);
        accountListLabel.setBounds(new Rectangle(new Point(70, 80), accountListLabel.getPreferredSize()));

        //---- settingsLabel ----
        settingsLabel.setText("Settings");
        settingsLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        mainFrame.add(settingsLabel);
        settingsLabel.setBounds(new Rectangle(new Point(300, 80), settingsLabel.getPreferredSize()));

        //---- locationLabel ----
        locationLabel.setText("Location:");
        mainFrame.add(locationLabel);
        locationLabel.setBounds(220, 120, locationLabel.getPreferredSize().width, 20);

        //---- coordinatesLabel ----
        coordinatesLabel.setText("Coordinates:");
        mainFrame.add(coordinatesLabel);
        coordinatesLabel.setBounds(220, 150, coordinatesLabel.getPreferredSize().width, 20);

        //---- coordinatesTooltipLabel ----
        coordinatesTooltipLabel.setText("(x,y)");
        mainFrame.add(coordinatesTooltipLabel);
        coordinatesTooltipLabel.setBounds(375, 150, coordinatesTooltipLabel.getPreferredSize().width, 25);

        //---- messageDelayLabel ----
        messageDelayLabel.setText("Message Delay:");
        mainFrame.add(messageDelayLabel);
        messageDelayLabel.setBounds(220, 180, messageDelayLabel.getPreferredSize().width, 25);

        //---- msLabel ----
        msLabel.setText("ms");
        mainFrame.add(msLabel);
        msLabel.setBounds(365, 180, msLabel.getPreferredSize().width, 25);

        //---- changeAccountTimeLabel ----
        changeAccountTimeLabel.setText("Change account every");
        mainFrame.add(changeAccountTimeLabel);
        changeAccountTimeLabel.setBounds(new Rectangle(new Point(220, 210), changeAccountTimeLabel.getPreferredSize()));

        //---- minutesLabel ----
        minutesLabel.setText("minutes");
        mainFrame.add(minutesLabel);
        minutesLabel.setBounds(new Rectangle(new Point(385, 210), minutesLabel.getPreferredSize()));

        //---- tooltipLabel ----
        tooltipLabel.setText("login:password");
        tooltipLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        mainFrame.add(tooltipLabel);
        tooltipLabel.setBounds(new Rectangle(new Point(70, 265), tooltipLabel.getPreferredSize()));

        //---- messagesLabel ----
        messagesLabel.setText("Message List");
        messagesLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        mainFrame.add(messagesLabel);
        messagesLabel.setBounds(new Rectangle(new Point(180, 290), messagesLabel.getPreferredSize()));

        //---- messagesTooltipLabel ----
        messagesTooltipLabel.setText("1 row = 1 message. OSRS supports up to 80 characters in 1 message.");
        messagesTooltipLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        mainFrame.add(messagesTooltipLabel);
        messagesTooltipLabel.setBounds(new Rectangle(new Point(10, 400), messagesTooltipLabel.getPreferredSize()));
    }

    private void setAccountListArea(){
        scrollPanel.setViewportView(accountsTextArea);
        accountsTextArea.setMargin(new Insets(5,5,0,0));
        accountsTextArea.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        mainFrame.add(scrollPanel);
        scrollPanel.setBounds(10, 110, 200, 150);
    }

    private void setLocationChoiceBox() {
        locationComboBox.setBounds(285, 120, 120, 20);
        locationComboBox.addItem("Lumbridge");
        locationComboBox.addItem("Grand Exchange");
        locationComboBox.addItem("Custom");

        coordinatesLabel.setForeground(Color.GRAY);
        coordinatesTooltipLabel.setForeground(Color.GRAY);
        coordinatesTextField.setEnabled(false);

        locationComboBox.addItemListener(e -> {
            if(e.getItem().equals("Custom")){
                coordinatesLabel.setForeground(null);
                coordinatesTooltipLabel.setForeground(null);
                coordinatesTextField.setEnabled(true);
            }else{
                coordinatesLabel.setForeground(Color.GRAY);
                coordinatesTooltipLabel.setForeground(Color.GRAY);
                coordinatesTextField.setEnabled(false);
            }
        });
        mainFrame.add(locationComboBox);
    }

    private void setCoordinatesField() {
        coordinatesTextField.setBounds(300, 150, 70, 20);
        coordinatesTextField.setText("3000,3000");
        mainFrame.add(coordinatesTextField);
    }

    private void setMessageDelayField() {
        messageDelayTextField.setBounds(315, 180, 45, 20);
        messageDelayTextField.setText("10000");
        mainFrame.add(messageDelayTextField);
    }

    private void setAccountChangeIntervalField() {
        changeAccountTimeTextField.setBounds(350, 210, 30, 20);
        changeAccountTimeTextField.setText("20");
        mainFrame.add(changeAccountTimeTextField);
    }

    private void setMessagesTextArea(){
        messagesScrollPane.setViewportView(messagesTextArea);
        messagesTextArea.setMargin(new Insets(5,5,0,5));
        messagesTextArea.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        messagesTextArea.setText("cyan: Bet King - #1 RS Discord Gambling Community! Giveaways, Games, Sports!\n");
        messagesTextArea.setText(messagesTextArea.getText() + "red:Discord[dot]gg/hav7k4k OR google \"BetKing Discord\"\n");
        messagesTextArea.setText(messagesTextArea.getText() + "green:Join now and get free 100k on balance! <3\n");

        mainFrame.add(messagesScrollPane);
        messagesScrollPane.setBounds(10, 320, 445, 80);
    }

    private void getIcon(){
        try{
            icon = ImageIO.read(new URL("https://dreambot.org/forums/uploads/monthly_2019_09/PixelPiece.png.2d3e22037b50236bb0f7cca45bae987c.png"));
        }catch (Exception e){
            MethodProvider.log(e.getMessage());
        }
    }
}
