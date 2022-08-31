import org.apache.commons.lang.StringUtils;
import utils.Translator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class DetailFrame extends JFrame{
    private boolean isEdit;
    private Issue currentIssue;
    private JComboBox<String> bugType, informPerson,orginPerson;
    private JCheckBox isOffice, isNewFeature, isInform;
    private JTextField issueID,informTo;
    private JTextArea chText, enText, code;
    private JButton sumbit,translatorBtn;

    private SubmitListener listener;

    public DetailFrame(Issue issue){
        super();
        this.currentIssue=issue;
        this.isEdit=true;
        init();
    }
    public DetailFrame() {
        super();
        init();
    }
    private void init() {

        int width = 400;
        int height = 840;
        int y;
        y=50;
        JLabel label1 = new JLabel("问题类型:");
        label1.setBounds(10, y, 60, 30);

        bugType = new JComboBox<>();
        bugType.setEnabled(true);
        for(String type:Static.type){
            bugType.addItem(type);
        }
        bugType.setSelectedIndex(0);
        bugType.setBounds(70, y, 100, 25);

        JLabel label2 = new JLabel("通知人:");
        label2.setBounds(200, y, 60, 30);

        informPerson = new JComboBox<>();
        informPerson.setEnabled(true);
        for(String name:Static.names){
            informPerson.addItem(name);
        }
        informPerson.setSelectedIndex(0);
        informPerson.setBounds(260, y, 100, 25);
        y+=50;
        JLabel label3 = new JLabel("问题编号:");
        label3.setBounds(10, y, 60, 30);

        issueID = new JTextField();
        issueID.setEditable(true);
        issueID.setBounds(70, y, 100, 25);
        issueID.setDocument(new NumberDocument());

        JLabel label4 = new JLabel("是否通知:");
        label4.setBounds(200, y, 60, 30);

        isInform = new JCheckBox();
        isInform.setSelected(true);
        isInform.setBounds(260, y, 60, 25);
        y+=50;
        JLabel label5 = new JLabel("Is Office:");
        label5.setBounds(10, y, 60, 30);

        isOffice = new JCheckBox();
        isOffice.setSelected(false);
        isOffice.setBounds(70, y, 60, 25);

        JLabel label6 = new JLabel("新功能:");
        label6.setBounds(200, y, 60, 30);

        isNewFeature = new JCheckBox();
        isNewFeature.setSelected(false);
        isNewFeature.setBounds(260, y, 60, 30);
        y+=50;
        JLabel label10 = new JLabel("原归属:");
        label10.setBounds(10, y, 60, 30);

        orginPerson=new JComboBox<>();
        orginPerson.setEnabled(true);
        orginPerson.addItem("");
        for(String name:Static.names){
            orginPerson.addItem(name);
        }
        orginPerson.setSelectedIndex(0);
        orginPerson.setBounds(70, y, 100, 25);
        y=y+50;
        JLabel label11 = new JLabel("被通知:");
        label11.setBounds(10, y, 60, 30);

        informTo=new JTextField();
        informTo.setBounds(70,y,280,30);

        y+=50;
        JLabel label7 = new JLabel("中文描述:");
        label7.setBounds(10, y, 60, 30);
        y+=30;
        chText = new JTextArea();
        chText.setLineWrap(true);
        chText.setWrapStyleWord(true);
        chText.setBounds(0,0,350,100);
        JScrollPane chTextJScrollPane=new JScrollPane(chText);
        chTextJScrollPane.setBounds(10, y, 350, 100);
        y+=100;
        JLabel label8 = new JLabel("英文描述:");
        label8.setBounds(10, y, 60, 30);
        translatorBtn=new JButton("点我-Google机翻");
        translatorBtn.setBounds(80,y,150,30);
        translatorBtn.setBorderPainted(false);
        translatorBtn.setFocusPainted(false);
        translatorBtn.setContentAreaFilled(false);
        translatorBtn.setForeground(Color.red);
        translatorBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cnStr=chText.getText();
                if(StringUtils.isBlank(cnStr)){
                    JOptionPane.showMessageDialog(DetailFrame.this,"请先填写中文描述.","提示",JOptionPane.WARNING_MESSAGE);
                    return;
                }
                try {
                    String enStr=Translator.translate("zh-CN","en",cnStr);
                    enText.setText(enStr);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        y+=30;
        enText = new JTextArea();
        enText.setLineWrap(true);
        enText.setWrapStyleWord(true);
        enText.setBounds(0,0,350,100);
        JScrollPane enTextJScrollPane=new JScrollPane(enText);
        enTextJScrollPane.setBounds(10, y, 350, 100);
        y+=100;
        JLabel label9 = new JLabel("示例代码（可选）:");
        label9.setBounds(10, y, 150, 30);
        y+=30;

        code = new JTextArea();
        code.setLineWrap(true);
        code.setWrapStyleWord(true);
        code.setBounds(0,0,350,100);
        JScrollPane codeScrollPane=new JScrollPane(code);
        codeScrollPane.setBounds(10, y, 350, 100);

        y+=120;
        String btnStr=isEdit?"确认修改":"确认添加";
        sumbit = new JButton(btnStr);
        sumbit.setBounds(150,y,100,50);
        sumbit.addActionListener(e -> {
            String issueIDStr=issueID.getText();
            String informToStr=informTo.getText();
            if(issueIDStr==null||issueIDStr.isEmpty()){
                JOptionPane.showMessageDialog(DetailFrame.this,"请填写问题编号.","提示",JOptionPane.WARNING_MESSAGE);
                return;
            }
            if(informToStr==null||informToStr.isEmpty()){
                JOptionPane.showMessageDialog(DetailFrame.this,"请填写客户信息.","提示",JOptionPane.WARNING_MESSAGE);
                return;
            }
            String bugTypeStr=bugType.getSelectedItem().toString();
            String informPersonStr=informPerson.getSelectedItem().toString();
            boolean isInformVal=isInform.isSelected();
            boolean isOfficeVal=isOffice.isSelected();
            boolean isNewFeatureVal=isNewFeature.isSelected();
            String chTextStr=chText.getText();
            String codeStr=code.getText();
            String enTextStr=enText.getText();
            String orginPersonStr=orginPerson.getSelectedItem().toString();


            if(isEdit){
                //todo
                currentIssue.setIssueID(issueIDStr);
                currentIssue.setIssueType(bugTypeStr);
                currentIssue.setCode(codeStr);
                currentIssue.setChText(chTextStr);
                currentIssue.setEnText(enTextStr);
                currentIssue.setInform(isInformVal);
                currentIssue.setNewFeature(isNewFeatureVal);
                currentIssue.setOffice(isOfficeVal);
                currentIssue.setInformPerson(informPersonStr);
                JOptionPane.showMessageDialog(DetailFrame.this,"修改成功.","提示",JOptionPane.INFORMATION_MESSAGE);
            }else{
                //todo
                currentIssue=new Issue(issueIDStr, bugTypeStr, informPersonStr, codeStr, enTextStr, chTextStr,isOfficeVal,  isInformVal,isNewFeatureVal,informToStr,orginPersonStr);
                JOptionPane.showMessageDialog(DetailFrame.this,"添加成功.","提示",JOptionPane.INFORMATION_MESSAGE);
            }
            listener.onSubmit(currentIssue,isEdit);
            DetailFrame.this.dispose();
        });
        if(isEdit){
            code.setText(currentIssue.getCode());
            enText.setText(currentIssue.getEnText());
            chText.setText(currentIssue.getChText());
            isNewFeature.setSelected(currentIssue.isNewFeature());
            isOffice.setSelected(currentIssue.isOffice());
            informPerson.setSelectedItem(currentIssue.getInformPerson());
            bugType.setSelectedItem(currentIssue.getIssueType());
            issueID.setText(currentIssue.getIssueID());
            isInform.setSelected(currentIssue.isInform());
            informTo.setText(currentIssue.getInformTo());
            orginPerson.setSelectedItem(currentIssue.getOrginPerson());
        }
        this.add(translatorBtn);
        this.add(orginPerson);
        this.add(informTo);
        this.add(sumbit);
        this.add(codeScrollPane);
        this.add(enTextJScrollPane);
        this.add(chTextJScrollPane);
        this.add(isNewFeature);
        this.add(isOffice);
        this.add(informPerson);
        this.add(bugType);
        this.add(issueID);
        this.add(isInform);
        this.add(label1);
        this.add(label2);
        this.add(label3);
        this.add(label4);
        this.add(label5);
        this.add(label6);
        this.add(label7);
        this.add(label8);
        this.add(label9);
        this.add(label10);
        this.add(label11);

        Toolkit tool = Toolkit.getDefaultToolkit();
        int x = (int) ((tool.getScreenSize().width - (float) width) / 2);
        y = (int) ((tool.getScreenSize().height - (float) height) / 2);
        this.setSize(width, height);
        this.setLocation(x, y);
        this.setTitle("详情信息");
        this.setResizable(false);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLayout(null);
        this.setVisible(true);
    }

    public void setListener(SubmitListener listener) {
        this.listener = listener;
    }
}
