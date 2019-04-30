package ca.ulaval.ima.mecenapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import ca.ulaval.ima.mecenapp.R;
import ca.ulaval.ima.mecenapp.models.Messages;

class ReceivedMessageHolder extends RecyclerView.ViewHolder {
    TextView messageText, timeText, nameText;
    ImageView profileImage;

    ReceivedMessageHolder(View itemView) {
        super(itemView);
        messageText = (TextView) itemView.findViewById(R.id.text_message_body);
        nameText = (TextView) itemView.findViewById(R.id.text_message_name);
    }

    void bind(Messages.Message message) {
        messageText.setText(message.getContent());

        // Format the stored timestamp into a readable String using method.
        nameText.setText(message.getUser().getFirstName());

        // Insert the profile image from the URL into the ImageView.
        //Utils.displayRoundImageFromUrl(mContext, message.getUser().getImageUrl(), profileImage);
    }
}