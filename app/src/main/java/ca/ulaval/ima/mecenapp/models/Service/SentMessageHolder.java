package ca.ulaval.ima.mecenapp.models;

import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import ca.ulaval.ima.mecenapp.R;
import okhttp3.internal.Util;

import static android.text.format.DateUtils.formatDateTime;

class SentMessageHolder extends RecyclerView.ViewHolder {
    TextView messageText, timeText;

    SentMessageHolder(View itemView) {
        super(itemView);
        messageText = (TextView) itemView.findViewById(R.id.text_message_body);
        timeText = (TextView) itemView.findViewById(R.id.text_message_time);

    }

    void bind(Message message) {
        messageText.setText(message.getContent());

        // Format the stored timestamp into a readable String using method.
        timeText.setText((CharSequence) message.getCreatedDate());

    }
}