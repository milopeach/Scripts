package com.peach.os.scripts.firemaker;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.AbstractListModel;
import javax.swing.JLabel;
import javax.swing.border.EtchedBorder;
import javax.swing.JCheckBox;

import org.powerbot.script.rt4.ClientAccessor;
import org.powerbot.script.rt4.ClientContext;

import com.peach.os.items.Log;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Gui extends ClientAccessor {

	private JFrame frmTrailBlazer;

	/**
	 * Create the application.
	 */
	public Gui(ClientContext ctx) {
		super(ctx);
		initialize();
		this.frmTrailBlazer.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmTrailBlazer = new JFrame();
		frmTrailBlazer.setTitle("Trail Blazer");
		frmTrailBlazer.setBounds(100, 100, 204, 291);
		frmTrailBlazer.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmTrailBlazer.getContentPane().setLayout(null);
		
		final JList<String> listLogs = new JList<String>();
		listLogs.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		listLogs.setModel(new AbstractListModel() {
			String[] values = new String[] {"Normal", "Oak", "Willow", "Maple", "Teak", "Mahogany", "Achey", "Yew", "Magic"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		listLogs.setBounds(22, 32, 142, 155);
		frmTrailBlazer.getContentPane().add(listLogs);
		
		JLabel lblLogs = new JLabel("Logs");
		lblLogs.setBounds(70, 11, 46, 14);
		frmTrailBlazer.getContentPane().add(lblLogs);
		
		final JCheckBox chckbxManualTrails = new JCheckBox("Manual trails");
		chckbxManualTrails.setBounds(19, 189, 97, 23);
		frmTrailBlazer.getContentPane().add(chckbxManualTrails);
		
		JButton btnState = new JButton("Start");
		btnState.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (listLogs.getSelectedIndices().length < 1) {
					JOptionPane.showMessageDialog(null, "You must select one, or more, logs to burn.\nHold Control (Ctrl) to select multiple logs.", "No logs selected.", JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				for (int i : listLogs.getSelectedIndices()) {
					((TrailBlazer) ctx.controller.script()).addLog(Log.Normal.values()[i]);
				}
				((TrailBlazer) ctx.controller.script()).setManual(chckbxManualTrails.isSelected());
				frmTrailBlazer.setVisible(false);
				frmTrailBlazer.dispose();
				ctx.controller.resume();
			}
		});
		btnState.setBounds(48, 219, 89, 23);
		frmTrailBlazer.getContentPane().add(btnState);
	}
}
