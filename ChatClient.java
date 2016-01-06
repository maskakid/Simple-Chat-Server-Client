
package chatclient;

import java.awt.*;
import java.lang.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.logging.*;
import javax.swing.*;
/**
 *
 * @author AKID(CSE'11,CUET)
 */
class UserData implements ActionListener {

    public JFrame frame = new JFrame("Client");
    public JPanel uppanel, downpanel, buttonpanel, message, history, lebel1, lebel2, blankpanel;
    public JTextField text[] = new JTextField[2];
    public JButton button;
    String ret = null;
    PrintStream pr;

    UserData() {
    }

    public void F(final JTextField J, final String T) {
        J.setText(T);
        J.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                String S = J.getText();
                if (S.equals(T)) {
                    S = "";
                }
                J.setText(S);
            }

            public void focusLost(FocusEvent e) {
                String S = J.getText();
                if (S == (T) || S.length() == 0) {
                    J.setText(T);
                }
            }
        });
    }

    public JTextField T(JTextField T) {
        T.setFont(new Font("Courier New", 0, 15));
        T.addActionListener(this);
        T.setPreferredSize(new Dimension(330, 40));
        return T;
    }

    public JButton B(JButton B) {
        B.setBackground(new Color(160, 195, 220));
        B.setFont(new Font("Courier New", 1, 14));
        B.setPreferredSize(new Dimension(100, 40));
        B.addActionListener(this);
        return B;
    }

    public JLabel L(JLabel L) {
        L.setFont(new Font("Maiandra GD", 0, 18));
        L.setPreferredSize(new Dimension(20, 60));
        return L;
    }

    public void Interface() {

        lebel1 = new JPanel(new GridLayout(0, 1, 1, 1));
        lebel2 = new JPanel(new GridLayout(0, 1, 1, 1));
        uppanel = new JPanel(new GridLayout(0, 1, 1, 1));
        downpanel = new JPanel(new GridLayout(0, 1, 1, 1));
        buttonpanel = new JPanel(new GridLayout(0, 1, 1, 1));
        message = new JPanel(new GridLayout(0, 1, 1, 1));
        history = new JPanel(new GridLayout(0, 1, 1, 1));
        blankpanel = new JPanel(new GridLayout(0, 1, 1, 1));

        lebel1.add(L(new JLabel("M e s s a g e  H i s t o r y")));
        history.add(T(text[0] = new JTextField()));
        lebel2.add(L(new JLabel("W r i t e  M e s s a g e")));

        message.add(T(text[1] = new JTextField()), BorderLayout.WEST);
        message.add(B(button = new JButton("S e n d")), BorderLayout.EAST);

        //North
        uppanel.add(lebel1, BorderLayout.NORTH);
        uppanel.add(history, BorderLayout.CENTER);
        uppanel.add(lebel2, BorderLayout.SOUTH);

        //South
        downpanel.add(message, BorderLayout.WEST);
        downpanel.add(buttonpanel, BorderLayout.EAST);
        downpanel.add(blankpanel, BorderLayout.PAGE_END);

        frame.add(uppanel, BorderLayout.NORTH);
        frame.add(downpanel, BorderLayout.SOUTH);

        //------------- Frame --------------------------------------------------
        frame.setBounds(300, 300, 600, 700);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void clientConnection() {
        try {
            //192.168.129.1
            Socket socket = new Socket("localhost", 1234);
            pr = new PrintStream(socket.getOutputStream());

            BufferedReader bufferreader1 = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String receive_message = bufferreader1.readLine();
            text[0].setText(receive_message);

        } catch (Exception ex) {
        }
    }

    public void actionPerformed(ActionEvent ae) {

        String command = ae.getActionCommand();
        if (command.equals("S e n d")) {
            ret = text[1].getText();
            text[1].setText("");
            text[0].setText(ret);
            System.out.print("Server ret: " + ret);
            pr.println(ret);

        }
    }
}

public class ChatClient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        UserData d = new UserData();
        d.Interface();
        while (true) {
            d.clientConnection();
        }
    }
}

