import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

public class MainFrame extends JFrame implements ActionListener, SubmitListener {
    public static void main(String[] args) {
        new MainFrame();
    }

    private JComboBox<String> releasePerson;
    private JList<String> issueList;
    private JLabel label;
    private JButton btn_edit, btn_add, btn_del;
    private ArrayList<Issue> issueArrayList;
    private JButton btn_sub, btn_clear;

    public MainFrame() {
        super();
        issueArrayList = new ArrayList<>();
        int width = 300;
        int height = 500;

        Toolkit tool = Toolkit.getDefaultToolkit();
        int x = width;
        int y = (int) ((tool.getScreenSize().height - (float) height) / 4);

        issueList = new JList<>();
        issueList.setBounds(0,0,150,300);
        JScrollPane scrollPane=new JScrollPane(issueList);
        scrollPane.setBounds(10, 50, 150, 300);
        issueList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        label = new JLabel();
        label.setText("问题列表");
        label.setBounds(10, 20, 100, 30);

        JLabel label1=new JLabel("发布人:");
        label1.setBounds(10,350,60,30);

        releasePerson=new JComboBox<>();
        releasePerson.setEnabled(true);
        for(String name:Static.names){
            releasePerson.addItem(name);
        }
        releasePerson.setSelectedIndex(0);
        releasePerson.setBounds(70, 360, 100, 25);

        btn_add = new JButton();
        btn_add.setText("添加");
        btn_add.setBounds(170, 80, 80, 40);
        btn_add.addActionListener(this);

        btn_edit = new JButton();
        btn_edit.setText("编辑");
        btn_edit.setBounds(170, 150, 80, 40);
        btn_edit.addActionListener(this);

        btn_del = new JButton();
        btn_del.setText("删除");
        btn_del.setBounds(170, 220, 80, 40);
        btn_del.addActionListener(this);

        btn_sub = new JButton();
        btn_sub.setText("生成并复制内容");
        btn_sub.setBounds(50, 400, 200, 40);
        btn_sub.addActionListener(this);

        btn_clear = new JButton();
        btn_clear.setText("重置");
        btn_clear.setBounds(170, 290, 80, 40);
        btn_clear.addActionListener(this);

        this.add(btn_clear);
        this.add(btn_sub);
        this.add(btn_del);
        this.add(btn_edit);
        this.add(btn_add);
        this.add(label);
        this.add(scrollPane);
        this.add(label1);
        this.add(releasePerson);

        this.setSize(width, height);
        this.setLocation(x, y);
        this.setTitle("发布产品信息");
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(null);
        //this.pack();

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String name = e.getActionCommand();
        int selectIndex;
        DetailFrame detailFrame;
        int n;
        switch (name) {
            case "重置":
                n = JOptionPane.showConfirmDialog(this, "确认重置？", "信息", JOptionPane.YES_NO_OPTION);
                if (n == JOptionPane.OK_OPTION) {
                    issueList.removeAll();
                    issueArrayList.clear();
                }
                break;
            case "生成并复制内容":
                generateReport();
                break;
            case "添加":
                detailFrame = new DetailFrame();
                detailFrame.setListener(this);
                //this.setEnabled(false);
                break;
            case "编辑":
                selectIndex = issueList.getSelectedIndex();
                if (selectIndex != -1) {
                    detailFrame = new DetailFrame(issueArrayList.get(issueList.getSelectedIndex()));
                    detailFrame.setListener(this);
                } else {
                    JOptionPane.showMessageDialog(this, "没选中你编辑啥？", "警告", JOptionPane.WARNING_MESSAGE);
                }
                //this.setEnabled(false);
                break;
            case "删除":
                selectIndex = issueList.getSelectedIndex();
                if (selectIndex != -1) {
                    n = JOptionPane.showConfirmDialog(this, "确认删除？", "信息", JOptionPane.YES_NO_OPTION);
                    if (n == JOptionPane.OK_OPTION) {
                        issueList.remove(selectIndex);
                        //issueList.repaint();
                        issueList.invalidate();
                        issueArrayList.remove(selectIndex);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "没选中你删个啥？", "警告", JOptionPane.WARNING_MESSAGE);
                }
                break;
        }
    }

    private void generateReport() {
        if (issueArrayList.size() == 0) {
            JOptionPane.showMessageDialog(this, "没有要通知的就自己写吧朋友？", "警告", JOptionPane.WARNING_MESSAGE);
            return;
        }
        StringBuilder sb = new StringBuilder();

        sb.append("*Fixes bugs*:\n");
        for (Issue issue : issueArrayList) {
            sb.append(issue.getTicketStr() + (issue.isOffice() ? " office\n" : "\n"));
        }
        sb.append("Change Logs:\n");
        sb.append("New features:\n");
        for (Issue issue : issueArrayList) {
            if (issue.isNewFeature()) {
                sb.append(issue.getEnText() + "(" + issue.getTicketStr() + "):\n");
                if(!issue.getCode().isEmpty())
                    sb.append("{code:java}\n" + issue.getCode() + "{code}\n");
            }
        }
        sb.append("Bug fixes:\n");
        for (Issue issue : issueArrayList) {
            if (!issue.isNewFeature()) {
                sb.append(issue.getEnText() + "(" + issue.getTicketStr() + ")" + "\n");
            }
        }
        sb.append("\n\n");
        sb.append("修改日志:\n");
        sb.append("新功能:\n");
        for (Issue issue : issueArrayList) {
            if (issue.isNewFeature()) {
                sb.append(issue.getChText() + "(" + issue.getTicketStr() + "):\n");
                sb.append("{code:java}\n" + issue.getCode() + "{code}\n");
            }
        }
        sb.append("问题修复:\n");
        for (Issue issue : issueArrayList) {
            if (!issue.isNewFeature()) {
                sb.append(issue.getChText() + "(" + issue.getTicketStr() + ")" + "\n");
            }
        }

        sb.append("[" + releasePerson.getSelectedItem().toString() + "]release:\n");
        sb.append("[Nina&Kylie&Abel&Andy&Annika&Amy&Lisa&William] informs:\n");
        HashMap<String, ArrayList<Issue>> map = new HashMap<>();
        for (Issue issue : issueArrayList) {
            if (!issue.isInform()) {
                continue;
            }
            if (map.containsKey(issue.getInformPerson())) {
                map.get(issue.getInformPerson()).add(issue);
            } else {
                ArrayList<Issue> temp = new ArrayList<>();
                temp.add(issue);
                map.put(issue.getInformPerson(), temp);
            }
        }
        for (String key : map.keySet()) {
            ArrayList<Issue> list = map.get(key);
            sb.append(key + ":\n");
            for (Issue issue : list) {
                sb.append(issue.getInformTo() + "(" + issue.getTicketStr() + ")");
                if (!issue.getOrginPerson().isEmpty()) {
                    sb.append("(原" + issue.getOrginPerson() + ")");
                }
                sb.append("\n");
            }
        }

        Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable tText = new StringSelection(sb.toString());
        clip.setContents(tText, null);

        JOptionPane.showMessageDialog(this, "生成结果已复制，可直接粘贴使用！", "提示", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void onSubmit(Issue issue, boolean isEdit) {
        if (!isEdit) {
            issueArrayList.add(issue);
        }
        String[] issueIDs = new String[issueArrayList.size()];
        int i = 0;
        for (Issue issue1 : issueArrayList) {
            issueIDs[i] = issue1.getTicketStr();
            i++;
        }
        issueList.setListData(issueIDs);
        issueList.invalidate();

        //this.setEnabled(true);
    }
}
