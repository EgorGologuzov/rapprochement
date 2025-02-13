package com.nti.rapprochement.domain;

import android.content.Context;

import com.nti.rapprochement.domain.contracts.IGestureAnalyzer;
import com.nti.rapprochement.domain.implementations.FakeGestureAnalyzer;

public class Domain {
    public static IGestureAnalyzer getGestureAnalyzer(Context context) {
        return new FakeGestureAnalyzer(context);
    }
}
