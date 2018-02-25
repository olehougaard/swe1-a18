package dk.via.editor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Editor {
	public static final String LOREM_IPSUM = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis laoreet risus diam, id mollis metus elementum sit amet. Proin urna ante, varius lobortis sollicitudin quis, malesuada nec risus. Nam sodales vulputate rhoncus. Nullam dui erat, tempus sit amet sapien efficitur, congue luctus libero. Vivamus sit amet condimentum felis, ut consequat est. Morbi elementum metus non posuere viverra. Nulla lorem augue, suscipit at dignissim ac, suscipit id nulla.\nMorbi ut gravida magna, ut consequat nulla. Aliquam velit urna, pellentesque ut mauris vel, gravida bibendum metus. Donec id malesuada ante, vitae imperdiet libero. Duis ornare aliquam tincidunt. Duis eu massa sodales, dictum mauris at, pharetra ipsum. Integer vel lorem dapibus, dapibus orci nec, posuere tortor. Integer ut lacus malesuada, convallis sem quis, consequat turpis. Mauris efficitur congue ligula, eu porta sem condimentum id. Sed sed eleifend sem.\nSed auctor facilisis hendrerit. Fusce felis risus, placerat ut diam eget, porttitor vestibulum orci. Mauris ullamcorper ultrices velit. Donec ultricies pharetra ante, nec posuere erat suscipit eget. Donec tempus ligula dolor, nec rhoncus nunc laoreet quis. In elit enim, tincidunt et lorem vitae, vulputate semper erat. Integer tincidunt nunc eget pulvinar vulputate.\nNullam pellentesque lectus dui, vulputate finibus ante tristique eget. Phasellus laoreet, arcu non porta pharetra, orci sapien rutrum risus, sed elementum nunc magna quis eros. Sed ac tortor ac nisl bibendum lobortis. Donec sed lacus vehicula, iaculis nibh vel, porttitor ipsum. Curabitur vestibulum, erat a bibendum rhoncus, tellus risus rutrum augue, non efficitur neque enim vel nisi. Curabitur gravida urna ut arcu consequat, a venenatis ante iaculis. Aliquam a bibendum nibh, non ultricies nisi. Maecenas vulputate purus sit amet sem vulputate feugiat. Phasellus porta id augue quis convallis. Ut mollis dui quis sapien hendrerit bibendum. Donec accumsan sed felis vel semper. Integer aliquam odio at orci scelerisque, vitae congue tellus consequat. Maecenas dapibus neque tristique, congue justo eget, placerat enim.\nMaecenas semper, dui a venenatis gravida, turpis quam faucibus sapien, auctor volutpat justo nibh vel lacus. Suspendisse a pharetra dolor. Aenean in purus ut lorem eleifend convallis et id neque. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Vestibulum pellentesque sem tortor, eu imperdiet sapien condimentum sed. Integer a lacus mollis, vestibulum lacus vestibulum, laoreet lacus. Sed gravida tempus turpis non lobortis. Nulla sit amet felis lorem. Aliquam est urna, suscipit at ex sed, faucibus luctus diam. Integer urna tellus, mollis ut lacinia in, sollicitudin ac libero. Donec pharetra arcu in mauris eleifend, eu volutpat ex varius. Suspendisse suscipit, turpis eu sagittis consectetur, turpis felis pharetra arcu, a lacinia risus libero vitae lectus."; 

	public static void main(String[] args) {
		JFrame frame = new JFrame("Editor");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300, 600);
		JTextArea textArea = new JTextArea(LOREM_IPSUM);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		frame.add(new JScrollPane(textArea));
		JButton printButton = new JButton("Print");
		printButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(textArea.getText());
			}
		});
		textArea.add(printButton);
		printButton.setSize(printButton.getPreferredSize());
		printButton.setLocation(200, 530);
		frame.setVisible(true);
	}
}
