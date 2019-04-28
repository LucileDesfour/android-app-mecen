package ca.ulaval.ima.mecenapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import ca.ulaval.ima.mecenapp.R;
import ca.ulaval.ima.mecenapp.models.Messages;
import okhttp3.internal.Util;

import static android.text.format.DateUtils.formatDateTime;

class ReceivedMessageHolder extends RecyclerView.ViewHolder {
    TextView messageText, timeText, nameText;
    ImageView profileImage;

    ReceivedMessageHolder(View itemView) {
        super(itemView);
        messageText = (TextView) itemView.findViewById(R.id.text_message_body);
        timeText = (TextView) itemView.findViewById(R.id.text_message_time);
        nameText = (TextView) itemView.findViewById(R.id.text_message_name);
        profileImage = (ImageView) itemView.findViewById(R.id.image_message_profile);
    }

    void bind(Messages.Message message) {
        messageText.setText(message.getContent());

        // Format the stored timestamp into a readable String using method.
        timeText.setText((CharSequence) message.getCreatedDate());
        nameText.setText(message.getUser().getEmail());

        // Insert the profile image from the URL into the ImageView.
        //Utils.displayRoundImageFromUrl(mContext, message.getUser().getImageUrl(), profileImage);
    }
}