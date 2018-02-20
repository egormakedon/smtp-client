package client.view;

import client.Controller;
import client.command.exception.RuntimeError;
import client.command.exception.InvalidParameter;
import client.model.MemoBuffer;
import client.model.ParameterCriteria;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

class CommandsDialog {
    private static final Logger LOGGER = LogManager.getLogger(CommandsDialog.class);

    private JDialog dialog;
    private JTextField argumentField;
    private JTextArea memoArea;

    private Map<ParameterCriteria, String> parameters;

    CommandsDialog(JTextArea memoArea) {
        this.memoArea = memoArea;

        dialog = new JDialog();
        setDialog();

        argumentField = new JTextField();
        setArgumentField();

        setButtons();

        parameters = new HashMap<ParameterCriteria, String>();
        parameters.put(ParameterCriteria.ARGUMENT, "");
    }

    public void show() {
        dialog.setVisible(true);
    }

    private void setDialog() {
        dialog.setLayout(new GridLayout(2,1));
        dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        dialog.setPreferredSize(new Dimension(500,250));
        dialog.setSize(new Dimension(500, 250));
        dialog.setTitle("Commands dialog");
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(null);
    }

    private void setArgumentField() {
        JPanel panel = new JPanel();

        panel.add(new Label("argument"));
        argumentField.setColumns(25);
        argumentField.setToolTipText("argument");
        panel.add(argumentField);

        JButton connectButton = new JButton();
        connectButton.setText("connect");
        addListenerToButton(connectButton);
        panel.add(connectButton);

        JButton authButton = new JButton();
        authButton.setText("auth");
        addListenerToButton(authButton);
        panel.add(authButton);

        JButton submitButton = new JButton();
        submitButton.setText("submit");
        addListenerToButton(submitButton);
        panel.add(submitButton);

        dialog.add(panel);
    }

    private void setButtons() {
        JButton ehloButton = new JButton();
        ehloButton.setText("ehlo");
        addListenerToButton(ehloButton);

        JButton heloButton = new JButton();
        heloButton.setText("helo");
        addListenerToButton(heloButton);

        JButton mailButton = new JButton();
        mailButton.setText("mail");
        addListenerToButton(mailButton);

        JButton rcptButton = new JButton();
        rcptButton.setText("rcpt");
        addListenerToButton(rcptButton);

        JButton dataButton = new JButton();
        dataButton.setText("data");
        addListenerToButton(dataButton);

        JButton rsetButton = new JButton();
        rsetButton.setText("rset");
        addListenerToButton(rsetButton);

        JButton sendButton = new JButton();
        sendButton.setText("send");
        addListenerToButton(sendButton);

        JButton somlButton = new JButton();
        somlButton.setText("soml");
        addListenerToButton(somlButton);

        JButton samlButton = new JButton();
        samlButton.setText("saml");
        addListenerToButton(samlButton);

        JButton vrfyButton = new JButton();
        vrfyButton.setText("vrfy");
        addListenerToButton(vrfyButton);

        JButton expnButton = new JButton();
        expnButton.setText("expn");
        addListenerToButton(expnButton);

        JButton helpButton = new JButton();
        helpButton.setText("help");
        addListenerToButton(helpButton);

        JButton noopButton = new JButton();
        noopButton.setText("noop");
        addListenerToButton(noopButton);

        JButton quitButton = new JButton();
        quitButton.setText("quit");
        addListenerToButton(quitButton);

        JButton turnButton = new JButton();
        turnButton.setText("turn");
        addListenerToButton(turnButton);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        panel.add(ehloButton);
        panel.add(heloButton);
        panel.add(mailButton);
        panel.add(rcptButton);
        panel.add(dataButton);
        panel.add(rsetButton);
        panel.add(sendButton);
        panel.add(somlButton);
        panel.add(samlButton);
        panel.add(vrfyButton);
        panel.add(expnButton);
        panel.add(helpButton);
        panel.add(noopButton);
        panel.add(quitButton);
        panel.add(turnButton);

        dialog.add(panel);
    }

    private void updateMemo() {
        memoArea.setText("");
        memoArea.setText(MemoBuffer.getInstance().toString());
    }

    private void addListenerToButton(JButton button) {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    parameters.replace(ParameterCriteria.ARGUMENT, argumentField.getText());
                    Controller.getInstance().processRequest(button.getText(), parameters);
                    updateMemo();
                } catch (InvalidParameter | RuntimeError exc) {
                    LOGGER.log(Level.ERROR, exc);
                    JOptionPane.showMessageDialog(dialog, exc.getMessage());
                }
            }
        });
    }
}