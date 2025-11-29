import java.awt.*;
import  java.util.*;
import javax.swing.*;
///import javax.swing.text.html.HTMLDocument;

public class Chatbotapp
{
    public static void main(String[] args) 
    {
        SwingUtilities.invokeLater(()-> new Chatbot().new ChatUI(new Chatbot()).show());

        
    }
}
class Chatbot
{
    private Map<String ,String> faq = new LinkedHashMap<>();
    public Chatbot()
    {
        faq.put("hi","hello!how can i help you");
        faq.put("hello","hi there i'm your AI chatbot.");
        faq.put("how are you", "I'm just a program, but I’m doing great!");
        faq.put("what is your name ","i'm codealfa chatbot created useing java ");
        faq.put("what is your name", "I'm CodeAlpha ChatBot created using Java.");
        faq.put("what is nlp", "NLP stands for Natural Language Processing — helping machines understand human language.");
        faq.put("who created you", "I was created by Abhi during his CodeAlpha internship!");
        faq.put("bye", "Goodbye! Have a great day!");
    }

    public String getResponse(String userInput)
    {
        userInput = userInput.toLowerCase().trim();
        for(String key:faq.keySet())
        {
            if(userInput.contains(key))
            {
                return faq.get(key);
            }
        }

        String bestMatch=null;
        int bestScore=0;
        for(String key : faq.keySet())
        {
            int score=similarity(userInput,key);
            if(score>bestScore)
            {
                bestScore=score;
                bestMatch=key;
            }
        }

        if(bestScore>50)
        {
            return faq.get(bestMatch);
        }
        return " i'm not sure that yet.you can add me more knowleadge useing 'Add FAQ'.";

    }
    private  int similarity(String a,String b)
    {
        Set<String> setA=new HashSet<>(Arrays.asList(a.split(" ")));
        Set<String> setB=new HashSet<>(Arrays.asList(a.split("  ")));
        setA.retainAll(setB);
        return setA.size()*100/Math.max(a.split(" ").length,1);
    }
    public void addFAQ(String question,String answer)
    {
        faq.put(question.toLowerCase().trim(),answer);
    }
     public class ChatUI{
        private  JFrame frame ;
        private JTextArea chatArea;
        private JTextField inputField;
        private JButton sendBtn,addBtn;
        private Chatbot bot;
        public ChatUI(Chatbot bot)
        {
            this.bot=bot;
            initUI();
        }
        private void initUI()
        {
            frame = new JFrame(" ai chatbot ");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600,500);
            frame.setLayout(new BorderLayout());

            chatArea = new JTextArea();
            chatArea.setEditable(false);
            chatArea.setLineWrap(true);
            JScrollPane scroll = new JScrollPane(chatArea);

            inputField = new JTextField();
            sendBtn =new JButton("send ");
            addBtn = new JButton("add faq");

            JPanel bottom = new JPanel(new BorderLayout());
            bottom.add(inputField, BorderLayout.CENTER);

            JPanel rightJPanel = new JPanel(new GridLayout(1,2,5,5));
            rightJPanel.add(sendBtn);
            rightJPanel.add(addBtn);
            bottom.add(rightJPanel,BorderLayout.EAST);

            frame.add(scroll,BorderLayout.CENTER);
            frame.add(bottom,BorderLayout.SOUTH);
            sendBtn.addActionListener(e -> send());
            inputField.addActionListener(e -> send());
            addBtn.addActionListener(e -> addFAQ());
            chatArea.append("🤖 Bot: Hello! I'm your AI ChatBot. Ask me anything...\n\n");
        }
        public void show() {
        frame.setVisible(true);
    }

    private void send() {
        String text = inputField.getText().trim();
        if (text.isEmpty()) return;

        chatArea.append("🧑 You: " + text + "\n");
        String reply = bot.getResponse(text);
        chatArea.append("🤖 Bot: " + reply + "\n\n");
        inputField.setText("");
    }

    private void addFAQ() {
        JTextField qField = new JTextField();
        JTextField aField = new JTextField();
        Object[] message = {
            "Question:", qField,
            "Answer:", aField
        };

        int option = JOptionPane.showConfirmDialog(frame, message, "Add New FAQ", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String q = qField.getText();
            String a = aField.getText();
            if (!q.isEmpty() && !a.isEmpty()) {
                bot.addFAQ(q, a);
                chatArea.append("✅ New FAQ added successfully!\n\n");
            } else {
                chatArea.append("⚠️ Please fill both Question and Answer.\n\n");
            }
        }
    }







        
    }
}

    



