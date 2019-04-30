package ca.ulaval.ima.mecenapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import ca.ulaval.ima.mecenapp.R;
import ca.ulaval.ima.mecenapp.models.Messages;

class SentMessageHolder extends RecyclerView.ViewHolder {
    TextView messageText, timeText;

    SentMessageHolder(View itemView) {
        super(itemView);
        messageText = (TextView) itemView.findViewById(R.id.text_message_body);
    }

    void bind(Messages.Message message) {
        messageText.setText(message.getContent());
    }
}