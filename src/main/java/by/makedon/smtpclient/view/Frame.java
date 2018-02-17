package by.makedon.smtpclient.view;

import by.makedon.smtpclient.exception.CommandException;
import by.makedon.smtpclient.model.MemoBuffer;
import by.makedon.smtpclient.controller.Controller;
import by.makedon.smtpclient.model.ParameterCriteria;
import by.makedon.smtpclient.exception.InvalidParameterException;
import by.makedon.smtpclient.exception.MailSocketException;
import by.makedon.smtpclient.socket.MailSocket;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class Frame {
    private static final Logger LOGGER = LogManager.getLogger(Frame.class);

    private JFrame frame;

    private JTextField smtpServerField = new JTextField();
    private JTextField fromField = new JTextField();
    private JTextField toField = new JTextField();
    private JTextField subjectField = new JTextField();
    private JTextArea messageArea = new JTextArea();

    private JTextArea memoArea = new JTextArea();

    public Frame() {
        frame = new JFrame();
        setFrame();

        setFieldsPanel();
        setMessagePanel();
        setButtons();
        setMemoPanel();
    }

    public void show() {
        frame.setVisible(true);
    }

    private void setFrame() {
        frame.setLayout(new GridLayout(4, 1));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(700,550));
        frame.setSize(new Dimension(700, 550));
        frame.setTitle("Thunderbird");
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
    }

    private void setFieldsPanel() {
        JPanel panel = new JPanel();

        panel.add(new Label("SMTP server"));
        smtpServerField.setColumns(25);
        smtpServerField.setToolTipText("SMTP server");
        panel.add(smtpServerField);

        panel.add(new Label("from"));
        fromField.setColumns(25);
        fromField.setToolTipText("from");
        panel.add(fromField);

        panel.add(new Label("to"));
        toField.setColumns(25);
        toField.setToolTipText("to");
        panel.add(toField);

        panel.add(new Label("subject"));
        subjectField.setColumns(25);
        subjectField.setToolTipText("subject");
        panel.add(subjectField);

        frame.add(panel);
    }

    private void setMessagePanel() {
        frame.add(new JScrollPane((new JPanel()).add(messageArea)));
    }

    private void setButtons() {
        JButton sendButton = new JButton();
        sendButton.setText("send_message");

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Map<ParameterCriteria, String> parameters = new HashMap<ParameterCriteria, String>();
                parameters.put(ParameterCriteria.SMTP_SERVER, smtpServerField.getText());
                parameters.put(ParameterCriteria.FROM, fromField.getText());
                parameters.put(ParameterCriteria.TO, toField.getText());
                parameters.put(ParameterCriteria.SUBJECT, subjectField.getText());
                parameters.put(ParameterCriteria.MAIL_TEXT, messageArea.getText());

                try {
                    Controller.getInstance().processRequest(sendButton.getText(), parameters);
                    updateMemo();
                } catch (InvalidParameterException | CommandException exc) {
                    LOGGER.log(Level.ERROR, exc);
                    JOptionPane.showMessageDialog(frame, exc.getMessage());
                }
            }
        });

        JButton allCommandsButton = new JButton();
        allCommandsButton.setText("all commands");

        allCommandsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CommandsDialog commandsDialog = new CommandsDialog(memoArea);
                commandsDialog.show();
            }
        });

        JPanel panel = new JPanel();
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(0, 50, 0, 50);
        panel.setLayout(new GridBagLayout());
        panel.add(sendButton, c);
        panel.add(allCommandsButton, c);
        frame.add(panel);
    }

    private void setMemoPanel() {
        memoArea.setBackground(Color.BLACK);
        memoArea.setEnabled(false);
        frame.add(new JScrollPane((new JPanel()).add(memoArea)));
    }

    private void updateMemo() {
        memoArea.setText("");
        memoArea.setText(MemoBuffer.getInstance().toString());
    }
}
