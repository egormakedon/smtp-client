package by.makedon.smtpclient.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Frame {
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
        setSendButton();
        setMemoPanel();
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
    private void setSendButton() {
        JButton button = new JButton();
        button.setText("send");

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.add(button);
        frame.add(panel);
    }
    private void setMemoPanel() {
        memoArea.setEnabled(false);
        frame.add(new JScrollPane((new JPanel()).add(memoArea)));
    }

    public void show() {
        frame.setVisible(true);
    }
}
