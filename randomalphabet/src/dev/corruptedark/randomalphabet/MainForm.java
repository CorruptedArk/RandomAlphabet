package dev.corruptedark.randomalphabet;

import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class MainForm {

    private JTextField keyBox;
    private JLabel keyLabel;
    private JButton generateAlphabetButton;
    private JButton encodeButton;
    private JButton decodeButton;
    private JTextArea plainTextBox;
    private JLabel plainTextLabel;
    private JLabel encodedTextLabel;
    private JTextArea encodedTextBox;
    private JPanel mainForm;
    private JSpinner bucketSizeSpinner;
    private JLabel bucketSizeLabel;
    private JLabel feedBackLabel;
    private JButton plainTextCopyButton;
    private JButton encodedTextCopyButton;
    private JButton plainTextPasteButton;
    private JButton encodeTextPasteButton;
    private JSpinner decoySpinner;
    private JLabel decoyCountLabel;
    private JButton plainTextClearButton;
    private JButton encodeTextClearButton;
    private RandomTranslator translator;
    private SpinnerNumberModel bucketNumberModel;
    private SpinnerNumberModel decoyNumberModel;
    private Clipboard clipboard;


    public MainForm() {
        clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

        bucketNumberModel = new SpinnerNumberModel();
        bucketNumberModel.setMinimum(1);
        bucketNumberModel.setMaximum(9999);

        decoyNumberModel = new SpinnerNumberModel();
        decoyNumberModel.setMinimum(1);
        decoyNumberModel.setMaximum(9999);

        bucketSizeSpinner.setModel(bucketNumberModel);

        bucketSizeSpinner.setValue(10);

        decoySpinner.setModel(decoyNumberModel);

        decoySpinner.setValue(2);

        generateAlphabetButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                translator = new RandomTranslator(keyBox.getText(), (int)bucketSizeSpinner.getValue(), (int)decoySpinner.getValue());
                feedBackLabel.setForeground(Color.green);
                feedBackLabel.setText("Alphabet Generated");
                feedBackLabel.setVisible(true);
            }
        });


        encodeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(translator != null)
                {
                    try
                    {
                        encodedTextBox.setText(translator.encodeText(plainTextBox.getText()));
                        feedBackLabel.setVisible(false);
                    }
                    catch (Exception except)
                    {
                        feedBackLabel.setText("Invalid Translation");
                        feedBackLabel.setForeground(Color.red);
                        feedBackLabel.setVisible(true);
                    }
                }
            }
        });


        decodeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(translator != null)
                {
                    try
                    {
                        plainTextBox.setText(translator.decodeText(encodedTextBox.getText()));
                        feedBackLabel.setVisible(false);
                    }
                    catch(Exception except)
                    {
                        feedBackLabel.setText("Invalid Translation");
                        feedBackLabel.setForeground(Color.red);
                        feedBackLabel.setVisible(true);
                    }
                }
            }
        });


        plainTextCopyButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                StringSelection selection = new StringSelection(plainTextBox.getText());
                clipboard.setContents(selection, selection);

                feedBackLabel.setText("Text Copied");
                feedBackLabel.setForeground(Color.green);
                feedBackLabel.setVisible(true);
            }
        });


        plainTextPasteButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                try
                {
                    StringBuilder pasteText = new StringBuilder();
                    InputStreamReader reader = (InputStreamReader)clipboard.getData(DataFlavor.selectBestTextFlavor(clipboard.getAvailableDataFlavors()));
                    BufferedReader bufferedReader = new BufferedReader(reader);


                    while (bufferedReader.ready())
                    {
                        pasteText.append(bufferedReader.readLine()).append("\n");
                    }

                    bufferedReader.close();

                    if(pasteText.length() > 0)
                        pasteText.deleteCharAt(pasteText.length() - 1);

                    plainTextBox.setText(pasteText.toString());
                    feedBackLabel.setText("Paste Successful");
                    feedBackLabel.setForeground(Color.green);
                    feedBackLabel.setVisible(true);
                }
                catch (Exception except)
                {
                    feedBackLabel.setText("Invalid Paste");
                    feedBackLabel.setForeground(Color.red);
                    feedBackLabel.setVisible(true);
                    except.printStackTrace();
                }

            }
        });


        encodedTextCopyButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                StringSelection selection = new StringSelection(encodedTextBox.getText());
                clipboard.setContents(selection, selection);

                feedBackLabel.setText("Text Copied");
                feedBackLabel.setForeground(Color.green);
                feedBackLabel.setVisible(true);
            }
        });


        encodeTextPasteButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                try
                {
                    StringBuilder pasteText = new StringBuilder();
                    try {

                        InputStreamReader reader = (InputStreamReader) clipboard.getData(DataFlavor.selectBestTextFlavor(clipboard.getAvailableDataFlavors()));
                        BufferedReader bufferedReader = new BufferedReader(reader);

                        while (bufferedReader.ready()) {
                            pasteText.append(bufferedReader.readLine()).append("\n");
                        }
                        bufferedReader.close();

                        if (pasteText.length() > 0)
                            pasteText.deleteCharAt(pasteText.length() - 1);
                    }
                    catch (ClassCastException classExcept)
                    {
                        String tempPasteString =  (String) clipboard.getData(DataFlavor.selectBestTextFlavor(clipboard.getAvailableDataFlavors()));
                        pasteText.append(tempPasteString);
                    }

                    encodedTextBox.setText(pasteText.toString());
                    feedBackLabel.setText("Paste Successful");
                    feedBackLabel.setForeground(Color.green);
                    feedBackLabel.setVisible(true);
                }
                catch (Exception except)
                {
                    feedBackLabel.setText("Invalid Paste");
                    feedBackLabel.setForeground(Color.red);
                    feedBackLabel.setVisible(true);
                    except.printStackTrace();
                }
            }
        });


        plainTextClearButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                plainTextBox.setText("");
            }
        });


        encodeTextClearButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                encodedTextBox.setText("");
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Random Alphabet");
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            System.out.println(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        frame.setContentPane(new MainForm().mainForm);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();

        frame.setVisible(true);
    }
}
