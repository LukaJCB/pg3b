
package pg3b.ui.swing;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;

import pg3b.ui.Script;
import pg3b.util.UI;

public class ScriptEditor extends EditorPanel<Script> {
	private int lastSelectedTriggerIndex;

	private RSyntaxTextArea codeText;
	private JButton recordButton;

	public ScriptEditor (PG3BUI owner) {
		super(owner, Script.class, new File("scripts"), ".script");

		initializeLayout();
		initializeEvents();
	}

	protected void updateFieldsFromItem (Script script) {
		if (script == null)
			codeText.setText("");
		else
			codeText.setText(script.getCode());
	}

	protected void updateItemFromFields (Script script) {
		script.setCode(codeText.getText());
	}

	private void initializeEvents () {
		recordButton.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent event) {
				UI.errorDialog(ScriptEditor.this, "Error", "Not implemented yet.");
			}
		});

		codeText.addFocusListener(new FocusAdapter() {
			public void focusLost (FocusEvent event) {
				saveItem(false);
			}
		});
	}

	private void initializeLayout () {
		{
			codeText = new RSyntaxTextArea();
			codeText.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
			codeText.setHighlightCurrentLine(false);
			codeText.setCaretColor(Color.black);
			codeText.setBackground(Color.white);
			codeText.setSelectionColor(new Color(0xb8ddff));
			try {
				codeText
					.setFont(Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/VeraMono.ttf")).deriveFont(10f));
			} catch (Exception ignored) {
				ignored.printStackTrace();
			}
			{
				RTextScrollPane scroll = new RTextScrollPane(codeText);
				getContentPanel().add(
					scroll,
					new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0,
						0, 0), 0, 0));
			}
		}
		{
			JPanel panel = new JPanel(new GridLayout(1, 1, 6, 6));
			getContentPanel().add(
				panel,
				new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE,
					new Insets(6, 6, 6, 0), 0, 0));
			{
				recordButton = new JButton("Record");
				panel.add(recordButton);
			}
		}

		UI.enableWhenModelHasSelection(getSelectionModel(), recordButton, codeText);
	}
}