/**
 * Classe Teste - Willian
 * Para substituir o alert Dialog que esta depreciado pela Google
 */


package com.appoena.mobilenote;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class EditNameDialog 
  extends DialogFragment 
  implements OnEditorActionListener {

  private static final 
    String DIALOG_TAG = "editDialog";
  private static final 
    String EXTRA_INPUT_TEXT = "message";
  private static final 
    String EXTRA_TITLE = "inputText";

  private EditText mEditText;

  // Sua Activity deve implementar essa Interface
  public interface EditNameDialogListener {
    void onFinishEditDialog(String inputText);
  }

  public static EditNameDialog newInstance(
    String title, String inputText) {

    Bundle bundle = new Bundle();
    bundle.putString(EXTRA_TITLE, title);
    bundle.putString(EXTRA_INPUT_TEXT, inputText);

    EditNameDialog dialog = new EditNameDialog();
    dialog.setArguments(bundle);
    return dialog;
  }

  @Override
  public View onCreateView(LayoutInflater inflater, 
    ViewGroup container, Bundle savedInstanceState){

    String title = getArguments()
      .getString(EXTRA_TITLE);
    String inputText = getArguments()
      .getString(EXTRA_INPUT_TEXT);

    View view = inflater.inflate(
      R.layout.activity_adicionar_caderno, container);

    TextView txtView = (TextView)
      view.findViewById(R.id.edtNomeCaderno);
    txtView.setText(inputText);
    // Exibe o teclado virtual ao exibir o Dialog
    getDialog().getWindow().setSoftInputMode(
      WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

    getDialog().setTitle(title);

    mEditText = (EditText)
      view.findViewById(R.id.edtNomeCaderno);
    mEditText.requestFocus();
    // Listener para quando clicarmos 
    // em 'Done' no teclado
    mEditText.setOnEditorActionListener(this);

    return view;
  }

  @Override
  public boolean onEditorAction(TextView v, 
    int actionId, KeyEvent event) {
    // Se clicou em 'Done'
    if (EditorInfo.IME_ACTION_DONE == actionId) {
      // Notifique a Activity
      EditNameDialogListener activity = 
        (EditNameDialogListener) getActivity();
      activity.onFinishEditDialog(
        mEditText.getText().toString());
      // Feche o dialog
      dismiss();
      return true;
    }
    return false;
  }

  public void openDialog(FragmentManager fm) {
    if (fm.findFragmentByTag(DIALOG_TAG) == null) {
      show(fm, DIALOG_TAG);
    }
  }
}
