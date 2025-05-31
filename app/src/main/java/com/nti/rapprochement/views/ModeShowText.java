package com.nti.rapprochement.views;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nti.rapprochement.R;
import com.nti.rapprochement.data.Settings;
import com.nti.rapprochement.utils.Convert;
import com.nti.rapprochement.utils.ViewsUtils;
import com.nti.rapprochement.viewmodels.RecordCallVM;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ModeShowText extends RecordCallVM.Mode {
    @Override
    public View createInnerView(RecordCallVM.CreateArgs args) {
        ViewGroup parent = args.parent;
        RecordCallVM vm = args.vm;

        View view = ViewsUtils.createView(R.layout.mode_show_text, parent);
        ImageView sourceTypeView = view.findViewById(R.id.sourceTypeView);
        TextView datetimeView = view.findViewById(R.id.datetimeView);
        TextView recordTextView = view.findViewById(R.id.recordTextView);
        ActionButton toGestureButton = view.findViewById(R.id.toGestureButton);
        ActionButton toSoundButton = view.findViewById(R.id.toSoundButton);
        ActionButton optionsButton = view.findViewById(R.id.optionsButton);

        sourceTypeView.setImageResource(Convert.recordCallStatusToDrawableId(vm.getStatus()));

        datetimeView.setText(formatTime(vm.getCreationTime()));

        recordTextView.setText(vm.getText());

        toGestureButton.setOnClickListener(v -> {
            vm.activateMode(new ModeShowGesture());
        });

        toSoundButton.setOnClickListener(v -> {
            vm.activateMode(new ModeShowSound());
        });

        return view;
    }

    @Override
    public View createPanelView(RecordCallVM.CreateArgs args) {
        return null;
    }

    @Override
    public boolean hasPanel() {
        return false;
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
