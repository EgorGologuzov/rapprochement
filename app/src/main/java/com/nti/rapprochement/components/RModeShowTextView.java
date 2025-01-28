package com.nti.rapprochement.components;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nti.rapprochement.App;
import com.nti.rapprochement.MainActivity;
import com.nti.rapprochement.R;
import com.nti.rapprochement.data.Settings;
import com.nti.rapprochement.models.RecordMultiMode;
import com.nti.rapprochement.utils.Event;
import com.nti.rapprochement.utils.ViewsUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class RModeShowTextView {
    public static View create(ViewGroup parent, RecordMultiMode model) {
        View view = ViewsUtils.createView(R.layout.rmode_show_text, parent);
        ImageView sourceTypeView = view.findViewById(R.id.sourceTypeView);
        TextView datetimeView = view.findViewById(R.id.datetimeView);
        TextView recordTextView = view.findViewById(R.id.recordTextView);
        ActionButton toGestureButton = view.findViewById(R.id.toGestureButton);
        ActionButton toSoundButton = view.findViewById(R.id.toSoundButton);
        ActionButton optionsButton = view.findViewById(R.id.optionsButton);

        RecordMultiMode.SourceType sourceType = model.getSourceType();
        if (sourceType == RecordMultiMode.SourceType.Gesture) {
            sourceTypeView.setImageResource(R.drawable.source_type_gesture);
        } else if (sourceType == RecordMultiMode.SourceType.Sound) {
            sourceTypeView.setImageResource(R.drawable.source_type_sound);
        } else if (sourceType == RecordMultiMode.SourceType.Text) {
            sourceTypeView.setImageResource(R.drawable.source_type_text);
        }

        datetimeView.setText(formatTime(model.getCreateTime()));

        recordTextView.setText(model.getText());

        return view;
    }

    public static String formatTime(Date date) {
        return new SimpleDateFormat(
                "d MMM HH:mm",
                Locale.forLanguageTag(Settings.getLocale())
        ).format(date);
    }

//    public static String formatTime(Date date) {
//        Date now = new Date();
//        long diffInMillis = now.getTime() - date.getTime();
//
//        long seconds = TimeUnit.MILLISECONDS.toSeconds(diffInMillis);
//        long minutes = TimeUnit.MILLISECONDS.toMinutes(diffInMillis);
//        long hours = TimeUnit.MILLISECONDS.toHours(diffInMillis);
//        long days = TimeUnit.MILLISECONDS.toDays(diffInMillis);
//
//        SimpleDateFormat dateFormat;
//
//        if (days == 0) {
//            if (hours == 0) {
//                if (minutes == 0) {
//                    return seconds + " сек. назад";
//                } else {
//                    return minutes + " мин. назад";
//                }
//            } else if (hours == 1) {
//                return "час назад";
//            } else {
//                return hours + " ч. назад";
//            }
//        } else if (days == 1) {
//            return "вчера " + new SimpleDateFormat("HH:mm", Locale.forLanguageTag("ru")).format(date);
//        } else if (days < 7) {
//            return days + " д. назад";
//        } else {
//            dateFormat = new SimpleDateFormat("d MMM HH:mm", Locale.forLanguageTag("ru"));
//            return dateFormat.format(date);
//        }
//    }
}
